package com.trackingservices.repository;

import com.trackingservices.dto.AllApplicantDataDto;
import com.trackingservices.model.Tracking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrackingRepository extends PagingAndSortingRepository<Tracking, Long> {
    @Query(value = "SELECT * FROM hr_user_table h WHERE h.user_id=:user_id", nativeQuery = true)
    Optional<Tracking> findByTrackingID(@Param("user_id") Integer userid);

    @Query(value = "SELECT new com.trackingservices.dto.AllApplicantDataDto(mt.id,u.uid,t.tid,u.firstname,u.lastname,mt.status,mt.stage,j.stream,mt.round )\n" +
                   "FROM  User u\n" +
                   "JOIN Tracking  t on t.user.uid=u.uid \n" +
                   "JOIN JobDescription j ON j.user.uid =t.user.uid\n" +
                   "JOIN ApplicantTracking mt ON t.tid = mt.tracking.tid AND mt.id = (" +
                   "  SELECT MAX(mt2.id)\n" +
                   "  FROM ApplicantTracking mt2\n" +
                   "  WHERE mt2.tracking.tid = mt.tracking.tid\n" +
                   ") ")
    Optional<Page<AllApplicantDataDto>> findAllByUser(PageRequest of);

    @Query(value = "SELECT new com.trackingservices.dto.AllApplicantDataDto(mt.id,t.user.uid,t.tid,u.firstname,u.lastname,mt.status,mt.stage,j.stream,mt.round)\n" +
                   "FROM Tracking t\n" +
                   "JOIN t.user u\n" +
                   "JOIN JobDescription j ON t.user.uid = j.user.uid\n" +
                   "JOIN ApplicantTracking mt ON t.tid = mt.tracking.tid AND mt.id = (\n" +
                   " SELECT MAX(mt2.id)\n" +
                   " FROM ApplicantTracking mt2\n" +
                   " WHERE mt2.tracking.tid = mt.tracking.tid\n" +
                   ")\n" +
                   "WHERE mt.futureRef = true")
    Optional<List<AllApplicantDataDto>> findByFutureReference();

    @Query("SELECT new com.trackingservices.dto.AllApplicantDataDto(mt.id,t.user.uid,t.tid,u.firstname,u.lastname,mt.status,mt.stage,j.stream,mt.round) " +
           "FROM Tracking t " +
           "JOIN t.user u " +
           "JOIN JobDescription j ON t.user.uid = j.user.uid " +
           "JOIN ApplicantTracking mt ON t.tid = mt.tracking.tid AND mt.id = (" +
           " SELECT MAX(mt2.id) " +
           " FROM ApplicantTracking mt2 " +
           " WHERE mt2.tracking.tid = mt.tracking.tid " +
           " ) " +
           "WHERE LOWER(u.firstname) LIKE %:search% OR LOWER(j.stream) LIKE %:search%")
    List<AllApplicantDataDto> searchApplicants(@Param("search") String search);

    @Query(value = "SELECT new com.trackingservices.dto.AllApplicantDataDto(mt.id,t.user.uid,t.tid,u.firstname,u.lastname,mt.status,mt.stage,j.stream,mt.round)\n" +
                   "FROM Tracking t\n" +
                   "JOIN t.user u\n" +
                   "JOIN JobDescription j ON t.user.uid = j.user.uid\n" +
                   "JOIN ApplicantTracking mt ON t.tid = mt.tracking.tid AND mt.id = (\n" +
                   " SELECT MAX(mt2.id)\n" +
                   " FROM ApplicantTracking mt2\n" +
                   " WHERE mt2.tracking.tid = mt.tracking.tid\n" +
                   ")\n" +
                   "WHERE mt.futureRef = true" +
                   " AND " +
                   "LOWER(u.firstname) LIKE %:search% OR LOWER(j.stream) LIKE %:search%")
    Optional<List<AllApplicantDataDto>> SearchByFutureReference(@Param("search") String search);


//    public ApplicantTrackingDto(String status, String stage, String review,
//                                LocalDate startDate, LocalDate endDate, Tracking tracking) {
//        this.status = status;
//        this.stage = stage;
//        this.review = review;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.tracking = tracking;
//    }


}
