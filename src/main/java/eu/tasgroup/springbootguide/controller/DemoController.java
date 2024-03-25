package eu.tasgroup.springbootguide.controller;

import eu.tasgroup.springbootguide.controller.model.DemoRequest;
import eu.tasgroup.springbootguide.controller.model.DemoResponse;
import eu.tasgroup.springbootguide.repository.DemoRepository;
import eu.tasgroup.springbootguide.repository.mapper.MapperDemoEntity;
import eu.tasgroup.springbootguide.repository.model.DemoEntity;
import eu.tasgroup.springbootguide.service.DemoService;
import eu.tasgroup.springbootguide.service.mapper.MapperDemoDto;
import eu.tasgroup.springbootguide.service.model.DemoRequestDto;
import eu.tasgroup.springbootguide.service.model.DemoResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
