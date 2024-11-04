package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(value = DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @Test
    public void getByIdWithMeals() {
        User actualUser = service.getByIdWithMeals(USER_ID);
        USER_MATCHER.assertMatch(actualUser, UserTestData.user);
        List<Meal> actualMeals = actualUser.getMeals();
        MEAL_MATCHER.assertMatch(actualMeals, MealTestData.meals);
    }

    @Test
    public void getByIdWithMealsNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByIdWithMeals(NOT_FOUND));
    }

    @Test
    public void getByIdWithMealsEmptyMeals() {
        User actualUser = service.getByIdWithMeals(GUEST_ID);
        USER_MATCHER.assertMatch(actualUser, guest);
        List<Meal> actualMeals = actualUser.getMeals();
        MEAL_MATCHER.assertMatch(actualMeals, Collections.emptyList());
    }
}