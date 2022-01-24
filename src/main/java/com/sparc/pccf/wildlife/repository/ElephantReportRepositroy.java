package com.sparc.pccf.wildlife.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.ElephantReportDO;
import com.sparc.pccf.wildlife.entity.PublicUserDO;

public interface ElephantReportRepositroy extends JpaRepository<ElephantReportDO, Integer>{

	List<ElephantReportDO> findAllByUserId(long parseInt);
	
	Optional<ElephantReportDO> findByReportId(Integer reportId);
	
}
