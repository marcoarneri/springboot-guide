package eu.tasgroup.springbootguide;

import eu.tasgroup.springbootguide.controller.model.DemoRequest;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static eu.tasgroup.springbootguide.constants.TestConstants.BASE_URL;
import static eu.tasgroup.springbootguide.constants.TestConstants.POST_ENDPOINT;
import static eu.tasgroup.springbootguide.util.GeneratedParams.generateIuv;
import static eu.tasgroup.springbootguide.util.GeneratedParams.generateNoticeId;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.verify.VerificationTimes.once;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class MockServerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static MockServerClient mockServer;

    @BeforeAll
    public static void startServer(){
        mockServer = ClientAndServer.startClientAndServer(1080);
    }
    @AfterAll
    public static void stopServer(){
        mockServer.stop();
    }
    @Test
    void testd_Demo_Post_Ok() {
        int port = mockServer.getPort();

        String url = BASE_URL + port + POST_ENDPOINT;

        String requestBody = "{\n" +
                "    \"iuv\":\"123\",\n" +
                "    \"city\":\"MI\",\n" +
                "    \"nation\":\"IT\",\n" +
                "    \"noticeId\":\"1234\"\n" +
                "}";

        String responseBodyExpected = "{\n" +
                "    \"outcome\": \"OK\",\n" +
                "    \"status\": \"ELABORATO\"\n" +
                "}";

        mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath(POST_ENDPOINT)
                                .withBody(requestBody)
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(responseBodyExpected)
                );

        String response = restTemplate.postForObject(
                url,
                requestBody,
                String.class);

        mockServer
                .verify(
                        request()
                                .withMethod("POST")
                                .withPath(POST_ENDPOINT),
                        once()
                        );

        AssertionsForClassTypes.assertThat(response).isEqualTo(responseBodyExpected);
    }
}
