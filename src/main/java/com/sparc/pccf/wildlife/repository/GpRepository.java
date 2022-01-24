package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparc.pccf.wildlife.entity.BlockMaster;
import com.sparc.pccf.wildlife.entity.DistrictMasterDO;
import com.sparc.pccf.wildlife.entity.GpMaster;

public interface GpRepository extends JpaRepository<GpMaster, Integer>{

	List<GpMaster> findByBlockOrderByGpNameAsc(BlockMaster blockMaster);



}
