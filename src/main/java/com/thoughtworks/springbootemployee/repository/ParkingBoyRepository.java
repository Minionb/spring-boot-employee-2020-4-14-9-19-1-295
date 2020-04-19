package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ParkingBoyRepository extends JpaRepository<ParkingBoy, Integer> {
    List<ParkingBoy> findAllByNickname(String nickname);

    @Modifying
    @Query(value = "ALTER TABLE PARKING_BOY ALTER COLUMN ID RESTART WITH 1", nativeQuery = true)
    void resetAutoIncrement();
}
