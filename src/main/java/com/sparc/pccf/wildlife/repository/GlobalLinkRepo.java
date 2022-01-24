package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.GlobalLinkMaster;

public interface GlobalLinkRepo extends JpaRepository<GlobalLinkMaster, Integer> {
@Query(value="SELECT g.glink_id, g.globalseq, g.path as globalpath, g.name as glinkname,p.id plinkid, p.path as plinkpath,p.name as plinkanme\r\n" + 
		"FROM wildlife_oltp.mt_global_link g left join\r\n" + 
		"       (SELECT * FROM wildlife_oltp.mt_primary_link) as p\r\n" + 
		"    on p.glink_id=g.glink_id order by  ",nativeQuery = true)
	List<Object[]> findAllGLinkAndPlink();

List<GlobalLinkMaster> findAllByOrderByGlobalSeqAsc();

}
