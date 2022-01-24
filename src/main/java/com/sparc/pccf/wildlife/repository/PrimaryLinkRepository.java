package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.PrimaryLinkMaster;

public interface PrimaryLinkRepository extends JpaRepository<PrimaryLinkMaster,Integer>{
	/*
	 * @Query(
	 * value="SELECT * FROM WILDLIFE_OLTP.T_ROLE_PERMISSION WHERE ROLE_ID=:parseInt ORDER BY ID"
	 * ,nativeQuery = true) List<PrimaryLinkMaster> findByLinkId(int parseInt);
	 */

}
