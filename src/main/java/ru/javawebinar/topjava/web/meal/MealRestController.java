package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.List;

@Controller
public class MealRestController {
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

//    public Meal create(Meal meal) {
//        return service.create(meal);
//    }

    public void delete(int id, Integer userId) {
        service.delete(id, userId);
    }

    public Meal get(int id, Integer userId) {
        return service.get(id, userId);
    }

    public List<Meal> getAll(int userId) {
        return service.getAll(userId);
    }

    public void update(Meal meal, int userId) {
        service.update(meal, userId);
    }
}