package com.sparc.pccf.wildlife.custom.repository;

import java.util.List;

public interface UserRepositoryCustom {

	List<Object[]> getAllUserListByFilter(String circle, String division, String range, String section, String beat,
			String role);

	List<Object[]> getAllUserList();

	List<Object[]> getAllUserListByCreatedBy(Integer id);

	List<Object[]> getAllCitizenUserList();

	List<Object[]> getAllCitizenUserListByCreatedBy(Integer id);

	List<Object[]> getAllCitizenUserListByFilter(String distId, String blockId, String gpId, String villId);

	

}
