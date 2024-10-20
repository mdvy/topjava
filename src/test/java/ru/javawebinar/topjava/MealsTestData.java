package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealsTestData {
    public static final int NOT_EXISTING_MEAL_ID = 100;
    public static final int START_MEALS_ID = START_SEQ + 3;
    public static final LocalDate DATE1 = LocalDate.of(2024, 1, 1);
    public static final LocalDate DATE2 = LocalDate.of(2024, 1, 2);
    public static final LocalTime TIME1 = LocalTime.of(8, 0, 0);
    public static final LocalTime TIME2 = LocalTime.of(13, 0, 0);
    public static final LocalTime TIME3 = LocalTime.of(18, 0, 0);

    public static final Meal USER_MEAL1 = new Meal(START_MEALS_ID, DATE1.atTime(TIME1), "завтрак USER", 600);
    public static final Meal USER_MEAL2 = new Meal(START_MEALS_ID + 1, DATE1.atTime(TIME2), "обед USER", 800);
    public static final Meal USER_MEAL3 = new Meal(START_MEALS_ID + 2, DATE1.atTime(TIME3), "ужин USER", 700);
    public static final Meal USER_MEAL4 = new Meal(START_MEALS_ID + 3, DATE2.atTime(TIME1), "завтрак USER", 600);
    public static final Meal USER_MEAL5 = new Meal(START_MEALS_ID + 4, DATE2.atTime(TIME2), "обед USER", 900);
    public static final Meal USER_MEAL6 = new Meal(START_MEALS_ID + 5, DATE2.atTime(TIME3), "ужин USER", 800);

    public static final Meal ADMIN_MEAL1 = new Meal(START_MEALS_ID + 6, DATE1.atTime(TIME1), "завтрак ADMIN", 300);
    public static final Meal ADMIN_MEAL2 = new Meal(START_MEALS_ID + 7, DATE1.atTime(TIME2), "обед ADMIN", 500);
    public static final Meal ADMIN_MEAL3 = new Meal(START_MEALS_ID + 8, DATE1.atTime(TIME3), "ужин ADMIN", 1200);
    public static final Meal ADMIN_MEAL4 = new Meal(START_MEALS_ID + 9, DATE2.atTime(TIME1), "завтрак ADMIN", 400);
    public static final Meal ADMIN_MEAL5 = new Meal(START_MEALS_ID + 10, DATE2.atTime(TIME2), "обед ADMIN", 600);
    public static final Meal ADMIN_MEAL6 = new Meal(START_MEALS_ID + 11, DATE2.atTime(TIME3), "ужин ADMIN", 1300);
    public static final List<Meal> USER_MEALS = Arrays.asList(USER_MEAL6, USER_MEAL5, USER_MEAL4, USER_MEAL3, USER_MEAL2, USER_MEAL1);
    public static final List<Meal> ADMIN_MEALS_DATE1 = Arrays.asList(ADMIN_MEAL3, ADMIN_MEAL2, ADMIN_MEAL1);
    public static final List<Meal> ADMIN_MEALS_DATE2 = Arrays.asList(ADMIN_MEAL6, ADMIN_MEAL5, ADMIN_MEAL4);

    public static Meal getUpdated() {
        Meal meal = USER_MEAL1;
        return new Meal(meal.getId(), meal.getDateTime(), "ужин USER обновлен", 100);
    }

    public static void assertMatch(Meal actualMeal, Meal expectedMeal) {
        assertThat(actualMeal)
                .usingRecursiveComparison()
                .isEqualTo(expectedMeal);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    public static Meal getCreated() {
        return new Meal(LocalDateTime.of(2024, 1, 1, 12, 0), "полдник USER", 300);
    }
}
