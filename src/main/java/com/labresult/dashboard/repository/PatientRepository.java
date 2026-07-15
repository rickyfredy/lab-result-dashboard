package com.labresult.dashboard.repository;

import com.labresult.dashboard.entity.Patient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query("SELECT p FROM Patient p ORDER BY p.name")
    List<Patient> findAllOrderedByName();
}
