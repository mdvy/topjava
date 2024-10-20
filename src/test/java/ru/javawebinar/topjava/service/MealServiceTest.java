package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealsTestData.assertMatch;
import static ru.javawebinar.topjava.MealsTestData.getUpdated;
import static ru.javawebinar.topjava.MealsTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal actualMeal = service.get(START_MEALS_ID, USER_ID);
        assertMatch(actualMeal, USER_MEAL1);
    }

    @Test
    public void getByNotExistingId() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXISTING_MEAL_ID, USER_ID));
    }

    @Test
    public void getAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL2.getId(), ADMIN_ID));
    }

    @Test
    public void delete() {
        int mealId = USER_MEAL1.getId();
        service.delete(mealId, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(mealId, USER_ID));
    }

    @Test
    public void deleteNotExistingMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_EXISTING_MEAL_ID, USER_ID));
    }

    @Test
    public void deleteAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(ADMIN_MEAL1.getId(), USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> betweenInclusiveMeals = service.getBetweenInclusive(DATE1, DATE1, ADMIN_ID);
        assertMatch(betweenInclusiveMeals, ADMIN_MEALS_DATE1);
    }

    @Test
    public void getBetweenInclusiveWithStartDateNull() {
        List<Meal> betweenInclusiveMeals = service.getBetweenInclusive(null, DATE1, ADMIN_ID);
        assertMatch(betweenInclusiveMeals, ADMIN_MEALS_DATE1);
    }

    @Test
    public void getBetweenInclusiveWithEndDateNull() {
        List<Meal> betweenInclusiveMeals = service.getBetweenInclusive(DATE2, null, ADMIN_ID);
        assertMatch(betweenInclusiveMeals, ADMIN_MEALS_DATE2);
    }

    @Test
    public void getBetweenInclusiveWithNullDates() {
        List<Meal> betweenInclusiveMeals = service.getBetweenInclusive(null, null, USER_ID);
        assertMatch(betweenInclusiveMeals, USER_MEALS);
    }

    @Test
    public void getBetweenInclusiveWithWrongDates() {
        List<Meal> betweenInclusiveMeals = service.getBetweenInclusive(DATE2, DATE1, USER_ID);
        assertEquals(betweenInclusiveMeals, Collections.emptyList());
    }

    @Test
    public void getAll() {
        List<Meal> actualMeals = service.getAll(USER_ID);
        assertMatch(actualMeals, USER_MEALS);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(START_MEALS_ID, USER_ID), updated);
    }

    @Test
    public void updateAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.update(USER_MEAL1, ADMIN_ID));
    }

    @Test
    public void updateWithNotExistingId() {
        Meal meal = getUpdated();
        meal.setId(NOT_EXISTING_MEAL_ID);
        assertThrows(NotFoundException.class, () -> service.update(meal, USER_ID));
    }

    @Test
    public void create() {
        Meal newMeal = getCreated();
        Meal storedMeal = service.create(newMeal, USER_ID);
        newMeal.setId(storedMeal.getId());
        assertMatch(storedMeal, newMeal);
    }

    @Test
    public void createWithNotExistingUser() {
        Meal newMeal = getCreated();
        assertThrows(DataAccessException.class, () -> service.create(newMeal, NOT_FOUND));
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(LocalDateTime.of(2024, 1, 1, 13, 0), "повторный обед USER", 1000), USER_ID));
    }
}