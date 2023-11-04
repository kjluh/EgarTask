package egar.egartask.mapper;

import egar.egartask.dto.PostEmployeeDto;
import egar.egartask.entites.PostEmployee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostEmployeeMapper {

    PostEmployeeDto toPostEmployeeDto(PostEmployee postEmployee);
}
