package egar.egartask.service;

import egar.egartask.dto.EmpDtoWithSalary;
import egar.egartask.dto.EmpToSalary;
import egar.egartask.entites.Employee;
import egar.egartask.entites.PostEmployee;
import egar.egartask.exception.MyException;
import egar.egartask.mapper.EmployeeMapper;
import egar.egartask.repository.PostEmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class SalaryService {
    private final EmployeeService employeeService;
    private final AccountingService accountingService;

    private final PostEmployeeRepository postEmployeeRepository;

    public SalaryService(EmployeeService employeeService, AccountingService accountingService, PostEmployeeRepository postEmployeeRepository,
                         EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.accountingService = accountingService;
        this.postEmployeeRepository = postEmployeeRepository;
        this.employeeMapper = employeeMapper;
    }

    private Logger logger = LoggerFactory.getLogger(SalaryService.class);
    private final EmployeeMapper employeeMapper;

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
                            .getWorkingTime() * eOld.getPostEmployee().getPostSalary());
            return eNew;
        } catch (NullPointerException e) {
            logger.info("не установлен отдел или должность");
            return eNew;
        }
    }

    /**
     * Изменение зп  в час сотрудника.
     * @param id сотрудника.
     * @param salary зп.
     * @return сотрудник.
     */
    public EmpDtoWithSalary setSalary(Long id, Integer salary) {
        Employee employee = employeeService.find(id);
        if (null != employee) {
            employee.setSalary(salary);
            employeeService.save(employee);
        }
        return employeeMapper.toDtoWithSalary(employee);
    }

    /**
     * Транзакционный метод для обновления данных по зп у сотрудника и у должности
     * @param employeeId д сотрудника
     * @param employeeSalary новая зп сотрудника
     * @param postId ид должности
     * @param postSalary зп должности
     */
    @Transactional
    public void updateSalarys(Long employeeId,
                                Integer employeeSalary,
                                Long postId,
                                Float postSalary){
        try {
            Employee employee = employeeService.find(employeeId);
            employee.setSalary(employeeSalary);
            PostEmployee postEmployee = postEmployeeRepository.findById(postId).orElseThrow();
            postEmployee.setPostSalary(postSalary);
        } catch (RuntimeException e){
            throw new MyException();
        }
    }
}
