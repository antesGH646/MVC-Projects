package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Gender;
import com.cydeo.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //Test post method
    static UserDTO userDTO;
    static ProjectDTO projectDTO;
    static String token;

    //initialize the data
    @BeforeAll
    static void setUp() {
        token =  "Bearer " + getTokenFromKeyCloak();//getting the token from keycloak
        //hard coding the token
      //  token =  "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJHV3lSUjA4anJKREZ1NkVwSTNrRERyaWtndlllWDNmUk54R1JodDNfOGRNIn0.eyJleHAiOjE2ODc5ODM3MDEsImlhdCI6MTY4Nzk4MzQwMSwianRpIjoiZmUzYTJiNzAtZWY4NS00Yzk0LWIxOTUtNGZkNGJjYjc1MGQ3IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL3RyYWluLWRldiIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI1YTcwOGM5Ny1hMTM1LTRmMGYtOTU4YS02NjFmMzE4MjYxMjIiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ0aWNrZXRpbmctYXBwIiwic2Vzc2lvbl9zdGF0ZSI6IjMwYjE2YWFmLWE1MWMtNGYyOS1iMDBkLTI3MGQ2ODZmMjZjOSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo4MDgxIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLXRyYWluLWRldiIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJ0aWNrZXRpbmctYXBwIjp7InJvbGVzIjpbIk1hbmFnZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwiLCJzaWQiOiIzMGIxNmFhZi1hNTFjLTRmMjktYjAwZC0yNzBkNjg2ZjI2YzkiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwicHJlZmVycmVkX3VzZXJuYW1lIjoiYWRhbXNtaXRoIn0.PE4xwOe4VYjVq6fSsC_ODphhl6WIjAYD6DongQY5FQ7zUe45JuY87LTnIP-6fgNCKoB34Gey_Z7u_R7ztTGxW5k5UMzu9S2RSF4H4OiXdlU22rrcG49CGAqMhHa05O89EJblBa1ulwMkWuFDNgjU_VBca2i-FHf_xloZTWP4XLqaZRXrBMm1RJR0DUW6DGd3sFFX7NGV7euFBr36vMG2OmvCymxo-O9Vefjsm5J1c777GDkLByG9hCy7FII9XvWEkcuziFSgIPXdP231MixUsyJUW-fhXpBgKQVz-07NuCeaD9-kefbi7mGpt2-H4PSthXPrAOikaZqASKMin82Qeg";
        userDTO = UserDTO.builder()
                .id(2L)
                .firstName("Adam")
                .lastName("Smith")
                .userName("adamsmith")
                .passWord("Abc1")
                .role(new RoleDTO(2L, "Manager"))
                .gender(Gender.MALE)
                .build();

        projectDTO = ProjectDTO.builder()
                .projectCode("Api1")
                .projectName("Api-adam")
                .assignedManager(userDTO)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .projectDetail("Api Test")
                .projectStatus(Status.OPEN)
                .build();
    }

    @Test
    public void withoutToken_whenGetRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/project"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void withToken_whenGetRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/project")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].projectCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].assignedManager.userName").isNotEmpty());
    }

    /**
     * Creates a project in the database
     * throws Exception
     */
    @Test
    public void withToken_createProject() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/project")
                .header("Authorization", token)
                .content(toJsonString(projectDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

    }

    @Test
    public void withToken_updateProject() throws Exception {
        projectDTO.setProjectName("Api-cydeo");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/project")
                .header("Authorization", token)
                .content(toJsonString(projectDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Project value is successfully updated"));
    }

    @Test
    public void withToken_deleteProject() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/project/" + projectDTO.getProjectCode())
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //helper method
    private static String toJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A helper method that get a token from keycloak
     */
    private static String getTokenFromKeyCloak() {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", "ticketing-app");
        map.add("client_secret", "8aZu9TysjYKD7oLxStQlN0GikwyFLnGt");
        map.add("username", "adamsmith");
        map.add("password", "Abc1");
        map.add("scope", "openid");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<ResponseDTO> response =
                restTemplate.exchange("http://localhost:8080/auth/realms/train-dev/protocol/openid-connect/token",
                        HttpMethod.POST,
                        entity,
                        ResponseDTO.class);
        if (response.getBody() != null) {
            return response.getBody().getAccess_token();
        }
        return "";
    }

}
