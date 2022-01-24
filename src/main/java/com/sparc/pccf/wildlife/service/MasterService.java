package com.sparc.pccf.wildlife.service;

import java.util.List;

import com.sparc.pccf.wildlife.entity.BlockMaster;
import com.sparc.pccf.wildlife.entity.CircleMasterDO;
import com.sparc.pccf.wildlife.entity.DistrictMasterDO;
import com.sparc.pccf.wildlife.entity.DivisionMasterDO;
import com.sparc.pccf.wildlife.entity.RangeMasterResponse;
import com.sparc.pccf.wildlife.entity.SectionMasterResponse;
import com.sparc.pccf.wildlife.response.AssignResponse;
import com.sparc.pccf.wildlife.response.BeatMasterResonse;
import com.sparc.pccf.wildlife.response.BeatResponse;
import com.sparc.pccf.wildlife.response.BlockResponse;
import com.sparc.pccf.wildlife.response.DivisionResponse;
import com.sparc.pccf.wildlife.response.GpResponse;
import com.sparc.pccf.wildlife.response.RangeResponse;
import com.sparc.pccf.wildlife.response.SanctuaryResponse;
import com.sparc.pccf.wildlife.response.SectionResponse;
import com.sparc.pccf.wildlife.response.VillageResponse;

public interface MasterService {

	List<DivisionMasterDO> getAllDivision();
	
	List<RangeMasterResponse> getAllRange();

	List<SectionMasterResponse> getAllSection();

	List<BeatMasterResonse> getAllBeat();

	List<RangeResponse> getAllRangeByDivid(String id);

	List<SectionResponse> getAllSectionByRangeid(String id);

	List<BeatResponse> getAllBeatBySecId(String id);

    List<CircleMasterDO> getAllCircle();

	List<DivisionResponse> getAllDivisionByCircleId(String id);

	CircleMasterDO getAllCircleById(String circleId);

	AssignResponse getAssignCircleDivisionRangeByUserId(String userId, String roleId);

	List<DivisionMasterDO> getAllDivisionByType();

	List<CircleMasterDO> getAllCircleByType();

	List<DivisionResponse> getAllDivisionByTypeByCircleId(String id);

	List<SanctuaryResponse> getAllSanctuary();

	List<DistrictMasterDO> getAllDistrict();

	List<BlockResponse> getAllBlockByDistId(Integer distId);

	List<GpResponse> getAllGpByBlockId(Integer blockId);

	List<VillageResponse> getAllVillageByGpId(Integer gpId);

}
