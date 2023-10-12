package egar.egartask.mapper;

import egar.egartask.dto.EmpDto;
import egar.egartask.dto.WorkingTimeDto;
import egar.egartask.entites.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(source = "department.departmentName", target = "departmentName")
    @Mapping(source = "postEmployee.postName", target = "postEmployeeName")
    EmpDto toDto(Employee employee);

    Employee toEmp(EmpDto empDto);

    WorkingTimeDto toWorkingTimeDto(Employee employee);
}
