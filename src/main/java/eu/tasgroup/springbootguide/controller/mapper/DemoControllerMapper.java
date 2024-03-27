package eu.tasgroup.springbootguide.controller.mapper;

import eu.tasgroup.springbootguide.controller.model.DemoGetAllByLocation;
import eu.tasgroup.springbootguide.controller.model.DemoGetByIuvAndNoticeId;
import eu.tasgroup.springbootguide.service.dto.DemoGetAllByLocationDto;
import eu.tasgroup.springbootguide.service.dto.DemoGetByIuvAndNoticeIdDto;
import eu.tasgroup.springbootguide.service.dto.ParamsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DemoControllerMapper {
    ParamsDto toParamsDto(String iuv, String noticeId);

    DemoGetByIuvAndNoticeId toGetResponse(DemoGetByIuvAndNoticeIdDto responseDto);

    List<DemoGetByIuvAndNoticeId> toGetResponse(List<DemoGetByIuvAndNoticeIdDto> responseDto);
    @Mapping(target="responseList", source = "responseDto.responseDtoList")
    DemoGetAllByLocation toGetAllByLocationResponse(DemoGetAllByLocationDto responseDto);

}
