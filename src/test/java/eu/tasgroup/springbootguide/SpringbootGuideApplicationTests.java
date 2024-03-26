package eu.tasgroup.springbootguide;

import eu.tasgroup.springbootguide.controller.advice.model.ApiErrorResponse;
import eu.tasgroup.springbootguide.controller.model.DemoRequest;
import eu.tasgroup.springbootguide.controller.model.DemoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static eu.tasgroup.springbootguide.constants.TestConstants.BASE_URL;
import static eu.tasgroup.springbootguide.constants.TestConstants.POST_ENDPOINT;
import static eu.tasgroup.springbootguide.util.GeneratedParams.generateIuv;
import static eu.tasgroup.springbootguide.util.GeneratedParams.generateNoticeId;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SpringbootGuideApplicationTests {

	@Test
	void contextLoads() {
	}
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void insertsOk() {
		String iuv = generateIuv();
		String noticeId = generateNoticeId();

		String url = BASE_URL + port + POST_ENDPOINT;

		DemoRequest demoRequest = DemoRequest.builder()
				.iuv(iuv)
				.city("MI")
				.nation("IT")
				.noticeId(noticeId).build();

		ResponseEntity<DemoResponse> response = restTemplate.postForEntity(
				url,
				demoRequest,
				DemoResponse.class);

		assertThat( response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getOutcome()).isEqualTo("OK");
		assertThat(response.getBody().getStatus()).isEqualTo("ELABORATO");
	}

	@Test
	public void insertsKo() {
		String iuv = generateIuv();
		String noticeId = generateNoticeId();

		String url = BASE_URL + port + POST_ENDPOINT;

		DemoRequest demoRequest = DemoRequest.builder()
				.iuv(iuv)
				.city("MI")
				.nation("IT")
				.noticeId(noticeId).build();

		ResponseEntity<DemoResponse> response = restTemplate.postForEntity(
				url,
				demoRequest,
				DemoResponse.class);

		assertThat( response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getOutcome()).isEqualTo("OK");
		assertThat(response.getBody().getStatus()).isEqualTo("ELABORATO");

		DemoRequest demoRequest2 = DemoRequest.builder()
				.iuv(iuv)
				.city("MI")
				.nation("IT")
				.noticeId(noticeId).build();

		ResponseEntity<ApiErrorResponse> response2 = restTemplate.postForEntity(
				url,
				demoRequest2,
				ApiErrorResponse.class);

		assertThat(response2.getBody().getAppErrorCode()).isEqualTo("DEMO-0500");
		assertThat(response2.getBody().getMessage()).isEqualTo( "An unexpected error has occurred. Please contact support");
	}
}
