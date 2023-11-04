package egar.egartask.mapper;

import egar.egartask.dto.NotWorkingDaysDto;
import egar.egartask.entites.NotWorkingDays;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotWorkingDaysMapper {
    @Mapping(source = "employee.name", target = "employeeName")
    @Mapping(source = "employee.secondName", target = "employeeSecondName")
    NotWorkingDaysDto toDto(NotWorkingDays notWorkingDays);
}
