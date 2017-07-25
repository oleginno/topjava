package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;


@Controller
public class MealRestController {

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        return service.save(meal);
    }

    public void remove(int userId, int id) {
        service.delete(userId, id);
    }

    public Meal get(int userId, int id) {
        return service.get(userId, id);
    }

    public void update(int userId, int id, Meal meal) {
        service.update(userId, id, meal);
    }

    public Collection<Meal> getAll() {
        return service.getAll();
    }

    public Collection<Meal> getAllByUser(int userId) {
        return service.getAllByUser(userId);
    }
}