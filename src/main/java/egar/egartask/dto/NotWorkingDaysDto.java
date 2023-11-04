package egar.egartask.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class NotWorkingDaysDto {
    private LocalDate startDate;
    private LocalDate finishDate;
    private String comment;
    private String employeeName;
    private String employeeSecondName;

}
