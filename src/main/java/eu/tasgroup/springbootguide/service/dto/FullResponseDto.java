package eu.tasgroup.springbootguide.service.dto;

import lombok.Data;

@Data
public class FullResponseDto {

    DemoGetResponseDto responseDtoIuv;

    DemoGetResponseDto responseDtoNoticeId;
}
