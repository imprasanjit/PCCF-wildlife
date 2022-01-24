package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.ElephantDeathDO;

public interface ElephantDeathReportRepository extends JpaRepository<ElephantDeathDO, Integer> {

	@Query(value = "SELECT c.circle_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_elephant_death_report r \r\n" + 
			"join WILDLIFE_OLTP.master_circle c on r.circle_id=c.circle_id join WILDLIFE_OLTP.t_elephant_death_details d \r\n" + 
			"on r.death_id=d.death_id where c.circle_id=:circleId and where r.incident_reported_on>=:startDate and r.incident_reported_on<=:endDate\r\n" + 
			"group by 1,2", nativeQuery = true)
	List<Object[]> getTotalIncidentCountByCircle(int circleId, String startDate, String endDate);

	@Query(value = "SELECT c.division_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_elephant_death_report r join WILDLIFE_OLTP.master_division c on r.division_id=c.division_id join WILDLIFE_OLTP.t_elephant_death_details d on r.death_id=d.death_id where c.division_id=:divisionId r.incident_reported_on>=:startDate and r.incident_reported_on<=:endDate group by 1,2", nativeQuery = true)
	List<Object[]> getTotalIncidentCountByDivision(int divisionId,String startDate, String endDate);

	@Query(value="SELECT c.division_id,rng.range_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_elephant_death_report r \r\n" + 
			"join WILDLIFE_OLTP.master_division c on r.division_id=c.division_id \r\n" + 
			"join  WILDLIFE_OLTP.master_range rng on r.range_id=rng.range_id \r\n" + 
			"join WILDLIFE_OLTP.t_elephant_death_details d on r.death_id=d.death_id where c.division_id=:divisionId and rng.range_id=:rangeId r.incident_reported_on>=:startDate and r.incident_reported_on<=:endDate group by 1,2,3",nativeQuery = true)
	List<Object[]> getTotalIncidentCountByDivisionAndRange(int divisionId, int rangeId,String startDate, String endDate);

	@Query(value = "SELECT d.name,SUM(d.value) FROM WILDLIFE_OLTP.t_elephant_death_details d join WILDLIFE_OLTP.t_elephant_death_report r \r\n" + 
				"on r.death_id=d.death_id where r.incident_reported_on>=:startDate and r.incident_reported_on<=:endDate group by 1", nativeQuery = true)
	List<Object[]> getTotalIncidentCount( String startDate, String endDate);

	@Query(value="SELECT c.circle_id,div.division_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_elephant_death_report r \r\n" + 
			"join WILDLIFE_OLTP.master_circle c on r.circle_id=c.circle_id \r\n" + 
			"join  WILDLIFE_OLTP.master_division div on r.division_id=div.division_id \r\n" + 
			"join WILDLIFE_OLTP.t_elephant_death_details d on r.death_id=d.death_id where c.circle_id=:circleId and div.division_id=:divisionId and r.incident_reported_on>=:startDate and r.incident_reported_on<=:endDate\r\n" + 
			"group by 1,2,3",nativeQuery = true)
	List<Object[]> getTotalIncidentCountByCircleAndDivision(int circleId, int divisionId,String startDate, String endDate);

	@Query(value="SELECT c.circle_id,div.division_id,rng.range_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_elephant_death_report r \r\n" + 
			"join WILDLIFE_OLTP.master_circle c on r.circle_id=c.circle_id \r\n" + 
			"join  WILDLIFE_OLTP.master_division div on r.division_id=div.division_id \r\n" + 
			"join  WILDLIFE_OLTP.master_range rng on r.range_id=rng.range_id\r\n" + 
			"join WILDLIFE_OLTP.t_elephant_death_details d on r.death_id=d.death_id where c.circle_id=:circleId and\r\n" + 
			"div.division_id=:divisionId and rng.range_id=:rangeId and r.incident_reported_on>=:startDate and r.incident_reported_on<=:endDate\r\n" + 
			"group by 1,2,3,4",nativeQuery = true)
	List<Object[]> getTotalIncidentCountByCircleAndDivisionAndRange(int circleId, int divisionId, int rangeId,String startDate, String endDate);

	@Query(value = "SELECT div.division_id,rng.range_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_elephant_death_report r \r\n" + 
			"join  WILDLIFE_OLTP.master_division div on r.division_id=div.division_id \r\n" + 
			"join  WILDLIFE_OLTP.master_range rng on r.range_id=rng.range_id\r\n" + 
			"join WILDLIFE_OLTP.t_elephant_death_details d on r.death_id=d.death_id where rng.range_id=:rangeId and r.incident_reported_on>=:startDate and r.incident_reported_on<=:endDate\r\n" + 
			"group by 1,2,3", nativeQuery = true)
	List<Object[]> getTotalIncidentCountByRange(int rangeId,String startDate, String endDate);
	

}
