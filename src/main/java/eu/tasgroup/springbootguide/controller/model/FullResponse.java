package eu.tasgroup.springbootguide.controller.model;

import eu.tasgroup.springbootguide.service.model.DemoGetResponseDto;
import lombok.Data;

@Data
public class FullResponse {

    DemoGetResponseDto responseDtoIuv;

    DemoGetResponseDto responseDtoNoticeId;
}
