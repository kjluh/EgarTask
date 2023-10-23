package egar.egartask.service;

import egar.egartask.entites.Department;
import egar.egartask.entites.PostEmployee;
import egar.egartask.repository.DepartmentRepository;
import egar.egartask.repository.PostEmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Сохраняем отдел в бд.
     * @param department отдел.
     * @return отдел.
     */
    @Transactional
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    /**
     * Получаем отдел из бд.
     * @param id отдела.
     * @return отдел.
     */
    public Department getDepartment(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    /**
     * Обновляем отдел в бд.
     * @param department отдел.
     * @return отдел.
     */
    @Transactional
    public Department patchDepartment(Department department) {
        return departmentRepository.update(department);
    }

    /**
     * Удаляем отдел из бд.
     * @param id отдела.
     * @return удаленный отдел.
     */
    @Transactional
    public Department deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (null == department) {
            return null;
        } else
            departmentRepository.delete(department);
        return department;
    }
}
