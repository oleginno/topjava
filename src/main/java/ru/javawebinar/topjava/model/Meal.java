package ru.javawebinar.topjava.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;


public class Meal implements Serializable {
    static final long serialVersionUID = 1L;

    private final String id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(String uuid, LocalDateTime dateTime, String description, int calories) {
        this.id = uuid;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }


    public String getId() {
        return id;
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

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return getCalories() == meal.getCalories() &&
                Objects.equals(getId(), meal.getId()) &&
                Objects.equals(getDateTime(), meal.getDateTime()) &&
                Objects.equals(getDescription(), meal.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDateTime(), getDescription(), getCalories());
    }
}
