package eu.tasgroup.springbootguide.service;

import eu.tasgroup.springbootguide.exception.AppErrorCodeMessageEnum;
import eu.tasgroup.springbootguide.exception.AppException;
import eu.tasgroup.springbootguide.repository.DemoRepository;
import eu.tasgroup.springbootguide.repository.mapper.MapperDemoEntity;
import eu.tasgroup.springbootguide.repository.model.DemoEntity;
import eu.tasgroup.springbootguide.repository.specification.DemoSpecifications;
import eu.tasgroup.springbootguide.service.dto.*;
import eu.tasgroup.springbootguide.service.mapper.MapperDemoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DemoService {

    @Autowired
    DemoRepository demoRepository;

    @Autowired
    MapperDemoEntity mapperDemoEntity;

    public DemoResponseDto callDemoService(DemoRequestDto requestDto){

        validazioneSintattica(requestDto.getIuv());

        validazioneSemantica(requestDto.getIuv());

        DemoEntity entity = mapperDemoEntity.toEntity(requestDto);

        demoRepository.save(entity);

//        Implementazione logica del servizio
//        Se tutto passa senza errori setto la risposta dto da tornare al controller
        DemoResponseDto responseDto = new DemoResponseDto();
        responseDto.setOutcome("OK");
        responseDto.setStatus("ELABORATO");
        return responseDto;
    }

    public DemoGetByIuvAndNoticeIdDto getIuvAndNoticeId(ParamsDto paramsDto){

        String iuv = paramsDto.getIuv();
        String noticeId = paramsDto.getNoticeId();

        validazioneSintattica(iuv);

        Specification<DemoEntity> spec;

        spec = Specification.where(DemoSpecifications.iuv(iuv));

        if(noticeId != null) {
            spec = spec.and(DemoSpecifications.noticeId(noticeId));
        }

        Optional<DemoEntity> all = demoRepository.findOne(spec);

        if(all.isEmpty()){
            throw new AppException(AppErrorCodeMessageEnum.BAD_REQUEST);
        }
        return mapperDemoEntity.toGetResponseDto(all.get());
    }

    public DemoGetAllByLocationDto getAllByLocation(String location){

        List<DemoGetByIuvAndNoticeIdDto> getAllByLocation = mapperDemoEntity.toGetResponseDtoList(demoRepository.findAllByLocation(location));
        DemoGetAllByLocationDto responseDto = new DemoGetAllByLocationDto();
        responseDto.setResponseDtoList(getAllByLocation);

        return responseDto;
    }

    private void validazioneSemantica(String iuv) {
        //Implementazione validazione semantica e logica (a db)
        Optional<DemoEntity> byIuv = demoRepository.findByIuv(iuv);
        if(byIuv.isPresent()){
            throw new AppException(AppErrorCodeMessageEnum.IUV_DUPLICATE);
        }
    }

    private void validazioneSintattica(String iuv) {
        //Implementazione validazione sitattica e logica di validazione della request
        if (iuv.equals("IUV")) {
            throw new AppException(AppErrorCodeMessageEnum.IUV_DUPLICATE);
        }
    }
}
