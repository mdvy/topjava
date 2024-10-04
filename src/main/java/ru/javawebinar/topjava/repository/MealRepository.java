package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealRepository {
    List<MealTo> findAll();
    void save(Meal meal);
    void deleteById(int id);
    Meal findById(int id);
    void update(Integer id, Meal meal);
}
