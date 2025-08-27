package com.trackingservices.repository;

import com.trackingservices.dto.ApplicantTrackingDto;
import com.trackingservices.dto.ManageUser;
import com.trackingservices.dto.UserDto;
import com.trackingservices.dto.UserEmailDTO;
import com.trackingservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT new com.trackingservices.dto.UserDto(u.uid,u.firstname,u.lastname, u.email, u.phoneNumber, j.stream,j.roleAppliedFor,mt.status,mt.stage,mt.round) " +
                   " FROM User u JOIN JobDescription j ON j.user.uid=u.uid " +
                   " join Tracking t On t.user.uid=u.uid" +
                   " JOIN ApplicantTracking mt ON t.tid = mt.tracking.tid AND mt.id = (" +
                   " SELECT MAX(mt2.id) " +
                   " FROM ApplicantTracking mt2 " +
                   " WHERE mt2.tracking.tid = mt.tracking.tid)" +
                   " WHERE mt.stage=:stage AND mt.status=:status AND j.stream = :stream AND mt.round=:round ")
    Optional<List<UserDto>> findUsersByStageAndStatusAndStreamAndRound(@Param("stage") String stage,
                                                                       @Param("status") String status,
                                                                       @Param("stream") String stream,
                                                                       @Param("round") String round);

    @Query(value = "SELECT new com.trackingservices.dto.UserDto(u.uid,u.firstname,u.lastname, u.email, u.phoneNumber, j.stream,j.roleAppliedFor,mt.status,mt.stage,mt.round) " +
                   " FROM User u JOIN JobDescription j ON j.user.uid=u.uid " +
                   " join Tracking t On t.user.uid=u.uid" +
                   " JOIN ApplicantTracking mt ON t.tid = mt.tracking.tid AND mt.id = (" +
                   " SELECT MAX(mt2.id) " +
                   " FROM ApplicantTracking mt2 " +
                   " WHERE mt2.tracking.tid = mt.tracking.tid)" +
                   " WHERE mt.stage=:stage AND mt.status=:status AND j.stream = :stream ")
    Optional<List<UserDto>> findUsersByStageAndStatusAndStream(@Param("stage") String stage,
                                                               @Param("status") String status,
                                                               @Param("stream") String stream);

    @Query(value = "SELECT new com.trackingservices.dto.UserDto(u.uid,u.firstname,u.lastname, u.email, u.phoneNumber, j.stream,j.roleAppliedFor,mt.status,mt.stage,mt.round) " +
                   " FROM User u JOIN JobDescription j ON j.user.uid=u.uid " +
                   " join Tracking t On t.user.uid=u.uid" +
                   " JOIN ApplicantTracking mt ON t.tid = mt.tracking.tid AND mt.id = (" +
                   " SELECT MAX(mt2.id) " +
                   " FROM ApplicantTracking mt2 " +
                   " WHERE mt2.tracking.tid = mt.tracking.tid)" +
                   " WHERE mt.stage=:stage AND j.stream = :stream")
    Optional<List<UserDto>> findUsersByStageAndStream(@Param("stage") String stage,
                                                      @Param("stream") String stream);

    @Query(value = "SELECT new com.trackingservices.dto.UserDto(u.uid,u.firstname,u.lastname, u.email, u.phoneNumber, j.stream,j.roleAppliedFor,mt.status,mt.stage,mt.round) " +
                   " FROM User u JOIN JobDescription j ON j.user.uid=u.uid " +
                   " join Tracking t On t.user.uid=u.uid" +
                   " JOIN ApplicantTracking mt ON t.tid = mt.tracking.tid AND mt.id = (" +
                   " SELECT MAX(mt2.id) " +
                   " FROM ApplicantTracking mt2 " +
                   " WHERE mt2.tracking.tid = mt.tracking.tid)" +
                   " WHERE j.stream = :stream")
    Optional<List<UserDto>> findUsersByStream(@Param("stream") String stream);

    @Query(value = "SELECT new com.trackingservices.dto.UserDto(u.uid,u.firstname,u.lastname, u.email, u.phoneNumber, j.stream,j.roleAppliedFor,mt.stage,mt.status,mt.round) " +
                   " FROM User u JOIN JobDescription j ON j.user.uid=u.uid " +
                   " join Tracking t On t.user.uid=u.uid" +
                   " JOIN ApplicantTracking mt ON t.tid = mt.tracking.tid AND mt.id = (" +
                   " SELECT MAX(mt2.id) " +
                   " FROM ApplicantTracking mt2 " +
                   " WHERE mt2.tracking.tid = mt.tracking.tid)" +
                   " WHERE mt.status = :status AND j.stream = :stream")
    Optional<List<UserDto>> findUsersByStatusAndStream(@Param("status") String stage,
                                                       @Param("stream") String stream);

    @Query(value = "SELECT new com.trackingservices.dto.ApplicantTrackingDto(m.status,m.stage,m.review,m.endDate,m.endDate,m.round) " +
                   " FROM User u JOIN Tracking  t on t.user.uid=u.uid" +
                   " join  ApplicantTracking  m on  m.tracking.tid=t.tid" +
                   " WHERE u.uid=:uid")
    Optional<List<ApplicantTrackingDto>> findByUserId(@Param("uid") Long userId);

    @Query(value = "SELECT new com.trackingservices.dto.ManageUser(u.uid,u.firstname, u.phoneNumber ,u.lastname, u.email,u.isActive, r.rolename)\n" +
                   " FROM User u\n" +
                   " INNER JOIN Role r ON u.role.id = r.id\n" +
                   "WHERE r.id NOT IN (1, 3)")
    List<ManageUser> findAllManagedUser();

    @Query(value = "SELECT new com.trackingservices.dto.UserDto(u.uid,u.firstname,u.lastname, u.email, u.phoneNumber, j.stream,j.roleAppliedFor)\n" +
                   "FROM User u\n" +
                   "LEFT OUTER JOIN Tracking t ON t.user.uid = u.uid OR t.recruiter.uid = u.uid\n" +
                   "JOIN JobDescription j ON j.user.uid = u.uid\n" +
                   "WHERE t.tid IS NULL\n\n")
    Optional<List<UserDto>> findNewUsers();

    User findByUid(Long uid);

    @Query(value = "SELECT new com.trackingservices.dto.UserDto(u.uid,u.firstname,u.lastname, u.email, u.phoneNumber, j.stream,j.roleAppliedFor,mt.status,mt.stage,mt.round) " +
                   " FROM User u JOIN JobDescription j ON j.user.uid=u.uid " +
                   " join Tracking t On t.user.uid=u.uid" +
                   " JOIN ApplicantTracking mt ON t.tid = mt.tracking.tid AND mt.id = (" +
                   " SELECT MAX(mt2.id) " +
                   " FROM ApplicantTracking mt2 " +
                   " WHERE mt2.tracking.tid = mt.tracking.tid)" +
                   " WHERE mt.stage=:stage AND j.stream = :stream AND mt.round=:round")
    Optional<List<UserDto>> findUsersByStageAndStreamAndRound(@Param("stage") String stage, @Param("stream") String stream,@Param("round") String round);
    @Query(value = "SELECT new com.trackingservices.dto.UserDto(u.uid,u.firstname,u.lastname, u.email, u.phoneNumber, j.stream,j.roleAppliedFor,mt.stage,mt.status,mt.round) " +
                   " FROM User u JOIN JobDescription j ON j.user.uid=u.uid " +
                   " join Tracking t On t.user.uid=u.uid" +
                   " JOIN ApplicantTracking mt ON t.tid = mt.tracking.tid AND mt.id = (" +
                   " SELECT MAX(mt2.id) " +
                   " FROM ApplicantTracking mt2 " +
                   " WHERE mt2.tracking.tid = mt.tracking.tid)" +
                   " WHERE mt.status = :status AND j.stream = :stream and mt.round=:round ")
    Optional<List<UserDto>> findUsersByStatusAndStreamAndRound(@Param("status") String stage,
                                                               @Param("stream") String stream,
                                                               @Param("round") String round);


}
