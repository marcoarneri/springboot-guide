package eu.tasgroup.springbootguide.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DemoRequest {

    @Schema(example = "IUV123456", description = "Identificativo univoco")
    @NotBlank(message = "{iuv.notBlank}")
    @Size(max = 70)
    private String iuv;

    @Schema(example = "MI", description = "Citta")
    @Pattern(regexp = "([A-Z]{2})", message = "{city.pattern.violation}")
    @NotBlank(message = "{city.notBlank}")
    private String city;

    @Schema(example = "IT", description = "Nazione")
    @Pattern(regexp = "([A-Z]{2})", message = "{nation.pattern.violation}")
    @NotBlank(message = "{nation.notBlank}")
    private String nation;

    @Schema(example = "noticeID123", description = "noticeID")
    @NotBlank(message = "{noticeId.notBlank}")
    private String noticeId;
}
