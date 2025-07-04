package com.lta.cursoapis.curso_introduccion_apis.service;

import com.lta.cursoapis.curso_introduccion_apis.entity.EstadoProducto;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    Producto registrarProducto(Producto producto);

    List<Producto> listarProductos();
    Optional<Producto> buscarPorNombre(String nombre);
    Optional<Producto> buscarPorId(Long idProducto);
    Producto actualizarProducto(Long idProducto, Producto producto);

    void eliminarProducto(Long idProducto);

    Producto cambiarEstadoProducto(Long idProducto, EstadoProducto nuevoEstadoProducto);
    List<Producto> obtenerProductosPorEstado(EstadoProducto estadoProducto);
}
