package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;


public interface MealService {

    Meal save(Meal meal);

    void delete(int userId, int id);

    Meal get(int userId, int id);

    void update(int userId, int id, Meal meal);

    Collection<Meal> getAll();

    Collection<Meal> getAllByUser(int userId);
}