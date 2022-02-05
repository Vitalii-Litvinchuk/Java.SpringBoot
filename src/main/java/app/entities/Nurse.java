package app.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="tbl_nurse")
public class Nurse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phone;

    @ManyToOne
    private Doctor doctor;
}
