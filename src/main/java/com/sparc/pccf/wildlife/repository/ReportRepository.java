package com.sparc.pccf.wildlife.repository;

import java.util.Date;
import java.util.List;

import org.hibernate.type.DateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.DivisionMasterDO;
import com.sparc.pccf.wildlife.entity.ReportDO;
import com.sparc.pccf.wildlife.response.LatLongResponse;
import com.sparc.pccf.wildlife.response.ReportResponse;

public interface ReportRepository extends JpaRepository<ReportDO, Integer> {

	ReportDO findByReportId(String reportId);

	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division AND SURVEYOR_USERID=:userId AND SIGHTING_DATE>=:stDate AND SIGHTING_DATE<=:enDate AND REPORT_TYPE=:reportType", nativeQuery = true)
	List<ReportDO> findByDivisionAndUserIdAndStDateAndEndDateAndReportType(String reportType,Integer division, Date stDate, Date enDate, int userId);

	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division AND SURVEYOR_USERID=:userId AND SIGHTING_DATE>=:enDate AND REPORT_TYPE=:reportType", nativeQuery = true)
	List<ReportDO> findByDivisionAndUserIdAndReportType(String reportType,Integer division, Date enDate, int userId);

	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division AND SIGHTING_DATE>=:stDate AND SIGHTING_DATE<=:enDate AND REPORT_TYPE=:reportType", nativeQuery = true)
	List<ReportDO> findByDivisionAndStDateAndEndDateAndReportType(String reportType,Integer division, Date stDate, Date enDate);

	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division AND SIGHTING_DATE>=:enDate AND REPORT_TYPE=:reportType", nativeQuery = true)
	List<ReportDO> findByDivisionAndReportType(String reportType,Integer division, Date enDate);

	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division", nativeQuery = true)
	int getCountByDivision(int division);

	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division AND REPORT_TYPE=" + "'direct'", nativeQuery = true)
	int getAllReportCountDirectByDiv(int division);

	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division AND REPORT_TYPE=" + "'indirect'", nativeQuery = true)
	int getAllReportCountInDirectByDiv(int division);

	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE SURVEYOR_USERID=:userId AND SIGHTING_DATE>=:stDate AND SIGHTING_DATE<=:enDate AND REPORT_TYPE=:reportType", nativeQuery = true)
	List<ReportDO> findByUserIdAndStDateAndEndDateAndReportType(String reportType,Date stDate, Date enDate, int userId);

	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE SURVEYOR_USERID=:userId AND SIGHTING_DATE>=:enDate AND REPORT_TYPE=:reportType", nativeQuery = true)
	List<ReportDO> findByUserIdAndReportType(String reportType,Date enDate, int userId);

	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE SIGHTING_DATE>=:stDate AND SIGHTING_DATE<=:enDate AND REPORT_TYPE=:reportType", nativeQuery = true)
	List<ReportDO> findByStDateAndEndDateAndReportType(String reportType,Date stDate, Date enDate);

	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE SIGHTING_DATE>=:enDate AND REPORT_TYPE=:reportType", nativeQuery = true)
	List<ReportDO> findByAllAndReportType(String reportType,Date enDate);

	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT", nativeQuery = true)
	int getAllCount();

	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE  REPORT_TYPE=" + "'direct'", nativeQuery = true)
	int getAllReportCountDirect();

	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE REPORT_TYPE=" + "'indirect'", nativeQuery = true)
	int getAllReportCountInDirect( );
	
	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE SIGHTING_DATE>=:enDate", nativeQuery = true)
	int getAllCountIn24Hrs(Date enDate);

	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE SIGHTING_DATE>=:enDate AND REPORT_TYPE=" + "'direct'", nativeQuery = true)
	int getAllReportCountDirectIn24Hrs(Date enDate);

	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE SIGHTING_DATE>=:enDate AND REPORT_TYPE=" + "'indirect'", nativeQuery = true)
	int getAllReportCountInDirectIn24Hrs(Date enDate);
	
	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE SIGHTING_DATE>=:enDate AND REPORT_TYPE=" + "'nill'", nativeQuery = true)
	int getAllReportCounNillIn24Hrs(Date enDate);
	
	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.t_elephant_death WHERE created_on>=:enDate", nativeQuery = true)
	int getAllReportCountElephantDeathIn24Hrs(Date enDate);
	
	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.t_fire_details WHERE incident_reported_on>=:enDate", nativeQuery = true)
	int getAllReportCountIncidentFireAlertIn24Hrs(Date enDate);
	
	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division AND SIGHTING_DATE>=:enDate", nativeQuery = true)
	int getCountByDivisionIn24Hrs(Date enDate, int division);

	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division AND SIGHTING_DATE>=:enDate AND REPORT_TYPE=" + "'direct'", nativeQuery = true)
	int getAllReportCountDirectByDivIn24Hrs(Date enDate, int division);

	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division AND  SIGHTING_DATE>=:enDate AND REPORT_TYPE=" + "'indirect'", nativeQuery = true)
	int getAllReportCountInDirectByDivIn24Hrs(Date enDate, int division);
	
	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division AND SIGHTING_DATE>=:enDate AND REPORT_TYPE=" + "'nill'", nativeQuery = true)
	int getAllReportCountInNillByDivIn24Hrs(Date enDate, int division);
	
	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.t_elephant_death WHERE DIVISION_ID=:division AND created_on>=:enDate", nativeQuery = true)
	int getAllReportCountElephantDeathByDivIn24Hrs(Date enDate, int division);
	
	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.t_fire_details WHERE DIVISION_ID=:division AND incident_reported_on>=:enDate", nativeQuery = true)
	int getAllReportCountIncidentFireAlertByDivIn24Hrs(Date enDate, int division);

	@Query(value = "SELECT LATITUDE,LONGITUDE FROM wildlife_oltp.T_SIGHTING_REPORT WHERE REPORT_TYPE='direct'", nativeQuery = true)
	List<Object[]> findAllLatLong();
	
	@Query(value = "SELECT LATITUDE,LONGITUDE FROM wildlife_oltp.T_SIGHTING_REPORT WHERE REPORT_TYPE='direct' and division_id=:division", nativeQuery = true)
	List<Object[]> findAllLatLongByDivId(int division);
	
	List<ReportDO> findByDivision(DivisionMasterDO divisionMasterDO);
	
	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE SIGHTING_DATE>=:enDate", nativeQuery = true)
	List<ReportDO> findAllByDate(Date enDate);

	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE  DIVISION_ID=:division AND SIGHTING_DATE>=:enDate", nativeQuery = true)
	List<ReportDO> findAllByDivision(int division, Date enDate);

	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE SIGHTING_DATE>=:stDate AND SIGHTING_DATE<=:enDate", nativeQuery = true)
	List<ReportDO> findByStDateAndEndDate(Date stDate, Date enDate);

	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division AND SIGHTING_DATE>=:stDate AND SIGHTING_DATE<=:enDate", nativeQuery = true)
	List<ReportDO> findByStDateAndEndDateAndDivision(int division, Date stDate, Date enDate);
	
	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE SURVEYOR_USERID=:userId AND SIGHTING_DATE>=:enDate", nativeQuery = true)
	List<ReportDO> findByUserIdAndEndDate(Date enDate, int userId);
	
	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division AND SURVEYOR_USERID=:userId AND SIGHTING_DATE>=:enDate", nativeQuery = true)
	List<ReportDO> findByUserIdAndDivison(int division, Date enDate, int userId);

	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE SURVEYOR_USERID=:userId AND SIGHTING_DATE>=:stDate AND SIGHTING_DATE<=:enDate", nativeQuery = true)
	List<ReportDO> findByUserIdAndStDateAndEndDate(Date stDate, Date enDate, int userId);
	
	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division AND SURVEYOR_USERID=:userId AND SIGHTING_DATE>=:stDate AND SIGHTING_DATE<=:enDate", nativeQuery = true)
	List<ReportDO> findByUserIdAndStDateAndEndDateAndDivision(int division, Date stDate, Date enDate, int userId);

	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE REPORT_TYPE=" + "'nill'", nativeQuery = true)
	int getAllReportCounNill();
	
	@Query(value = "SELECT COUNT(*) FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division AND REPORT_TYPE=" + "'nill'", nativeQuery = true)
	int getAllReportCountInNillByDiv(int division);

	@Query(value="select latitude,longitude from wildlife_oltp.t_sighting_report WHERE sighting_date >=:date",nativeQuery = true)
	List<Object[]> findAllLatLongByDate(Date date);

	List<ReportDO> findAllBySurveyorUserId(Integer surveyorUserId);
     
	//@Query(value = "select * from wildlife_oltp.t_sighting_report where surveyor_userid='3' and sighting_date between 'sightingDate1' and 'sightingDate2'",nativeQuery = true)
	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE SURVEYOR_USERID=:surveyorUserId AND sighting_time_from>=:from AND sighting_time_to<=:to", nativeQuery = true)
	List<ReportDO> findAllBysurveyorUserIdAndSightingDateBetween(Integer surveyorUserId,Date from ,Date to);

	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT", nativeQuery = true)
	List<ReportDO> getAllData();

	@Query(value = "SELECT * FROM wildlife_oltp.T_SIGHTING_REPORT WHERE DIVISION_ID=:division", nativeQuery = true)
	List<ReportDO> getAllDataByDivisionId(int division);
	

	

	

	


}
