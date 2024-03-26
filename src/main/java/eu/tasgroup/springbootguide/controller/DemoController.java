package eu.tasgroup.springbootguide.controller;

import eu.tasgroup.springbootguide.controller.mapper.DemoControllerMapper;
import eu.tasgroup.springbootguide.controller.model.DemoRequest;
import eu.tasgroup.springbootguide.controller.model.DemoResponse;
import eu.tasgroup.springbootguide.controller.model.FullResponse;
import eu.tasgroup.springbootguide.repository.DemoRepository;
import eu.tasgroup.springbootguide.repository.mapper.MapperDemoEntity;
import eu.tasgroup.springbootguide.repository.model.DemoEntity;
import eu.tasgroup.springbootguide.service.DemoService;
import eu.tasgroup.springbootguide.service.mapper.MapperDemoDto;
import eu.tasgroup.springbootguide.service.dto.DemoRequestDto;
import eu.tasgroup.springbootguide.service.dto.DemoResponseDto;
import eu.tasgroup.springbootguide.service.dto.FullResponseDto;
import eu.tasgroup.springbootguide.service.dto.ParamsDto;
import eu.tasgroup.springbootguide.util.DemoConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
@Validated
public class DemoController {

    private final DemoService demoService;
    private final MapperDemoDto mapperDemoDto;
    private final MapperDemoEntity mapperDemoEntity;
    private final DemoRepository repository;
    @Autowired
    DemoControllerMapper controllerMapper;

    @PostMapping(
            value = "/demo",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DemoResponse> demo(
            @Valid @RequestBody DemoRequest request) {
        log.info(
                "[demo] request body: [{}]",
                request);

        DemoRequestDto requestDto = mapperDemoDto.toRequestDto(request);

        DemoEntity entity = mapperDemoEntity.toEntity(requestDto);

        repository.save(entity);

        DemoResponseDto responseDto = demoService.callDemoService(requestDto);

        DemoResponse response = mapperDemoDto.toResponse(responseDto);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value ="/get/iuv/{iuv}/noticeId/{noticeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FullResponse> getIuvAndnoticeId(

            @PathVariable(DemoConstants.IUV)
            @NotBlank(message = "{iuv.notBlank}")String iuv,

            @PathVariable(DemoConstants.NOTICE_ID)
            @NotBlank(message = "{noticeId.notBlank}") String noticeId){
        ParamsDto paramsDto = controllerMapper.toParamsDto(iuv,noticeId);
        FullResponseDto responseDto = demoService.getIuvAndLocation(paramsDto);
        FullResponse response = controllerMapper.toFullResponse(responseDto);

      return ResponseEntity.ok().body(response);
    }
}
