package com.example.mspedidoservice.repository;

import com.example.mspedidoservice.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRespository extends JpaRepository<Pedido, Integer> {
}
