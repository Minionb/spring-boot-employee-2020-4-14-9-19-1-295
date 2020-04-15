package com.thoughtworks.springbootemployee.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping(path = "/{userName}")
    @ResponseStatus(HttpStatus.OK)
    public String getAll(@PathVariable String userName) {

        return "Hello:" + userName;
    }
}
