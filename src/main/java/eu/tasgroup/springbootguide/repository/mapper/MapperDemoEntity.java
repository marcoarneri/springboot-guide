package eu.tasgroup.springbootguide.repository.mapper;

import eu.tasgroup.springbootguide.controller.model.DemoRequest;
import eu.tasgroup.springbootguide.controller.model.DemoResponse;
import eu.tasgroup.springbootguide.repository.model.DemoEntity;
import eu.tasgroup.springbootguide.service.model.DemoRequestDto;
import eu.tasgroup.springbootguide.service.model.DemoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MapperDemoEntity {

    public abstract DemoEntity toEntity(DemoRequestDto requestDto);

    public abstract DemoResponseDto toResponseDto(DemoEntity entity);

}
