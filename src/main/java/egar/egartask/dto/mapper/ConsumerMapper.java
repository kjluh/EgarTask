package egar.egartask.dto.mapper;

import egar.egartask.dto.ConsumerDTO;
import egar.egartask.entites.Consumer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsumerMapper {
    Consumer toConsumer(ConsumerDTO consumerDTO);
    ConsumerDTO toDTO(Consumer consumer);
}
