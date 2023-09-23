package egar.egartask.controllers;

import egar.egartask.entites.Employee;
import egar.egartask.repository.EmployeeRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
class AccountingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    //    private JSONObject testJS = new JSONObject();
    private Employee employee = new Employee();

    @BeforeEach
    void createEmpl() throws JSONException {
        employee.setId(1L);
        employee.setName("name");
        employee.setFamily("family");
        employee.setSecondName("secondName");
        employee.setDismissed(true);
        employee.setHiringDate(LocalDate.now());

//        testJS.put("id", 1);
//        testJS.put("name", "name");
//        testJS.put("family", "family");
//        testJS.put("secondName", "secondName");
//        testJS.put("hiringDate", LocalDate.now());
//        testJS.put("dismissed", true);

    }

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void ECome() throws Exception {
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        mockMvc.perform(
                        post("/time/come/1"))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.name").value("name"));
    }

//    @Test
//    void EOut() {
//when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
//        mockMvc.perform(
//    post("/time/come/1"))
//            .andExpect(status().isOk());
//                .andExpect(jsonPath("$.name").value("name"));
//    }

    @Test
    void searchAll() throws Exception {
        mockMvc.perform(
                        get("/time/search?date=2023-09-23&family=ivan"))
                .andExpect(status().isOk());
    }
}
//http://localhost:8080/search?date=2023-09-23&family=ivan'