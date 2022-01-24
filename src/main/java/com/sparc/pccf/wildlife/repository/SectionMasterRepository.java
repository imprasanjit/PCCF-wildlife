package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.BeatMasterDO;
import com.sparc.pccf.wildlife.entity.DivisionMasterDO;
import com.sparc.pccf.wildlife.entity.RangeMasterDO;
import com.sparc.pccf.wildlife.entity.SectionMasterDO;

public interface SectionMasterRepository extends JpaRepository<SectionMasterDO, Integer> {

	SectionMasterDO findBySecNameLike(String string);
	List<SectionMasterDO> findByRange(RangeMasterDO rangeMasterDO);
	@Query(value="select sec.section_name,sec.section_id,sec.range_id,sec.division_id,\r\n" + 
			"rng.range_name,div.division_name from wildlife_oltp.master_section sec \r\n" + 
			"join wildlife_oltp.master_range rng \r\n" + 
			"on sec.range_id=rng.range_id \r\n" + 
			"join wildlife_oltp.master_division div\r\n" + 
			"on sec.division_id=div.division_id order by sec.section_name",nativeQuery = true)
	List<Object[]> getAllSection();

	SectionMasterDO findSecNameBySecId(Integer userId);
	SectionMasterDO findRangeBySecId(Integer secid);
}
