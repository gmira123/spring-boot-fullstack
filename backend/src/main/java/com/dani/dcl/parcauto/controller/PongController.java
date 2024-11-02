package com.dani.dcl.parcauto.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PongController {

    record PingPong(String result){}

    @GetMapping("/pong")
    public PingPong getPingPong() {
        return new PingPong("Ping");
    }
}
