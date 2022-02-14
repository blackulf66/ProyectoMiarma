package com.salesianostriana.dam.users.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.hibernate.annotations.Parameter;



import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements UserDetails {

    @Id
    private UUID id;

    private String email;

    private String password;

    private String avatar;

    private Date fecha;

    private String nick;

    private UserRole role;

    private boolean perfilprivado;

    public UserEntity(UUID id, String email, String password, String avatar, Date fecha, String nick, UserRole role,Boolean perfilprivado) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.fecha = fecha;
        this.nick = nick;
        this.role = role;
        this.perfilprivado = perfilprivado;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getUsername() {
        return email;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

}
