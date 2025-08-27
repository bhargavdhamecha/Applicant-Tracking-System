package com.trackingservices.repository;

import com.trackingservices.dto.UserEmailDTO;
import com.trackingservices.model.ApplicantTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicantTrackingRepository extends JpaRepository<ApplicantTracking, Long> {

    @Query(value = "SELECT * FROM main_table m WHERE m.trackingid=:trackingid", nativeQuery = true)
    List<ApplicantTracking> findByTrackingID(@Param("trackingid") Integer trackingID);

    Integer countByStatus(String status);

    //    ApplicantTracking findByTracking_Tid(Long id);
    @Query("SELECT j.stream,SUM(CASE WHEN m.status = 'Offered' THEN 1 ELSE 0 END),SUM(CASE WHEN m.status = 'Rejected' THEN 1 ELSE 0 END),SUM(CASE WHEN m.status = 'BackedOut' THEN 1 ELSE 0 END) FROM JobDescription j JOIN Tracking h ON j.user.uid = h.user.uid JOIN ApplicantTracking m ON h.tid = m.tracking.tid GROUP BY j.stream")
    List<Object[]> countByStream();

    @Query(value = "SELECT new com.trackingservices.dto.UserEmailDTO(u.uid,u.firstname,u.lastname,u.email)" +
                   "from ApplicantTracking mt JOIN User u ON u.uid = mt.tracking.user.uid WHERE mt.id = :id")
    UserEmailDTO getUserByTrackingId(@Param("id") Long trackingID);





}
