package egar.egartask.controllers;

import egar.egartask.dto.EmpDto;
import egar.egartask.entites.Employee;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Employee employee = new Employee();
    private EmpDto empDto = new EmpDto();

    @BeforeEach
    void createEmpl() {
        employee.setId(1L);
        employee.setName("name");
        employee.setFamily("family");
        employee.setSecondName("secondName");
        employee.setDismissed(true);
        employee.setHiringDate(LocalDate.now());

        empDto.setName("name");
        empDto.setFamily("family");
        empDto.setSecondName("secondName");
        empDto.setDismissed(true);
        empDto.setHiringDate(LocalDate.now());
    }

    @Test
    void createEmployee() throws Exception {
        mockMvc.perform(
                        post("/employee/").contentType(MediaType.APPLICATION_JSON).content(empDto.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void getEmployee() throws Exception {
        mockMvc.perform(
                        get("/employee/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEmployee() {
    }

    @Test
    void deleteEmployee() {
    }

    @Test
    void getAllEmployee() {
    }
}