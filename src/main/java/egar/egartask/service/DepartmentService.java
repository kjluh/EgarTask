package egar.egartask.service;

import egar.egartask.dto.DepartmentDto;
import egar.egartask.entites.Department;
import egar.egartask.entites.PostEmployee;
import egar.egartask.mapper.DepartmentMapper;
import egar.egartask.repository.DepartmentRepository;
import egar.egartask.repository.PostEmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository,
                             DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    /**
     * Сохраняем отдел в бд.
     * @param department отдел.
     * @return отдел.
     */
    @Transactional
    public DepartmentDto saveDepartment(Department department) {
        return departmentMapper.toDepartmentDto(departmentRepository.save(department));
    }

    /**
     * Получаем отдел из бд.
     * @param id отдела.
     * @return отдел.
     */
    public DepartmentDto getDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        return department != null ? departmentMapper.toDepartmentDto(department) : null;
    }

    /**
     * Обновляем отдел в бд.
     * @param department отдел.
     * @return отдел.
     */
    @Transactional
    public DepartmentDto patchDepartment(Department department) {
        return departmentMapper.toDepartmentDto(departmentRepository.update(department));
    }

    /**
     * Удаляем отдел из бд.
     * @param id отдела.
     * @return удаленный отдел.
     */
    @Transactional
    public DepartmentDto deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (null == department) {
            return null;
        } else
            departmentRepository.delete(department);
        return departmentMapper.toDepartmentDto(department);
    }
}
