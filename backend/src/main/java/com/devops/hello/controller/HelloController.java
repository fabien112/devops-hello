package com.devops.hello.controller;

import com.devops.hello.dto.HelloResponse;
import com.devops.hello.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping
    public HelloResponse hello() {
        return helloService.sayHello();
    }

    @GetMapping("/{name}")
    public HelloResponse helloByName(@PathVariable String name) {
        return helloService.sayHello(name);
    }
}
