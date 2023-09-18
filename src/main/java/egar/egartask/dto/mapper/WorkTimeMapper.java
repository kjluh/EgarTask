package egar.egartask.dto.mapper;

import egar.egartask.dto.EmpDto;
import egar.egartask.dto.WorkTimeDto;
import egar.egartask.entites.Employee;
import egar.egartask.entites.WorkTime;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkTimeMapper {

    WorkTimeMapper mapper = Mappers.getMapper(WorkTimeMapper.class);
    WorkTimeDto toDto (WorkTime workTime);

    WorkTime toWorkTime (WorkTimeDto workTimeDto);
}
