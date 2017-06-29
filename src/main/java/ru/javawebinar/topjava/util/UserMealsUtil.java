package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;


/**
 * GKislin
 * 31.05.2015.
 */

public class UserMealsUtil {

    public static void main(String[] args) {

        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0),
                        "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0),
                        "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0),
                        "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0),
                        "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0),
                        "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0),
                        "Ужин", 510)
        );

        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0),
                LocalTime.of(18,0), 1900));
    }


    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                    LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDays = new HashMap<>();
        List<UserMealWithExceed> exceededCaloriesMeals = new ArrayList<>();
        for (UserMeal meal: mealList) {
            caloriesPerDays.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }
        for (Map.Entry<LocalDate, Integer> me: caloriesPerDays.entrySet()) {
            if (me.getValue() > caloriesPerDay) {
                for (UserMeal meal: mealList) {
                    if (meal.getDateTime().toLocalDate().isEqual(me.getKey())
                            && TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                        exceededCaloriesMeals.add(createMealWithExceed(meal, true));
                    }
                }
            }
        }
        return exceededCaloriesMeals;
    }

    private static UserMealWithExceed createMealWithExceed(UserMeal userMeal, boolean exceeded) {
        return new UserMealWithExceed(
                userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceeded);
    }


//    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
//                                                                    LocalTime endTime, int caloriesPerDay) {
//
//        Map<LocalDate, ArrayList<UserMeal>> inDuration = new HashMap<>();
//        List<UserMealWithExceed> exceededCaloriesMeals = new ArrayList<>();
//
//        for (UserMeal meal: mealList) {
//            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
//
//                LocalDate currentMealDate = meal.getDateTime().toLocalDate();
//                if(!inDuration.containsKey(currentMealDate)) {
//                    inDuration.put(currentMealDate, new ArrayList<>());
//                }
//                inDuration.get(currentMealDate).add(meal);
//            }
//        }
//
//        for (Map.Entry<LocalDate, ArrayList<UserMeal>> me: inDuration.entrySet()) {
//            int sumCalories = 0;
//            for (UserMeal meal: me.getValue()) {
//                sumCalories += meal.getCalories();
//            }
//            if (sumCalories > caloriesPerDay) {
//                for (UserMeal meal: me.getValue()) {
//                    exceededCaloriesMeals.add(new UserMealWithExceed(
//                            meal.getDateTime(), meal.getDescription(), meal.getCalories(), true));
//                }
//            }
//        }
//        return exceededCaloriesMeals;
//    }
}
