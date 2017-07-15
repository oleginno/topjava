package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;


public class MealsUtil {

    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> meals, /*LocalTime startTime,
                                                               LocalTime endTime,*/ int caloriesPerDay) {
        return meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate())).values().stream()
                .map(mealList -> {
                    boolean exceeded = mealList.stream().mapToInt(Meal::getCalories).sum() > caloriesPerDay;
                    return mealList.stream()
                            //.filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                            .map(meal -> createWithExceed(meal, exceeded));
                })
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    private static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getDateTime(), meal.getDescription(),
                meal.getCalories(), exceeded);
    }
}