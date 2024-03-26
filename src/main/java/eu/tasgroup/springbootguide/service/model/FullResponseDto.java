package eu.tasgroup.springbootguide.service.model;

import lombok.Data;

@Data
public class FullResponseDto {

    DemoGetResponseDto responseDtoIuv;

    DemoGetResponseDto responseDtoNoticeId;
}
