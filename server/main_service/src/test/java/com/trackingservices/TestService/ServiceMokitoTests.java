package com.trackingservices.TestService;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.trackingservices.dto.JobApplicationDetailsDto;
import com.trackingservices.model.Address;
import com.trackingservices.model.JobDescription;
import com.trackingservices.model.User;
import com.trackingservices.repository.AddressRepository;
import com.trackingservices.repository.JobDescriptionRepository;
import com.trackingservices.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ServiceMokitoTests {

    @Mock
    JobDescriptionRepository jobDescriptionRepository;

    @Mock
    AddressRepository addressRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach()
    void setup() {
        userService = new UserService(modelMapper, jobDescriptionRepository);
        MockitoAnnotations.openMocks(this);
    }

    private ModelMapper modelMapper = new ModelMapper();


    public List<JobDescription> jobdesc;
    ObjectMapper objectMapper= new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();


    @Test
    public void test_getAllJobDescription(){
        JobApplicationDetailsDto jobDto = new JobApplicationDetailsDto("dotnet","srdev",3,
                3,false, 50000L, 80000L,3,"ssd-1",true,
                "ssd-1desc",false,false,"N/A","google",new User(1l));
        JobApplicationDetailsDto jobDto1 = new JobApplicationDetailsDto("java","srdev",3,
                3,false, 50000L, 80000L,3,"ssd-1",true,
                "ssd-1desc",false,false,"N/A","google",new User(2l));
        JobApplicationDetailsDto jobDto2 = new JobApplicationDetailsDto("python","srdev",3,3,
                false, 50000L, 80000L,3,"ssd-1",true,"ssd-1desc",
                false,false,"N/A","google",new User(3l));
        JobDescription job1 = modelMapper.map(jobDto,JobDescription.class);
        JobDescription job2 = modelMapper.map(jobDto1,JobDescription.class);
        JobDescription job3 = modelMapper.map(jobDto2,JobDescription.class);
        List<JobDescription> myjobdesc = new ArrayList<JobDescription>();
        myjobdesc.add(job1);
        myjobdesc.add(job2);
        myjobdesc.add(job3);
        when(jobDescriptionRepository.findAll()).thenReturn(myjobdesc);
        assertEquals(3,userService.getAllJobApplication().size());

    }

    @Test
    public void test_getAddressByUId(){
        Address add1 = new Address(1l,"stree1","country1","state1","city1",123456,"per",new User(101l));
        Address add2 = new Address(2l,"stree2","country2","state2","city2",123457,"temp",new User(101l));
        List<Address> addressList = new ArrayList<>();
        addressList.add(add1);
        addressList.add(add2);
        when(addressRepository.findAllByUserId(101l)).thenReturn(addressList);
        assertEquals(addressList.size(),userService.getAddressByuserId(101l).get().size());
    }

    @Test
    public void test_addJobApplicationDetails(){

        JobApplicationDetailsDto jobDto = new JobApplicationDetailsDto("dotnet","srdev",3,3
                ,false, 50000L, 80000L,3,"ssd-1",true
                ,"ssd-1desc",false,false,"N/A","google",new User(1l));
        JobDescription jobDescription = new JobDescription(101,"dotnet","srdev"
                ,3,3,40000l, 50000L, 6,"ssd-1"
                ,true,"jobdesc",false,false,"N/A"
                ,"google",true,new User(1l));
        assertEquals(jobDescription.getUser().getUid(),userService.addJobApplicationDetails(jobDto).getUser().getUid());


    }




}
