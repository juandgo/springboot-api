package com.lta.cursoapis.curso_introduccion_apis.service.impl;

import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
import com.lta.cursoapis.curso_introduccion_apis.repository.CategoriaRepository;
import com.lta.cursoapis.curso_introduccion_apis.service.CategoriaService;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    @SneakyThrows
    public Categoria crearCategoria(Categoria categoria) {
        if(categoriaRepository.existsByNombreCategoria(categoria.getNombreCategoria())){
            throw new BadRequestException("Ya existe una categoría con ese nombre.");
        }
        return categoriaRepository.save(categoria);
    }

    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Optional<Categoria> obtenerCategoriaPorId(Long idCategoria) {
        return categoriaRepository.findById(idCategoria);
    }

    @Override
    @SneakyThrows
    public Categoria actualizarCategoria(Long idCategoria, Categoria categoria) {
        Categoria categoriaExistente = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new Exception("Categoría no encontrada"));

        categoriaExistente.setNombreCategoria(categoria.getNombreCategoria());
        return categoriaRepository.save(categoriaExistente);
    }

    @Override
    @SneakyThrows
    public void eliminarCategoria(Long idCategoria) {
        Optional<Categoria> categoriaExitente = categoriaRepository.findById(idCategoria);
        if (!categoriaExitente.isPresent()){
            throw new Exception("Categoría no encontrada para eliminar.");
        }
        categoriaRepository.deleteById(idCategoria);
    }
}
