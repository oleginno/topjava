package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;


/**
 * GKislin
 * 31.05.2015.
 */

public class UserMealsUtil {

    private static List<UserMeal> mealListTest;

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
        //populateList();

        long start = new Date().getTime();
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0),
                LocalTime.of(19,0), 2000));
        System.out.println(new Date().getTime() - start);
    }

//    public static List<UserMealWithExceed>  getFilteredWithExceeded2(List<UserMeal> mealList, LocalTime startTime,
//                                                                    LocalTime endTime, int caloriesPerDay) {
//        Map<LocalDate, Integer> caloriesPerDays = new HashMap<>();
//        List<UserMealWithExceed> exceededCaloriesMeals = new ArrayList<>();
//
//        for (UserMeal meal: mealList) {
//            caloriesPerDays.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
//        }
//        for (Map.Entry<LocalDate, Integer> me: caloriesPerDays.entrySet()) {
//            if (me.getValue() > caloriesPerDay) {
//                for (UserMeal meal: mealList) {
//                    if (meal.getDateTime().toLocalDate().isEqual(me.getKey())
//                            && TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
//                        exceededCaloriesMeals.add(newUserMealWithExceed(meal, true));
//                    }
//                }
//            }
//        }
//        return exceededCaloriesMeals;
//    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                            LocalTime endTime, int caloriesPerDay) {
//        Map<LocalDate, Integer> caloriesPerAllDays = mealList.stream()
//                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
//                        Collectors.summingInt(UserMeal::getCalories)));
//        return mealList.stream()
//                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
//                .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
//                        caloriesPerAllDays.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
//                .collect(Collectors.toList());

        return mealList.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate()))
                .entrySet().stream().map(entry -> {
                            int sumOfCalories = entry.getValue().stream().mapToInt(UserMeal::getCalories).sum();
                            return entry.getValue().stream().filter(meal ->
                                    TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                                    .map(meal -> new UserMealWithExceed(meal.getDateTime(),
                                            meal.getDescription(), meal.getCalories(),
                                            sumOfCalories > caloriesPerDay))
                                    .collect(Collectors.toList());})
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }


    private static void printExceeded(List<UserMealWithExceed> userMealWithExceeds) {
        userMealWithExceeds.stream()
                .filter(meal -> !meal.isExceed())
                .forEach(System.out::println);
    }

    private static void populateList() {
        mealListTest = new ArrayList<>();
        for(int i = 0; i < 10000; i++) {
            Random random = new Random();
            int day = 1 + random.nextInt(30);
            mealListTest.add(new UserMeal(LocalDateTime.of(
                    2017, Month.JULY, day, 15, 5),
                    "Test " + i, (int) (Math.random() * (800 - 55)) + 55));
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    public static List<UserMealWithExceed>  getFilteredWithExceeded2(List<UserMeal> mealList, LocalTime startTime,
//                                                                    LocalTime endTime, int caloriesPerDay) {
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
