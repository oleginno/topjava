package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Oleh Savych
 * 15.07.17
 */
public class MemoryStorage implements IStorage {

    private static MemoryStorage memoryStorage;

    private Map<String, Meal> meals;

    public static MemoryStorage get(ServletContext servletContext) {
        if (memoryStorage == null) {
            memoryStorage = new MemoryStorage(servletContext);
        }
        return memoryStorage;
    }

    private MemoryStorage(ServletContext servletContext) {
        meals = new ConcurrentHashMap<>();
        for (int i = 0; i < 15; i++) {
//            Meal meal = new Meal();
//            meals.put(meal);
        }
    }


    @Override
    public void clear() {

    }

    @Override
    public void save(Meal meal) {

    }

    @Override
    public void update(Meal meal) {

    }

    @Override
    public Meal load(String id) {
        for (Meal meal : meals.values()) {
            if (meal.getId().equals(id)) {
                return meal;
            }
        }
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
