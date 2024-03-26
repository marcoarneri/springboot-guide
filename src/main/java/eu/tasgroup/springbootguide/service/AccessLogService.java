package eu.tasgroup.springbootguide.service;

import eu.tasgroup.springbootguide.repository.AccessLogRepository;
import eu.tasgroup.springbootguide.repository.model.AccessLogEntity;
import eu.tasgroup.springbootguide.service.dto.HttpServerRequestDto;
import eu.tasgroup.springbootguide.service.dto.HttpServerResponseDto;
import eu.tasgroup.springbootguide.service.mapper.MapperEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AccessLogService {

  private final MapperEntity mapperEntity;
  private final AccessLogRepository accessLogsRepository;

  @Transactional
  public void save(HttpServerRequestDto httpServerRequestDto) {
    log.info("SERVICE SAVE SERVER REQ {}", httpServerRequestDto.toString());
    AccessLogEntity accessLogEntity =
        mapperEntity.httpServerRequestDtoToHttpLogEntity(httpServerRequestDto);
//    accessLogEntity.setCallerId(StringUtils.abbreviate(accessLogEntity.getCallerId(),255));
//    accessLogEntity.setUriWithQueryString(StringUtils.abbreviate(accessLogEntity.getUriWithQueryString(),255));
    accessLogsRepository.save(accessLogEntity);
  }

  @Transactional
  public void save(HttpServerResponseDto httpServerResponseDto) {
    log.info("SERVICE SAVE SERVER RES {}", httpServerResponseDto.toString());
    AccessLogEntity accessLogEntity =
        mapperEntity.httpServerResponseDtoToHttpLogEntity(httpServerResponseDto);
//    accessLogEntity.setCallerId(StringUtils.abbreviate(accessLogEntity.getCallerId(),255));
//    accessLogEntity.setUriWithQueryString(StringUtils.abbreviate(accessLogEntity.getUriWithQueryString(),255));
    accessLogsRepository.save(accessLogEntity);
  }
}
