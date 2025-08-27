package com.trackingservices.repository;

import com.trackingservices.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {

    @Query("SELECT a FROM Address a WHERE a.user.uid = :userId")
    List<Address> findAllByUserId(@Param("userId") Long userId);
    @Modifying
    @Transactional
    @Query("UPDATE Address a SET a.street = :street, a.city = :city, a.state =:state, a.country=:country, a.pincode =:pincode,a.addressType=:addressType WHERE a.user.uid = :id and a.addressId=:addressId")
    void updateUserById(@Param("id") Long uid,
                        @Param("street") String street,
                        @Param("city") String city,
                        @Param("state") String state,
                        @Param("country") String country,
                        @Param("pincode") Integer pincode,
                        @Param("addressType") String addressType,
                        @Param("addressId") Long aid);;

}
