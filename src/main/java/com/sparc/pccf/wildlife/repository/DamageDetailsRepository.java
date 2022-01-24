package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.DamageDeatilsDO;

public interface DamageDetailsRepository extends JpaRepository<DamageDeatilsDO, Integer> {
	@Query(value="Select * from wildlife_oltp.t_damage_details where incident_id=:parseInt",nativeQuery = true)
	List<DamageDeatilsDO> findByReportId(int parseInt);


}
