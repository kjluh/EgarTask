package egar.egartask.repository;

import egar.egartask.entites.NotWorkingDays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotWorkingDaysRepository extends JpaRepository<NotWorkingDays,Long> {
    List<NotWorkingDays> getNotWorkingDaysByEmployee_Id(Long id);
}
