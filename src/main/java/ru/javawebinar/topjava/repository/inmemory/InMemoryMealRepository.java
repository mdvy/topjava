package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
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
            log.info("save new meal {}", meal);
            return repository.putIfAbsent(meal.getId(), meal) == null ? meal : null;
        }
        log.info("update meal {}", meal);
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> {
            if (oldMeal.getUserId() == userId) {
                meal.setUserId(userId);
                return meal;
            } else return null;
        });
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete meal with id={} of user with id={}", id, userId);
        return repository.computeIfPresent(id, (oldId, oldMeal) -> {
            if (oldMeal.getUserId() == userId) {
                return null;
            }
            return oldMeal;
        }) == null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal with id={} of user with id={}", id, userId);
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId && meal.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> filterByDate(LocalDate startDate, LocalDate endDate, int userId){
        return getAll(userId)
                .stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}

