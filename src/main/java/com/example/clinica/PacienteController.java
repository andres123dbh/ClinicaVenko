package com.example.clinica;

import com.example.clinica.model.Persona;
import com.example.clinica.servicio.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteServicio pacienteServicio;

    @PostMapping("/registrar")
    public ResponseEntity<String> crear(@RequestBody Persona paciente) {
        return pacienteServicio.registrar(paciente);
    }

    @GetMapping("/{numeroDocumento}")
    public ResponseEntity<?> getInfo(@PathVariable long numeroDocumento) {
        return pacienteServicio.ver(numeroDocumento);
    }

    @PutMapping("/editar/{numeroDocumento}")
    public ResponseEntity<String> editar(@PathVariable long numeroDocumento, @RequestBody Persona paciente) {
        return pacienteServicio.editar(numeroDocumento, paciente);
    }

    @DeleteMapping("/eliminar/{numeroDocumento}")
    public ResponseEntity<String> eliminar(@PathVariable long numeroDocumento) {
        return pacienteServicio.eliminar(numeroDocumento);
    }

}
