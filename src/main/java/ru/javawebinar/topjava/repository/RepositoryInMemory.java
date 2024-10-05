package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RepositoryInMemory implements Repository {
    private final AtomicInteger id = new AtomicInteger();
    private final Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();

    public RepositoryInMemory() {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        meals.forEach(this::create);
    }

    public List<Meal> findAll() {
        return new ArrayList<>(mealsMap.values());
    }

    public Meal create(Meal meal) {
        meal.setId(id.incrementAndGet());
        return mealsMap.put(meal.getId(), meal);
    }

    public void deleteById(int id) {
        mealsMap.remove(id);
    }

    @Override
    public Meal findById(int id) {
        return mealsMap.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        return mealsMap.replace(meal.getId(), meal);
    }

    private Integer getId() {
        return id.incrementAndGet();
    }
}