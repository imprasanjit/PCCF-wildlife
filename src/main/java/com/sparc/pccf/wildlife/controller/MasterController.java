package com.sparc.pccf.wildlife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparc.pccf.wildlife.entity.BlockMaster;
import com.sparc.pccf.wildlife.entity.CircleMasterDO;
import com.sparc.pccf.wildlife.entity.DistrictMasterDO;
import com.sparc.pccf.wildlife.entity.DivisionMasterDO;
import com.sparc.pccf.wildlife.entity.MobileApplicationVersion;
import com.sparc.pccf.wildlife.entity.RangeMasterResponse;
import com.sparc.pccf.wildlife.entity.SectionMasterResponse;
import com.sparc.pccf.wildlife.repository.MergeBeatRepository;
import com.sparc.pccf.wildlife.repository.MobileAppversionRepository;
import com.sparc.pccf.wildlife.response.ApplicationVersionResponse;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path="/api/v1/masters")
public class MasterController {
	
	@Autowired
	private MasterService masterService;
	
	@Autowired
	private MergeBeatRepository mergeBeatRepository;
	
	@Autowired
	private MobileAppversionRepository MobileAppversionRepo;
	
	@GetMapping(path="/getAllCircle")
	public  List<CircleMasterDO> getAllCircle() {
		return masterService.getAllCircle();
		
	}
	
	@GetMapping(path="/getAllCircleByType")
	public  List<CircleMasterDO> getAllCircleByType() {
		return masterService.getAllCircleByType();
		
	}
	
	@GetMapping(path="/getAllCircleById")
	public  CircleMasterDO getAllCircleById(@RequestParam String circleId) {
		return masterService.getAllCircleById(circleId);
		
	}
	
	@GetMapping(path="/getAllDivision")
	public  List<DivisionMasterDO> getAllDivision() {
		return masterService.getAllDivision();
		
	}
	@GetMapping(path="/getAllDivisionByType")
	public  List<DivisionMasterDO> getAllDivisionByType() {
		return masterService.getAllDivisionByType();
		
	}
	@GetMapping(path="/getAllDivisionByTypeByCircleId")
	public  List<DivisionResponse> getAllDivisionByTypeByCircleId(@RequestParam String id) {
		return masterService.getAllDivisionByTypeByCircleId(id);
		
	}
	@GetMapping("/getAllDivisionByCircleId")
	public List<DivisionResponse> getAllDivisionByCircleId(@RequestParam String id) {
		return masterService.getAllDivisionByCircleId(id);
	}
	@GetMapping(path="/getAllRange")
	public  List<RangeMasterResponse> getAllRange() {
		return masterService.getAllRange();
		
	}
	@GetMapping("/getAllRangeByDivid")
	public List<RangeResponse> getAllRangeByDivid(@RequestParam String id) {
		return masterService.getAllRangeByDivid(id);
	}
	@GetMapping(path="/getAllSection")
	public  List<SectionMasterResponse> getAllSection() {
		return masterService.getAllSection();
		
	}
	@GetMapping("/getAllSectionByRangeid")
	public List<SectionResponse> getAllSectionByRangeid(@RequestParam String id) {
		return masterService.getAllSectionByRangeid(id);
	}
	
	@GetMapping(path="/getAllBeat")
	public  List<BeatMasterResonse> getAllBeat() {
		return masterService.getAllBeat();
		
	}
	@GetMapping("/getAllBeatBySecId")
	public List<BeatResponse> getAllBeatBySecId(@RequestParam String id) {
		return masterService.getAllBeatBySecId(id);
	}
	
	@GetMapping(path="/getmergeBeat")
	public  String mergeBeatRepository() {
		 List<Object> allBeat = mergeBeatRepository.getAllBeat();
		 return null;
		
	}
	@GetMapping(path="/getAllSanctuary")
	public  List<SanctuaryResponse> getAllSanctuary() {
		return masterService.getAllSanctuary();
		
	}
	@GetMapping("/getAssignCircleDivisionRangeByUserId")
	public AssignResponse getAssignCircleDivisionRangeByUserId(@RequestParam String roleId,@RequestParam String jurdicitionId) {
		return masterService.getAssignCircleDivisionRangeByUserId(roleId,jurdicitionId);
	}
	
	@GetMapping(path="/getAppVersion")
	public ApplicationVersionResponse getAppVersion() 
	{
		 ApplicationVersionResponse version=new ApplicationVersionResponse();
		 List<MobileApplicationVersion> findAll = MobileAppversionRepo.findAll();
		 for (MobileApplicationVersion mobileApplicationVersion : findAll) 
		 {
			 version.setVersionCode(mobileApplicationVersion.getAppVersionCode().toString());
		 }
		return version;
		
	}
	@GetMapping(path="/getAllDistrict")
	public List<DistrictMasterDO> getAllDistrict()
	{
		List<DistrictMasterDO> allDistrict = masterService.getAllDistrict();
		return allDistrict;
	}
	@GetMapping(path="/getAllBlockByDistId")
	public List<BlockResponse> getAllBlockByDistId(@RequestParam Integer distId)
	{
		 List<BlockResponse> allBlockByDistId = masterService.getAllBlockByDistId(distId);
		return allBlockByDistId;
	}
	@GetMapping(path="/getAllGpByBlockId")
	public List<GpResponse> getAllGpByBlockId(@RequestParam Integer blockId)
	{
		 List<GpResponse> allGp= masterService.getAllGpByBlockId(blockId);
		return allGp;	
	}
	@GetMapping(path="/getAllVillageByGpId")
	public List<VillageResponse> getAllVillageByGpId(@RequestParam Integer gpId)
	{
		 List<VillageResponse> allGp= masterService.getAllVillageByGpId(gpId);
		return allGp;
	}

}
