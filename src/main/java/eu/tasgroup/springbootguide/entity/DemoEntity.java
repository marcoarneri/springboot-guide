package eu.tasgroup.springbootguide.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "PERSONA")
@NoArgsConstructor
public class DemoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PA_FISCAL_CODE", unique = true, nullable = false)
    private String paFiscalCode;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;
}

