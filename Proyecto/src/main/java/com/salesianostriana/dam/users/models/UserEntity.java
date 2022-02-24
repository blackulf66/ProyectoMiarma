package com.salesianostriana.dam.users.models;

import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.model.Seguimiento;
import lombok.*;

import org.hibernate.annotations.Fetch;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
@Getter @Setter
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    private String email;

    private String password;

    private String avatar;

    private LocalDate fecha;

    private String nick;

    private UserRole role;

    private boolean perfilprivado;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Post> posts;

   @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "relation",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))

    private List<UserEntity> following;

    @OneToMany(mappedBy = "destino")
    @Builder.Default
    private List<Seguimiento> seguido= new ArrayList<>();


    //Helpers

    public void addPeticion(Seguimiento p){
        if(this.getSeguido()== null){
            this.setSeguido(new ArrayList<>());
        }

    }
    public void addSeguidor(UserEntity u) {
        this.following = List.of(u);
        u.getFollowing().add(this);
    }

    public void removeSeguidor(UserEntity u) {
        u.getFollowing().remove(this);
        this.following = null;
    }
    public List<UserEntity> getFollowing() {
        return following;
    }

    public void setFollowing(List<UserEntity> following) {
        this.following = following;
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
