package ilp.tutorials.simpledemo;

import ilp.tutorials.simpledemo.controller.SimpleController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
		webEnvironment = WebEnvironment.RANDOM_PORT //,

		// webEnvironment = WebEnvironment.DEFINED_PORT,
		// properties = {
		//	"server.port=8080"
		// }
		)
class SimpledemoApplicationTests {

	@Autowired
	private SimpleController simpleController;

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;


	@Test
	void contextLoads() throws Exception {
		assertThat(simpleController).isNotNull();
	}

	@Test
	void isAliveShouldReturnTrue() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/isAlive",
				String.class)).contains("true");
	}

	@Test
	void studentIdCameron() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/studentId/Cameron",
				String.class)).contains("Cameron");
	}

	@Test
	void studentIdNotCameron() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/studentId/Michael",
				String.class)).startsWith("We do not have your name in our register");
	}

	private HttpEntity<String> getPositionPairRequest() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		return new HttpEntity<>("{" +
				"  \"position1\": { \n" +
				"    \"lng\": -3.192473, \n" +
				"    \"lat\": 55.946233 \n" +
				"  }, \n" +
				"  \"position2\": { \n" +
				"    \"lng\": -3.192473, \n" +
				"    \"lat\": 55.942617 \n" +
				"  } \n" +
				"}", headers);
	}

	@Test
	void sampleRequest() throws Exception {
		ResponseEntity<String> result = this.restTemplate.postForEntity("http://localhost:" + port + "/sample",
				getPositionPairRequest(),
				String.class);
		assertThat(result.getStatusCode() == HttpStatus.OK).isTrue();
		assertThat(Objects.requireNonNull(result.getBody()).startsWith("Got")).isTrue();
	}

	@Test
	void invalidSampleRequest() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		var entity = new HttpEntity<>("{" +
				"  \"positionA\": { \n" +
				"    \"lng\": -3.192473, \n" +
				"    \"lat\": 55.946233 \n" +
				"  }, \n" +
				"  \"positionB\": { \n" +
				"    \"lng\": -3.192473, \n" +
				"    \"lat\": 55.942617 \n" +
				"  } \n" +
				"}", headers);

		assertThat(this.restTemplate.postForEntity("http://localhost:" + port + "/sample",
				entity,
				String.class).getStatusCode() == HttpStatus.BAD_REQUEST).isTrue();
	}

	@Test
	void sample2Request() throws Exception {
		ResponseEntity<String> result = this.restTemplate.postForEntity("http://localhost:" + port + "/sample2",
			getPositionPairRequest(),
			String.class);
		assertThat(result.getStatusCode() == HttpStatus.OK).isTrue();
		System.out.println("Response: " + result.getBody());
		assertThat(Objects.requireNonNull(result.getBody()).startsWith("Got")).isTrue();
	}

	@Test
	void dumpRequest() throws Exception {
		ResponseEntity<String> result = this.restTemplate.postForEntity("http://localhost:" + port + "/dumprequest",
			getPositionPairRequest(),
			String.class);
		assertThat(result.getStatusCode() == HttpStatus.OK).isTrue();
		String body = result.getBody();
		assert body != null;
		System.out.println("Response: " + body);
		assertThat(body.contains("application/json")).isTrue();
		// assertThat(body.contains("text/json")).isTrue();
	}
}
