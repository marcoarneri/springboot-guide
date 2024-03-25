package eu.tasgroup.springbootguide.repository.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "DEMO")
@NoArgsConstructor
public class DemoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IUV", unique = true, nullable = false)
    private String iuv;

    @Column(name = "LOCATION")
    private String location;
}

