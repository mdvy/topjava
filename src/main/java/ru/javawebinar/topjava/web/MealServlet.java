package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
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
    public static final int CALORIES_PER_DAY = 2000;
    private MealRepository mealRepository;

    @Override
    public void init() {
        mealRepository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("get request to {}", request.getRequestURI());

        String forwardTo;
        switch (request.getRequestURI().substring(request.getContextPath().length())) {
            case "/meals/new":
                forwardTo = "/editMeal.jsp";
                break;
            case "/meals/edit":
                String id = request.getParameter("id");
                log.debug("set attribute meal to request");
                request.setAttribute("meal", mealRepository.findById(Integer.parseInt(id)));
                forwardTo = "/editMeal.jsp";
                break;
            default:
                List<MealTo> mealTos = MealsUtil.filteredByStreams(mealRepository.findAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
                log.debug("set attribute meals to request");
                request.setAttribute("meals", mealTos);
                forwardTo = "/meals.jsp";
        }
        log.debug("forward to {}", forwardTo);
        request.getRequestDispatcher(forwardTo).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("post request to {}", request.getRequestURI());

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date_time"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String id = request.getParameter("id");
        if (id.isEmpty()) {
            log.info("create new meal");
            mealRepository.create(new Meal(dateTime, description, calories));
        } else {
            int idValue = Integer.parseInt(id);
            log.info("update meal with id = {}", id);
            mealRepository.update(new Meal(idValue, dateTime, description, calories));
        }
        String redirectTo = request.getContextPath() + "/meals";
        log.debug("redirect to {}", redirectTo);
        response.sendRedirect(redirectTo);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        log.debug("delete request to {}", request.getRequestURI());
        log.info("delete meal with id = {}", request.getParameter("id"));

        mealRepository.deleteById(Integer.parseInt(request.getParameter("id")));
    }
}
