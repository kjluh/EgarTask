package egar.egartask.controllers;

import egar.egartask.entites.Employee;
import egar.egartask.entites.WorkTime;
import egar.egartask.repository.EmployeeRepository;
import egar.egartask.repository.WorkTimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.core.annotation.Order;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private Employee employee = new Employee();

    private WorkTime workTime = new WorkTime();

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private WorkTimeRepository workTimeRepository;

    @BeforeEach
    void setUp(){
        employee.setId(1L);
        employee.setFamily("ivanov");
        employee.setWorking(true);

        workTime.setNow(LocalDate.now());
        workTime.setEmployee(employee);
    }

    @Test
    @Order(1)
    void ECome() throws Exception {
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        mockMvc.perform(
                        put("/time/come/1"))
                .andExpect(status().isOk());
    }

    @Test
    void EOut() throws Exception {
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        System.out.println(employee.getId());
        mockMvc.perform(
                        put("/time/out/1"))
                .andExpect(status().isOk());
    }

    @Test
    void searchAll() throws Exception {
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        mockMvc.perform(
                        put("/time/come/1"))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/time/search?date=" + LocalDate.now()+"&family=ivanov"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
}