package com.devops.hello.service;

import com.devops.hello.dto.HelloResponse;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private static final String VERSION = "1.0.0";

    public HelloResponse sayHello() {
        return new HelloResponse("Hello DevOps", VERSION);
    }

    public HelloResponse sayHello(String name) {
        return new HelloResponse("Hello " + name, VERSION);
    }
}
