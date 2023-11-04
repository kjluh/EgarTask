package egar.egartask.mapper;

import egar.egartask.dto.DepartmentDto;
import egar.egartask.entites.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentDto toDepartmentDto(Department department);
}
