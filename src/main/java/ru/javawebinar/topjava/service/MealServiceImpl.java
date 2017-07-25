package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;


@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int userId, int id) {
        repository.delete(userId, id);
    }

    @Override
    public Meal get(int userId, int id) {
        return repository.get(userId, id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    @Override
    public Collection<Meal> getAllByUser(int userId) {
        return repository.getAllByUser(userId);
    }

    @Override
    public void update(int userId, int id, Meal meal) {
        repository.update(userId, id, meal);
    }
}