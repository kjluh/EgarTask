package egar.egartask.repository;

import egar.egartask.entites.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkTimeRepository extends StartRepository {
     @Override
     List<WorkTime> findByEmployee_IdAndNow(Long id,LocalDate localDate);
}
