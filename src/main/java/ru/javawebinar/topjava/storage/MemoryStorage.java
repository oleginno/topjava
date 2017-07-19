package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Oleh Savych
 * 15.07.17
 */
public class MemoryStorage implements IStorage {

    private static MemoryStorage memoryStorage;

    private final Map<String, Meal> meals;

    public static MemoryStorage get() {
        if (memoryStorage == null) {
            memoryStorage = new MemoryStorage();
        }
        return memoryStorage;
    }

    private MemoryStorage() {
        meals = new ConcurrentHashMap<>(128);
        Random random = new Random(47);

        for (int i = 1; i < 101; i++) {
            int day = 1 + random.nextInt(31);
            int hour = 1 + random.nextInt(23);
            int minute = 1 + random.nextInt(59);
            int second = 1 + random.nextInt(59);

            //String id = UUID.randomUUID().toString();
            meals.put(Meal.getAndIncrementCounter(), new Meal(LocalDateTime.of(
                    2017, Month.JULY, day, hour, minute, second), "Meal - " + i,
                    (int) (Math.random() * (400 - 35)) + 35));
        }
    }


    @Override
    public void clear() {
        synchronized (meals) {
            meals.clear();
        }
    }

    @Override
    public void save(Meal meal) {
        synchronized (meals) {
            meals.putIfAbsent(Meal.getAndIncrementCounter(), meal);
        }
    }

    @Override
    public void update(String id, Meal meal) {
        synchronized (meals) {
            meals.replace(id, meal);
        }
    }

    @Override
    public Meal load(String id) {
        synchronized (meals) {
            return meals.get(id);
        }
    }

    @Override
    public void delete(String id) {
        synchronized (meals) {
            meals.remove(id);
        }
    }

    public boolean exist(String id) {
        synchronized (meals) {
            return meals.containsKey(id);
        }
    }

    public List<Meal> getList() {
        synchronized (meals) {
            List<Meal> list = new ArrayList<>();
            list.addAll(meals.values());
            return list;
        }
    }

    public int size() {
        synchronized (meals) {
            return meals.size();
        }
    }
}