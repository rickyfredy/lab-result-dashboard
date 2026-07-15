package com.labresult.dashboard.repository;

import com.labresult.dashboard.entity.TestResult;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Integer> {

    @Query("SELECT DISTINCT t.title FROM TestResult t WHERE t.patientId = :patientId ORDER BY t.title")
    List<String> findDistinctTitlesByPatientId(@Param("patientId") Integer patientId);

    @Query("SELECT DISTINCT t.testName FROM TestResult t WHERE t.patientId = :patientId AND t.title = :title ORDER BY t.testName")
    List<String> findDistinctTestNamesByPatientIdAndTitle(@Param("patientId") Integer patientId,
                                                          @Param("title") String title);

    @Query("SELECT t FROM TestResult t WHERE t.patientId = :patientId AND t.title = :title ORDER BY t.testDate DESC")
    Page<TestResult> findByPatientIdAndTitleOrderByTestDateDesc(@Param("patientId") Integer patientId,
                                                                @Param("title") String title,
                                                                Pageable pageable);

    long countByPatientIdAndTitle(Integer patientId, String title);

    @Query("SELECT t FROM TestResult t WHERE t.patientId = :patientId AND t.title = :title AND t.testName = :testName AND t.testDate >= :startDate ORDER BY t.testDate ASC")
    List<TestResult> findHistoricalAfter(@Param("patientId") Integer patientId,
                                         @Param("title") String title,
                                         @Param("testName") String testName,
                                         @Param("startDate") LocalDateTime startDate);

    @Query("SELECT t FROM TestResult t WHERE t.patientId = :patientId AND t.title = :title AND t.testName = :testName AND t.testDate < :cutoffDate ORDER BY t.testDate ASC")
    List<TestResult> findHistoricalBefore(@Param("patientId") Integer patientId,
                                          @Param("title") String title,
                                          @Param("testName") String testName,
                                          @Param("cutoffDate") LocalDateTime cutoffDate);
}
