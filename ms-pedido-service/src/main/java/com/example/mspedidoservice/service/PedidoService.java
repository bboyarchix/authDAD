package com.example.mspedidoservice.service;

import com.example.mspedidoservice.entity.Pedido;

import java.util.List;

public interface PedidoService {
    public List<Pedido> listar();

    public Pedido guardar(Pedido pedido);

    public Pedido buscarPorId(Integer id);

    public Pedido editar(Pedido pedido);

    public void eliminar(Integer id);
}
