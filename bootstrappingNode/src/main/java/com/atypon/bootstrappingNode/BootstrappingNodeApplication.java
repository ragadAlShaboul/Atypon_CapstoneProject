package com.atypon.bootstrappingNode;

import com.atypon.bootstrappingNode.initialization.Initialization;
import com.atypon.bootstrappingNode.initialization.InitializationV1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootstrappingNodeApplication {

	public static void main(String[] args) {
		Initialization initialization = new InitializationV1();
		SpringApplication.run(BootstrappingNodeApplication.class, args);
		initialization.initialize(4);
	}

}
