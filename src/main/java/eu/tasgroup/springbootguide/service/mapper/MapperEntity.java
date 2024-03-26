package eu.tasgroup.springbootguide.service.mapper;

import eu.tasgroup.springbootguide.repository.model.AccessLogEntity;
import eu.tasgroup.springbootguide.service.dto.HttpServerRequestDto;
import eu.tasgroup.springbootguide.service.dto.HttpServerResponseDto;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true),
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MapperEntity {

  @Mapping(target = "logType",
          expression = "java(eu.tasgroup.springbootguide.repository.model.AccessLogTypeEnum.HTTP_SERVER_RESPONSE)")
  @Mapping(target = "callerId",
          expression = "java(eu.tasgroup.springbootguide.util.MappingUtil.callerId())")
  @Mapping(target = "component",
          expression = "java(eu.tasgroup.springbootguide.controller.model.ComponentEnum.HTTP)")
  AccessLogEntity httpServerResponseDtoToHttpLogEntity(HttpServerResponseDto httpServerResponseDto);

  @Mapping(target = "logType",
          expression = "java(eu.tasgroup.springbootguide.repository.model.AccessLogTypeEnum.HTTP_SERVER_REQUEST)")
  @Mapping(target = "callerId",
          expression = "java(eu.tasgroup.springbootguide.util.MappingUtil.callerId())")
  @Mapping(target = "component",
          expression = "java(eu.tasgroup.springbootguide.controller.model.ComponentEnum.HTTP)")
  AccessLogEntity httpServerRequestDtoToHttpLogEntity(HttpServerRequestDto httpServerRequestDto);
}