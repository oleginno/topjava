package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class MealsUtil {

    public static final int DEFAULT_CALORIES_PER_DAY = 750;

    public static List<MealWithExceed> getWithExceeded(Collection<Meal> meals, int caloriesPerDay) {
        return meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate())).values().stream()
                .map(mealList -> {
                    boolean exceeded = mealList.stream().mapToInt(Meal::getCalories).sum() > caloriesPerDay;
                    return mealList.stream()
                            .map(meal -> createWithExceed(meal, exceeded));
                })
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals, LocalTime startTime,
                                                                     LocalTime endTime, int caloriesPerDay) {
        return meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate())).values().stream()
                .map(mealList -> {
                    boolean exceeded = mealList.stream().mapToInt(Meal::getCalories).sum() > caloriesPerDay;
                    return mealList.stream()
                            .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(),
                                    startTime, endTime))
                            .map(meal -> createWithExceed(meal, exceeded));
                })
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals, LocalDate startDate,
                                                                     LocalDate endDate, int caloriesPerDay) {
        return meals.stream()
                //.peek(System.out::println)
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate())).values().stream()
                .map(mealList -> {
                    boolean exceeded = mealList.stream().mapToInt(Meal::getCalories).sum() > caloriesPerDay;
                    return mealList.stream()
                            .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalDate(),
                                    startDate, endDate))
                            .map(meal -> createWithExceed(meal, exceeded));
                })
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals,
                                                                         LocalDateTime startDateTime,
                                                                         LocalDateTime endDateTime,
                                                                         int caloriesPerDay) {
        return meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate())).values().stream()
                .map(mealList -> {
                    boolean exceeded = mealList.stream().mapToInt(Meal::getCalories).sum() > caloriesPerDay;
                    return mealList.stream()
                            .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(),
                                    startDateTime, endDateTime))
                            .map(meal -> createWithExceed(meal, exceeded));
                })
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    private static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getId(), meal.getUserId(), meal.getDateTime(),
                meal.getDescription(), meal.getCalories(), exceeded);
    }
}