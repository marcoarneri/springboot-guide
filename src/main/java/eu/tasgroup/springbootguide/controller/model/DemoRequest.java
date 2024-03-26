package eu.tasgroup.springbootguide.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DemoRequest {

    @Schema(example = "IUV123456", description = "Identificativo univoco")
    @NotBlank
    @Size(max = 70)
    private String iuv;

    @Schema(example = "MI", description = "Citta")
    @Pattern(regexp = "(?:[A-Z]{2})")
    private String city;

    @Schema(example = "IT", description = "Nazione")
    @Pattern(regexp = "(?:[A-Z]{2})")
    private String nation;
}
