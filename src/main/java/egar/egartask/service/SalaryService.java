package egar.egartask.service;

import egar.egartask.dto.EmpToSalary;
import egar.egartask.entites.Employee;
import egar.egartask.exception.MyException;
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

    /**
     * Получение списка сотрудников с отработанными часами и зп.
     * @param number страницы.
     * @param size сотрудников на странице.
     * @return список сотрудников.
     */
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

    /**
     * Получение зп и отработанного времени одного сотрудника.
     * @param id сотрудника.
     * @return ДТОСотрудника.
     */
    public EmpToSalary getEmplSalary(Long id) {
        Employee employee = employeeService.find(id);
        if (employee == null || !employee.isWorking()) {
            throw new MyException();
        } else
            return createEmpToSalary(employee);
    }

    /**
     * Маппер для подсчета отработанных часов и зп сотрудника за прошлый месяц.
     * @param eOld сущность сутрудник.
     * @return ДТО сотрудника.
     */
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

    /**
     * Изменение зп  в час сотрудника.
     * @param id сотрудника.
     * @param salary зп.
     * @return сотрудник.
     */
    public Employee setSalary(Long id, Integer salary) {
        Employee employee = employeeService.find(id);
        if (null != employee) {
            employee.setSalary(salary);
            employeeService.save(employee);
        }
        return employee;
    }
}
