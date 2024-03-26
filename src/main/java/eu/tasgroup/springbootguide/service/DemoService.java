package eu.tasgroup.springbootguide.service;

import eu.tasgroup.springbootguide.repository.DemoRepository;
import eu.tasgroup.springbootguide.repository.mapper.MapperDemoEntity;
import eu.tasgroup.springbootguide.service.model.*;
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
//        throw new AppException(AppErrorCodeMessageEnum.ERROR);

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
    }

}
