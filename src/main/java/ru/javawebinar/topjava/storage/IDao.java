package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

/**
 * Oleh Savych
 * 15.07.17
 */
public interface IDao {

    void clear();

    void save(Meal meal);

    void update(String id, Meal meal);

    Meal load(String id);

    void delete(String id);
}
