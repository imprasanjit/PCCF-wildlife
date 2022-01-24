package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparc.pccf.wildlife.entity.PublicUserDO;

public interface PublicUserRepository extends JpaRepository<PublicUserDO, Integer>
{
		List<PublicUserDO>findByDivision(String divisionId);

}
