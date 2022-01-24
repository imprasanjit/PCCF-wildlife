package com.sparc.pccf.wildlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparc.pccf.wildlife.entity.SanctuaryInfoMasterDO;

public interface SanctuaryRepository extends JpaRepository<SanctuaryInfoMasterDO, Integer> {

}
