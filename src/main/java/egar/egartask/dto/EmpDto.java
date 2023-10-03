package egar.egartask.dto;

import egar.egartask.entites.PostEmployee;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpDto {

    private String name;
    private String family;
    private String secondName;
    private String postEmployeeName;
    private LocalDate hiringDate;
    private String departmentName;
}
