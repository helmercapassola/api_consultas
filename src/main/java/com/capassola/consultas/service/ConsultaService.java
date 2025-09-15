package com.capassola.consultas.service;

import com.capassola.consultas.model.Consulta;
import com.capassola.consultas.model.Medico;
import com.capassola.consultas.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConsultaService {
    private final ConsultaRepository consultaRepository;
    private  final RedisLockService lockService;
    private final long lockTimeoutMs;

    public ConsultaService(ConsultaRepository consultaRepository,
                           RedisLockService lockService,
                           @Value("${app.redis.lock.timeout-ms:10000}") long lockTimeoutMs) {
        this.consultaRepository = consultaRepository;
        this.lockService = lockService;
        this.lockTimeoutMs = lockTimeoutMs;
    }

    private  String lockkyerFor(Consulta c){
        Medico medico = c.getMedico();
        return "lock:medico" + medico.getId() + ":data:" + c.getDia().toString()+
                ":inicio:" +c.getStartTime().toString() + ":fim:" +c.getEndTime().toString();
    }

    /**
     * Cria nova consulta, validando conflitos e atualizando cache.
     */
    @Cacheable(value = "consulta", key="#consulta.id")
    @CacheEvict(value = "consultas", allEntries = true) // limpa cache da lista
    @Transactional
    public Consulta criarConsulta( Consulta consulta){
        String lockkey = lockkyerFor(consulta);
        boolean locked = lockService.tryLock(lockkey, lockTimeoutMs);

        if(!locked){
            throw new RuntimeException("Sistema ocupado. Tente de novo em alguns instantes.");
        }

        try {
            //verificar conflito na BD
            boolean conflito = consultaRepository.existsByMedicoAndDiaAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                    consulta.getMedico(),
                    consulta.getDia(),
                    consulta.getStartTime(),
                    consulta.getEndTime()
            );

            if(conflito){
                throw  new RuntimeException("Conflito de horário! O Médico já possui consulta nesse período");
            }
            // save
            if(consulta.getStatus() == null) consulta.setStatus("AGENDADA");
            return  consultaRepository.save(consulta);
        } finally {
            lockService.releaseLock(lockkey);
        }

    }

    /**
     * Lista todas as consultas (cacheada em Redis).
     */
    @Cacheable(value = "consultas")
    public List<Consulta> listarConsultas(){
        return consultaRepository.findAll();

    }
    /**
     * Busca uma consulta por ID (cacheada individualmente).
     */
    @Cacheable(value = "consulta", key ="#id")
    public  Consulta  buscarConsulta(Long id){
        return  consultaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Consulta não encontrada"));
    }


    @Transactional
    public Consulta actualizarConsulta(Long id, Consulta consulta){
        Consulta existente = buscarConsulta(id);

        existente.setPaciente(consulta.getPaciente());
        existente.setMedico(consulta.getMedico());
        existente.setDia(consulta.getDia());
        existente.setStartTime(consulta.getStartTime());
        existente.setEndTime(consulta.getEndTime());
        existente.setStatus(consulta.getStatus());

        return consultaRepository.save(existente);
    }

    public void deletarConsulta(Long id){
        consultaRepository.deleteById(id);
    }
}

