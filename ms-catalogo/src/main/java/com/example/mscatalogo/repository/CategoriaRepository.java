package com.example.mscatalogo.repository;

import com.example.mscatalogo.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {

}
