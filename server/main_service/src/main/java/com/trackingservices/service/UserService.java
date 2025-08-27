package com.trackingservices.service;

import com.trackingservices.dto.*;
import com.trackingservices.exception.ResourceNotFoundException;
import com.trackingservices.model.Address;
import com.trackingservices.model.JobDescription;
import com.trackingservices.model.User;
import com.trackingservices.repository.AddressRepository;
import com.trackingservices.repository.ApplicantRepository;
import com.trackingservices.repository.JobDescriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Service
@Configuration
@Slf4j
public class UserService {
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private JobDescriptionRepository jobDescriptionRepository;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;

    public UserService(ModelMapper modelMapper, JobDescriptionRepository jobDescriptionRepository) {
        this.modelMapper=modelMapper;
        this.jobDescriptionRepository=jobDescriptionRepository;
    }

    public Optional<List<ApplicantTrackingDto>> getApplicantTracking(Long id) throws HttpServerErrorException.InternalServerError {
        return applicantRepository.findByUserId(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SQLException.class, isolation = Isolation.SERIALIZABLE)
    public JobDescription addJobApplicationDetails(JobApplicationDetailsDto jobdto) throws HttpServerErrorException.InternalServerError {
        JobDescription jobdetails = this.modelMapper.map(jobdto, JobDescription.class);
        System.out.printf("" + jobdetails.toString());
        jobDescriptionRepository.save(jobdetails);
        log.info("Job application details {} saved", jobdetails);
        return jobdetails;
    }

    public List<JobDescription> getAllJobApplication() throws HttpServerErrorException.InternalServerError {
        Optional<List<JobDescription>> jobDescription = Optional.of(jobDescriptionRepository.findAll());
//        System.out.println(jobOpt.toString());
        if (!jobDescription.get().isEmpty()) {
            System.out.print(jobDescription.toString());
            return jobDescription.get();
        } else {
            throw new ResourceNotFoundException("Job description", "job description not found");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SQLException.class, isolation = Isolation.SERIALIZABLE)
    public void addAddressByUserID(AddressDto address) throws HttpServerErrorException.InternalServerError {
        Address add = this.modelMapper.map(address, Address.class);
        System.out.printf("" + add.toString());
        addressRepository.save(add);
        log.info("Address {} saved", add);
    }


    public Optional<List<Address>> getAddressByuserId(Long uid) throws HttpServerErrorException.InternalServerError {
        Optional<List<Address>> addopt = Optional.ofNullable(addressRepository.findAllByUserId(uid));
        if (addopt.get().isEmpty()) {
            throw new ResourceNotFoundException("Address", "Address not found");
        } else {
            return addopt;
        }
    }

    @Transactional(propagation = Propagation.NESTED, rollbackFor = SQLException.class, isolation = Isolation.SERIALIZABLE)
    public void updateAddressById(updateAddressDto dto) throws HttpServerErrorException.InternalServerError {
        addressRepository.updateUserById(dto.getUser().getUid(), dto.getStreet(),
                dto.getCity(), dto.getState(),
                dto.getCountry(), dto.getPincode()
                , dto.getAddressType(), dto.getAddress().getAddressId());
        // Address addu = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    @Transactional(propagation = Propagation.NESTED, rollbackFor = SQLException.class, isolation = Isolation.SERIALIZABLE)
    public ResponseDTO updateBasicDetailsById(UserUpdateDetails dto, Long id) throws HttpServerErrorException.InternalServerError {
        User user = applicantRepository.findByUid(id);
        ResponseDTO responseDTO = new ResponseDTO();
        if (user != null) {
            System.out.println(user);
            user.setFirstname(dto.getFirstName());
            user.setLastname(dto.getLastName());
            user.setEmail(dto.getEmail());
            user.setRole(dto.getRole());
            applicantRepository.save(user);
            responseDTO.setMessage("Success");
        }
        else {
            responseDTO.setMessage("User is not available");
        }
        return responseDTO;
    }

}


