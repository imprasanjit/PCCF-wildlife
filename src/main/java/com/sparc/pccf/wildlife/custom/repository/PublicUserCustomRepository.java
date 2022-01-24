package com.sparc.pccf.wildlife.custom.repository;

import java.util.List;


import com.sparc.pccf.wildlife.dto.MobileNumberResponseDTO;

public interface PublicUserCustomRepository 
{
	List<MobileNumberResponseDTO> getAllBroadcastNum(double lat,double lon,String division);

	List<Object> getAllAuthorityNum(double lat, double lon);

}
