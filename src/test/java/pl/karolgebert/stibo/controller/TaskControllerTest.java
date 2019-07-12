package pl.karolgebert.stibo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import pl.karolgebert.stibo.dao.TaskRepository;
import pl.karolgebert.stibo.dto.TaskDto;
import pl.karolgebert.stibo.model.Status;
import pl.karolgebert.stibo.model.Task;

import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateEndpoint() throws Exception {
        // GIVEN
        String url = "/task";
        TaskDto task = new TaskDto("Test", (short) 0, (short) 4);

        // WHEN
        mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task))
        )

        // THEN
        .andExpect(status().isOk());

        Mockito.verify(taskRepository).save(argThat(taskMock ->
                Objects.equals(taskMock.getName(), "Test") &&
                taskMock.getStatus().getDone() == 0 &&
                taskMock.getStatus().getPlanned() == 4
        ));
    }

    @Test
    public void testReadEndpoint() throws Exception {
        // GIVEN
        String url = "/task";
        Mockito.when(taskRepository.getAll()).thenReturn(
                Collections.singletonList(new Task("Test", new Status(0, 4)))
        );

        // WHEN
        mockMvc.perform(get(url))

        // THEN
        .andExpect(status().isOk())
        .andExpect(content().json("[{name: \"Test\", \"done\": 0, \"planned\": 4}]"));
    }

    @Test
    public void testUpdateEndpoint() throws Exception {
        // GIVEN
        String url = "/task/1";
        TaskDto task = new TaskDto("Test", (short) 1, (short) 4);

        // WHEN
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task))
        )

        // THEN
        .andExpect(status().isOk());

        Mockito.verify(taskRepository).update(eq(1L), argThat(taskMock ->
                Objects.equals(taskMock.getName(), "Test") &&
                        taskMock.getStatus().getDone() == 1 &&
                        taskMock.getStatus().getPlanned() == 4
        ));
    }

    @Test
    public void testDeleteEndpoint() throws Exception {
        // GIVEN
        String url = "/task/1";

        // WHEN
        mockMvc.perform(delete(url))

        // THEN
        .andExpect(status().isNoContent());

        Mockito.verify(taskRepository).delete(eq(1L));
    }

    @Test
    public void testValidationEmptyName() throws Exception {
        testValidation(new TaskDto("", (short) 0, (short) 4), (String url) -> post(url));
    }

    @Test
    public void testValidationNullName() throws Exception {
        testValidation(new TaskDto(null, (short) 0, (short) 4), (String url) -> post(url));
    }

    @Test
    public void testValidationDoneOutOfRange() throws Exception {
        testValidation(new TaskDto("Test", (short) 5, (short) 4), (String url) -> post(url));
    }

    @Test
    public void testValidationPlannedOutOfRange() throws Exception {
        testValidation(new TaskDto("Test", (short) 0, (short) 5), (String url) -> post(url));
    }

    @Test
    public void testValidationOnPut() throws Exception {
        testValidation(new TaskDto("", (short) 5, (short) 5), (String url) -> put(url.concat("/1")));
    }

    private void testValidation(TaskDto task, Function<String, MockHttpServletRequestBuilder> requestMethod) throws Exception {
        // GIVEN
        String url = "/task";

        // WHEN
        mockMvc.perform(requestMethod.apply(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task))
        )

        // THEN
        .andExpect(status().isBadRequest());
    }
}
