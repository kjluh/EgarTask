package egar.egartask.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class WorkTimeDto {

    private LocalTime comeTime;

    private LocalTime outTime;
}
