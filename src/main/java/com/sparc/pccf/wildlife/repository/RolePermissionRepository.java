package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparc.pccf.wildlife.entity.RolePermissionDO;

public interface RolePermissionRepository extends JpaRepository<RolePermissionDO, Integer> {

	RolePermissionDO findByRoleIdAndGlinkIdAndPlinkId(String string, String string2, String string3);

	RolePermissionDO findByRoleIdAndGlinkId(Integer id, int parseInt);

	List<RolePermissionDO> findByRoleId(String roleId);

	RolePermissionDO findByRoleIdAndGlinkId(String roleId, String glinkId);

}
