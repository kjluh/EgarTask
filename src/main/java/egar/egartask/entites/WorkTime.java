package egar.egartask.entites;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class WorkTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime comeTime;

    private LocalTime outTime;

    private LocalDate now;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;

}
