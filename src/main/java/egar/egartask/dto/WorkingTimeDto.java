package egar.egartask.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkingTimeDto {
    private String name;
    private String family;
    private String secondName;
    private LocalDate start;
    private LocalDate end;
    private float workingTime;
}
