package com.sparc.pccf.wildlife.custom.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.sparc.pccf.wildlife.custom.repository.UserRepositoryCustom;
@Component
public class UserRepositoryCustomImpl implements UserRepositoryCustom{
	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public List<Object[]> getAllUserListByFilter(String circle, String division, String range, String section,
			String beat, String role) {
		String jpql = "SELECT * FROM wildlife_oltp.fn_getalluser('"+circle+"','"+division+"','"+range+"','"+section+"','"+beat+"','"+role+"')";
		Query query = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = query.getResultList();
		return resultList;
	}
	@Override
	public List<Object[]> getAllUserList() {
		String jpql = "SELECT * FROM wildlife_oltp.wildlife_view_getalluser";
		Query query = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = query.getResultList();
		return resultList;
	}
	@Override
	public List<Object[]> getAllUserListByCreatedBy(Integer id) {
		String jpql = "SELECT * FROM wildlife_oltp.wildlife_view_getalluserdetails where created_by='"+id+"'";
		Query query = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = query.getResultList();
		return resultList;
	}
	@Override
	public List<Object[]> getAllCitizenUserList() {
		String jpql = "SELECT * FROM wildlife_oltp.wildlife_view_getallCitizenUser";
		Query query = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = query.getResultList();
		return resultList;
	}
	@Override
	public List<Object[]> getAllCitizenUserListByCreatedBy(Integer id) {
		String jpql = "SELECT * FROM wildlife_oltp.wildlife_view_getallCitizenUser where created_by='"+id+"'";
		Query query = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = query.getResultList();
		return resultList;
	}
	@Override
	public List<Object[]> getAllCitizenUserListByFilter(String distId, String blockId, String gpId, String villId) {
		String jpql = "SELECT * FROM wildlife_oltp.fn_getallCitizenUserByFilter('"+distId+"','"+blockId+"','"+gpId+"','"+villId+"')";
		Query query = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = query.getResultList();
		return resultList;
	}
}
