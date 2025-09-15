package com.capassola.consultas.service;

import com.capassola.consultas.model.Paciente;
import com.capassola.consultas.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    private  final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente criarPaciente(Paciente paciente){
        return  pacienteRepository.save(paciente);
    }

    public List<Paciente> listarPaciente(){
        return pacienteRepository.findAll();
    }

    public Paciente buscarPaciente(Long id){
        return pacienteRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Paciente não encontrado"));
    }

    public Paciente actualizarPaciente(Long id, Paciente paciente){
        Paciente existente = buscarPaciente(id);
        existente.setName(paciente.getName());
        existente.setEmail(paciente.getEmail());
        existente.setPhoneNumber(paciente.getPhoneNumber());
        if(paciente.getPassword() != null && !paciente.getPassword().isBlank()){
            existente.setPassword(paciente.getPassword());
        }
        return  pacienteRepository.save(existente);
    }

    public void deletarPaciente(Long id){
        if(!pacienteRepository.existsById(id)){
            throw  new RuntimeException("Paciente não encontrado");
        }
        pacienteRepository.deleteById(id);
    }
}
