package egar.egartask.repository;

import egar.egartask.entites.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Long> {
    List<Employee> findAllByNameContainsIgnoreCaseAndFamilyContainsIgnoreCase(String name, String family);

    @Query(value = "select * from employee where family like ?%",nativeQuery = true)
    List<Employee> findByDateAndFamily(String family);

    void deleteById(Long id);

    Optional<Employee> findById(Long id);

    Employee save(Employee employee);

    List<Employee> findAll();
}
