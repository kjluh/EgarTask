package egar.egartask.repository;

import egar.egartask.entites.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<Consumer,Long> {
    Consumer findByLoginIgnoreCase(String login);
}
