package eu.tasgroup.springbootguide.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DemoResponse {

    @Schema(example = "OK", description = "Indica il risultato della chiamata")
    private String outcome;

    @Schema(example = "ELABORATO", description = "Indica lo stato della chiamata")
    private String status;
}
