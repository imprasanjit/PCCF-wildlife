package com.sparc.pccf.wildlife.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.entity.User;

public interface AuthRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	User findByusername(String username);

	@Query(value="select distinct mobile from wildlife_oltp.mt_users where user_id=(select user_id from wildlife_oltp.t_user_roles where role_id='2') \r\n" + 
			"and juridiction_id=(select division_id from wildlife_oltp.master_division where division_name=:division)",nativeQuery = true)
	List<String> findMobileByDivision(String division);

	@Query(value="select distinct mobile from wildlife_oltp.mt_users u join wildlife_oltp.t_user_roles ro on ro.user_id=u.user_id \r\n" + 
			"where ro.role_id='2' and juridiction_id=:division",nativeQuery = true)
	List<String> findDFOMobilesByDivisionID(Integer division);
	
	User findUserNameAndPasswordByMobile(String parseInt);

	//@Query(value="select * from wildlife_oltp.mt_users where mobile=:mobile)",nativeQuery = true)
	Optional<User> findByMobile(String mobile);
	//List<User> findByMobile(String mobile);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByResetToken(String resetToken);

	@Query(value="SELECT  distinct mobile FROM wildlife_oltp.\"wildlife_view_authMobile\" where division_id=:division",nativeQuery = true)
	List<String> findMobileByDivisionId(Integer division);

	Boolean existsByMobile(String phone);
	
	List<User> findByCraetedBy(Integer id);	
	
}