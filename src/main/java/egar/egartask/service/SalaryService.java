package egar.egartask.service;

import egar.egartask.dto.EmpToSalary;
import egar.egartask.entites.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryService {
    private final EmployeeService employeeService;
    private final AccountingService accountingService;

    public SalaryService(EmployeeService employeeService, AccountingService accountingService) {
        this.employeeService = employeeService;
        this.accountingService = accountingService;
    }

    public List<EmpToSalary> getAllEmplSalary(Integer number, Integer size) {
        List<Employee> employees = employeeService.getAllEmployee(number,size);
        List<EmpToSalary> empToSalaries = new ArrayList<>();
        for (Employee e : employees) {
            if (e.isWorking()) {
                empToSalaries.add(createEmpToSalary(e));
            }
        }
        return empToSalaries;
    }

    public EmpToSalary getEmplSalary(Long id) {
        Employee employee = employeeService.find(id);
        if (employee == null || !employee.isWorking()) {
            return null;
        } else
            return createEmpToSalary(employee);
    }

    private EmpToSalary createEmpToSalary(Employee eOld) {

        EmpToSalary eNew = new EmpToSalary();
        eNew.setName(eOld.getName());
        eNew.setFamily(eOld.getFamily());
        try {
            eNew.setDepartmentName(eOld.getDepartment().getDepartmentName());
            eNew.setWorkingTime(accountingService.workingTime(eOld.getId())
                    .getWorkingTime());
            eNew.setSalary(eOld.getSalary() *
                    eOld.getDepartment().getSalaryPercent() *
                    accountingService.workingTime(eOld.getId())
                            .getWorkingTime() * eOld.getSalary());
            return eNew;
        } catch (NullPointerException e) {
            eNew.setDepartmentName("НЕ УСТАНОВЛЕН!");
            return eNew;
        }
    }

    public Employee setSalary(Long id, Integer salary) {
        Employee employee = employeeService.find(id);
        if (null != employee) {
            employee.setSalary(salary);
            employeeService.save(employee);
        }
        return employee;
    }
}
