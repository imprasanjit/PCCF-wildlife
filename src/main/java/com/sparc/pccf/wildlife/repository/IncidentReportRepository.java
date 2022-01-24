package com.sparc.pccf.wildlife.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.FireDataDO;

public interface IncidentReportRepository extends JpaRepository<FireDataDO, Integer> {

	@Query(value = "	SELECT C.CIRCLE_NAME,C.CIRCLE_ID,D.NAME,sum(D.VALUE) FROM wildlife_oltp.T_DAMAGE_DETAILS D JOIN WILDLIFE_OLTP.T_FIRE_DETAILS F\r\n"
			+ "	ON \r\n" + "	D.incident_id=F.id JOIN WILDLIFE_OLTP.master_circle C \r\n" + "	ON\r\n"
			+ "	F.CIRCLE_ID=C.CIRCLE_ID GROUP BY 1,2,3", nativeQuery = true)
	List<Object[]> getTotalcountGrpByCircleId();

	@Query(value = "SELECT D.DIVISION_NAME,COUNT(*),D.DIVISION_ID FROM WILDLIFE_OLTP.T_FIRE_DETAILS F JOIN WILDLIFE_OLTP.master_division D ON F.DIVISION_ID=D.DIVISION_ID WHERE F.CIRCLE_ID=:circleid GROUP BY D.DIVISION_ID", nativeQuery = true)
	List<Object[]> getTotalcountByCirleIdGrpByDivisionId(Integer circleid);

	@Query(value = "SELECT R.RANGE_NAME,COUNT(*),R.RANGE_ID FROM WILDLIFE_OLTP.T_FIRE_DETAILS F JOIN WILDLIFE_OLTP.master_range R ON F.RANGE_ID=R.RANGE_ID WHERE F.DIVISION_ID=:divisionid GROUP BY R.RANGE_ID", nativeQuery = true)
	List<Object[]> getTotalcountByDivisionIdGrpByCircleID(Integer divisionid);

	@Query(value = "SELECT C.SECTION_NAME,COUNT(*),C.SECTION_ID FROM WILDLIFE_OLTP.T_FIRE_DETAILS F JOIN WILDLIFE_OLTP.master_section C ON F.SECTION_ID=C.SECTION_ID WHERE F.RANGE_ID=:rangeid GROUP BY C.SECTION_ID", nativeQuery = true)
	List<Object[]> getTotalcountByRangeIdGrpBySectionID(Integer rangeid);

	@Query(value = "SELECT B.BEAT_NAME,COUNT(*),B.BEAT_ID FROM WILDLIFE_OLTP.T_FIRE_DETAILS F JOIN WILDLIFE_OLTP.master_beat B ON F.BAET_ID=B.BEAT_ID WHERE F.RANGE_ID=:sectionid GROUP BY B.BEAT_ID", nativeQuery = true)
	List<Object[]> getTotalcountBySectionIdGrpByBeatID(Integer sectionid);

	@Query(value = "SELECT name,SUM(value) FROM WILDLIFE_OLTP.T_DAMAGE_DETAILS d join WILDLIFE_OLTP.T_FIRE_DETAILS f ON d.incident_id=f.id where \r\n" + 
			"f.incident_reported_on>=:startDate and f.incident_reported_on<=:endDate GROUP BY 1", nativeQuery = true)
	List<Object[]> getTotalIncidentCount(Date startDate, Date endDate);
	
	@Query(value = "SELECT c.circle_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_fire_details f join WILDLIFE_OLTP.master_circle c on f.circle_id=c.circle_id \r\n" + 
			"join WILDLIFE_OLTP.T_DAMAGE_DETAILS d on f.id=d.incident_id where c.circle_id=:circle and f.incident_reported_on>=:startDate and f.incident_reported_on<=:endDate group by 1,2", nativeQuery = true)
	List<Object[]> getTotalIncidentCountByCircle(Integer circle,Date startDate, Date endDate);
	
	@Query(value = "SELECT div.division_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_fire_details f join WILDLIFE_OLTP.master_division div on f.division_id=div.division_id \r\n" + 
			"join WILDLIFE_OLTP.T_DAMAGE_DETAILS d on f.id=d.incident_id where div.division_id=:divisionId and f.incident_reported_on>=:startDate and f.incident_reported_on<=:endDate group by 1,2", nativeQuery = true)
	List<Object[]> getTotalIncidentCountByDivision(int divisionId,Date startDate, Date endDate);
	
	@Query(value = "SELECT rng.range_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_fire_details f join WILDLIFE_OLTP.master_range rng on f.range_id=rng.range_id \r\n" + 
			"join WILDLIFE_OLTP.T_DAMAGE_DETAILS d on f.id=d.incident_id where rng.range_id=:rangeId and f.incident_reported_on>=:startDate and f.incident_reported_on<=:endDate group by 1,2", nativeQuery = true)
	List<Object[]> getTotalIncidentCountByRange(int rangeId,Date startDate, Date endDate);

	@Query(value="SELECT c.circle_id,div.division_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_fire_details f join WILDLIFE_OLTP.master_circle c \r\n" + 
			"on f.circle_id=c.circle_id join  WILDLIFE_OLTP.master_division div on f.division_id=div.division_id \r\n" + 
			"join WILDLIFE_OLTP.T_DAMAGE_DETAILS d on f.id=d.incident_id where c.circle_id=:circleId and div.division_id=:divisionId and f.incident_reported_on>=:startDate and f.incident_reported_on<=:endDate group by 1,2,3",nativeQuery = true)
	List<Object[]> getTotalIncidentCountByCircleAndDivision(int circleId, int divisionId,Date startDate, Date endDate);

	@Query(value="SELECT c.circle_id,div.division_id,rng.range_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_fire_details f join WILDLIFE_OLTP.master_circle c \r\n" + 
			"on f.circle_id=c.circle_id join  WILDLIFE_OLTP.master_division div on f.division_id=div.division_id\r\n" + 
			"join  WILDLIFE_OLTP.master_range rng on f.range_id=rng.range_id\r\n" + 
			"join WILDLIFE_OLTP.T_DAMAGE_DETAILS d on f.id=d.incident_id where c.circle_id=:circleId and div.division_id=:divisionId and rng.range_id=:rangeId and f.incident_reported_on>=:startDate and f.incident_reported_on<=:endDate group by 1,2,3,4",nativeQuery = true)
	List<Object[]> getTotalIncidentCountByCircleAndDivisionAndRange(int circleId, int divisionId, int rangeId,Date startDate, Date endDate);

	@Query(value="SELECT div.division_id,rng.range_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_fire_details f \r\n" + 
			"join WILDLIFE_OLTP.master_division div on f.division_id=div.division_id\r\n" + 
			"join  WILDLIFE_OLTP.master_range rng on f.range_id=rng.range_id\r\n" + 
			"join WILDLIFE_OLTP.T_DAMAGE_DETAILS d on f.id=d.incident_id where div.division_id=:divisionId and rng.range_id=:rangeId and f.incident_reported_on>=:startDate and f.incident_reported_on<=:endDate group by 1,2,3",nativeQuery = true)
	List<Object[]> getTotalIncidentCountByDivisionAndRange( int divisionId, int rangeId,Date startDate, Date endDate);

}
