package com.example.clinica.servicio;

import com.example.clinica.IProceso;
import com.example.clinica.model.Persona;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicoServicio implements IProceso<Persona> {

    private final List<Persona> medicos = new ArrayList<>();

    @Override
    public ResponseEntity<String> registrar(Persona medico) {
        if (medicos.stream().anyMatch(p -> p.getNumeroDocumento() == medico.getNumeroDocumento())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya se encuentra registrado el medico");
        }
        medicos.add(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body("Medico registrado");
    }

    @Override
    public ResponseEntity<String> editar(long numeroDocumento, Persona medico) {
        Optional<Persona> medicoRegistrado = medicoRegistrado(numeroDocumento);
        if (medicoRegistrado.isPresent()) {
            Persona persona = medicoRegistrado.get();
            persona.setPrimerNombre(medico.getPrimerNombre());
            persona.setSegundoNombre(medico.getSegundoNombre());
            persona.setPrimerApellido(medico.getPrimerApellido());
            persona.setSegundoApellido(medico.getSegundoApellido());
            persona.setTipoDocumento(medico.getTipoDocumento());
            persona.setFechaExpedicion(medico.getFechaExpedicion());
            return ResponseEntity.ok("Información del medico editada");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el medico");
        }
    }

    @Override
    public ResponseEntity<String> eliminar(long numeroDocumento) {
        Optional<Persona> medicoRegistrado = medicoRegistrado(numeroDocumento);
        if (medicoRegistrado.isPresent()) {
            medicos.remove(medicoRegistrado.get());
            return ResponseEntity.ok("Medico eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el medico");
        }
    }

    @Override
    public ResponseEntity<?> ver(long numeroDocumento) {
        Persona medico = medicos.stream().filter(p -> p.getNumeroDocumento() == numeroDocumento).findFirst().orElse(null);
        if (medico == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el medico con número de documento: " + numeroDocumento);
        }
        return ResponseEntity.ok(medico);
    }

    public Optional<Persona> medicoRegistrado(long numeroDocumento) {
        return medicos.stream().filter(p -> p.getNumeroDocumento() == numeroDocumento).findFirst();
    }
}
