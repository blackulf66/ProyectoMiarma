package com.salesianostriana.dam.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends
        JpaRepository<Producto, Long> {
}
