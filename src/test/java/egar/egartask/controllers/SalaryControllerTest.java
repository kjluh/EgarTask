package egar.egartask.controllers;

import egar.egartask.entites.*;
import egar.egartask.repository.ConsumerRepository;
import egar.egartask.repository.EmployeeRepository;
import egar.egartask.repository.WorkTimeRepository;
import org.h2.mvstore.Page;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

    @MockBean
    private ConsumerRepository consumerRepository;

    private static Consumer admin = new Consumer();

    @BeforeAll
    static void setUp() {
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

        admin.setLogin("Admin");
        admin.setPassword("$2a$12$r5kvsAvblFXSPSyv5EfQt.W6TAppM3vg83Sz0NPOINYLnixK0JUf2");
        admin.setRole(Role.ADMIN);
    }


    @Test
    void getAllEmplSalary() throws Exception {
        when(consumerRepository.findByLoginIgnoreCase(any())).thenReturn(admin);
        List<Employee> list = new ArrayList<>();
        list.add(employee);
        when(employeeRepository.findAll(PageRequest.of(0, 1))).thenReturn(new PageImpl<>(list));
        mockMvc.perform(
                        get("/salary")
                                .queryParam("number", "0")
                                .queryParam("size", "1")
                                .header("Authorization", "Basic " +
                                        Base64.getEncoder().encodeToString(("Admin" + ":" + "password").getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().isOk());
    }

    @Test
    void getEmplSalary() throws Exception {
        when(consumerRepository.findByLoginIgnoreCase(any())).thenReturn(admin);
        List<WorkTime> list = new ArrayList<>();
        list.add(workTime);
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        when(workTimeRepository.findByEmployeeIdAndNow(any(), any())).thenReturn(list);
        mockMvc.perform(
                        get("/salary/1")
                                .header("Authorization", "Basic " +
                                        Base64.getEncoder().encodeToString(("Admin" + ":" + "password").getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.family").value("family"));
    }

    @Test
    void getEmplSalaryException() throws Exception {
        when(consumerRepository.findByLoginIgnoreCase(any())).thenReturn(admin);
        mockMvc.perform(
                        get("/salary/11")
                                .header("Authorization", "Basic " +
                                        Base64.getEncoder().encodeToString(("Admin" + ":" + "password").getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().is(400));
    }

    @Test
    void setSalary() throws Exception {
        when(consumerRepository.findByLoginIgnoreCase(any())).thenReturn(admin);
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
    void setSalaryException() throws Exception {
        when(consumerRepository.findByLoginIgnoreCase(any())).thenReturn(admin);
        mockMvc.perform(
                        patch("/salary/111/50")
                                .header("Authorization", "Basic " +
                                        Base64.getEncoder().encodeToString(("Admin" + ":" + "password").getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().is(404));
    }
}