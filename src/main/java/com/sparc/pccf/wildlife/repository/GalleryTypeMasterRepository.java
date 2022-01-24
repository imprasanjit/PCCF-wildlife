package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparc.pccf.wildlife.entity.GalleryTypeMaster;

public interface GalleryTypeMasterRepository extends JpaRepository<GalleryTypeMaster, Integer>{
	
     
	GalleryTypeMaster findByType(String gtype);

	List<GalleryTypeMaster> findByActive(boolean b);

}
