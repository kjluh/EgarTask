package egar.egartask.service;

import egar.egartask.dto.EmpDto;
import egar.egartask.mapper.EmployeeMapper;
import egar.egartask.mapper.WorkTimeMapper;
import egar.egartask.entites.Employee;
import egar.egartask.repository.EmployeeRepository;
import egar.egartask.repository.PostEmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PostEmployeeRepository postEmployeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, PostEmployeeRepository postEmployeeRepository, EmployeeMapper employeeMapper, WorkTimeMapper workTimeMapper) {
        this.employeeRepository = employeeRepository;
        this.postEmployeeRepository = postEmployeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public EmpDto save(EmpDto empDto) {
        Employee newEmployee = employeeMapper.toEmp(empDto);
        newEmployee.setWorking(true);
        newEmployee.setPostEmployee(postEmployeeRepository
                .findByPostNameContainingIgnoreCase(empDto.getPostEmployeeName()));
        employeeRepository.save(newEmployee);

        return empDto;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public EmpDto update(Employee employee) {
        Employee e = employeeRepository.findById(employee.getId()).orElse(null);
        if (null == e) {
            return null;
        }
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    public List<EmpDto> get(String name, String family) {
        return employeeRepository.findAllByNameContainsIgnoreCaseAndFamilyContainsIgnoreCase(name, family)
                .stream().map(employeeMapper::toDto).collect(Collectors.toList());
    }

    public Employee find(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public EmpDto findDto(Long id) {
        return employeeMapper.toDto(employeeRepository.findById(id).orElse(null));
    }

    public List<EmpDto> getAll() {
        List<EmpDto> list = new ArrayList<>();
        for (Employee e : employeeRepository.findAll()) {
            if (e.isWorking()) {
                list.add(employeeMapper.toDto(e));
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

    public List<Employee> getAllEmployee(Integer number, Integer size) {
        return employeeRepository.findAll(PageRequest.of(number, size)).getContent();
    }
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }
}
