package com.sparc.pccf.wildlife.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.CitizenUser;
import com.sparc.pccf.wildlife.entity.VillageMaster;

public interface CitizenUserRepository extends JpaRepository<CitizenUser, Integer>{

	Boolean existsByMobile(String phone);

	@Query(value="SELECT distinct mobile FROM wildlife_oltp.citizen_user where is_active=true and village_id=:village_id",nativeQuery = true)
	List<String> findMobileByVillage(Integer village_id);

	


}
