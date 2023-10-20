package egar.egartask.repository;

import egar.egartask.entites.WorkTime;

import java.time.LocalDate;
import java.util.List;

public interface WorkTimeRepository extends StartRepository {
     @Override
     List<WorkTime> findByEmployeeIdAndNow(Long id, LocalDate localDate);


}
