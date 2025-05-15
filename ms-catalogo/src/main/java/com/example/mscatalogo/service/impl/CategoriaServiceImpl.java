package com.example.mscatalogo.service.impl;

import com.example.mscatalogo.entity.Categoria;
import com.example.mscatalogo.repository.CategoriaRepository;
import com.example.mscatalogo.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findById(id).get();
    }

    @Override
    public Categoria editar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void eliminar(Integer id) {
        categoriaRepository.deleteById(id);
    }
}
