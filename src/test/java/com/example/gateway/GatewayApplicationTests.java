package com.example.gateway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = {"httpbin=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
class GatewayApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	public void shouldReturnHelloWorldForGet() throws Exception {
		//Stubs
		stubFor(get(urlEqualTo("/get"))
				.willReturn(aResponse()
						.withBody("{\"headers\":{\"Hello\":\"World\"}}")
						.withHeader("Content-Type", "application/json")));
		stubFor(get(urlEqualTo("/delay/3"))
				.willReturn(aResponse()
						.withBody("no fallback")
						.withFixedDelay(3000)));

		webClient
				.get().uri("/get")
				.exchange()
				.expectStatus().isOk()
				.expectBody().equals("Hello World");

	}
}
