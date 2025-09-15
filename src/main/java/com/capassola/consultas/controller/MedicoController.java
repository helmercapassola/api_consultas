package com.capassola.consultas.controller;

import com.capassola.consultas.model.Medico;
import com.capassola.consultas.service.MedicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    public Medico criar(@RequestBody Medico medico) {
        return medicoService.criarMedico(medico);
    }

    @GetMapping
    public List<Medico> listar() {
        return medicoService.listarMedico();
    }

    @GetMapping("/{id}")
    public Medico buscar(@PathVariable Long id) {
        return medicoService.buscarMedico(id);
    }

    @PutMapping("/{id}")
    public Medico actualizar(@PathVariable Long id, @RequestBody Medico medico) {
        return medicoService.actualizarMedico(id, medico);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        medicoService.deletarMedico(id);
    }

}
