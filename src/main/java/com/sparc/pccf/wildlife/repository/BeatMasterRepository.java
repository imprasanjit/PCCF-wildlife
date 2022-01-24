package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.BeatMasterDO;
import com.sparc.pccf.wildlife.entity.SectionMasterDO;

public interface BeatMasterRepository extends JpaRepository<BeatMasterDO, Integer> {

	//@Query(value="SELECT * FROM wildlife_oltp.master_beat where beat_name like :?1% AND section_id=:?2",nativeQuery = true)
	BeatMasterDO findByBeatNameAndSection(String string, SectionMasterDO sectionMasterDO);
	
	List<BeatMasterDO> findBySection(SectionMasterDO sectionMasterDO);
	@Query(value="select beat.beat_name,beat.beat_id,beat.section_id,beat.range_id,beat.division_id,sec.section_name,rag.range_name,div.division_name,div.circle_id,cir.circle_name\r\n" + 
			"from wildlife_oltp.master_beat beat join wildlife_oltp.master_section sec on beat.section_id=sec.section_id \r\n" + 
			"join wildlife_oltp.master_range rag on beat.range_id=rag.range_id \r\n" + 
			"join wildlife_oltp.master_division div on beat.division_id=div.division_id \r\n" + 
			"join wildlife_oltp.master_circle cir on div.circle_id=cir.circle_id\r\n" + 
			"order by beat.beat_name",nativeQuery = true)
	List<Object[]> getAllBeat();
	 
	BeatMasterDO findBeatNameByBeatId(Integer userId);

	BeatMasterDO findSectionByBeatId(Integer juridictionId);
}
