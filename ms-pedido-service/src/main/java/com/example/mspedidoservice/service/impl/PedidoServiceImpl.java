package com.example.mspedidoservice.service.impl;

import com.example.mspedidoservice.entity.Pedido;
import com.example.mspedidoservice.entity.PedidoDetalle;
import com.example.mspedidoservice.feign.ClienteFeign;
import com.example.mspedidoservice.feign.ProductoFeign;
import com.example.mspedidoservice.repository.PedidoRespository;
import com.example.mspedidoservice.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRespository pedidoRespository;
    @Autowired
    private ClienteFeign clienteFeign;
    @Autowired
    private ProductoFeign productoFeign;

    @Override
    public List<Pedido> listar() {
        return pedidoRespository.findAll();
    }

    @Override
    public Pedido guardar(Pedido pedido) {

        return pedidoRespository.save(pedido);
    }

    @Override
    public Pedido buscarPorId(Integer id) {

        Pedido pedido = pedidoRespository.findById(id).get();
        pedido.setClienteDto(clienteFeign.buscarPorId(pedido.getClienteId()).getBody());
        /*for (PedidoDetalle pedidoDetalle : pedido.getDetalle()) {
            pedidoDetalle.setProducto(productoFeign.buscarPorId(pedidoDetalle.getProductoId()).getBody());
        }*/

        List<PedidoDetalle> pedidoDetalles = pedido.getDetalle().stream().map(pedidoDetalle -> {
            pedidoDetalle.setProducto(productoFeign.buscarPorId(pedidoDetalle.getProductoId()).getBody());
            return pedidoDetalle;
        }).toList();
        pedido.setDetalle(pedidoDetalles);
        return pedido;
    }

    @Override
    public Pedido editar(Pedido pedido) {
        return pedidoRespository.save(pedido);
    }

    @Override
    public void eliminar(Integer id) {
        pedidoRespository.deleteById(id);
    }
}
