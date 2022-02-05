package app.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="Badges")
public class Badge {
    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="Name")
    private String name;
    @Column(name = "UserId")
    private int userId;
    @Column(name="Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}