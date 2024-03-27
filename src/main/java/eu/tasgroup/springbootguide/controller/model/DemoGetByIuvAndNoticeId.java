package eu.tasgroup.springbootguide.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DemoGetByIuvAndNoticeId {

    @Schema(example = "123", description = "Codice univoco")
    private String iuv;

    @Schema(example = "IT - MI", description = "Indica il luogo della chiamata")
    private String location;

    @Schema(example = "1234", description = "Codice non univoco")
    private String noticeId;
}
