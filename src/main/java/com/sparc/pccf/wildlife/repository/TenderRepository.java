package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparc.pccf.wildlife.entity.TenderDO;

public interface TenderRepository extends JpaRepository<TenderDO, Integer>{

	List<TenderDO> findByDeletedStatus(boolean b);

}
