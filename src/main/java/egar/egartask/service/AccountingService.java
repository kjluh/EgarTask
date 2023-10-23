package egar.egartask.service;

import egar.egartask.dto.EmpDto;
import egar.egartask.dto.WorkTimeDto;
import egar.egartask.dto.WorkingTimeDto;
import egar.egartask.mapper.EmployeeMapper;
import egar.egartask.mapper.WorkTimeMapper;
import egar.egartask.entites.Employee;
import egar.egartask.entites.NotWorkingDays;
import egar.egartask.entites.WorkTime;
import egar.egartask.repository.EmployeeRepository;
import egar.egartask.repository.NotWorkingDaysRepository;
import egar.egartask.repository.WorkTimeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class AccountingService {

    private final EmployeeRepository employeeRepository;
    private final WorkTimeRepository workTimeRepository;
    private final NotWorkingDaysRepository notWorkingDaysRepository;
    private final EmployeeMapper employeeMapper;
    private final WorkTimeMapper workTimeMapper;

    public AccountingService(EmployeeRepository employeeRepository, WorkTimeRepository workTimeRepository, NotWorkingDaysRepository notWorkingDaysRepository, EmployeeMapper employeeMapper, WorkTimeMapper workTimeMapper) {
        this.employeeRepository = employeeRepository;
        this.workTimeRepository = workTimeRepository;
        this.notWorkingDaysRepository = notWorkingDaysRepository;
        this.employeeMapper = employeeMapper;
        this.workTimeMapper = workTimeMapper;
    }

    /**
     * Метод, фиксирует время прихода сотрудника в бд, повторный вход не записывается.
     * @param id сотрудника.
     * @return дто сотрудника.
     */
    public EmpDto comeEmp(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (workTimeRepository.findByEmployeeIdAndNow(id, LocalDate.now()).size() != 0) {
            List<WorkTime> workTime = workTimeRepository.findByEmployeeIdAndNow(id, LocalDate.now());
            for (WorkTime w : workTime) {
                if (w.getOutTime() == null) {
                    return null;
                }
            }
        }
        if (null != employee && employee.isWorking()) {
            WorkTime workTime = new WorkTime();
            workTime.setComeTime(LocalTime.now());
            workTime.setNow(LocalDate.now());
            workTime.setEmployee(employee);
            workTimeRepository.save(workTime);
            return employeeMapper.toDto(employee);
        } else {
            return null;
        }
    }

    /**
     * Метод, фиксирует время ухода сотрудника в бд.
     * @param id сотрудника.
     * @return дто сотрудника.
     */
    public EmpDto outEmp(Long id) {
        List<WorkTime> workTime = workTimeRepository.findByEmployeeIdAndNow(id, LocalDate.now());
        if (workTime.size() == 0) {
            return null;
        }
        for (WorkTime w : workTime) {
            if (workTime.size() != 0 && w.getOutTime() == null) {
                w.setOutTime(LocalTime.now());
                workTimeRepository.save(w);
            }
        }
        return employeeMapper.toDto(employeeRepository.findById(id).orElse(null));
    }

    /**
     * Получение данных при времени прихода и ухода сотрудника в определенный день.
     * @param date дата для запроса.
     * @param family сотрудника.
     * @return  мапа приходов с уходами сотрудника.
     */
    public Map<EmpDto, List<WorkTimeDto>> search(LocalDate date, String family) {
        List<Employee> employees = employeeRepository.findByDateAndFamily(family);
        Map<EmpDto, List<WorkTimeDto>> workTimes = new HashMap<>();
        for (Employee e : employees) {
            workTimes.put(employeeMapper.toDto(e), mapperList(workTimeRepository.findByEmployeeIdAndNow(e.getId(), date)));
        }

        return workTimes;
    }

    /**
     * Получение часов отработанных сотрудником за прошлый месяц.
     * @param id сотрудника.
     * @return сущность с данными.
     */
    public WorkingTimeDto workingTime(Long id) {
        LocalDate start = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate end = LocalDate.now().minusMonths(1)
                .withDayOfMonth(LocalDate.now().getMonth().minus(1)
                        .length(
                                LocalDate.now().getYear() % 4 == 0
                                        && LocalDate.now().getYear() % 100 != 0
                                        || LocalDate.now().getYear() % 400 == 0));
        WorkingTimeDto workingTimeDto = workTimeCounter(id, start, end);
        if (workingTimeDto == null) {
            return null;
        }
        workingTimeDto.setStart(start);
        workingTimeDto.setEnd(end);
        return workingTimeDto;
    }

    /**
     * Получение часов отработанных сотрудником за выбранный промежуток времени.
     * @param id сотрудника.
     * @param start дата начальная.
     * @param end дата до которой запрос включительно.
     * @return  сущность с данными.
     */
    public WorkingTimeDto workingTime(Long id, LocalDate start, LocalDate end) {
        WorkingTimeDto workingTimeDto = workTimeCounter(id, start, end);
        if (workingTimeDto == null) {
            return null;
        }
        workingTimeDto.setStart(start);
        workingTimeDto.setStart(end);
        return workingTimeDto;
    }

    /**
     * Запись в базу данных о нерабочих днях сотрудника.
     * @param id сотрудника.
     * @param start дата начальная.
     * @param end дата конечная.
     * @param info причина.
     * @return сущность с данными сотрудника
     */
    public NotWorkingDays setNotWorkingDays(Long id, LocalDate start, LocalDate end, String info) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            return null;
        } else {
            NotWorkingDays notWorkingDays = new NotWorkingDays();
            notWorkingDays.setEmployee(employee);
            notWorkingDays.setStartDate(start);
            notWorkingDays.setFinishDate(end);
            notWorkingDays.setComment(info);
            notWorkingDaysRepository.save(notWorkingDays);
            return notWorkingDays;
        }
    }

    /**
     * Получение всех периодов нерабочих дней сотрудника.
     * @param id сотрудника.
     * @return коллекция нерабочих дней.
     */
        public List<NotWorkingDays> getNotWorkingDays (Long id, String com){
            if (null != id && null != com) {
                return notWorkingDaysRepository.getNotWorkingDaysByCommentsAndId(id,com);
            } else if (null != com) {
                return notWorkingDaysRepository.getNotWorkingDaysByComments(com);
            } else if(null != id) {
                return notWorkingDaysRepository.getNotWorkingDaysByEmployee_Id(id);
            } else
            return null;
        }

    /**
     * Маппер из сущности в ДТО.
     * @param workTimes коллекция для маппига.
     * @return List<WorkTimeDto>
     */
    private List<WorkTimeDto> mapperList(List<WorkTime> workTimes) {
        List<WorkTimeDto> workTimeDtoList = new ArrayList<>();
        for (int i = 0; i < workTimes.size(); i++) {
            workTimeDtoList.add(workTimeMapper.toDto(workTimes.get(i)));
        }
        return workTimeDtoList;
    }

    /**
     * Метод для подсчета рабочего времени.
     * @param id сотрудника.
     * @param start начальная дата.
     * @param end конечная дата.
     * @return WorkingTimeDto.
     */
    private WorkingTimeDto workTimeCounter(Long id, LocalDate start, LocalDate end) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (null == employee) {
            return null;
        }
        int hour = 0;
        List<WorkTime> workTime;
        LocalDate count = start.minusDays(1);
        do {
            count = count.plusDays(1);
            workTime = workTimeRepository.findByEmployeeIdAndNow(id, count);
            for (WorkTime w : workTime) {
                hour += (w.getOutTime().getHour() - w.getComeTime().getHour()) * 60 +
                        w.getOutTime().getMinute() - w.getComeTime().getMinute();
            }
        } while (!count.equals(end));
        WorkingTimeDto workingTimeDto;
        workingTimeDto = employeeMapper.toWorkingTimeDto(employee);
        workingTimeDto.setWorkingTime(((float) hour / 60 + (float) hour % 60 / 100));
        return workingTimeDto;
    }
}
