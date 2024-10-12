package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            return repository.putIfAbsent(meal.getId(), meal) == null ? meal : null;
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> {
            if (oldMeal.getUserId().equals(userId)) {
                meal.setUserId(userId);
                return meal;
            } else return null;
        });
    }

    @Override
    public boolean delete(int id, Integer userId) {
        return repository.computeIfPresent(id, (oldId, oldMeal) -> {
            if (oldMeal.getUserId().equals(userId)) {
                return null;
            }
            return oldMeal;
        }) == null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        return repository.containsKey(id) && repository.get(id).getUserId().equals(userId) ? repository.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

