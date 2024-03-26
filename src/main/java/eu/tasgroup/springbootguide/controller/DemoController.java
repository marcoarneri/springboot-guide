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
import eu.tasgroup.springbootguide.service.dto.DemoRequestDto;
import eu.tasgroup.springbootguide.service.dto.DemoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Demo spring boot", description = "Demo spring boot project")
@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
@Validated
public class DemoController {

    private final DemoService demoService;
    private final MapperDemoDto mapperDemoDto;

    @Autowired
    DemoControllerMapper controllerMapper;

    @Operation(
            operationId = "demo",
            summary = "demo POST call",
            description = "example of POST rest api")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = DemoResponse.class))
                            })
            })
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
