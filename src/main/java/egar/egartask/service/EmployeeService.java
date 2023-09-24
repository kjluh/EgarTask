package egar.egartask.service;

import egar.egartask.dto.EmpDto;
import egar.egartask.dto.mapper.EmployeeMapper;
import egar.egartask.entites.Employee;
import egar.egartask.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(EmpDto empDto) {
        if (empDto.getHiringDate() != null) {
            empDto.setHiringDate(LocalDate.now());
        }
        return employeeRepository.save(EmployeeMapper.mapper.toEmp(empDto));
    }

    public EmpDto update(Employee employee) {
        try {
            employeeRepository.findById(employee.getId()).orElseThrow();
        } catch (RuntimeException e){
            return null;
        }
        return EmployeeMapper.mapper.toDto(employeeRepository.save(employee));
    }

    public List<Employee> get(String name, String family) {
        return employeeRepository.findAllByNameContainsIgnoreCaseAndFamilyContainsIgnoreCase(name, family);
    }

    public Employee find(Long id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public boolean delete(Long id) {
        if (null== employeeRepository.findById(id).orElse(null)) {
            return false;
        }
        employeeRepository.deleteById(id);
        return true;
    }
}