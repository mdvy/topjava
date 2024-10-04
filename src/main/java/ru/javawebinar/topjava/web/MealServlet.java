package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private final static Logger log = getLogger(MealServlet.class);
    private final static MealRepository mealRepository = new MealRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("get request to {}", request.getRequestURI());

        String forwardTo;
        switch (request.getRequestURI()) {
            case "/topjava/meals/new":
                request.setAttribute("meals", mealRepository.findAll());
                forwardTo = "/edit-meal.jsp";
                break;
            case "/topjava/meals/edit":
                String id = request.getParameter("id");
                request.setAttribute("meal", mealRepository.findById(Integer.parseInt(id)));
                forwardTo = "/edit-meal.jsp";
                break;
            default:
                request.setAttribute("meals", mealRepository.findAll());
                forwardTo = "/meals.jsp";
                break;
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
            mealRepository.save(new Meal(dateTime, description, calories));
        } else {
            int idValue = Integer.parseInt(id);
            mealRepository.update(idValue, new Meal(idValue, dateTime, description, calories));
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("delete request to {}", request.getRequestURI());

        mealRepository.deleteById(Integer.parseInt(request.getParameter("id")));
    }
}
