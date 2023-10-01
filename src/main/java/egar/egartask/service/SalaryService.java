package egar.egartask.service;

import egar.egartask.dto.EmpToSalary;
import egar.egartask.entites.Department;
import egar.egartask.entites.Employee;
import egar.egartask.repository.DepartmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryService {

    private DepartmentRepository departmentRepository;
    private EmployeeService employeeService;
    private AccountingService accountingService;

    public SalaryService(DepartmentRepository departmentRepository, EmployeeService employeeService, AccountingService accountingService) {
        this.departmentRepository = departmentRepository;
        this.employeeService = employeeService;
        this.accountingService = accountingService;
    }

    public List<EmpToSalary> getAllEmplSalary() {
        List<Employee> employees = employeeService.getAllEmployee();
        List<EmpToSalary> empToSalaries = new ArrayList<>();
        for (Employee e : employees) {
            if (e.isWorking()) {
                EmpToSalary empToSalary = new EmpToSalary();
                empToSalary.setName(e.getName());
                empToSalary.setFamily(e.getFamily());
                empToSalary.setDepartmentName(e.getDepartment().getDepartmentName());
                empToSalary.setWorkingTime(accountingService.workingTime(e.getId())
                        .getWorkingTime());
                empToSalary.setSalary(e.getSalary() *
                        e.getDepartment().getSalaryPercent() *
                        accountingService.workingTime(e.getId())
                                .getWorkingTime());
                empToSalaries.add(empToSalary);
            }
        }
        return empToSalaries;
    }

    public EmpToSalary getEmplSalary(Long id) {
        Employee employee = employeeService.find(id);
        EmpToSalary empToSalary = new EmpToSalary();
        if ( employee==null || employee.isWorking()) {
            empToSalary.setName(employee.getName());
            empToSalary.setFamily(employee.getFamily());
            empToSalary.setDepartmentName(employee.getDepartment().getDepartmentName());
            empToSalary.setWorkingTime(accountingService.workingTime(employee.getId())
                    .getWorkingTime());
            empToSalary.setSalary(employee.getSalary() *
                    employee.getDepartment().getSalaryPercent() *
                    accountingService.workingTime(employee.getId())
                            .getWorkingTime());
        } else {
            return null;
        }
        return empToSalary;
    }

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department getDepartment(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department patchDepartment(Department department){
        return departmentRepository.save(department);
    }

    public Department deleteDepartment(Long id) {
        Department department= departmentRepository.findById(id).orElse(null);
        if (null== department){
            return null;
        } else
            departmentRepository.delete(department);
        return department;
    }
}
