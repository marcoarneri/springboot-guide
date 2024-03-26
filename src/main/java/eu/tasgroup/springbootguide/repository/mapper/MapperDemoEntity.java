package eu.tasgroup.springbootguide.repository.mapper;

import eu.tasgroup.springbootguide.repository.model.DemoEntity;
import eu.tasgroup.springbootguide.service.model.DemoGetResponseDto;
import eu.tasgroup.springbootguide.service.model.DemoRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MapperDemoEntity {

    public abstract DemoEntity toEntity(DemoRequestDto requestDto);

    public abstract DemoGetResponseDto toGetResponseDto(DemoEntity entity);

}
