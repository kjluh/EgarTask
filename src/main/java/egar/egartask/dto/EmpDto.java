package egar.egartask.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpDto {

    private String name;

    private String family;

    private String secondName;

    private LocalDate hiringDate;

    private boolean working;
}
