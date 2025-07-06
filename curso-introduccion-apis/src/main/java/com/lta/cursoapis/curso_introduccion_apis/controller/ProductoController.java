package com.lta.cursoapis.curso_introduccion_apis.controller;

import com.lta.cursoapis.curso_introduccion_apis.dto.ProductoDTO;
import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
import com.lta.cursoapis.curso_introduccion_apis.entity.EstadoProducto;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;
import com.lta.cursoapis.curso_introduccion_apis.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/registrar/{categoriaId}")
    public ResponseEntity<?> registrarProducto(
            // @RequestBody Producto producto
            @PathVariable(required = true) Long categoriaId,
            @RequestParam("nombreProducto") String nombreProducto,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") Double precio,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("estado") EstadoProducto estado) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombreProducto(nombreProducto);
        productoDTO.setDescripcion(descripcion);
        productoDTO.setPrecio(precio);
        productoDTO.setCantidad(cantidad);
        productoDTO.setEstado(estado);

        ProductoDTO productoBBDD = productoService.registrarProducto(categoriaId, productoDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(productoBBDD);
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<ProductoDTO> productosDtos = productoService.listarProductos();
        return ResponseEntity.ok(productosDtos);
    }

    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        Optional<ProductoDTO> productoDTO = productoService.buscarPorNombre(nombre);
        return productoDTO.isPresent() ? ResponseEntity.ok(productoDTO.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @GetMapping("/buscar/id/{idProducto}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idProducto) {
        Optional<ProductoDTO> productoDTO = productoService.buscarPorId(idProducto);
        return productoDTO.isPresent() ? ResponseEntity.ok(productoDTO.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @PutMapping("/actualizar/{idProducto}")
    public ResponseEntity<?> actualizarProducto(
            // @PathVariable Long idProducto,@RequestBody Producto producto
            @PathVariable Long idProducto,
            @RequestParam("nombreProducto") String nombreProducto,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") Double precio,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("estado") EstadoProducto estado) {
        try {
            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setNombreProducto(nombreProducto);
            productoDTO.setDescripcion(descripcion);
            productoDTO.setPrecio(precio);
            productoDTO.setCantidad(cantidad);
            productoDTO.setEstado(estado);

            ProductoDTO productoActualizado = productoService.actualizarProducto(idProducto, productoDTO);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
            // System.out.println(exception.getMessage());
        }
    }

    @DeleteMapping("/{idProducto}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long idProducto) {
        try {
            productoService.eliminarProducto(idProducto);
            // return ResponseEntity.status(HttpStatus.NO_CONTENT).body();
            return ResponseEntity.noContent().build();
        } catch (Exception exception) {
            // return
            // ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 sin cuerpo
        }
    }

    @PutMapping("/estado/{idProducto}")
    public ResponseEntity<?> cambiarEstadoProducto(@PathVariable Long idProducto,
            @RequestBody EstadoProducto estadoProducto) {
        try {
            ProductoDTO productoActualizado = productoService.cambiarEstadoProducto(idProducto, estadoProducto);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 sin cuerpo
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ProductoDTO>> listarProductosPorEstado(@PathVariable EstadoProducto estado) {
        List<ProductoDTO> productoDTOS = productoService.obtenerProductosPorEstado(estado);
        return ResponseEntity.ok(productoDTOS);
    }
}
