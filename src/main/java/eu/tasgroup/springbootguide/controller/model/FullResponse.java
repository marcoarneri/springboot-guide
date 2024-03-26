package eu.tasgroup.springbootguide.controller.model;

import eu.tasgroup.springbootguide.service.dto.DemoGetResponseDto;
import lombok.Data;

@Data
public class FullResponse {

    DemoGetResponseDto responseDtoIuv;

    DemoGetResponseDto responseDtoNoticeId;
}
