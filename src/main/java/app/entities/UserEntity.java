package app.entities;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String fullName;

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="tblUserRoles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="id")})
    private List<RoleEntity> roles;

    private boolean enabled = true;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;


    public UserEntity() {
        roles=new ArrayList<RoleEntity>();
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = true;
        roles=new ArrayList<RoleEntity>();
    }
}