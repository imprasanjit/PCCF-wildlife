package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.CircleMasterDO;


public interface CircleMasterRepository  extends JpaRepository<CircleMasterDO, Integer>{

	CircleMasterDO findByCircleName(String circleId);
	@Query(value="select * from wildlife_oltp.master_circle where wildlife_type='WL'",nativeQuery = true)
	List<CircleMasterDO> getAllCircleByType();

}
