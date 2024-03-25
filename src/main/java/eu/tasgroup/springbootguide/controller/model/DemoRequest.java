package eu.tasgroup.springbootguide.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DemoRequest {

    @NotBlank
    @Size(max = 70)
    private String iuv;

    @Pattern(regexp = "[A-Z]{2}")
    private String city;

    @Pattern(regexp = "[A-Z]{2}")
    private String nation;
}
