package egar.egartask.repository;

import egar.egartask.entites.Employee;
import egar.egartask.entites.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDate;
import java.util.List;

@NoRepositoryBean
public interface StartRepository extends JpaRepository<WorkTime,Long> {

    List<WorkTime> findByEmployee_IdAndNow(Long id, LocalDate localDate);
}
