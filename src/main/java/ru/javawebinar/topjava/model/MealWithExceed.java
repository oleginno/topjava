package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.Objects;


public class MealWithExceed implements Comparable<MealWithExceed> {

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public MealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealWithExceed that = (MealWithExceed) o;
        return getCalories() == that.getCalories() &&
                Objects.equals(getDateTime(), that.getDateTime()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDateTime(), getDescription());
    }

    @Override
    public String toString() {
        return "{" + dateTime + " " + description + " " + calories + " " + isExceed() + "}";
    }

    @Override
    public int compareTo(MealWithExceed o) {
        return this.getDescription().compareTo(o.getDescription());
    }
}
