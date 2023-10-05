package egar.egartask.entites;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
