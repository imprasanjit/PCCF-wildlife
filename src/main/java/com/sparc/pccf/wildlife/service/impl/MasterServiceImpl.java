package com.sparc.pccf.wildlife.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sparc.pccf.wildlife.entity.BeatMasterDO;
import com.sparc.pccf.wildlife.entity.BlockMaster;
import com.sparc.pccf.wildlife.entity.CircleMasterDO;
import com.sparc.pccf.wildlife.entity.DistrictMasterDO;
import com.sparc.pccf.wildlife.entity.DivisionMasterDO;
import com.sparc.pccf.wildlife.entity.GpMaster;
import com.sparc.pccf.wildlife.entity.RangeMasterDO;
import com.sparc.pccf.wildlife.entity.RangeMasterResponse;
import com.sparc.pccf.wildlife.entity.SanctuaryInfoMasterDO;
import com.sparc.pccf.wildlife.entity.SectionMasterDO;
import com.sparc.pccf.wildlife.entity.SectionMasterResponse;
import com.sparc.pccf.wildlife.entity.VillageMaster;
import com.sparc.pccf.wildlife.repository.BeatMasterRepository;
import com.sparc.pccf.wildlife.repository.BlockRepository;
import com.sparc.pccf.wildlife.repository.CircleMasterRepository;
import com.sparc.pccf.wildlife.repository.DistrictRepository;
import com.sparc.pccf.wildlife.repository.DivMasterRepository;
import com.sparc.pccf.wildlife.repository.GpRepository;
import com.sparc.pccf.wildlife.repository.RangeMasterRepository;
import com.sparc.pccf.wildlife.repository.SanctuaryRepository;
import com.sparc.pccf.wildlife.repository.SectionMasterRepository;
import com.sparc.pccf.wildlife.repository.VillageRepository;
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
import com.sparc.pccf.wildlife.service.MasterService;

@Service
public class MasterServiceImpl implements MasterService {

	@Autowired
	private CircleMasterRepository circleMasterRepository;
	
	@Autowired
	private DivMasterRepository divMasterRepository;

	@Autowired
	private RangeMasterRepository rangeMasterRepository;

	@Autowired
	private SectionMasterRepository sectionasterRepository;

	@Autowired
	private BeatMasterRepository beatMasterRepository;
	
	@Autowired
	private SanctuaryRepository sanctuaryRepository;
	
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private  BlockRepository blockRepository;
	
	@Autowired
	private GpRepository gpRepository;
	
	@Autowired
	private VillageRepository villRepository;

	@Override
	public List<DivisionMasterDO> getAllDivision() {
		List<DivisionMasterDO>  divisionMasterDOList =divMasterRepository.findAll(Sort.by(Sort.Direction.ASC, "divisionName"));
	    return divisionMasterDOList;
	}

	@Override
	public List<RangeMasterResponse> getAllRange() {
		//List<RangeMasterDO>  rangeMasterDOList =rangeMasterRepository.findAll(Sort.by(Sort.Direction.ASC, "rangeName"));
		List<Object[]> allRange = rangeMasterRepository.getAllRange();
		List<RangeMasterResponse> list = new ArrayList<RangeMasterResponse>();
		for (Object[] objects : allRange) {
			int i=0;
			RangeMasterResponse rangeReportResponse = new RangeMasterResponse();
			if (objects[i] != null)
				rangeReportResponse.setRangeName(objects[i].toString());	
			if (objects[++i] != null)
				rangeReportResponse.setRangeId(objects[i].toString());
			if (objects[++i] != null)
				rangeReportResponse.setDivisionId(objects[i].toString());								
			if (objects[++i] != null)
				rangeReportResponse.setDivisionName(objects[i].toString());				
			list.add(rangeReportResponse);
	}
		return list;
	}

	@Override
	public List<SectionMasterResponse> getAllSection() {
		//List<SectionMasterDO> sectionMasterDOList =sectionasterRepository.findAll(Sort.by(Sort.Direction.ASC, "secName"));
		List<Object[]> allSection = sectionasterRepository.getAllSection();
		List<SectionMasterResponse> list = new ArrayList<SectionMasterResponse>();
		for (Object[] objects : allSection) {
			int i=0;
			SectionMasterResponse sectionReportResponse = new SectionMasterResponse();
			if (objects[i] != null)
				sectionReportResponse.setSectionName(objects[i].toString());
			if (objects[++i] != null)
				sectionReportResponse.setSectionId(objects[i].toString());
			if (objects[++i] != null)
				sectionReportResponse.setRangeId(objects[i].toString());
			if (objects[++i] != null)
				sectionReportResponse.setDivisionId(objects[i].toString());			
			if (objects[++i] != null)
				sectionReportResponse.setRangeName(objects[i].toString());				
			if (objects[++i] != null)
				sectionReportResponse.setDivisionName(objects[i].toString());				
			list.add(sectionReportResponse);
	}
		return list;
	}

	@Override
	public List<BeatMasterResonse> getAllBeat() {
		//List<BeatMasterDO> beatMasterDOList =beatMasterRepository.findAll(Sort.by(Sort.Direction.ASC, "beatName"));
		List<Object[]> allBeat = beatMasterRepository.getAllBeat();
		List<BeatMasterResonse> list = new ArrayList<BeatMasterResonse>();
		for (Object[] objects : allBeat) {
				int i=0;
				BeatMasterResonse beatReportResponse = new BeatMasterResonse();
				if (objects[i] != null)
					beatReportResponse.setBeatName(objects[i].toString());
				if (objects[++i] != null)
					beatReportResponse.setBeatId((Number) objects[i]);
				if (objects[++i] != null)
					beatReportResponse.setSectionId((Number)objects[i]);
				if (objects[++i] != null)
					beatReportResponse.setRangeId((Number)objects[i]);
				if (objects[++i] != null)
					beatReportResponse.setDivisionId((Number)objects[i]);
				if (objects[++i] != null)
					beatReportResponse.setSectionName(objects[i].toString());
				if (objects[++i] != null)
					beatReportResponse.setRangeName(objects[i].toString());				
				if (objects[++i] != null)
					beatReportResponse.setDivisionName(objects[i].toString());
				if (objects[++i] != null)
					beatReportResponse.setCircleId((Number)objects[i]);				
				if (objects[++i] != null)
					beatReportResponse.setCircleName(objects[i].toString());	
				list.add(beatReportResponse);
		}
		return list;
	}

	@Override
	public List<RangeResponse> getAllRangeByDivid(String id) {
		List<RangeResponse> rangeResponse = new ArrayList<RangeResponse>();
		List<RangeMasterDO> findByDivision = rangeMasterRepository.findByDivision(divMasterRepository.findById(Integer.parseInt(id)).get());
		for (RangeMasterDO rangeMasterDO : findByDivision) {
			RangeResponse temp = new RangeResponse();
			temp.setRangeId(rangeMasterDO.getRangeId().toString());
			temp.setRangeName(rangeMasterDO.getRangeName());
			rangeResponse.add(temp);
		}
		return rangeResponse;
	}

	@Override
	public List<SectionResponse> getAllSectionByRangeid(String id) {
		List<SectionResponse> sectionResponse = new ArrayList<SectionResponse>();
		List<SectionMasterDO> findByRange = sectionasterRepository.findByRange(rangeMasterRepository.findById(Integer.parseInt(id)).get());
		for (SectionMasterDO sectionMasterDO : findByRange) {
			SectionResponse temp = new SectionResponse();
			temp.setSecId(sectionMasterDO.getSecId().toString());
			temp.setSecName(sectionMasterDO.getSecName());
			sectionResponse.add(temp);
		}
		return sectionResponse;
	}

	@Override
	public List<BeatResponse> getAllBeatBySecId(String id) {
		List<BeatResponse> beatResponse = new ArrayList<BeatResponse>();
		List<BeatMasterDO> findBySection = beatMasterRepository.findBySection(sectionasterRepository.findById(Integer.parseInt(id)).get());
		for (BeatMasterDO beatMasterDO : findBySection) {
			BeatResponse temp = new BeatResponse();
			temp.setBeatId(beatMasterDO.getBeatId().toString());
			temp.setBeatName(beatMasterDO.getBeatName());
			beatResponse.add(temp);
		}
		return beatResponse;
	}

	@Override
	public List<CircleMasterDO> getAllCircle() {

		return circleMasterRepository.findAll();
	}

	@Override
	public CircleMasterDO getAllCircleById(String circleId) {
		// TODO Auto-generated method stub
		return circleMasterRepository.findById(Integer.parseInt(circleId)).get();
	}

	@Override
	public List<DivisionResponse> getAllDivisionByCircleId(String id) {
		List<DivisionResponse> divisionResponse = new ArrayList<DivisionResponse>();
		List<DivisionMasterDO> findByCircle = divMasterRepository.findByCircle(circleMasterRepository.findById(Integer.parseInt(id)).get());
		for (DivisionMasterDO divisionMasterDO : findByCircle) {
			DivisionResponse temp = new DivisionResponse();
			temp.setDivisionId(divisionMasterDO.getDivisionId().toString());
			temp.setDivisionName(divisionMasterDO.getDivisionName());
			divisionResponse.add(temp);
		}
		return divisionResponse;
	}

	@Override
	public AssignResponse getAssignCircleDivisionRangeByUserId( String roleId,String jurdicitionId) {
		AssignResponse assignResponse=new AssignResponse();		
		 if (roleId.equals("1") || roleId.equals("3")) {
				assignResponse.setCircleId(0);
				assignResponse.setDivisonId(0);
				assignResponse.setRangeId(0);
				assignResponse.setSectionId(0);
				assignResponse.setBeatId(0);
			}
		 else if (roleId.equals("6")) {
				assignResponse.setCircleId(Integer.parseInt(jurdicitionId));
				assignResponse.setDivisonId(0);
				assignResponse.setRangeId(0);
				assignResponse.setSectionId(0);
				assignResponse.setBeatId(0);
			}
		else if(roleId.equals("2")) {
			assignResponse.setCircleId(divMasterRepository.findById(Integer.parseInt(jurdicitionId)).get().getCircle().getCircleId());
			assignResponse.setDivisonId(Integer.parseInt(jurdicitionId));
			assignResponse.setRangeId(0);
			assignResponse.setSectionId(0);
			assignResponse.setBeatId(0);
		}else if(roleId.equals("5")) {
			Integer divisionId = rangeMasterRepository.findById(Integer.parseInt(jurdicitionId)).get().getDivision().getDivisionId();
			assignResponse.setCircleId(divMasterRepository.findById(divisionId).get().getCircle().getCircleId());
			assignResponse.setDivisonId(divisionId);
			assignResponse.setRangeId(Integer.parseInt(jurdicitionId));
			assignResponse.setSectionId(0);
			assignResponse.setBeatId(0);
		}else if(roleId.equals("4")) {
			Integer rangeId = sectionasterRepository.findById(Integer.parseInt(jurdicitionId)).get().getRange().getRangeId();
			Integer divisionId=rangeMasterRepository.findById(rangeId).get().getDivision().getDivisionId();
			assignResponse.setCircleId(divMasterRepository.findById(divisionId).get().getCircle().getCircleId());
			assignResponse.setDivisonId(divisionId);
			assignResponse.setRangeId(rangeId);
			assignResponse.setSectionId(Integer.parseInt(jurdicitionId));
			assignResponse.setBeatId(0);
		}
		else if(roleId.equals("7")||roleId.equals("8")||roleId.equals("9")) {
			Integer sectionId=beatMasterRepository.findById(Integer.parseInt(jurdicitionId)).get().getSection().getSecId();
			Integer rangeId = sectionasterRepository.findById(sectionId).get().getRange().getRangeId();
			Integer divisionId=rangeMasterRepository.findById(rangeId).get().getDivision().getDivisionId();
			Integer circleId=divMasterRepository.findById(divisionId).get().getCircle().getCircleId();
			assignResponse.setCircleId(circleId);
			assignResponse.setDivisonId(divisionId);
			assignResponse.setRangeId(rangeId);
			assignResponse.setSectionId(sectionId);
			assignResponse.setBeatId(Integer.parseInt(jurdicitionId));
		}
		// TODO Auto-generated method stub
		return assignResponse;
	}

	@Override
	public List<DivisionMasterDO> getAllDivisionByType() {
		// TODO Auto-generated method stub
		return divMasterRepository.getAllDivisionByType();
	}

	@Override
	public List<CircleMasterDO> getAllCircleByType() {
		// TODO Auto-generated method stub
		return circleMasterRepository.getAllCircleByType();
	}

	@Override
	public List<DivisionResponse> getAllDivisionByTypeByCircleId(String id) {
		List<DivisionResponse> divisionResponse = new ArrayList<DivisionResponse>();
		List<DivisionMasterDO> findByCircle = divMasterRepository.getAllDivisionByTypeByCircleId(Integer.parseInt(id));
		for (DivisionMasterDO divisionMasterDO : findByCircle) {
			DivisionResponse temp = new DivisionResponse();
			temp.setDivisionId(divisionMasterDO.getDivisionId().toString());
			temp.setDivisionName(divisionMasterDO.getDivisionName());
			divisionResponse.add(temp);
		}
		return divisionResponse;
	}

	@Override
	public List<SanctuaryResponse> getAllSanctuary() {
		List<SanctuaryResponse> sanctuaryResponse = new ArrayList<SanctuaryResponse>();
		List<SanctuaryInfoMasterDO>  sanctuaryList =sanctuaryRepository.findAll(Sort.by(Sort.Direction.ASC, "sanctuaryName"));
		for (SanctuaryInfoMasterDO sanctuaryMasterDO : sanctuaryList) {
			SanctuaryResponse temp = new SanctuaryResponse();
			temp.setSanctuaryId(sanctuaryMasterDO.getSancId());
			temp.setSanctuaryName(sanctuaryMasterDO.getSanctuaryName());
			sanctuaryResponse.add(temp);
		}
		return sanctuaryResponse;
	}

	@Override
	public List<DistrictMasterDO> getAllDistrict() 
	{
		List<DistrictMasterDO> findAll = districtRepository.findAll(Sort.by(Sort.Direction.ASC, "districtName"));
		return findAll;
	}

	@Override
	public List<BlockResponse> getAllBlockByDistId(Integer distId) {
		List<BlockMaster> findAll = blockRepository.findBydistrict(districtRepository.findById(distId).get());
		List<BlockResponse> list= new ArrayList<BlockResponse>();
		for (BlockMaster blockMaster : findAll) {
			BlockResponse blockresp=new BlockResponse();
			blockresp.setBlockId(blockMaster.getBlockId().toString());
			blockresp.setBlockName(blockMaster.getBlockName());
			list.add(blockresp);
		}
		return list;
	}

	@Override
	public List<GpResponse> getAllGpByBlockId(Integer blockId) {
	
		List<GpMaster>gp=gpRepository.findByBlockOrderByGpNameAsc(blockRepository.findById(blockId).get());
		List<GpResponse> list= new ArrayList<GpResponse>();
		for (GpMaster gpMaster : gp) {
			GpResponse gpResponse=new GpResponse();
			gpResponse.setGpId(gpMaster.getGpId().toString());
			gpResponse.setGpName(gpMaster.getGpName());
			list.add(gpResponse);
		}
		return list;
	}

	@Override
	public List<VillageResponse> getAllVillageByGpId(Integer gpId) {
		List<VillageMaster> vill=villRepository.findByGpOrderByVillageNameAsc(gpRepository.findById(gpId).get());
		List<VillageResponse> list= new ArrayList<VillageResponse>();
		for (VillageMaster villMaster : vill) {
			VillageResponse villResponse=new VillageResponse();
			villResponse.setVillageId(villMaster.getVillageId().toString());
			villResponse.setVillageName(villMaster.getVillageName());
			list.add(villResponse);
		}
		return list;
	}

}
