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
public class PacienteServicio implements IProceso<Persona> {

    private final List<Persona> pacientes = new ArrayList<>();

    @Override
    public ResponseEntity<String> registrar(Persona paciente) {
        if (pacientes.stream().anyMatch(p -> p.getNumeroDocumento() == paciente.getNumeroDocumento())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya se encuentra registrado el paciente");
        }
        pacientes.add(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Paciente registrado");
    }

    @Override
    public ResponseEntity<String> editar(long numeroDocumento, Persona paciente) {
        Optional<Persona> pacienteRegistrado = pacienteRegistrado(numeroDocumento);
        if (pacienteRegistrado.isPresent()) {
            Persona persona = pacienteRegistrado.get();
            persona.setPrimerNombre(paciente.getPrimerNombre());
            persona.setSegundoNombre(paciente.getSegundoNombre());
            persona.setPrimerApellido(paciente.getPrimerApellido());
            persona.setSegundoApellido(paciente.getSegundoApellido());
            persona.setTipoDocumento(paciente.getTipoDocumento());
            persona.setFechaExpedicion(paciente.getFechaExpedicion());
            return ResponseEntity.ok("Información del paciente editada");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el paciente");
        }
    }

    @Override
    public ResponseEntity<String> eliminar(long numeroDocumento) {
        Optional<Persona> pacienteRegistrado = pacienteRegistrado(numeroDocumento);
        if (pacienteRegistrado.isPresent()) {
            pacientes.remove(pacienteRegistrado.get());
            return ResponseEntity.ok("Paciente eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el paciente");
        }
    }

    @Override
    public ResponseEntity<?> ver(long numeroDocumento) {
        Persona paciente = pacientes.stream().filter(p -> p.getNumeroDocumento() == numeroDocumento).findFirst().orElse(null);
        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el paciente con número de documento: " + numeroDocumento);
        }
        return ResponseEntity.ok(paciente);
    }

    public Optional<Persona> pacienteRegistrado(long numeroDocumento) {
        return pacientes.stream().filter(p -> p.getNumeroDocumento() == numeroDocumento).findFirst();
    }
}
