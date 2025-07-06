package com.lta.cursoapis.curso_introduccion_apis.mapper;

import com.lta.cursoapis.curso_introduccion_apis.dto.ProductoDTO;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Producto toEntity(ProductoDTO productoDTO){
        return modelMapper.map(productoDTO, Producto.class);
    }

    public void toEntity(ProductoDTO productoDTO, Producto productoExistente){
        modelMapper.map(productoDTO, productoExistente);
    }

    public ProductoDTO toDTO(ProductoDTO productoDTO){
        return modelMapper.map(productoDTO, ProductoDTO.class);
    }
}
