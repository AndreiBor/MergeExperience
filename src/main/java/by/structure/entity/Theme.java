package by.structure.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;


@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Theme {

    private Long id;
    private String name;
    private String description;
    private Duration duration;
    private Module module;

}
