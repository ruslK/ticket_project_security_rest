package com.ticketsproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ticketsproject.dto.ProjectDTO;
import com.ticketsproject.dto.RoleDTO;
import com.ticketsproject.dto.UserDTO;
import com.ticketsproject.enums.Gender;
import com.ticketsproject.enums.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    private static String projectCode = "IT__" + LocalDate.now().toEpochSecond(LocalTime.now(), ZoneOffset.UTC);

    @Autowired
    private MockMvc mockMvc;

    private static JacksonJsonParser jsonParser;
    private static UserDTO user;
    private static ProjectDTO project;
    private static RoleDTO role;
    private static String currentProjectCode;

    @BeforeAll
    public static void SetUP() {

        jsonParser = new JacksonJsonParser();

        role = RoleDTO.builder().description("Manager").build();
        role.setId(2L);

        user = UserDTO.builder()
                .firstName("manager")
                .lastName("manager")
                .userName("manager")
                .phone("4564564545")
                .password("manager")
                .role(role)
                .gender(Gender.MALE)
                .build();
        user.setId(2L);

        project = ProjectDTO.builder()
                .projectCode(projectCode)
                .projectName("Api")
                .assignedManager(user)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .projectDetails("Api Test")
                .projectStatus(Status.OPEN)
                .completeCount(0)
                .inCompleteCount(0)
                .build();
    }


    private String getToken(String userName, String password) throws Exception {
        String json = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"username\": \"" + userName + "\"," +
                                "\"password\": \"" + password + "\"" +
                                "}")
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        return jsonParser.parseMap(json).get("data").toString();
    }

    @Test
    @Order(1)
    public void test_1_givenNonToken_whenGetSecureRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/project/Api1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(2)
    public void test_2_givenGivenToken_getAllProject() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/project")
                        .header("Authorization", this.getToken("manager", "manager"))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[*].id").isArray())
                .andExpect(jsonPath("$.data[0]").isNotEmpty());
    }

    @Test
    @Order(3)
    public void test_3_givenToken_createProject() throws Exception {

        String response = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/project")
                        .header("Authorization", this.getToken("manager", "manager"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJsonString(project))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("success").value("true"))
                .andExpect(jsonPath("message").value("Project created"))
                .andExpect(jsonPath("code").value("200"))
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("$.data.assignedManager").isNotEmpty())
                .andExpect(jsonPath("$.data.assignedManager.role").isNotEmpty())
                .andExpect(jsonPath("$.data.assignedManager.role.id").value(2L))
                .andExpect(jsonPath("$.data.assignedManager.role.description").value("Manager"))
                .andExpect(jsonPath("$.data.assignedManager.userName").value("manager"))
                .andExpect(jsonPath("$.data.assignedManager.firstName").value("manager"))
                .andExpect(jsonPath("$.data.assignedManager.lastName").value("manager"))
                //                .andExpect(jsonPath("$.data.assignedManager..role").value("{id=2, insertDateTime=null, insertUserId=null, lastUpdateDateTime=null, lustUpdateUserId=null, isDeleted=false, description=Manager, users=null}"));
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(response);
        JsonNode data = jsonResponse.get("data");
        currentProjectCode = data.get("projectCode").asText();
    }

    protected String toJsonString(final Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(4)
    void test_4_readByProjectCode() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/project/" + currentProjectCode)
                        .header("Authorization", this.getToken("manager", "manager"))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[*].id").isArray())
                .andExpect(jsonPath("$.data.projectCode").value(currentProjectCode))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    @Order(5)
    void test_5_deleteByProjectCode() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/v1/project/" + currentProjectCode)
                        .header("Authorization", this.getToken("manager", "manager"))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Project is deleted"));
    }
}