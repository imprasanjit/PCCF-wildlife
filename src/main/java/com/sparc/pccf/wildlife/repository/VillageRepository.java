package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparc.pccf.wildlife.entity.GpMaster;
import com.sparc.pccf.wildlife.entity.VillageMaster;

public interface VillageRepository extends JpaRepository<VillageMaster, Integer>{

	List<VillageMaster> findByGpOrderByVillageNameAsc(GpMaster gpMaster);

}
