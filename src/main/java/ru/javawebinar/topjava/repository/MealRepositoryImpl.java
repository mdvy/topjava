package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepositoryImpl implements MealRepository {
    public static final int CALORIES_PER_DAY = 2000;
    public final AtomicInteger id = new AtomicInteger();
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();

    public MealRepositoryImpl() {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        meals.forEach(this::save);
    }

    public synchronized List<MealTo> findAll() {
        return MealsUtil.filteredByStreams(
                new ArrayList<>(mealsMap.values()), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
    }

    public synchronized void save(Meal meal) {
        meal.setId(id.incrementAndGet());
        mealsMap.put(meal.getId(), meal);
    }

    public void deleteById(int id) {
        mealsMap.remove(id);
    }

    @Override
    public Meal findById(int id) {
        return mealsMap.get(id);
    }

    @Override
    public void update(Integer id, Meal meal) {
        mealsMap.replace(id, meal);
    }

    public Integer getId() {
        return id.incrementAndGet();
    }
}