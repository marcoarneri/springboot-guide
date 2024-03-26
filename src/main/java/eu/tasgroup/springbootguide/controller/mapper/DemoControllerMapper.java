package eu.tasgroup.springbootguide.controller.mapper;

import eu.tasgroup.springbootguide.controller.model.FullResponse;
import eu.tasgroup.springbootguide.service.dto.FullResponseDto;
import eu.tasgroup.springbootguide.service.dto.ParamsDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DemoControllerMapper {
    ParamsDto toParamsDto(String iuv, String noticeId);

    FullResponse toFullResponse(FullResponseDto fullResponseDto);

}
