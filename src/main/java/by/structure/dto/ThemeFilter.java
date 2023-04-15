package by.structure.dto;

import java.sql.Time;

public record ThemeFilter(
        String name,
        String description,
        Time duration,
        Long moduleId) {

}
