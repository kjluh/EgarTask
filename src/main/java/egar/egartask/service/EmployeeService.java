package egar.egartask.service;

import egar.egartask.dto.EmpDto;
import egar.egartask.dto.mapper.EmployeeMapper;
import egar.egartask.entites.Employee;
import egar.egartask.repository.EmployeeRepository;
import egar.egartask.repository.PostEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private PostEmployeeRepository postEmployeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository, PostEmployeeRepository postEmployeeRepository) {
        this.employeeRepository = employeeRepository;
        this.postEmployeeRepository = postEmployeeRepository;
    }

    public Employee save(EmpDto empDto) {
        Employee newEmployee= EmployeeMapper.mapper.toEmp(empDto);
        newEmployee.setPostEmployee(postEmployeeRepository
                .findByPostNameContainingIgnoreCase(empDto.getPostEmployeeName()));

        return employeeRepository.save(newEmployee);
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public EmpDto update(Employee employee) {
        try {
            employeeRepository.findById(employee.getId()).orElseThrow();
        } catch (RuntimeException e) {
            return null;
        }
        return EmployeeMapper.mapper.toDto(employeeRepository.save(employee));
    }

    public List<Employee> get(String name, String family) {
        return employeeRepository.findAllByNameContainsIgnoreCaseAndFamilyContainsIgnoreCase(name, family);
    }

    public Employee find(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<EmpDto> getAll() {
        List<EmpDto> list = new ArrayList<>();
        for (Employee e : employeeRepository.findAll()) {
            if (e.isWorking()) {
                list.add(EmployeeMapper.mapper.toDto(e));
            }
        }
        return list;
    }

    public boolean delete(Long id) {
        if (null == employeeRepository.findById(id).orElse(null)) {
            return false;
        } else
        employeeRepository.deleteById(id);
        return true;
    }
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }
}
