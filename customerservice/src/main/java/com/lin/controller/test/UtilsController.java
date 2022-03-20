package com.lin.controller.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilsController {
    @Value("${test.jasypt}")
    private String test;

    @GetMapping("/testjasypt")
    public String testjasypt(){
        return test;
    }
}
