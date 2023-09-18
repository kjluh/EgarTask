package egar.egartask.dto.mapper;

import egar.egartask.dto.EmpDto;
import egar.egartask.entites.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);
    EmpDto toDto (Employee employee);

    Employee toEmp (EmpDto empDto);
}
