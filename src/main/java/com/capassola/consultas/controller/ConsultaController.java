package com.capassola.consultas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api/consultas")
public class ConsultaController {

    @GetMapping("/hello")
    public String Hello(){
        return "Teste API";
    }
}
