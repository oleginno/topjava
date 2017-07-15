package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

/**
 * Oleh Savych
 * 15.07.17
 */
public interface IStorage {

    void clear();

    void save(Meal meal);

    void update(String uuid, Meal meal);

    Meal load(String uuid);

    void delete(String uuid);
}
