package com.trackingservices.controller;

import com.trackingservices.ApplicationURI;
import com.trackingservices.dto.*;
import com.trackingservices.exception.ResourceNotFoundException;
import com.trackingservices.model.*;
import com.trackingservices.service.AdminService;
import com.trackingservices.service.PermissionService;
import com.trackingservices.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(path = "/main/api/admin")
public class AdminResource {
    @Autowired
    private AdminService service;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;


    @PostMapping(path = ApplicationURI.CREATE_USER_TRACKING, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDTO> createState(@RequestBody @Valid ApplicantTracking trackingRequest) {
        return ResponseEntity.ok(service.createApplicationState(trackingRequest));
    }

    @PutMapping(path = ApplicationURI.UPDATE_USER_TRACKING, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDTO> updateTracking(@RequestBody @Valid ApplicantTracking trackingRequest) {
        return ResponseEntity.ok(service.updateApplicantStatus(trackingRequest));
    }

    @GetMapping(path = ApplicationURI.GET_USER_TRACKING_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Long> getUserTrackingId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findTracking(id));
    }

    @GetMapping(path = ApplicationURI.GET_STAGE_STATUS_STREAM, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<UserDto>> getUsersByStageAndStatusAndStream(
            @PathVariable("stage") String stage,
            @PathVariable("status") String status,
            @PathVariable("stream") String stream) {
        return ResponseEntity.ok(service.getUsersByStageAndStatusAndStream(stage, status, stream));
    }

    @GetMapping(path = ApplicationURI.GET_STAGE_STATUS_STREAM_ROUND, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<UserDto>> getUsersByStageAndStatusAndStream(
            @PathVariable("stage") String stage,
            @PathVariable("status") String status,
            @PathVariable("stream") String stream,
            @PathVariable("round") String round) {
        return ResponseEntity.ok(service.getUsersByStageAndStatusAndStreamAndRound(stage, status, stream, round));
    }

    @GetMapping(path = ApplicationURI.GET_STAGE_STREAM, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<UserDto>> getUsersByStageAndStream(
            @PathVariable("stage") String stage,
            @PathVariable("stream") String stream) {
        return ResponseEntity.ok(service.getUsersByStageAndStream(stage, stream));
    }

    @GetMapping(path = ApplicationURI.STAGE_STREAM_ROUND, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<UserDto>> getUsersByStageAndStreamAndRound(
            @PathVariable("stage") String stage,
            @PathVariable("stream") String stream,
            @PathVariable("round") String round) {
        return ResponseEntity.ok(service.getUsersByStageAndStreamAndRound(stage, stream, round));
    }

    @GetMapping(path = ApplicationURI.GET_STATUS_STREAM, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<UserDto>> getUsersByStatusAndStream(@PathVariable("status") String status, @PathVariable("stream") String stream) {
        return ResponseEntity.ok(service.getUsersByStatusAndStream(status, stream));
    }

    @GetMapping(path = ApplicationURI.STATUS_STREAM_ROUND, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<UserDto>> getUsersByStatusAndStreamAndRound(@PathVariable("status") String status, @PathVariable("stream") String stream, @PathVariable String round) {
        return ResponseEntity.ok(service.getUsersByStatusAndStreamAndRound(status, stream, round));
    }

    @GetMapping(path = ApplicationURI.GET_STREAM, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<UserDto>> getUsersByStream(@PathVariable("stream") String stream) {
        return ResponseEntity.ok(service.getUsersByStream(stream));
    }

    @GetMapping(path = ApplicationURI.GET_ALL_APPLICANT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<Page<AllApplicantDataDto>>> getAllUser(@PathVariable Integer offset, @PathVariable Integer length, @PathVariable String field) {
        return ResponseEntity.ok(service.getAllUser(offset, length, field));
    }

    @PostMapping(path = ApplicationURI.ADD_ROLE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> saveRole(@RequestBody @Valid RoleRequest role) {
        return ResponseEntity.ok(roleService.saveRole(role));
    }

    @PostMapping(path = ApplicationURI.ADD_PERMISSION, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> savePermission(@RequestBody @Valid PermissionRequest permission) {
        return ResponseEntity.ok(permissionService.savePermission(permission));
    }

    @PostMapping(path = ApplicationURI.UPDATE_ROLE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDTO> updateRole(@RequestBody RoleRequest roleRequest) {
        return ResponseEntity.ok(roleService.updateRole(roleRequest));
    }

    @PostMapping(path = ApplicationURI.UPDATE_PERMISSION, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)

    public ResponseEntity<ResponseDTO> updatePermission(@RequestBody @Valid PermissionRequest permissionRequest, @PathVariable Long id) {
        return ResponseEntity.ok(permissionService.updatePermission(permissionRequest, id));
    }

    @GetMapping(path = ApplicationURI.GET_PERMISSIONS, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<List<Permission>>> getAllPermission() {
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }
    @GetMapping(path = ApplicationURI.GET_ALL_ROLE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<List<Role>>> getAllRole() {
        return ResponseEntity.ok(roleService.getAllRole());
    }

    @GetMapping(path = "/getUserDetailsById/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> getUserDetailsByUid(@PathVariable("uid") Long uid) {
        User user = service.getUserByUid(uid);
        if (user == null) {
            throw new ResourceNotFoundException("User", "uid", uid);
        }
        return ResponseEntity.ok(user);
    }


    //stream api
    @GetMapping(value = "/getAllStream", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<List<?>>> getAllStream() {
        return ResponseEntity.ok(service.getAllStream());
    }

    @PostMapping(path = "addStream", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDTO> addStream(@RequestBody Stream stream) {
        return ResponseEntity.ok(service.addStream(stream));
    }

    @PutMapping(path = "/updateStream/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Stream> updateStream(@PathVariable("id") Long id, @RequestBody Stream updatedStream) throws Exception {
        updatedStream.setStreamId(id);
        Stream updated = service.updateStream(updatedStream);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/deleteStream/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseDTO> deleteStream(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteStream(id));
    }

    @PutMapping(path = "activeStream/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> activateStream(@PathVariable("id") Long id) {
        service.activateStream(id);
        return ResponseEntity.ok().build();
    }

    //Rejection reason API
    @GetMapping(path = "/getAllRejectionreasons", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<RejectionReason> getAllRejectionReasons() {
        return service.getAllRegistrationReasons();
    }

    @GetMapping(path = "/getRegectionreason/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RejectionReason getRejectionReasonById(@PathVariable Long id) throws Exception {
        return service.getRejectionReasonById(id);
    }

    @PutMapping(path = "rejection/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<RejectionReason> updateRejectionReason(@PathVariable("id") Long id, @RequestBody RejectionReason updatedRejectionReason) throws Exception {
        updatedRejectionReason.setRejectionId(id);
        RejectionReason updated = service.updateRejectionReason(updatedRejectionReason);
        return ResponseEntity.ok(updated);
    }

    @PostMapping(path = "/addRejectionReason", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createRegectionReason(@RequestBody RejectionReason rejectionReason) {
        service.saveRejectionReason(rejectionReason);
    }

    //manage user
    @GetMapping(path = "/getManagedUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<List<?>>> getManagedUser() {
        return ResponseEntity.ok(service.getManagedUser());
    }


    @PutMapping(path = "activeManagedUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> activateManagedUser(@PathVariable("id") Long id) {
        service.activateManagedUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "deactiveManagedUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deactivateManagedUser(@PathVariable("id") Long id) {
        service.deactivateManagedUser(id);
        return ResponseEntity.ok().build();
    }

    //Stage
    @GetMapping(path = "/getAllStage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<List<Stage>>> getAllStage() {
        Optional<List<Stage>> stages = service.getAllStageSortedById();
        return ResponseEntity.ok(stages);
    }

    @PostMapping(path = "/addStage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDTO> saveStage(@RequestBody Stage stage) {
        return ResponseEntity.ok(service.saveStage(stage));
    }

    @PostMapping(path = "/addRound", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDTO> saveRound(@RequestBody Round round) {
        return ResponseEntity.ok( service.saveRound(round));
    }

    @PutMapping(path = "/updateStageStatus/{stageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateStageIsActive(@PathVariable Long stageId, @RequestParam boolean isActive) {
        service.updateStageIsActive(stageId, isActive);
        StringBuilder response = new StringBuilder();
        response.append("Stage with ID:").append(stageId);
        response.append(" has been ").append(isActive ? "true" : "false");
        return ResponseEntity.ok().body(response.toString());
    }

    @PutMapping(path = "/updateStage/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Stage> updateStage(@PathVariable("id") Long stageId, @RequestBody Stage stageRequest) throws Exception {
        stageRequest.setStageId(stageId);
        Stage updated = service.updateStage(stageRequest);
        return ResponseEntity.ok(updated);
    }

    @PostMapping(path = "/stage/{stageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> addRoundToStage(@PathVariable Long stageId, @RequestBody Round round) {
        return ResponseEntity.ok(service.addRoundToStage(stageId, round));
    }

    //end of stage

    @PostMapping(path = "/BulkUpdateFeedback", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDTO> bulkUpdateFeedback(@RequestBody MainBulkUpdateDto mainBulkUpdateDto) {
//        System.out.println(service.bulkupdateFeedback(mainBulkUpdateDto));
    ;
        return ResponseEntity.ok(service.bulkupdateFeedback(mainBulkUpdateDto));

    }

    @PostMapping(path = "/BulkUpdateCreate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDTO> bulkUpdateCreate(@RequestBody MainUpdateCreate mainUpdateCreate) {
//        System.out.println(service.bulkupdateFeedback(mainBulkUpdateDto))
        return ResponseEntity.ok(service.bulkupdateCreate(mainUpdateCreate));

    }

    @GetMapping(path = "/candidatesSelectionToRejection", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, Object>> getSelectionToRejection() {
        Map<String, Object> data = new HashMap<>();
        data = service.selectionToRejection();
        return ResponseEntity.ok(data);
    }

    @GetMapping(value = "/stream-count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Integer>> getStreamCount() {
        Map<String, Integer> streamCountMap = new HashMap<>();
        List<Object[]> results = service.countApplicantsByStream();

        for (Object[] result : results) {
            String stream = (String) result[0];
            Long count = (Long) result[1];
            streamCountMap.put(stream, count.intValue());
        }
        return ResponseEntity.ok(streamCountMap);
    }


    @GetMapping(path = "/getNewUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<List<UserDto>>> getNewUser() {
        Optional<List<UserDto>> roles = service.getNewUser();
        return ResponseEntity.ok(roles);
    }

    @GetMapping(path = "/SearchAllUser/{search}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<AllApplicantDataDto> searchAllUser(@PathVariable String search) {
        List<AllApplicantDataDto> listdemo = service.searchAllUser(search);
        return listdemo;
    }

    @GetMapping(path = "/getPermission/{rolename}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<List<PermissionDTO>>> getPermissionsByRoleName(@PathVariable String rolename) {
        return ResponseEntity.ok(service.getPermissionsByRoleName(rolename));
    }

    @GetMapping(path = "/getAllFutureRef", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<List<AllApplicantDataDto>>> getAllFutureRef() {
        return ResponseEntity.ok(service.futureRefApplicantData());
    }

    @GetMapping(path = "/searchFutureRef/{search}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<List<AllApplicantDataDto>>> searchFutureRef(@PathVariable String search) {
        return ResponseEntity.ok(service.SearchfutureRefApplicantData(search));
    }
}

