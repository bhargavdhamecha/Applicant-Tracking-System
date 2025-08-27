package com.trackingservices.controller;

import com.trackingservices.dto.*;
import com.trackingservices.model.Address;
import com.trackingservices.model.JobDescription;
import com.trackingservices.service.FileService;
import com.trackingservices.service.ImageService;
import com.trackingservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/main/api/user")
public class ApplicantResource {
    @Autowired
    private UserService service;
    @Autowired
    private FileService fileService;
    @Autowired
    private ImageService imageService;


    @PostMapping(path = "/addJobApplicationDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addJobApplicantDetails(@RequestBody JobApplicationDetailsDto jobdto) {
        service.addJobApplicationDetails(jobdto);
    }

    @GetMapping(path = "current/status/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ApplicantTrackingDto> userTracking(@PathVariable Long id) {
        return service.getApplicantTracking(id).orElse(null);
    }

    @GetMapping(path = "/GetAllJobApplication", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<JobDescription>> GetAllJobApplication() throws Exception {
        List<JobDescription> jobData = new ArrayList<>();
        jobData = service.getAllJobApplication();
        return ResponseEntity.ok(jobData);
    }

    @RequestMapping(value = "/getAddress/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<List<Address>>> getAddressById(@PathVariable Long id) throws Exception {
        Optional<List<Address>> details = service.getAddressByuserId(id);
        return ResponseEntity.ok(details);
    }

    @PutMapping(path = "/updateAddress", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void updateAddress(@RequestBody updateAddressDto updateAddDto) {
        service.updateAddressById(updateAddDto);
    }

    @PostMapping(path = "/addAddress", consumes = "application/*", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addAddress(@RequestBody AddressDto addAddressDto) {
        service.addAddressByUserID(addAddressDto);
    }

    @PostMapping(path = "uploadFile/{id}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile Datafile, @PathVariable("id") Long id) throws IOException {
        String contentType = Datafile.getContentType();
        if (!contentType.equals("application/pdf") && !contentType.equals("application/msword") && !contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            return ResponseEntity.badRequest().body("Invalid file type. Only PDF or DOC files are allowed.");
        }
        String uploadFile = fileService.uploadFile(Datafile, id);
        return ResponseEntity.status(HttpStatus.OK).body(uploadFile);
    }

    @GetMapping(path = "/getfile/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") Long id) throws IOException {

        byte[] fileData = fileService.downloadFile(id);
        String fileType = fileService.getFileType(id);
        MediaType contentType;

        switch (fileType) {
            case "application/pdf":
                contentType = MediaType.APPLICATION_PDF;
                break;
            case "application/msword":

                contentType = MediaType.APPLICATION_OCTET_STREAM;
                break;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":

                contentType = MediaType.APPLICATION_OCTET_STREAM;
                break;
            default:
                return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(fileData);
    }

    @GetMapping(path = "/getFilePath/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPathForFile(@PathVariable("id") Long id) throws IOException {
        String filePath = fileService.getPath(id);
        return ResponseEntity.ok().body(filePath);
    }

    @PostMapping(path = "/uploadImage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadImage(@RequestParam(value = "image",required = true) MultipartFile Datafile, @PathVariable("id") Long id) throws IOException {
        String contentType = Datafile.getContentType();
        if (!contentType.equals("image/png") && !contentType.equals("image/jpeg") && !contentType.equals("image/jpg")) {
            return ResponseEntity.badRequest().body("Invalid file type. Only JPEG or JPG files are allowed.");
        }
        String uploadFile = imageService.uploadImage(Datafile, id);
        return ResponseEntity.status(HttpStatus.OK).body(uploadFile);
    }

    @GetMapping(path = "/getImage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> downloadImage(@PathVariable(value = "id",required = true) Long id) throws IOException {

        byte[] fileData = imageService.downloadImage(id);
        String fileType = imageService.getFileType(id);
        MediaType contentType;

        switch (fileType) {
            case "image/png":
                contentType = MediaType.IMAGE_PNG;
                break;
            case "image/jpeg":
            case "image/jpg":
                contentType = MediaType.IMAGE_JPEG;
                break;
            default:
                return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(fileData);

    }

    @GetMapping(path = "/getImagePath/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPath(@PathVariable(value = "id",required = true) Long id) throws IOException {
        String filePath = imageService.getPath(id);
        return ResponseEntity.ok().body(filePath);

    }
    @PostMapping("/updateUserDetails/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDTO> updateUserDetails(@RequestBody UserUpdateDetails userUpdateDetails,@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(service.updateBasicDetailsById(userUpdateDetails,id));
    }

}
