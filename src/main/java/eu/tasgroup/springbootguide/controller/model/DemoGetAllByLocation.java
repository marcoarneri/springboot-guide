package eu.tasgroup.springbootguide.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class DemoGetAllByLocation {

    @Schema(description = "Lista dell'oggetto filtrato per location")
    List<DemoGetByIuvAndNoticeId> responseList;
}
