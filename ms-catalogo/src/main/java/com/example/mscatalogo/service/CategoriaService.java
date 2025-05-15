package com.example.mscatalogo.service;

import com.example.mscatalogo.entity.Categoria;

import java.util.List;

public interface CategoriaService {
    public List<Categoria> listar();

    public Categoria guardar(Categoria categoria);

    public Categoria buscarPorId(Integer id);

    public Categoria editar(Categoria categoria);

    public void eliminar(Integer id);

}
