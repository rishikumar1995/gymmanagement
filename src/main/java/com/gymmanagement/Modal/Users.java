package com.gymmanagement.Modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "username",unique = true,nullable = false,length = 100)
    private String username;

    @Column(name = "password",nullable = false,length = 100)
    private String password;

    @Column(name = "mobileno",nullable = false,length = 15)
    private String mobileno;

    @Column(name = "emailid",nullable = false,length = 30)
    private String emailid;

    /*@Column(name = "createdon",nullable = false)
    private LocalDateTime createdon;*/

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "users")
    private List<Token> tokens;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
