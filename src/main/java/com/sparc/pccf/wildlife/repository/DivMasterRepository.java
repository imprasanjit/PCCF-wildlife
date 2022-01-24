package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.CircleMasterDO;
import com.sparc.pccf.wildlife.entity.DivisionMasterDO;

public interface DivMasterRepository extends JpaRepository<DivisionMasterDO, Integer> {

	DivisionMasterDO findByDivisionNameLike(String string);

	List<DivisionMasterDO> findByCircle(CircleMasterDO circleMasterDO);
	
	@Query(value="select * from wildlife_oltp.master_division where type_of_forest='WL'",nativeQuery = true)
	List<DivisionMasterDO> getAllDivisionByType();

	@Query(value="select * from wildlife_oltp.master_division where type_of_forest='WL' and CIRCLE_ID=:circle ORDER BY DIVISION_NAME ASC",nativeQuery = true)
	List<DivisionMasterDO> getAllDivisionByTypeByCircleId(int circle);
	
	DivisionMasterDO findDivisionNameByDivisionId(Integer userId);

	DivisionMasterDO findCircleByDivisionId(Integer divisionId);
	
}
