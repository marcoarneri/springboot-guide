package eu.tasgroup.springbootguide.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class DemoGetAllByLocationDto {

    private List<DemoGetByIuvAndNoticeIdDto> responseDtoList;
}
