//package controller.test;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//import com.trackingservices.controller.ApplicantResource;
//import com.trackingservices.dto.JobApplicationDetailsDto;
//import com.trackingservices.dto.UserDto;
//import com.trackingservices.model.JobDescription;
//import com.trackingservices.model.User;
//import com.trackingservices.repository.ApplicantRepository;
//import com.trackingservices.repository.JobDescriptionRepository;
//import com.trackingservices.service.UserService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MockMvcBuilder;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.ResultMatcher;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.notNullValue;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest(ApplicantResource.class)
//public class JobDescriptionControllerTest {
//
//
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    ObjectMapper objectMapper= new ObjectMapper();
//    ObjectWriter objectWriter = objectMapper.writer();
//
//    @MockBean
//    private UserService userService;
//
//    @Mock
//    private JobDescriptionRepository jobDescriptionRepository;
//
//    @InjectMocks
//    private ApplicantResource applicantResource;
//
//
//    JobApplicationDetailsDto jobDto = new JobApplicationDetailsDto("dotnet","srdev",3,3,false, 50000L, 80000L,3,"ssd-1",true,"ssd-1desc",false,false,"N/A","google",new User(1l));
//    JobApplicationDetailsDto jobDto1 = new JobApplicationDetailsDto("java","srdev",3,3,false, 50000L, 80000L,3,"ssd-1",true,"ssd-1desc",false,false,"N/A","google",new User(2l));
//    JobApplicationDetailsDto jobDto2 = new JobApplicationDetailsDto("python","srdev",3,3,false, 50000L, 80000L,3,"ssd-1",true,"ssd-1desc",false,false,"N/A","google",new User(3l));
//    @Test
//    public void getJobDescriptionTest(){
//        JobDescription job1 = objectMapper.convertValue(jobDto,JobDescription.class);
//        JobDescription job2 = objectMapper.convertValue(jobDto1,JobDescription.class);
//        JobDescription job3 = objectMapper.convertValue(jobDto2,JobDescription.class);
//        List<JobDescription> records = new ArrayList<>(Arrays.asList(job1,job2,job3));
//        when(jobDescriptionRepository.findAll()).thenReturn(records);
//        assertEquals(2,userService.getAllJobApplication().size());
//    }
//
//
//
////    @Test
////    public void getAllJobDescriptionTest() throws Exception{
////        JobDescription job1 = objectMapper.convertValue(jobDto,JobDescription.class);
////        JobDescription job2 = objectMapper.convertValue(jobDto1,JobDescription.class);
////        JobDescription job3 = objectMapper.convertValue(jobDto2,JobDescription.class);
////        List<JobDescription> records = new ArrayList<>(Arrays.asList(job1,job2,job3));
////        Mockito.when(jobDescriptionRepository.findAll()).thenReturn(records);
////
////        mockMvc.perform(MockMvcRequestBuilders
////                .get("/GetAllJobApplication")
////                .contentType(MediaType.APPLICATION_JSON)
////                .andExpect(status().isOk())
////                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
////                .andExpect(jsonPath("$[]",is(1l)))
////    }
////
////
////
////    @Test
////    public void addJobDescriptionTest() throws Exception {
////        JobApplicationDetailsDto jobDto = new JobApplicationDetailsDto("dotnet","srdev",3,3,false, 50000L, 80000L,3,"ssd-1",true,"ssd-1desc",false,false,"N/A","google",new User(1l));
////        JobDescription jobDesc = objectMapper.convertValue(jobDto, JobDescription.class);
////        Mockito.when(jobDescriptionRepository.save(jobDesc)).thenReturn(jobDesc);
////
////        String content =  objectWriter.writeValueAsString(jobDesc);
////
////        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/addJobApplicationDetails")
////                .contentType(MediaType.APPLICATION_JSON)
////                .accept(MediaType.APPLICATION_JSON)
////                .content(objectMapper.writeValueAsString(jobDesc));
////
////        ResultActions response = mockMvc.perform(mockRequest)
////                .andExpect(status().isCreated());
////
////
////    }
//
//
//}
