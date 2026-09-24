package com.devops.hello.service;

import com.devops.hello.dto.HelloResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloServiceTest {

    private final HelloService helloService = new HelloService();

    @Test
    void sayHello_shouldReturnDefaultMessage() {
        HelloResponse response = helloService.sayHello();

        assertThat(response.getMessage()).isEqualTo("Hello DevOps");
        assertThat(response.getVersion()).isEqualTo("1.0.0");
    }

    @Test
    void sayHello_withName_shouldReturnPersonalizedMessage() {
        HelloResponse response = helloService.sayHello("Fabien");

        assertThat(response.getMessage()).isEqualTo("Hello Fabien");
        assertThat(response.getVersion()).isEqualTo("1.0.0");
    }
}
