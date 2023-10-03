package egar.egartask.repository;

import egar.egartask.entites.PostEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostEmployeeRepository extends JpaRepository<PostEmployee,Long> {
    PostEmployee findByPostNameContainingIgnoreCase(String name);
}
