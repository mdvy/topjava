package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    List<Meal> findAll();
    Meal create(Meal meal);
    void deleteById(int id);
    Meal findById(int id);
    Meal update(Meal meal);
}