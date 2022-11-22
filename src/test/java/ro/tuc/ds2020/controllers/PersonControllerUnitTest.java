package ro.tuc.ds2020.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ro.tuc.ds2020.enums.Role;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ro.tuc.ds2020.Ds2020TestConfig;
import ro.tuc.ds2020.dtos.detailsDTO.UserDetailsDTO;
import ro.tuc.ds2020.services.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class PersonControllerUnitTest extends Ds2020TestConfig {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    public void insertPersonTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDetailsDTO personDTO = new UserDetailsDTO("John", "123", Role.CLIENT);

        mockMvc.perform(post("/user")
                .content(objectMapper.writeValueAsString(personDTO))
                .contentType("application/json"))
                .andExpect(status().isCreated());
    }

    @Test
    public void insertPersonTestFailsDueToAge() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDetailsDTO personDTO = new UserDetailsDTO("John", "567", Role.ADMIN);

        mockMvc.perform(post("/user")
                .content(objectMapper.writeValueAsString(personDTO))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void insertPersonTestFailsDueToNull() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDetailsDTO personDTO = new UserDetailsDTO("John", null, Role.ADMIN);

        mockMvc.perform(post("/user")
                .content(objectMapper.writeValueAsString(personDTO))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
}
