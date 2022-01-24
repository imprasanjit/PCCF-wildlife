package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.BeatMasterDO;
import com.sparc.pccf.wildlife.entity.DivisionMasterDO;
import com.sparc.pccf.wildlife.entity.RangeMasterDO;

public interface RangeMasterRepository extends JpaRepository<RangeMasterDO, Integer> {

	//@Query(value="SELECT * FROM WILDLIFE_OLTP.MT_RANGE WHERE RANGE_NAME LIKE '?1%'",nativeQuery = true)
	RangeMasterDO findByRangeNameLike(String string);
	
	List<RangeMasterDO> findByDivision(DivisionMasterDO divisionMasterDO);
	@Query(value="select rng.range_name,rng.range_id,rng.division_id,div.division_name from wildlife_oltp.master_range rng\r\n" + 
			"join wildlife_oltp.master_division div\r\n" + 
			"on rng.division_id=div.division_id order by rng.range_name",nativeQuery = true)
	List<Object[]> getAllRange();
	
	RangeMasterDO findRangeNameByRangeId(Integer userId);

	RangeMasterDO findDivisionByRangeId(Integer juridictionId);

//	@Query(value="select range_name from wildlife_oltp.master_range where division_id=:divnCode")
//	RangeMasterDO findRangeNameByDivisionId(Integer divnCode);

}
