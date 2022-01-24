package com.sparc.pccf.wildlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparc.pccf.wildlife.entity.PublicUserDO;

public interface IGajasathiUserRepository extends JpaRepository<PublicUserDO, Integer> {

	PublicUserDO findByMobile(Long parseInt);

}
