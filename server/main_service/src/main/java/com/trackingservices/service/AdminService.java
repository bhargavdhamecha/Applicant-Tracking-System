package com.trackingservices.service;

import com.trackingservices.advice.Constant;
import com.trackingservices.dto.*;
import com.trackingservices.exception.ResourceNotFoundException;
import com.trackingservices.model.*;
import com.trackingservices.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@Configuration
@EnableTransactionManagement
public class AdminService {
    @Autowired
    private ApplicantTrackingRepository applicantTrackingRepository;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private TrackingRepository trackingRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private StreamRepository streamRepository;
    @Autowired
    private RejectionReasonRepository rejectionReasonRepository;

    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private JobDescriptionRepository jobDescriptionRepository;
    @Autowired
    private EmailService emailService;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SQLException.class, isolation = Isolation.SERIALIZABLE)
    public ResponseDTO createApplicationState(ApplicantTracking trackingRequest) {

        Tracking tracking = trackingRepository.save(trackingRequest.getTracking());
        ApplicantTracking applicantTracking = ApplicantTracking.builder()
                .stage(trackingRequest.getStage())
                .round(trackingRequest.getRound())
                .status(trackingRequest.getStatus())
                .tracking(tracking)
                .build();

        applicantTrackingRepository.save(applicantTracking);
        log.info("Tracking saved successfully {}", tracking);
        return ResponseDTO.builder().message("Tracking Created successfully")
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {SQLException.class, ConfigDataNotFoundException.class}, isolation = Isolation.READ_COMMITTED)
    public ResponseDTO updateApplicantStatus(ApplicantTracking trackingRequest) {

        ApplicantTracking tracking = ApplicantTracking.builder()
                .id(trackingRequest.getId())
                .status(trackingRequest.getStatus())
                .round(trackingRequest.getRound())
                .stage(trackingRequest.getStage())
                .review(trackingRequest.getReview())
                .tracking(trackingRequest.getTracking())
                .futureRef(trackingRequest.getFutureRef())
                .build();
        applicantTrackingRepository.save(tracking);
        log.info("updateStatus status Success {}",tracking);

        UserEmailDTO userEmailDTO = applicantTrackingRepository.getUserByTrackingId(trackingRequest.getId());
        System.out.println(userEmailDTO);
        String to = userEmailDTO.getEmail();
        String subject= "ATracker";
        String message = "Congratulations ! "+userEmailDTO.getFirstName()+ userEmailDTO.getLastName()+ " you are promoted to round -"+ trackingRequest.getRound() +" the stage "+trackingRequest.getStage() +".";

        EmailRequest emailRequest = new EmailRequest(to,subject,message);
       boolean status = emailService.sendEmail(emailRequest);

        return ResponseDTO.builder()
                .message("Tracking Updated successfully")
                .build();
    }

    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public Long findTracking(Integer userId) {
        Optional<Tracking> tracking = trackingRepository.findByTrackingID(userId);
        log.info("Tracking {}", tracking);
        return tracking.map(Tracking::getTid).orElse(null);
    }


    public List<UserDto> getUsersByStageAndStatusAndStreamAndRound(String stage, String status, String stream,String round) throws HttpServerErrorException.InternalServerError {
        return applicantRepository.findUsersByStageAndStatusAndStreamAndRound(stage, status, stream,round).orElse(null);
    }
    public List<UserDto> getUsersByStageAndStatusAndStream(String stage, String status, String stream) throws HttpServerErrorException.InternalServerError{
        return applicantRepository.findUsersByStageAndStatusAndStream(stage, status, stream).orElse(null);
    }
    public List<UserDto> getUsersByStageAndStreamAndRound(String stage, String stream,String round) throws HttpServerErrorException.InternalServerError{
        return applicantRepository.findUsersByStageAndStreamAndRound(stage, stream,round).orElse(null);
    }
    public List<UserDto> getUsersByStageAndStream(String stage, String stream) throws HttpServerErrorException.InternalServerError {
        return applicantRepository.findUsersByStageAndStream(stage, stream).orElse(null);
    }
    public List<UserDto> getUsersByStream(String stream) throws HttpServerErrorException.InternalServerError{
        System.out.println(applicantRepository.findUsersByStream(stream));
        return applicantRepository.findUsersByStream(stream).orElse(null);
    }

    public List<UserDto> getUsersByStatusAndStream(String status, String stream) throws HttpServerErrorException.InternalServerError{
        return applicantRepository.findUsersByStatusAndStream(status, stream).orElse(null);
    }
    public List<UserDto> getUsersByStatusAndStreamAndRound(String status, String stream,String round) throws HttpServerErrorException.InternalServerError{
        return applicantRepository.findUsersByStatusAndStreamAndRound(status, stream,round).orElse(null);
    }

    public Optional<Page<AllApplicantDataDto>> getAllUser(Integer offset, Integer pageSize, String field) throws HttpServerErrorException.InternalServerError {
        return trackingRepository.findAllByUser(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
    }


    //Stream service *************************
    public Optional<List<?>> getAllStream() throws HttpServerErrorException.InternalServerError {
        Sort sort = Sort.by(Sort.Direction.ASC, "streamId");
        Optional<List<Stream>> stream = Optional.of(streamRepository.findAll(sort));
        return Optional.of(stream.orElse(null));
    }

    public ResponseDTO addStream(Stream stream) throws HttpServerErrorException.InternalServerError {
        Stream addStream = Stream.builder()
                .streamName(stream.getStreamName())
                .createdTime(LocalDateTime.now())
                .isActive(Constant.True)
                .build();

        streamRepository.save(addStream);
        log.info("Stream {} saved", stream);
        return ResponseDTO.builder()
                .message("Stream saved successfully")
                .build();
    }

    public Stream updateStream(Stream stream) throws HttpServerErrorException.InternalServerError {
        Optional<Stream> streamOptional = streamRepository.findById(stream.getStreamId());
        if (!streamOptional.isPresent()) {
            throw new ResourceNotFoundException("stream", "streamId", stream.getStreamId());
        }

        Stream existingStream = streamOptional.get();
        existingStream.setStreamName(stream.getStreamName());
        existingStream.setUpdatedTime(LocalDateTime.now());
        Stream updatedStream = streamRepository.save(existingStream);
        log.info("Updated stream {}", updatedStream);
        return updatedStream;
    }

    public ResponseDTO deleteStream(Long id) throws HttpServerErrorException.InternalServerError {
        Optional<Stream> permission = streamRepository.findById(id);
        permission.orElseThrow(() -> new ResourceNotFoundException("Stream", "streamId", id));
        permission.ifPresent(i -> i.setIsActive(false));
        permission.ifPresent(streamRepository::save);
        return ResponseDTO.builder()
                .message("Stream deleted successfully")
                .build();
    }

    public void activateStream(Long id) throws HttpServerErrorException.InternalServerError {
        Optional<Stream> optionalStream = streamRepository.findById(id);
        optionalStream.ifPresent(stream -> {
            stream.setIsActive(true);
            streamRepository.save(stream);
            log.info("Active stream is " + stream);
        });
    }

    // rejection reason 
    public List<RejectionReason> getAllRegistrationReasons() throws HttpServerErrorException.InternalServerError {
        return rejectionReasonRepository.findAll();
    }

    public void saveRejectionReason(RejectionReason RejectionReason) throws HttpServerErrorException.InternalServerError {
        RejectionReason reason = RejectionReason
                .builder()
                .reason(RejectionReason.getReason())
                .userId(RejectionReason.getUserId())
                .build();

        rejectionReasonRepository.save(reason);
        log.info("Rejection reason {} saved", reason);
    }

    public RejectionReason getRejectionReasonById(Long id) throws HttpServerErrorException.InternalServerError {
        //   Predicate<? super RejectionReason> predicate = rejectionReason -> rejectionReason.getR_id().equals(id);
        //    return rejectionReasonRepository.s

        Optional<RejectionReason> optionalUser = rejectionReasonRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResourceNotFoundException("Rejection", "RejectionReasonId", id);
//            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

    public RejectionReason updateRejectionReason(RejectionReason rejectionReason) throws HttpServerErrorException.InternalServerError {
        // Check if rejection reason exists
        Optional<RejectionReason> existingRejectionReasonOptional = rejectionReasonRepository.findById(rejectionReason.getRejectionId());
        if (!existingRejectionReasonOptional.isPresent()) {
            throw new ResourceNotFoundException("Rejection", "RejectionReasonId", existingRejectionReasonOptional.get().getRejectionId());
//            throw new NotFoundException("Rejection reason not found with id " + rejectionReason.getR_id());
        }

        // Update the fields of the existing rejection reason
        RejectionReason existingRejectionReason = existingRejectionReasonOptional.get();
        existingRejectionReason.setReason(rejectionReason.getReason());

        // Save the updated rejection reason to the repository
        RejectionReason updatedRejectionReason = rejectionReasonRepository.save(existingRejectionReason);

        return updatedRejectionReason;
    }

    // getmanageduser
    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public Optional<List<?>> getManagedUser() throws HttpServerErrorException.InternalServerError {
        return Optional.of(applicantRepository.findAllManagedUser());
    }


    public void activateManagedUser(Long id) throws HttpServerErrorException.InternalServerError {
        Optional<User> optionalStream = applicantRepository.findById(id);
        optionalStream.ifPresent(user -> {
            user.setIsActive(true);
            applicantRepository.save(user);
        });
    }

    public void deactivateManagedUser(Long id) throws HttpServerErrorException.InternalServerError {
        Optional<User> optionalUser = applicantRepository.findById(id);
        optionalUser.ifPresent(user -> {
            user.setIsActive(false);
            applicantRepository.save(user);
        });
    }

    //Stage
    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public Optional<List<Stage>> getAllStageSortedById() throws HttpServerErrorException.InternalServerError {
        Sort sort = Sort.by(Sort.Direction.ASC, "sequenceNo");
        Optional<List<Stage>> stages = Optional.of(stageRepository.findAll(sort));
        return Optional.of(stages.orElse(null));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SQLException.class, isolation = Isolation.SERIALIZABLE)
    public ResponseDTO saveStage(Stage stageRequest) throws HttpServerErrorException.InternalServerError {
        Stage stage = Stage.builder()
                .stageName(stageRequest.getStageName())
                .isActive(Constant.True)
                .createdBy(stageRequest.getCreatedBy())
                .createdTime(stageRequest.getCreatedTime())
                .build();

        stageRepository.save(stage);
        stage.setSequenceNo(stage.getStageId().intValue());
        stageRepository.save(stage);
        log.info("Stage added {}", stage);
        return ResponseDTO.builder()
                .message("Stage added successfully")
                .build();
    }

    public User getUserByUid(Long uid) throws HttpServerErrorException.InternalServerError {
        return applicantRepository.findByUid(uid);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SQLException.class, isolation = Isolation.SERIALIZABLE)
    public ResponseDTO saveRound(Round roundRequest) throws HttpServerErrorException.InternalServerError {
        Round round = Round.builder()
                .roundName(roundRequest.getRoundName())
                .createdTime(roundRequest.getCreatedTime())
                .createdBy(roundRequest.getCreatedBy())
                .build();

        roundRepository.save(round);
        log.info("Round added {}", round);
        return ResponseDTO.builder()
                .message("Round added successfully")
                .build();
    }

    public Stage updateStage(Stage stage) throws HttpServerErrorException.InternalServerError {
        Optional<Stage> stageOptional = stageRepository.findById(stage.getStageId());
        if (!stageOptional.isPresent()) {
            throw new ResourceNotFoundException("Stage", "StageId", stageOptional.get().getStageId());
        }
        Stage existingStage = stageOptional.get();
        existingStage.setStageName(stage.getStageName());
        existingStage.setSequenceNo(stage.getSequenceNo());
        log.debug("Updated stage Successfully {}", existingStage);
        return stageRepository.save(existingStage);
    }

    public ResponseDTO addRoundToStage(Long stageId, Round round) throws HttpServerErrorException.InternalServerError {
        Optional<Stage> stageOptional = stageRepository.findById(stageId);
        if (stageOptional.isPresent()) {
            Stage stage = stageOptional.get();
            round.setStage(stage);
            roundRepository.save(round);
            log.info("Round added {}", round);
            return ResponseDTO.builder()
                    .message("Stage added to Round")
                    .build();
        } else
            throw new ResourceNotFoundException("Stage", "stageId", stageId);
    }

    public void updateStageIsActive(Long stageId, boolean isActive) throws HttpServerErrorException.InternalServerError {
        Optional<Stage> optionalStage = stageRepository.findById(stageId);
        if (optionalStage.isPresent()) {
            Stage stage = optionalStage.get();
            stage.setIsActive(isActive);
            stageRepository.save(stage);
            log.info("Stage updated successfully {}", stage);
        } else {
            throw new ResourceNotFoundException("Stage", "StageID", stageId);
        }
    }
    //End of Stage

    public ResponseDTO bulkupdateFeedback(MainBulkUpdateDto mainBulkUpdateDto) throws HttpServerErrorException.InternalServerError {
        List<BulkUpdateId> bulkUpdateIds = mainBulkUpdateDto.getIds();
        List<ApplicantTracking> applicantTrackings = new ArrayList<>();
        BulkUpdateData bulkUpdateData = mainBulkUpdateDto.getData();
        System.out.printf(bulkUpdateData.toString());

        for (BulkUpdateId bulkUpdateId : bulkUpdateIds) {
            Long id = bulkUpdateId.getId();
            if (applicantTrackingRepository.findById(id).isPresent()) {
                applicantTrackings.add(applicantTrackingRepository.findById(id).get());
            }
        }
        for (ApplicantTracking applicantTracking : applicantTrackings) {
            applicantTracking.setEndDate(bulkUpdateData.getEndDate());
            applicantTracking.setReview(bulkUpdateData.getReview());
            applicantTracking.setStatus(bulkUpdateData.getStatus());
            applicantTracking.setFutureRef(bulkUpdateData.getFutureRef());
        }
        for (ApplicantTracking tracking : applicantTrackings) {
            applicantTrackingRepository.saveAndFlush(tracking);
        }
        return ResponseDTO.builder()
                .message("Bulk update completed")
                .build();
    }

    public ResponseDTO bulkupdateCreate(MainUpdateCreate mainUpdateCreate) throws HttpServerErrorException.InternalServerError {
        List<BulkUpdateCreateId> bulkUpdateCreateIds = mainUpdateCreate.getIds();

        BulkUpdateCreate bulkUpdateCreates = mainUpdateCreate.getData();

        List<ApplicantTracking> applicantTrackings = new ArrayList<>();

//        System.out.printf(bulkUpdateData.toString());

        for (BulkUpdateCreateId bulkUpdateCreateId : bulkUpdateCreateIds) {
            ApplicantTracking applicantTracking = new ApplicantTracking();
            Tracking tracking = new Tracking();
            tracking.setTid(bulkUpdateCreateId.getTrackingId());
            applicantTracking.setTracking(tracking);
            applicantTracking.setStartDate(bulkUpdateCreates.getStartDate());
            applicantTracking.setStage(bulkUpdateCreates.getStage());
            applicantTracking.setRound(bulkUpdateCreates.getRound());

            applicantTracking.setStatus(bulkUpdateCreates.getStatus());
            applicantTrackings.add(applicantTracking);
            applicantTrackingRepository.save(applicantTracking);
        }
        return ResponseDTO.builder()
                .message("Bulk update completed")
                .build();
    }

    public List<Integer> getOfferedRejected() throws HttpServerErrorException.InternalServerError {
        Integer offered = applicantTrackingRepository.countByStatus("Offered");
        Integer rejected = applicantTrackingRepository.countByStatus("Rejected");
        Integer BackedOut = applicantTrackingRepository.countByStatus("BackedOut");
        List<Integer> offrej = new ArrayList<>();
        offrej.add(0, offered);
        offrej.add(1, rejected);
        offrej.add(2, BackedOut);
        return offrej;
    }

    public Map<String, Object> selectionToRejection() throws HttpServerErrorException.InternalServerError {
        List<Integer> CountOffRejBack = getOfferedRejected();

        List<Object[]> data = applicantTrackingRepository.countByStream();

        List<Map<String, Object>> streamData = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> map = new HashMap<>();
            map.put("stream", row[0]);
            map.put("selected", row[1]);
            map.put("rejected", row[2]);
            map.put("BackedOut", row[3]);
            streamData.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("offered", CountOffRejBack.get(0));
        result.put("rejected", CountOffRejBack.get(1));
        result.put("BackedOut", CountOffRejBack.get(2));
        result.put("streamData", streamData);

        return result;

    }

    public List<Object[]> countApplicantsByStream() throws HttpServerErrorException.InternalServerError {
        List<Object[]> results = jobDescriptionRepository.countApplicantsByStream();
        return results;
    }

    public Optional<List<UserDto>> getNewUser() throws HttpServerErrorException.InternalServerError {
        return applicantRepository.findNewUsers();
    }

    public List<AllApplicantDataDto> searchAllUser(String search) throws HttpServerErrorException.InternalServerError {
        return trackingRepository.searchApplicants(search);

    }

    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public Optional<List<AllApplicantDataDto>> futureRefApplicantData() throws HttpServerErrorException.InternalServerError {
        log.info("{}", trackingRepository.findByFutureReference());
        return trackingRepository.findByFutureReference();
    }

    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public Optional<List<AllApplicantDataDto>> SearchfutureRefApplicantData(String search) throws HttpServerErrorException.InternalServerError {
        return trackingRepository.SearchByFutureReference(search);
    }

    public Optional<List<PermissionDTO>> getPermissionsByRoleName(String roleName) throws HttpServerErrorException.InternalServerError {
        if (roleRepository.findPermissionByRolename(roleName).isPresent()) {
            return roleRepository.findPermissionByRolename(roleName);
        } else {
            log.error(" rolename is not present in database");
            throw new ResourceNotFoundException("Role", "Rolename");
        }
    }

}
