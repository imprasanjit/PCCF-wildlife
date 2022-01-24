package com.sparc.pccf.wildlife.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.constant.ERole;
import com.sparc.pccf.wildlife.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByName(ERole name);

	@Query(value = "SELECT role_id FROM wildlife_oltp.t_user_roles WHERE user_id=:loginId", nativeQuery = true)
	int findroleIDByLoginID(Integer loginId);

	@Query(value = " SELECT seq_no FROM wildlife_oltp.t_roles where id:roleId", nativeQuery = true)
	int findSeqNoById(Integer roleId);

	
	
	/*
	 * @Query(value =
	 * "SELECT * FROM wildlife_oltp.t_user_roles WHERE user_id=:loginId",
	 * nativeQuery = true) Role findroleNameByLoginID(Integer loginId);
	 */
}