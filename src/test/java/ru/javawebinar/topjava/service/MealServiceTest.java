package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealsTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void get() {
        Meal actualMeal = service.get(START_MEALS_ID, USER_ID);
        assertMatch(actualMeal, USER_MEAL1);
    }

    @Test
    public void getByNotExistingIdShouldThrowNotFoundException() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXISTING_ID, USER_ID));
    }

    @Test
    public void delete() {
        int mealId = USER_MEAL1.getId();
        service.delete(mealId, USER_ID);
        Assert.assertNull(repository.get(mealId, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> betweenInclusiveMeals = service.getBetweenInclusive(DATE_1, DATE_1, ADMIN_ID);
        assertMatch(betweenInclusiveMeals, ADMIN_MEALS_DATE1);
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
    public void updateWithWrongUserShouldThrowNotFoundException(){
        assertThrows(NotFoundException.class, () -> service.update(USER_MEAL1, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal newMeal = getCreated();
        Meal storedMeal = service.create(newMeal, USER_ID);
        newMeal.setId(storedMeal.getId());
        assertMatch(storedMeal, newMeal);
    }
}