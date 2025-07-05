package com.lta.cursoapis.curso_introduccion_apis.controller;

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
        Producto producto = new Producto();
        producto.setNombreProducto(nombreProducto);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setCantidad(cantidad);
        producto.setEstadoProducto(estado);

        Producto productoBBDD = productoService.registrarProducto(producto);

        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        Optional<Producto> producto = productoService.buscarPorNombre(nombre);
        return producto.isPresent() ? ResponseEntity.ok(producto.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @GetMapping("/buscar/id/{idProducto}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idProducto) {
        Optional<Producto> producto = productoService.buscarPorId(idProducto);
        return producto.isPresent() ? ResponseEntity.ok(producto.get())
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
//            Producto productoActualizado = new Producto();
            // productoActualizado.setIdProducto(idProducto);
            // productoActualizado.setNombreProducto(producto.getNombreProducto());
            // productoActualizado.setPrecio(producto.getPrecio());
            // productoActualizado.setDescripcion(producto.getDescripcion());
            // productoActualizado.setCantidad(producto.getCantidad());
            // productoActualizado.setEstadoProducto(producto.getEstadoProducto());
            Producto producto = new Producto();
            producto.setNombreProducto(nombreProducto);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setCantidad(cantidad);
            producto.setEstadoProducto(estado);

//            Producto productoBBDD = productoService.actualizarProducto(idProducto, productoActualizado);
            Producto productoActualizado = productoService.actualizarProducto(idProducto, producto);
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
            Producto productoActualizado = productoService.cambiarEstadoProducto(idProducto, estadoProducto);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 sin cuerpo
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Producto>> listarProductosPorEstado(@PathVariable EstadoProducto estado) {
        List<Producto> productos = productoService.obtenerProductosPorEstado(estado);
        return ResponseEntity.ok(productos);
    }
}
