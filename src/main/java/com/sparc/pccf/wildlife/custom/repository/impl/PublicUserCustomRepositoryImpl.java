package com.sparc.pccf.wildlife.custom.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.sparc.pccf.wildlife.custom.repository.PublicUserCustomRepository;
import com.sparc.pccf.wildlife.dto.MobileNumberResponseDTO;
import com.sparc.pccf.wildlife.entity.PublicUserDO;
@Repository
public class PublicUserCustomRepositoryImpl implements PublicUserCustomRepository
{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<MobileNumberResponseDTO> getAllBroadcastNum(double lat, double lon,String division) 
	{
		String jpql = "select wildlife_oltp.mt_public_users.mobile from wildlife_oltp.mt_public_users where division='"+division+"'and \r\n" + 
				      "ST_Within(ST_SetSRID(ST_MakePoint(wildlife_oltp.mt_public_users.long,wildlife_oltp.mt_public_users.lat),4326),\r\n" + 
				      "ST_Buffer(ST_SetSRID(ST_MakePoint('"+lon+"','"+lat+"'),4326),0.05,'quad_segs=8'))";
		Query queryy = entityManager.createNativeQuery(jpql,MobileNumberResponseDTO.class);
		List<MobileNumberResponseDTO> resultList = queryy.getResultList();
		return resultList;
	}

	@Override
	public List<Object> getAllAuthorityNum(double lat, double lon) {
		String jpql = "SELECT * from wildlife_oltp.fn_triggersms_getdivcode("+lon+","+lat+") where intersectdata=true";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object> resultList = queryy.getResultList();
		return resultList;
	}

}
