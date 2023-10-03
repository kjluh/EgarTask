package egar.egartask.controllers;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeControllerJsonTest {

    @Autowired
    private MockMvc mockMvc;
    private static JSONObject jsonObject = new JSONObject();

    @BeforeAll
    static void createJS() throws Exception {
        jsonObject.put("id", 1);
        jsonObject.put("name", "name");
        jsonObject.put("family", "family");
        jsonObject.put("SecondName", "SecondName");
        jsonObject.put("Dismissed", true);
        jsonObject.put("HiringDate", LocalDate.now());
    }

    @Test
    @Order(1)
    void createEmployee() throws Exception {
        mockMvc.perform(
                        post("/employeeJson")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObject.toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " +
                                        Base64.getEncoder().encodeToString(("Admin" + ":" + "password").getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.family").value("family"));
    }

    @Test
    void updateEmployeeExp() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("family", "newFamily");
        jsonObject.put("id", 11);
        mockMvc.perform(
                        patch("/employeeJson")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObject.toString())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }
    @Test
    @Order(4)
    void updateEmployee() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("family", "newFamily");
        jsonObject.put("id", 1);
        mockMvc.perform(
                        patch("/employeeJson")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObject.toString())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.family").value("newFamily"));
    }

    @Test
    @Order(2)
    void getEmployee() throws Exception {
        mockMvc.perform(
                        get("/employeeJson/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.family").value("family"));
    }

    @Test
    void getEmployeeExp() throws Exception {
        mockMvc.perform(
                        get("/employeeJson/111"))
                .andExpect(status().is(404));
    }

    @Test
    @Order(3)
    void getEmployee2() throws Exception {
        mockMvc.perform(
                        get("/employeeJson")
                                .queryParam("name","name")
                                .queryParam("family","family"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @Order(6)
    void deleteEmployee() throws Exception {
        mockMvc.perform(
                        delete("/employeeJson/1"))
                .andExpect(status().isOk());
    }
    @Test
    void deleteEmployeeExp() throws Exception {
        mockMvc.perform(
                        delete("/employeeJson/11"))
                .andExpect(status().is(400));
    }

    @Test
    @Order(5)
    void getAllEmployee() throws Exception {
        mockMvc.perform(
                        get("/employeeJson/all"))
                .andExpect(status().isOk());
    }

}