package com.example.clinica;

import com.example.clinica.model.Persona;
import com.example.clinica.servicio.MedicoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoServicio medicoServicio;

    @PostMapping("/registrar")
    public ResponseEntity<String> crear(@RequestBody Persona medico) {
        return medicoServicio.registrar(medico);
    }

    @GetMapping("/{numeroDocumento}")
    public ResponseEntity<?> getInfo(@PathVariable long numeroDocumento) {
        return medicoServicio.ver(numeroDocumento);
    }

    @PutMapping("/editar/{numeroDocumento}")
    public ResponseEntity<String> editar(@PathVariable long numeroDocumento, @RequestBody Persona medico) {
        return medicoServicio.editar(numeroDocumento, medico);
    }

    @DeleteMapping("/eliminar/{numeroDocumento}")
    public ResponseEntity<String> eliminar(@PathVariable long numeroDocumento) {
        return medicoServicio.eliminar(numeroDocumento);
    }
}
