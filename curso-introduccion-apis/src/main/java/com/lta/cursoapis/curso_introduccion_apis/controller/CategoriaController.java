package com.lta.cursoapis.curso_introduccion_apis.controller;

import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
import com.lta.cursoapis.curso_introduccion_apis.service.CategoriaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) throws BadRequestException {
        Categoria nuevaCategoria = categoriaService.crearCategoria(categoria);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias(){
        List<Categoria> categorias = categoriaService.listarCategorias();
        return new ResponseEntity<>(categorias,HttpStatus.OK);
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable Long idCategoria) throws Exception {
        Optional<Categoria> categoriaOptional = categoriaService.obtenerCategoriaPorId(idCategoria);
        if(categoriaOptional.isPresent()){
            return new ResponseEntity<>(categoriaOptional.get(),HttpStatus.OK);
        }else{
            throw new Exception("Categoria no encontrada");
        }
    }

    @PutMapping("/{idCategoria}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long idCategoria,@RequestBody Categoria categoria){
        try{
            Categoria categoriaActualizada = categoriaService.actualizarCategoria(idCategoria, categoria);
            if (categoriaActualizada != null){
                return new ResponseEntity<>(categoriaActualizada, HttpStatus.OK);
            }else{
                throw new Exception("Categoria no encontrada para actualizar");
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long idCategoria){
        try{
            categoriaService.eliminarCategoria(idCategoria);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
