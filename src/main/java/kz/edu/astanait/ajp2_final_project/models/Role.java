package kz.edu.astanait.ajp2_final_project.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "RoleEntity")
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long roleId;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "roles_authorities",
            joinColumns = @JoinColumn(name = "role_id"),//role
            inverseJoinColumns = @JoinColumn(name = "authority_id")//authority
    )
    private Set<Authority> authorities = new HashSet<>();

    public Role(){

    }

    public Role(int i) {
        this.roleId = i;
    }

    public long getId() {
        return this.roleId;
    }

    public void setId(long role_id) {
        this.roleId = role_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String role_name) {
        this.name = role_name;
    }


    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}