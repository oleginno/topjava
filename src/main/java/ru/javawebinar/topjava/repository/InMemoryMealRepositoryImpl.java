package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>(32);

    private AtomicInteger counter = new AtomicInteger(0);

    {
        Random random = new Random(29);
        for (int i = 1; i < 31; i++) {
            int day = 1 + random.nextInt(31);
            int hour = 1 + random.nextInt(23);
            int minute = 1 + random.nextInt(59);
            int second = 1 + random.nextInt(59);

            Integer userId = i % 2 != 0 ? 1 : 2;
            save(new Meal(null, userId, LocalDateTime.of(
                    2017, Month.JULY, day, hour, minute, second), "Meal-" + i,
                    (int) (Math.random() * (500 - 25)) + 25));
        }

    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.get(meal.getUserId()).put(meal.getId(), meal);
        log.info("saving meal: {}, {} ...", meal.getId(), meal.getDescription());
        return meal;
    }

    @Override
    public void delete(int userId, int id) {
        repository.get(userId).remove(id);
        log.info("deleting meal: {} ...", id);
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get meal: {}", id);
        return repository.get(userId).get(id);
    }

    @Override
    public void update(int userId, int id, Meal meal) {
        repository.get(userId).replace(id, meal);
    }

    @Override
    public Collection<Meal> getAll() {
        log.info("get all meals");
        return repository.values().stream()
                .flatMap(m -> m.values().stream())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllByUser(int userId) {
        log.info("get all meals of user with id: {}", userId);
        return new ArrayList<>(repository.get(userId).values());
    }
}

