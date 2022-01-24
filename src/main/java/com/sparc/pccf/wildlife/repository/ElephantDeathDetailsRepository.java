package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.ElephantDeathDetailsDO;

public interface ElephantDeathDetailsRepository extends JpaRepository<ElephantDeathDetailsDO, Integer> {

	@Query(value="Select * from wildlife_oltp.t_elephant_death_details where death_id=:parseInt",nativeQuery = true)
	List<ElephantDeathDetailsDO> findByReportId(int parseInt);

}
