package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparc.pccf.wildlife.entity.BlockMaster;
import com.sparc.pccf.wildlife.entity.DistrictMasterDO;

public interface BlockRepository extends JpaRepository<BlockMaster, Integer>{

	List<BlockMaster> findByDistrictOrderByBlockNameAsc(Integer distId);

	List<BlockMaster> findBydistrict(DistrictMasterDO districtMasterDO);
	
	

}
