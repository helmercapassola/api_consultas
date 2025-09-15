package com.capassola.consultas.service;

import com.capassola.consultas.model.Medico;
import com.capassola.consultas.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {
    private final MedicoRepository medicoRepository;


    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public Medico criarMedico(Medico medico) {
        return medicoRepository.save(medico);
    }

    public List<Medico> listarMedico(){
        return medicoRepository.findAll();
    }

    public Medico buscarMedico(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
    }

    public Medico actualizarMedico(Long id, Medico medico) {
        Medico existente = buscarMedico(id);
        existente.setName(medico.getName());
        existente.setSpecialty(medico.getSpecialty());
        existente.setOrderNumber(medico.getOrderNumber());
        return  medicoRepository.save(existente);
    }

    public void deletarMedico(Long id){
        if(!medicoRepository.existsById(id)){
            throw new RuntimeException("Médico não encontrado");
        }
        medicoRepository.deleteById(id);
    }

}
