package egar.egartask.entites;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String family;

    private String secondName;

    private LocalDate hiringDate;

    private boolean working;
    /**
     * Зарплата в час
     */
    private int salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
