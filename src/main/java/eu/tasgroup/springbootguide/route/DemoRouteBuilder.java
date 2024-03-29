package eu.tasgroup.springbootguide.route;

import eu.tasgroup.springbootguide.controller.model.DemoRequest;
import eu.tasgroup.springbootguide.service.dto.DemoResponseDto;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DemoRouteBuilder extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:processDemoResponse")
                .process(exchange -> {
                    // Elabora la richiesta come desiderato

                    // Crea un oggetto DemoResponse
                    DemoResponseDto response = new DemoResponseDto();
                    response.setOutcome("verifica");
                    response.setCity(exchange.getIn().getBody(DemoRequest.class).getCity());

                    // Imposta l'oggetto DemoResponse come corpo del messaggio
                    exchange.getIn().setBody(response);
                })
                .process(exchange -> {
                    DemoResponseDto responseDto = exchange.getIn().getBody(DemoResponseDto.class);
                    log.error(responseDto.getCity());
                    exchange.getIn().setHeader("city",responseDto.getCity());
                })
                .choice()
                    .when(header("city").isEqualTo("MI"))
                        .process(exchange -> log.info("MI"))
                        .to("direct:processDemoResponse2")
                    .when(header("city").isEqualTo("TO"))
                         .process(exchange -> log.info("TO"))
                        .to("direct:processDemoResponse2")
                .otherwise()
                    .process(exchange -> log.info("non lo so"))
                    .to("direct:processDemoResponse2");
    }
}
