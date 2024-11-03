package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles(value = DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @Test
    public void getByIdWithMealsTest() {
        User actualUser = service.getByIdWithMeals(USER_ID);
        USER_MATCHER.assertMatch(actualUser, UserTestData.user);
        List<Meal> actualMeals = actualUser.getMeals();
        MEAL_MATCHER.assertMatch(actualMeals, MealTestData.meals);
    }
}