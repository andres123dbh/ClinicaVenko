package com.example.clinica;

import org.springframework.http.ResponseEntity;

public interface IProceso<T> {
    ResponseEntity<String> registrar(T entidad);

    ResponseEntity<String> editar(long numeroDocumento, T entidad);

    ResponseEntity<String> eliminar(long numeroDocumento);

    ResponseEntity<?> ver(long numeroDocumento);
}
