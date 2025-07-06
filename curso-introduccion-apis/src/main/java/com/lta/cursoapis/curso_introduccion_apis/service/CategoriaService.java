package com.lta.cursoapis.curso_introduccion_apis.service;

import com.lta.cursoapis.curso_introduccion_apis.dto.CategoriaDTO;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO);
    List<CategoriaDTO> listarCategorias();
    Optional<CategoriaDTO> obtenerCategoriaPorId(Long idCategoria);
    CategoriaDTO actualizarCategoria(Long idCategoria, CategoriaDTO categoriaDTO);

    void eliminarCategoria(Long idCategoria);
}
