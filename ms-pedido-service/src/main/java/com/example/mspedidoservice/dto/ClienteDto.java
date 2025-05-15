package com.example.mspedidoservice.dto;

import lombok.Data;

@Data
public class ClienteDto {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String telefono;
    private String correo;
}
