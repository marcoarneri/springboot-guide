package eu.tasgroup.springbootguide.service.mapper;

import eu.tasgroup.springbootguide.controller.model.DemoRequest;
import eu.tasgroup.springbootguide.controller.model.DemoResponse;
import eu.tasgroup.springbootguide.service.model.DemoRequestDto;
import eu.tasgroup.springbootguide.service.model.DemoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MapperDemoDto {

    @Mapping(target = "location", expression = "java(mapLocation(request.getCity(), request.getNation()))")
    public abstract DemoRequestDto toRequestDto(DemoRequest request);

    public abstract DemoResponse toResponse(DemoResponseDto responseDto);

    public String mapLocation(String city, String nation){
        return nation + " - " + city;
    }
}
