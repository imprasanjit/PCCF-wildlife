package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparc.pccf.wildlife.entity.NewsDO;

public interface CmsNewsRepository extends JpaRepository<NewsDO, Integer> {

	List<NewsDO> findByDeletedStatus(boolean b);
	// public List<NewsDO> findAllByOrderByCraetedOnDesc();

}
