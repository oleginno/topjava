package ru.javawebinar.topjava.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>(32);

    private AtomicInteger counter = new AtomicInteger(0);

    {
        //MealsUtil.MEALS.forEach(this::save);
        Random random = new Random(29);
        for (int i = 1; i < 31; i++) {
            int day = 1 + random.nextInt(31);
            int hour = 1 + random.nextInt(23);
            int minute = 1 + random.nextInt(59);
            int second = 1 + random.nextInt(59);

            Integer userId = i % 2 != 0 ? 1 : 2;
            save(new Meal(null, userId, LocalDateTime.of(
                    2017, Month.JULY, day, hour, minute, second), "Meal - " + i,
                    (int) (Math.random() * (500 - 25)) + 25));
        }

    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }
}

