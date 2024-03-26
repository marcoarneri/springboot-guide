package eu.tasgroup.springbootguide.service;

import eu.tasgroup.springbootguide.exception.AppErrorCodeMessageEnum;
import eu.tasgroup.springbootguide.exception.AppException;
import eu.tasgroup.springbootguide.service.dto.DemoRequestDto;
import eu.tasgroup.springbootguide.service.dto.DemoResponseDto;
import eu.tasgroup.springbootguide.repository.DemoRepository;
import eu.tasgroup.springbootguide.repository.mapper.MapperDemoEntity;
import eu.tasgroup.springbootguide.repository.model.DemoEntity;
import eu.tasgroup.springbootguide.service.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DemoService {

    @Autowired
    DemoRepository demoRepository;

    @Autowired
    MapperDemoEntity mapperDemoEntity;

    public DemoResponseDto callDemoService(DemoRequestDto requestDto){

        validazioneSintattica(requestDto);

        validazioneSemantica();

        DemoEntity entity = mapperDemoEntity.toEntity(requestDto);

        demoRepository.save(entity);

//        Implementazione logica del servizio
//        Se tutto passa senza errori setto la risposta dto da tornare al controller
        DemoResponseDto responseDto = new DemoResponseDto();
        responseDto.setOutcome("OK");
        responseDto.setStatus("ELABORATO");
        return responseDto;
    }

    public FullResponseDto getIuvAndLocation (ParamsDto paramsDto){

        String iuv = paramsDto.getIuv();
        String noticeId = paramsDto.getNoticeId();

        DemoGetResponseDto getByIuv = mapperDemoEntity.toGetResponseDto(demoRepository.findByIuv(iuv));
        DemoGetResponseDto getByNoticeId = mapperDemoEntity.toGetResponseDto(demoRepository.findByNoticeId(noticeId));

        FullResponseDto responseDto = new FullResponseDto();
        responseDto.setResponseDtoIuv(getByIuv);
        responseDto.setResponseDtoNoticeId(getByNoticeId);
        return responseDto;
    }

    private void validazioneSemantica() {
        //Implementazione validazione semantica e logica (a db)
    }

    private void validazioneSintattica(DemoRequestDto requestDto) {
        //Implementazione validazione sitattica e logica di validazione della request
        if (requestDto.getIuv().equals("IUV")) {
            throw new AppException(AppErrorCodeMessageEnum.BAD_REQUEST);
        }
    }
}
