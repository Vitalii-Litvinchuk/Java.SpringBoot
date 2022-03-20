package app.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="roles")
public class RoleEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false)
    private String name;

    @ManyToMany(mappedBy="roles")
    private List<UserEntity> users;

    public RoleEntity() {
        users=new ArrayList<UserEntity>();
    }
    public RoleEntity(String name) {
        this.name = name;
        users=new ArrayList<UserEntity>();
    }
}