package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final MealRepository mealRepository = new MealRepositoryImpl();
    public static final int CALORIES_PER_DAY = 2000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("get request to {}", request.getContextPath());

        String forwardTo;
        switch (request.getRequestURI().substring(request.getContextPath().length())) {
            case "/meals/new":
                request.setAttribute("meals", mealRepository.findAll());
                forwardTo = "/edit-meal.jsp";
                break;
            case "/meals/edit":
                String id = request.getParameter("id");
                request.setAttribute("meal", mealRepository.findById(Integer.parseInt(id)));
                forwardTo = "/edit-meal.jsp";
                break;
            default:
                List<MealTo> mealTos = MealsUtil.filteredByStreams(mealRepository.findAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
                request.setAttribute("meals", mealTos);
                forwardTo = "/meals.jsp";
        }
        request.getRequestDispatcher(forwardTo).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("post request to {}", request.getRequestURI());

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date_time"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String id = request.getParameter("id");
        if (id.isEmpty()) {
            mealRepository.create(new Meal(dateTime, description, calories));
        } else {
            int idValue = Integer.parseInt(id);
            mealRepository.update(idValue, new Meal(idValue, dateTime, description, calories));
        }
        response.sendRedirect(request.getContextPath() + "/meals");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("delete request to {}", request.getRequestURI());

        mealRepository.deleteById(Integer.parseInt(request.getParameter("id")));
    }
}
