package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Integer>{

	List<Gallery> findByDeletedStatus(boolean b);

	@Query(value = "select g.* from wildlife_oltp.t_gallery g join wildlife_oltp.t_gallerytype t on g.type_id=t.type_id where g.deleted_status=false and t.active=true", nativeQuery = true)
	List<Gallery> findByDeletedStatusAndTypeMasterTrue();

	@Query(value = "select g.* from wildlife_oltp.t_gallery g join wildlife_oltp.t_gallerytype t on g.type_id=t.type_id where g.deleted_status=false and t.type_id=:gtype", nativeQuery = true)
	List<Gallery> findByDeletedStatusAndImgType(Integer gtype);

	

}

