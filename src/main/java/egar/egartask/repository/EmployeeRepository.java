package egar.egartask.repository;

import egar.egartask.entites.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findAllByNameContainsIgnoreCaseAndFamilyContainsIgnoreCase(String name, String family);

    @Query(value = "select * from employee where family like ?%",nativeQuery = true)
    List<Employee> findByDateAndFamily(String family);

}
