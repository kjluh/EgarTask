package egar.egartask.controllers;

import egar.egartask.entites.Department;
import egar.egartask.entites.Employee;
import egar.egartask.entites.PostEmployee;
import egar.egartask.entites.WorkTime;
import egar.egartask.repository.DepartmentRepository;
import egar.egartask.repository.EmployeeRepository;
import egar.egartask.repository.PostEmployeeRepository;
import egar.egartask.repository.WorkTimeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SalaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private WorkTimeRepository workTimeRepository;

    private static Employee employee = new Employee();
    private static Department department = new Department();
    private static PostEmployee post = new PostEmployee();
    private static WorkTime workTime = new WorkTime();

    @BeforeAll
    static void setUp(){
        department.setDepartmentName("IT");
        department.setId(1l);
        department.setSalaryPercent(10f);

        post.setId(1l);
        post.setPostName("developer");
        post.setPostSalary(20);

        employee.setId(1l);
        employee.setName("name");
        employee.setFamily("family");
        employee.setSecondName("secondName");
        employee.setWorking(true);
        employee.setHiringDate(LocalDate.now().minusMonths(4));
        employee.setSalary(1);
        employee.setDepartment(department);
        employee.setPostEmployee(post);

        workTime.setId(1l);
        workTime.setComeTime(LocalTime.now());
        workTime.setOutTime(LocalTime.now().plusHours(10));
        workTime.setNow(LocalDate.now().minusMonths(1));
        workTime.setEmployee(employee);
    }


    @Test
    void getAllEmplSalary() throws Exception{
        mockMvc.perform(
                        get("/salary")
                .header("Authorization", "Basic " +
                        Base64.getEncoder().encodeToString(("Admin" + ":" + "password").getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().isOk());
    }
    @Test
    void getEmplSalary() throws Exception{
        List<WorkTime> list = new ArrayList<>();
        list.add(workTime);
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        when(workTimeRepository.findByEmployee_IdAndNow(any(),any())).thenReturn(list);
        mockMvc.perform(
                        get("/salary/1")
                .header("Authorization", "Basic " +
                        Base64.getEncoder().encodeToString(("Admin" + ":" + "password").getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.family").value("family"));
    }

    @Test
    void getEmplSalaryException() throws Exception{
        mockMvc.perform(
                        get("/salary/11")       .header("Authorization", "Basic " +
                                Base64.getEncoder().encodeToString(("Admin" + ":" + "password").getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().is(404));
    }

    @Test
    void setSalary() throws Exception{
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        mockMvc.perform(
                        patch("/salary/1/")
                                .queryParam("sal", "50")
                                .header("Authorization", "Basic " +
                                Base64.getEncoder().encodeToString(("Admin" + ":" + "password").getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salary").value(50));
    }

    @Test
    void setSalaryException() throws Exception{
        mockMvc.perform(
                        patch("/salary/111/50")       .header("Authorization", "Basic " +
                                Base64.getEncoder().encodeToString(("Admin" + ":" + "password").getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().is(404));
    }
}