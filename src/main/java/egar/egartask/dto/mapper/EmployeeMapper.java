package egar.egartask.dto.mapper;

import egar.egartask.dto.EmpDto;
import egar.egartask.dto.WorkingTimeDto;
import egar.egartask.entites.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);
    @Mapping(
            source = "department.departmentName",
            target = "departmentName"
    )
    EmpDto toDto (Employee employee);

    Employee toEmp (EmpDto empDto);

    WorkingTimeDto toWorkingTimeDto(Employee employee);
}
