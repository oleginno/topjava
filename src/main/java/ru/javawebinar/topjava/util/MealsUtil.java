package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class MealsUtil {
//    public static final List<Meal> MEALS = Arrays.asList(
//            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
//            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
//            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
//            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
//            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
//            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
//    );
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;


    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> meals, /*LocalTime startTime,
                                                               LocalTime endTime,*/ int caloriesPerDay) {
        return meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate())).values().stream()
                .map(mealList -> {
                    boolean exceeded = mealList.stream().mapToInt(Meal::getCalories).sum() > caloriesPerDay;
                    return mealList.stream()
                            //.filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                            .map(meal -> createWithExceed(meal, exceeded));
                })
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getId(), meal.getUserId(), meal.getDateTime(),
                meal.getDescription(), meal.getCalories(), exceeded);
    }
}