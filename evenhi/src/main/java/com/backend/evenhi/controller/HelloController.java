package com.backend.evenhi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("helloworld")
    public String helloworld(){
        return "Olá Mundo!";
    }

    @GetMapping("testeauth")
    public String testeAuth(){
        return "Aplicação autenticada";
    }

}
