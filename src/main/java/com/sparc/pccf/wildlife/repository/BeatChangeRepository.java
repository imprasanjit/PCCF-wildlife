package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.DivisionMasterDO;

public interface BeatChangeRepository extends JpaRepository<DivisionMasterDO, Integer>{
	@Query(value="select u.mobile FROM wildlife_oltp.mt_users u JOIN wildlife_oltp.t_user_roles r ON u.user_id = r.user_id JOIN wildlife_oltp.master_division d ON u.juridiction_id = d.division_id JOIN wildlife_oltp.master_beat b ON d.division_id = b.division_id WHERE r.role_id = 2 and v.new_beat_id=:beat_id",nativeQuery = true)
	List<String> findDfoMobileByBeatId(int beat_id);
	
	@Query(value="select d.control_room_phno FROM wildlife_oltp.master_division d JOIN wildlife_oltp.master_beat b ON d.division_id = b.division_id WHERE b.beat_id=:beat_id",nativeQuery = true)
	String findControlRoomMobileByBeatId(int beat_id);
	
	@Query(value="select d.division_name from wildlife_oltp.master_division d JOIN wildlife_oltp.master_beat b ON d.division_id = b.division_id WHERE b.beat_id=:beat_id",nativeQuery = true)
	String findDivisionNameByBeatId(int beat_id);
	
}
