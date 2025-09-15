package com.capassola.consultas.controller;

import com.capassola.consultas.model.Consulta;
import com.capassola.consultas.service.ConsultaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {
    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public Consulta criar(@RequestBody Consulta consulta){
        return consultaService.criarConsulta(consulta);
    }

    @GetMapping
    public List<Consulta> listar(){
        return  consultaService.listarConsultas();
    }

    @GetMapping("/{id}")
    public Consulta buscar(@PathVariable Long id){
        return  consultaService.buscarConsulta(id);
    }

    @PutMapping("/{id}")
    public Consulta actualizar(@PathVariable Long id, @RequestBody Consulta consulta){
        return  consultaService.actualizarConsulta(id, consulta);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        consultaService.deletarConsulta(id);
    }

    @GetMapping("/hello")
    public String Hello(){
        return "Teste API";
    }


}
