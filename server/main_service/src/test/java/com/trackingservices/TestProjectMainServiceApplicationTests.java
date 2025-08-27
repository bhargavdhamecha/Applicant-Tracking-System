package com.trackingservices;

import com.trackingservices.TestService.ServiceMokitoTests;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.trackingservices.dto.JobApplicationDetailsDto;
import com.trackingservices.model.JobDescription;
import com.trackingservices.model.User;
import com.trackingservices.repository.JobDescriptionRepository;
import com.trackingservices.service.UserService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes={ServiceMokitoTests.class})
class TestProjectMainServiceApplicationTests {

    @Mock
    JobDescriptionRepository jobDescriptionRepository;

    @InjectMocks
    UserService userService;

    public List<JobDescription> jobdesc;
    ObjectMapper objectMapper= new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Test
    @Order(1)
    public void test_getAllJobDescription(){
        JobApplicationDetailsDto jobDto = new JobApplicationDetailsDto("dotnet","srdev",3,3,false, 50000L, 80000L,3,"ssd-1",true,"ssd-1desc",false,false,"N/A","google",new User(1l));
        JobApplicationDetailsDto jobDto1 = new JobApplicationDetailsDto("java","srdev",3,3,false, 50000L, 80000L,3,"ssd-1",true,"ssd-1desc",false,false,"N/A","google",new User(2l));
        JobApplicationDetailsDto jobDto2 = new JobApplicationDetailsDto("python","srdev",3,3,false, 50000L, 80000L,3,"ssd-1",true,"ssd-1desc",false,false,"N/A","google",new User(3l));
        JobDescription job1 = objectMapper.convertValue(jobDto,JobDescription.class);
        JobDescription job2 = objectMapper.convertValue(jobDto1,JobDescription.class);
        JobDescription job3 = objectMapper.convertValue(jobDto2,JobDescription.class);
        List<JobDescription> myjobdesc = new ArrayList<JobDescription>();
        myjobdesc.add(job1);
        myjobdesc.add(job2);
        myjobdesc.add(job3);
        when(jobDescriptionRepository.findAll()).thenReturn(myjobdesc);
        assertEquals(3,userService.getAllJobApplication().size());

    }

}
