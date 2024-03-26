package eu.tasgroup.springbootguide.service;

import eu.tasgroup.springbootguide.exception.AppErrorCodeMessageEnum;
import eu.tasgroup.springbootguide.exception.AppException;
import eu.tasgroup.springbootguide.service.dto.DemoRequestDto;
import eu.tasgroup.springbootguide.service.dto.DemoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DemoService {

    public DemoResponseDto callDemoService(DemoRequestDto requestDto){

        validazioneSintattica(requestDto);

        validazioneSemantica();

        //Implementazione logica del servizio
        //Se tutto passa senza errori setto la risposta dto da tornare al controller
        DemoResponseDto responseDto = new DemoResponseDto();
        responseDto.setOutcome("OK");
        responseDto.setStatus("ELABORATO");

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
