package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(value = JDBC)
public class JdbcMealServiceTest extends MealServiceTest {
}