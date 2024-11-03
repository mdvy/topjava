package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(value = DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {
    @Test
    public void getByIdAndUserIdWithUserTest() {
        Meal actualMeal = service.getByIdAndUserIdWithUser(MEAL1_ID, USER_ID);
        MEAL_MATCHER.assertMatch(actualMeal, meal1);
        User actualUser = actualMeal.getUser();
        USER_MATCHER.assertMatch(actualUser, user);
    }
}