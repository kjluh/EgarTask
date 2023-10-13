package egar.egartask.service;

import egar.egartask.entites.Department;
import egar.egartask.entites.PostEmployee;
import egar.egartask.repository.DepartmentRepository;
import egar.egartask.repository.PostEmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department getDepartment(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department patchDepartment(Department department) {
        return departmentRepository.update(department);
    }

    public Department deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (null == department) {
            return null;
        } else
            departmentRepository.delete(department);
        return department;
    }
}
