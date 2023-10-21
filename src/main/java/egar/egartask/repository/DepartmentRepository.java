package egar.egartask.repository;

import egar.egartask.entites.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public class DepartmentRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public Department save(Department department) {
        Department d = new Department();
        d.setDepartmentName(department.getDepartmentName());
        d.setSalaryPercent(department.getSalaryPercent());
        entityManager.persist(d);
        return department;
    }

    public Optional<Department> findById(Long id) {
        Optional<Department> department = Optional.ofNullable(entityManager.find(Department.class, id));
        return department;
    }

    public Department update(Department department) {
        entityManager.merge(department);
        return department;
    }

    public Department delete(Department department) {
        entityManager.remove(department);
        return department;
    }


}
