package com.lta.cursoapis.curso_introduccion_apis.service.impl;

import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
import com.lta.cursoapis.curso_introduccion_apis.entity.EstadoProducto;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;
import com.lta.cursoapis.curso_introduccion_apis.repository.CategoriaRepository;
import com.lta.cursoapis.curso_introduccion_apis.repository.ProductoRepository;
import com.lta.cursoapis.curso_introduccion_apis.service.ProductoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    @SneakyThrows
    public Producto registrarProducto(Long categoriaId, Producto producto) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(()-> new Exception("Categoría con ID " + categoriaId + " no encontrada"));

        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

//    @Override
//    public Producto registrarProducto(Producto producto) {
////        Producto nuevoProducto = productoRepository.save(producto);
////        return nuevoProducto;
//        return productoRepository.save(producto);
//    }

    @Override
    public List<Producto> listarProductos() {
//        List<Producto>  productos = productoRepository.findAll();
//        return productos.stream().toList();
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> buscarPorNombre(String nombre) {
//        Optional<Producto> productoOptional = productoRepository.findByNombreProducto(nombre);
//        return productoOptional;
        return productoRepository.findByNombreProducto(nombre);
    }

    @Override
    public Optional<Producto> buscarPorId(Long idProducto) {
        return productoRepository.findByIdProducto(idProducto);
    }

    @Override
    @SneakyThrows
    public Producto actualizarProducto(Long idProducto, Producto producto) {
        Producto productoExistente = productoRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new Exception("Producto con ID "+ idProducto + " no encontrado"));

         productoExistente.setNombreProducto(producto.getNombreProducto());
         productoExistente.setDescripcion(producto.getDescripcion());
         productoExistente.setPrecio(producto.getPrecio());
         productoExistente.setCantidad(producto.getCantidad());
         productoExistente.setEstadoProducto(producto.getEstadoProducto());

         if (producto.getCategoria() != null && producto.getCategoria().getIdCategoria() != null){
             Categoria categoria = categoriaRepository.findById(producto.getCategoria().getIdCategoria())
                     .orElseThrow(()-> new Exception("Categoria no encontrada"));
             productoExistente.setCategoria(categoria);
         }
         return productoRepository.save(productoExistente);
    }

    @Override
    @SneakyThrows
    public void eliminarProducto(Long idProducto) {
        Producto productoExistente = productoRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new Exception("Producto con ID "+ idProducto + " no encontrado"));
        productoRepository.deleteById(idProducto);
    }

    @Override
    @SneakyThrows
    public Producto cambiarEstadoProducto(Long idProducto, EstadoProducto nuevoEstadoProducto) {
        Producto productoExistente = productoRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new Exception("Producto con ID "+ idProducto + " no encontrado"));
        productoExistente.setEstadoProducto(nuevoEstadoProducto);
        return productoRepository.save(productoExistente);
    }

    @Override
    public List<Producto> obtenerProductosPorEstado(EstadoProducto estadoProducto) {
        return productoRepository.findByEstadoProducto(estadoProducto);
    }
}
