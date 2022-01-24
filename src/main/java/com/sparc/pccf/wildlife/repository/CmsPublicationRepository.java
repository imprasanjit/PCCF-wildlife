package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparc.pccf.wildlife.entity.PublicationDO;

public interface CmsPublicationRepository extends JpaRepository<PublicationDO, Integer>{

	List<PublicationDO> findByDeletedStatus(boolean b);

}
