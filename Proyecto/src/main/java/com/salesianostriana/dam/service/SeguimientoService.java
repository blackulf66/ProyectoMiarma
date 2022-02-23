package com.salesianostriana.dam.service;

import com.salesianostriana.dam.model.Seguimiento;
import com.salesianostriana.dam.repository.SeguimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeguimientoService {

    private final SeguimientoRepository seguimientoRepository;


    public List<Seguimiento> findAll (){

        return seguimientoRepository.findAll();

    }
}
