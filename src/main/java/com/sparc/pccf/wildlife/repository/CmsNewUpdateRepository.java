package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparc.pccf.wildlife.entity.NewUpdate;

public interface CmsNewUpdateRepository extends JpaRepository<NewUpdate, Integer> {

	List<NewUpdate> findByDeletedStatus(boolean b);

}
