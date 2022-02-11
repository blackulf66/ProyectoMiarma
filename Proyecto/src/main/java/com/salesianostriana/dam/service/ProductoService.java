package com.salesianostriana.dam.service;

import com.salesianostriana.dam.dto.CreateProductoDto;
import com.salesianostriana.dam.model.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductoService {
    Producto save(CreateProductoDto createProductoDto, MultipartFile file);
    List<Producto> findAll();
}
