package egar.egartask.dto.mapper;

import egar.egartask.dto.EmpDto;
import egar.egartask.dto.WorkTimeDto;
import egar.egartask.entites.Employee;
import egar.egartask.entites.WorkTime;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WorkTimeMapper {

    WorkTimeDto toDto (WorkTime workTime);

    WorkTime toWorkTime (WorkTimeDto workTimeDto);
}
