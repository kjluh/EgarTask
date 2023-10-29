package egar.egartask.controllers;

import egar.egartask.entites.Employee;
import egar.egartask.entites.WorkTime;
import egar.egartask.repository.EmployeeRepository;
import egar.egartask.repository.NotWorkingDaysRepository;
import egar.egartask.repository.WorkTimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Base64;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employee.setId(1L);
        employee.setFamily("ivanov");
        employee.setWorking(true);
        employee.setHiringDate(LocalDate.now());
        employee.setSecondName("ivanov");
        employeeRepository.save(employee);
    }

    @Test
    @Order(1)
    void ECome() throws Exception {
        mockMvc.perform(
                        patch("/time/come/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.family").value("ivanov"));
    }

    @Test
    @Order(2)
    void EOut() throws Exception {
        mockMvc.perform(
                        patch("/time/come/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.family").value("ivanov"));
        mockMvc.perform(
                        patch("/time/out/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.family").value("ivanov"));
    }

    @Test
    void searchAll() throws Exception {
        String date = String.valueOf(LocalDate.now());
        mockMvc.perform(
                        get("/time/search")
                                .queryParam("family", "ivanov")
                                .queryParam("date", date))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void searchAllException() throws Exception {
        String date = String.valueOf(LocalDate.now());
        mockMvc.perform(
                        get("/time/search")
                                .queryParam("family", "exep")
                                .queryParam("date", date))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void workingTimeEmpl() throws Exception {
        mockMvc.perform(
                        get("/time/workingTimeEmpl/1")
                                .queryParam("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void workingTimeEmplException() throws Exception {
        mockMvc.perform(
                        get("/time/workingTimeEmpl/1")
                                .queryParam("id", "22"))
                .andExpect(status().is(404));
    }

    @Test
    void workingTimeEmplInDate() throws Exception {
        mockMvc.perform(
                patch("/time/come/1"));
        mockMvc.perform(
                        patch("/time/out/1"))
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/time/workingTimeEmplInDate/1")
                                .queryParam("start", LocalDate.now().minusMonths(20).toString())
                                .queryParam("end", LocalDate.now().plusMonths(10).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.family").value("ivanov"));
    }

    @Test
    void setNotWorkingDays() throws Exception {
        mockMvc.perform(
                        post("/time/NWD/1")
                                .queryParam("start", LocalDate.now().minusDays(1).toString())
                                .queryParam("end", LocalDate.now().plusDays(1).toString())
                                .queryParam("info", "info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("info"));
    }

    @Test
    void setNotWorkingDaysException() throws Exception {
        mockMvc.perform(
                        post("/time/NWD/111")
                                .queryParam("id", "111")
                                .queryParam("start", LocalDate.now().minusMonths(10).toString())
                                .queryParam("end", LocalDate.now().plusMonths(10).toString())
                                .queryParam("info", "info"))
                .andExpect(status().is(404));
    }

    @Test
    void getNotWorkingDays() throws Exception {
        mockMvc.perform(
                        get("/time/NWD")
                                .queryParam("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
}