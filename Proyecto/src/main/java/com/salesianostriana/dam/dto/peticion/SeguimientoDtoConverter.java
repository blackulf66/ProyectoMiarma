package com.salesianostriana.dam.dto.peticion;
import com.salesianostriana.dam.model.Seguimiento;
import com.salesianostriana.dam.users.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeguimientoDtoConverter {

    public static GetSeguimientoDTO SeguimientoToGetSeguimientoDto(Seguimiento p){
        return GetSeguimientoDTO.builder()
                .id(p.getId())
                .texto(p.getPeticion())
                .destino(p.getDestino().getNick())
                .build();

    }

    public Seguimiento createSeguimientoDtoToSeguimiento(CreateSeguimientoDTO createSeguimientoDto, UserEntity user, UserEntity user2){
        return Seguimiento.builder()
                .peticion(createSeguimientoDto.getTexto() + user2.getNick())
                .destino(user2)
                .build();

    }
}
