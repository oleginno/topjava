package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class MealsUtil {

//    public static void main(String[] args) {
//        List<Meal> mealList = Arrays.asList(
//                new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0),
//                        "Завтрак", 500),
//                new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0),
//                        "Обед", 1000),
//                new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0),
//                        "Ужин", 500),
//                new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0),
//                        "Завтрак", 1000),
//                new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0),
//                        "Обед", 500),
//                new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0),
//                        "Ужин", 510)
//        );
//
//        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0),
//                LocalTime.of(19,0), 1900));
//    }

    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> meals, LocalTime startTime,
                                                               LocalTime endTime, int caloriesPerDay) {
        return meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate())).values().stream()
                .map(mealList -> {
                            boolean exceeded = mealList.stream().mapToInt(Meal::getCalories).sum() > caloriesPerDay;
                            return mealList.stream().filter(meal ->
                                    TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                                    .map(meal -> new MealWithExceed(meal.getDateTime(), meal.getDescription(),
                                            meal.getCalories(), exceeded));})
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

//    private static void printExceeded(List<MealWithExceed> mealWithExceeds) {
//        mealWithExceeds.stream()
//                .filter(meal -> !meal.isExceed())
//                .forEach(System.out::println);
//    }
}
