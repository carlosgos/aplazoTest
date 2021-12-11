package com.aplazo.pagos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aplazo.web.rest.CalculoCredito;

@SpringBootTest
class AplazoTestApplicationTests {
	@Autowired
	private CalculoCredito controllerRest;

	@Test
	void contextLoads() throws Exception {
			assertThat(controllerRest).isNotNull();
	}

}
