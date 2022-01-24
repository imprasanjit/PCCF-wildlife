package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparc.pccf.wildlife.entity.BlogDO;

public interface CmsBlogRepository extends JpaRepository<BlogDO, Integer>
{
	List<BlogDO> findByCraetedBy(Integer id);

	List<BlogDO> findByCraetedByAndDeletedStatus(Integer id, boolean b);

	List<BlogDO> findByDeletedStatus(boolean b);

}
