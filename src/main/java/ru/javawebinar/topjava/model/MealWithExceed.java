package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;


public class MealWithExceed implements Comparable<MealWithExceed> {

    private final String id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public MealWithExceed(String id, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }


    public final String getId() {
        return id;
    }

    public final LocalDateTime getDateTime() {
        return dateTime;
    }

    public final String getDescription() {
        return description;
    }

    public final int getCalories() {
        return calories;
    }

    public final boolean isExceed() {
        return exceed;
    }

    public final String getFormattedDateTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yy,  (HH:mm)"));
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
