package eu.tasgroup.springbootguide.service.dto;

import lombok.Data;


@Data
public class DemoGetByIuvAndNoticeIdDto {

    private String iuv;
    private String location;
    private String noticeId;
}
