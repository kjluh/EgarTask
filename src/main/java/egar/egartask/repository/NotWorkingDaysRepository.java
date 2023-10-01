package egar.egartask.repository;

import egar.egartask.entites.NotWorkingDays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotWorkingDaysRepository extends JpaRepository<NotWorkingDays,Long> {
    NotWorkingDays getNotWorkingDaysByEmployee_Id(Long id);
}
