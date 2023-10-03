package egar.egartask.service;

import egar.egartask.entites.Department;
import egar.egartask.entites.PostEmployee;
import egar.egartask.repository.DepartmentRepository;
import egar.egartask.repository.PostEmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentAndPostService {

    private DepartmentRepository departmentRepository;

    private PostEmployeeRepository postEmployeeRepository;

    public DepartmentAndPostService(DepartmentRepository departmentRepository, PostEmployeeRepository postEmployeeRepository) {
        this.departmentRepository = departmentRepository;
        this.postEmployeeRepository = postEmployeeRepository;
    }

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department getDepartment(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department patchDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (null == department) {
            return null;
        } else
            departmentRepository.delete(department);
        return department;
    }

    public PostEmployee getPostEmployee(Long id) {
        return postEmployeeRepository.findById(id).orElse(null);
    }

    public PostEmployee createPostEmployee(PostEmployee postEmployee) {
        if (null == postEmployeeRepository.findByPostNameContainingIgnoreCase(postEmployee.getPostName())) {
            return postEmployeeRepository.save(postEmployee);
        } else
            return null;
    }

    public PostEmployee updatePostEmployee(PostEmployee postEmployee) {
        try {
            postEmployeeRepository.findById(postEmployee.getId()).orElseThrow();
        } catch (NullPointerException e) {
            return null;
        }
        return postEmployeeRepository.save(postEmployee);
    }

    public PostEmployee deletePostEmployee(Long id) {
        PostEmployee postEmployee = postEmployeeRepository.findById(id).orElse(null);
        if (null == postEmployee) {
            return null;
        } else {
            postEmployeeRepository.deleteById(id);
            return postEmployee;
        }
    }
}
