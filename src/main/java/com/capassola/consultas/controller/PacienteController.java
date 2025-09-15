package com.capassola.consultas.controller;

import com.capassola.consultas.model.Paciente;
import com.capassola.consultas.service.PacienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public Paciente criar(@RequestBody Paciente paciente){
        return pacienteService.criarPaciente(paciente);
    }

    @GetMapping
    public List<Paciente> listar(){
        return pacienteService.listarPaciente();
    }

    @GetMapping("/{id}")
    public Paciente buscar(@PathVariable Long id){
        return pacienteService.buscarPaciente(id);
    }

    @PutMapping("/{id}")
    public Paciente actualizar(@PathVariable Long id, @RequestBody Paciente paciente){
        return pacienteService.actualizarPaciente(id, paciente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        pacienteService.deletarPaciente(id);
    }
}
