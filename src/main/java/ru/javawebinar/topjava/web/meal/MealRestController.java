package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        log.info("create {}", meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete meal with id={}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        log.info("get meal with id={}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public List<MealTo> getAll() {
        log.info("get all and return MealTo list");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        log.info("update meal with id={}", id);
        service.update(meal, SecurityUtil.authUserId());
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("apply filter {} - {} and {} - {}", startDate, endDate, startTime, endTime);
        List<Meal> filteredByDate = service.getFilteredByDate(startDate, endDate, SecurityUtil.authUserId());
        return MealsUtil.getFilteredTos(filteredByDate, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }
}