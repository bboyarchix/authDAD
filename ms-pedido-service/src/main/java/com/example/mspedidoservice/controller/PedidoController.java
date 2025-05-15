package com.example.mspedidoservice.controller;

import com.example.mspedidoservice.dto.ClienteDto;
import com.example.mspedidoservice.dto.ErrorResponseDto;
import com.example.mspedidoservice.dto.ProductoDto;
import com.example.mspedidoservice.entity.Pedido;
import com.example.mspedidoservice.entity.PedidoDetalle;
import com.example.mspedidoservice.feign.ClienteFeign;
import com.example.mspedidoservice.feign.ProductoFeign;
import com.example.mspedidoservice.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteFeign clienteFeign;

    @Autowired
    private ProductoFeign productoFeign;

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        return ResponseEntity.ok(pedidoService.listar());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Pedido pedido) {
        ResponseEntity<ClienteDto> clienteResponse = clienteFeign.buscarPorId(pedido.getClienteId());
        ClienteDto clienteDto = clienteResponse.getBody();

        if (clienteDto == null || clienteDto.getId() == null) {
            String mensajeError = "Error: Cliente no encontrado.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(mensajeError));
        }

        for (PedidoDetalle pedidoDetalle : pedido.getPedidoDetalles()) {
            ResponseEntity<ProductoDto> productoResponse = productoFeign.buscarPorId(pedidoDetalle.getProductoId());
            ProductoDto productoDto = productoResponse.getBody();

            if (productoDto == null || productoDto.getId() == null) {
                String mensajeError = "Error: Producto no encontrado.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(mensajeError));
            }
        }

        Pedido nuevoPedido = pedidoService.guardar(pedido);
        return ResponseEntity.ok(nuevoPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> editar(@PathVariable(required = true) Integer id,
                                          @RequestBody Pedido pedido) {
        pedido.setId(id);
        return ResponseEntity.ok(pedidoService.editar(pedido));

    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable(required = true) Integer id) {
        pedidoService.eliminar(id);
        return "Eliminacion correcta";
    }
}
