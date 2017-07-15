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

    private Map<String, Meal> meals;

    public static MemoryStorage get() {
        if (memoryStorage == null) {
            memoryStorage = new MemoryStorage();
        }
        return memoryStorage;
    }

    private MemoryStorage() {
        meals = new ConcurrentHashMap<>();
        Random random = new Random(47);
        
        for (int i = 0; i < 29; i++) {
            int day = 1 + random.nextInt(30);
            int hour = 1 + random.nextInt(23);
            int minute = 1 + random.nextInt(59);
            int second = 1 + random.nextInt(59);
            
            String id = UUID.randomUUID().toString();
            meals.put(id, new Meal(id, LocalDateTime.of(
                    2017, Month.JULY, day, hour, minute, second),"Test meal" + i,
                    (int) (Math.random() * (500 - 35)) + 35));
        }
    }


    @Override
    public void clear() {
        meals.clear();
    }

    @Override
    public void save(Meal meal) {
        meals.putIfAbsent(UUID.randomUUID().toString(), meal);
    }

    @Override
    public void update(String id, Meal meal) {
        meals.replace(id, meal);
    }

    @Override
    public Meal load(String id) {
        return meals.get(id);
    }

    @Override
    public void delete(String id) {
        meals.remove(id);
    }


    public boolean exist(String id) {
        return meals.containsKey(id);
    }

    public List<Meal> getList() {
        List<Meal> list = new ArrayList<>();
        list.addAll(meals.values());

        return list;
    }

    public int size() {
        return meals.size();
    }
}
