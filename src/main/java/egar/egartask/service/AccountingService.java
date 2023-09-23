package egar.egartask.service;

import egar.egartask.dto.WorkTimeDto;
import egar.egartask.dto.mapper.WorkTimeMapper;
import egar.egartask.entites.Employee;
import egar.egartask.entites.WorkTime;
import egar.egartask.repository.EmployeeRepository;
import egar.egartask.repository.WorkTimeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountingService {

    private EmployeeRepository employeeRepository;
    private WorkTimeRepository workTimeRepository;

    public AccountingService(EmployeeRepository employeeRepository, WorkTimeRepository workTimeRepository) {
        this.employeeRepository = employeeRepository;
        this.workTimeRepository = workTimeRepository;
    }

    public String comeEmp(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (workTimeRepository.findByEmployee_IdAndNow(id, LocalDate.now()).size() != 0) {
            List<WorkTime> workTime = workTimeRepository.findByEmployee_IdAndNow(id, LocalDate.now());
            for (WorkTime w : workTime) {
                if (w.getOutTime() == null) {
                    return "Вы еще не выходили";
                }
            }
        }
        if (null != employee && employee.isDismissed()) {
            WorkTime workTime = new WorkTime();
            workTime.setComeTime(LocalTime.now());
            workTime.setNow(LocalDate.now());
            workTime.setEmployee(employee);
            workTimeRepository.save(workTime);
            return "Добро пожаловать " + employeeRepository.findById(id).orElseThrow().getName();
        } else {
            return "Вам запрещен доступ";
        }
    }

    public String outEmp(Long id) {
        List<WorkTime> workTime = workTimeRepository.findByEmployee_IdAndNow(id, LocalDate.now());
        for (WorkTime w : workTime) {
            if (workTime.size() != 0 && w.getOutTime() == null) {
                w.setOutTime(LocalTime.now());
                workTimeRepository.save(w);
             }
        }
        return "До свидания " + employeeRepository.findById(id).orElseThrow().getName();
    }

    public Map<Employee, List<WorkTimeDto>> search(LocalDate date, String family) {
        List<Employee> employees = employeeRepository.findByDateAndFamily(family);
        Map<Employee, List<WorkTimeDto>> workTimes = new HashMap<>();
        for (Employee e : employees) {
            workTimes.put(e, mapperList(workTimeRepository.findByEmployee_IdAndNow(e.getId(), date)));
        }

        return workTimes;
    }

    private List<WorkTimeDto> mapperList(List<WorkTime> workTimes) {
        List<WorkTimeDto> workTimeDtoList = new ArrayList<>();
        for (int i = 0; i < workTimes.size(); i++) {
            workTimeDtoList.add(WorkTimeMapper.mapper.toDto(workTimes.get(i)));
        }
        return workTimeDtoList;
    }
}
