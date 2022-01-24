package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sparc.pccf.wildlife.entity.Role;

public interface RoleAssignRepository extends JpaRepository<Role, Integer> 
{
	@Query(value = " SELECT * FROM wildlife_oltp.t_roles where seq_no >:roleId", nativeQuery = true)
	List<Role> findRoleBySeqNo(Integer roleId);

}
