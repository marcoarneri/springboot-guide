package eu.tasgroup.springbootguide.repository.mapper;

import eu.tasgroup.springbootguide.repository.model.DemoEntity;
import eu.tasgroup.springbootguide.service.dto.DemoGetByIuvAndNoticeIdDto;
import eu.tasgroup.springbootguide.service.dto.DemoRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MapperDemoEntity {

    public abstract DemoEntity toEntity(DemoRequestDto requestDto);

    public abstract DemoGetByIuvAndNoticeIdDto toGetResponseDto(DemoEntity entity);

    public abstract List<DemoGetByIuvAndNoticeIdDto> toGetResponseDtoList(List<DemoEntity> entityList);

}
