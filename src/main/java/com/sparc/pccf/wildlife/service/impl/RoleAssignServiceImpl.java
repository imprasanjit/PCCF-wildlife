package com.sparc.pccf.wildlife.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparc.pccf.wildlife.entity.GlobalLinkMaster;
import com.sparc.pccf.wildlife.entity.PrimaryLinkMaster;
import com.sparc.pccf.wildlife.entity.RolePermissionDO;
import com.sparc.pccf.wildlife.repository.GlobalLinkRepo;
import com.sparc.pccf.wildlife.repository.PrimaryLinkRepository;
import com.sparc.pccf.wildlife.repository.RolePermissionRepository;
import com.sparc.pccf.wildlife.request.LinkAssignToLinkRequest;
import com.sparc.pccf.wildlife.response.GlobalLinkResponse;
import com.sparc.pccf.wildlife.response.PrimaryLinkResponse;
import com.sparc.pccf.wildlife.service.RoleAssignService;

@Service
public class RoleAssignServiceImpl implements RoleAssignService {

	@Autowired
	private GlobalLinkRepo globalLinkRepo;

	@Autowired
	private PrimaryLinkRepository primaryLinkRepo;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Override
	public List<GlobalLinkResponse> getAllGlinkByRoleId(String roleId) 
	{
		List<RolePermissionDO> findByRoleId = rolePermissionRepository.findByRoleId(roleId);
		Map<Integer, GlobalLinkResponse> map = new HashMap<Integer, GlobalLinkResponse>();
		List<GlobalLinkResponse> list = new ArrayList<GlobalLinkResponse>();
		for (RolePermissionDO rolePermissionDO : findByRoleId) 
		  {
			if (map.containsKey(Integer.parseInt(rolePermissionDO.getGlinkId()))) {
				GlobalLinkResponse globalLinkResponse = map.get(Integer.parseInt(rolePermissionDO.getGlinkId()));
				List<PrimaryLinkResponse> getpLInk = globalLinkResponse.getpLInk();
				if (rolePermissionDO.getPlinkId()!=null) {
					PrimaryLinkMaster primaryLinkMaster = primaryLinkRepo.findById(Integer.parseInt(rolePermissionDO.getPlinkId())).get();
					PrimaryLinkResponse pRep = new PrimaryLinkResponse();
					pRep.setId(primaryLinkMaster.getId());
					pRep.setLinkName(primaryLinkMaster.getLinkName());
					pRep.setpIcon(primaryLinkMaster.getIconName());
					pRep.setName(primaryLinkMaster.getName());
					pRep.setIsActive(rolePermissionDO.getIsActive().toString());
					pRep.setPsequence(primaryLinkMaster.getPlinkSequence());
					getpLInk.add(pRep);
					globalLinkResponse.setpLInk(getpLInk);
					map.put(Integer.parseInt(rolePermissionDO.getGlinkId()), globalLinkResponse);
				} else {
					GlobalLinkMaster globalLinkMaster = globalLinkRepo
							.findById(Integer.parseInt(rolePermissionDO.getGlinkId())).get();
					globalLinkResponse.setName(globalLinkMaster.getName());
					globalLinkResponse.setLinkName(globalLinkMaster.getLinkName());
					globalLinkResponse.setgIcon(globalLinkMaster.getIconName());
					globalLinkResponse.setGlink_id(globalLinkMaster.getGlink_id());
					globalLinkResponse.setGlobalSeq(globalLinkMaster.getGlobalSeq());
					globalLinkResponse.setIsActive(rolePermissionDO.getIsActive().toString());
				}

			} else {
				if (rolePermissionDO.getPlinkId()==null) {
					GlobalLinkResponse globallinkResponse = new GlobalLinkResponse();
					String glinkId = rolePermissionDO.getGlinkId();
					GlobalLinkMaster globalLinkMaster = globalLinkRepo.findById(Integer.parseInt(glinkId)).get();
					if (!globalLinkMaster.getName().isEmpty())
						globallinkResponse.setName(globalLinkMaster.getName());
					if (globalLinkMaster.getLinkName()!=null)
						globallinkResponse.setLinkName(globalLinkMaster.getLinkName());
					if (globalLinkMaster.getIconName()!=null)
						globallinkResponse.setgIcon(globalLinkMaster.getIconName());
					if (globalLinkMaster.getGlink_id() != null)
						globallinkResponse.setGlink_id(globalLinkMaster.getGlink_id());
					if (globalLinkMaster.getGlobalSeq() != null)
						globallinkResponse.setGlobalSeq(globalLinkMaster.getGlobalSeq());
					globallinkResponse.setIsActive(rolePermissionDO.getIsActive().toString());
					map.put(globalLinkMaster.getGlink_id(), globallinkResponse);
				} else {
					GlobalLinkResponse globallinkResponse = new GlobalLinkResponse();
					String glinkId = rolePermissionDO.getGlinkId();
					GlobalLinkMaster globalLinkMaster = globalLinkRepo.findById(Integer.parseInt(glinkId)).get();
					if (!globalLinkMaster.getName().isEmpty())
						globallinkResponse.setName(globalLinkMaster.getName());
					if (globalLinkMaster.getLinkName()!=null)
						globallinkResponse.setLinkName(globalLinkMaster.getLinkName());
					if (globalLinkMaster.getIconName()!=null)
						globallinkResponse.setgIcon(globalLinkMaster.getIconName());
					if (globalLinkMaster.getGlink_id() != null)
						globallinkResponse.setGlink_id(globalLinkMaster.getGlink_id());
					if (globalLinkMaster.getGlobalSeq() != null)
						globallinkResponse.setGlobalSeq(globalLinkMaster.getGlobalSeq());
					List<PrimaryLinkResponse> list1 = new ArrayList<PrimaryLinkResponse>();					
					PrimaryLinkMaster primaryLinkMaster = primaryLinkRepo
							.findById(Integer.parseInt(rolePermissionDO.getPlinkId())).get();
					PrimaryLinkResponse pRep = new PrimaryLinkResponse();
					pRep.setId(primaryLinkMaster.getId());
					pRep.setLinkName(primaryLinkMaster.getLinkName());
					pRep.setpIcon(primaryLinkMaster.getIconName());
					pRep.setName(primaryLinkMaster.getName());
					pRep.setPsequence(primaryLinkMaster.getPlinkSequence());
					pRep.setIsActive(rolePermissionDO.getIsActive().toString());
					list1.add(pRep);
					globallinkResponse.setpLInk(list1);
					map.put(globalLinkMaster.getGlink_id(), globallinkResponse);
				}
			}
		}
		for (GlobalLinkResponse globalresp : map.values()) {
			list.add(globalresp);
		}
		for (GlobalLinkResponse globalLinkResponse : list) {
			List<PrimaryLinkResponse> getpLInk = globalLinkResponse.getpLInk();
			if(getpLInk!=null) {
				List<PrimaryLinkResponse> collect2 = getpLInk.stream().sorted(Comparator.comparing(PrimaryLinkResponse::getPsequence)).collect(Collectors.toList());
				globalLinkResponse.setpLInk(collect2);
			}
			
		}
		
		List<GlobalLinkResponse> collect = list.stream().sorted(Comparator.comparingInt(GlobalLinkResponse::getGlobalSeq)).collect(Collectors.toList());
		
		return collect;
	}

	@Override
	public String setLinkToRole(LinkAssignToLinkRequest linkAssignToLinkRequest) {
		String message = null;
		RolePermissionDO save=null;
		String roleId = linkAssignToLinkRequest.getRoleID();
		String glinkId =linkAssignToLinkRequest.getgLinkId();
		if(linkAssignToLinkRequest.getpLinkId()!="") {
			String plinkId =linkAssignToLinkRequest.getpLinkId();
			save= rolePermissionRepository.findByRoleIdAndGlinkIdAndPlinkId(roleId,glinkId,plinkId);
		}else if(linkAssignToLinkRequest.getpLinkId()=="")  {
			save= rolePermissionRepository.findByRoleIdAndGlinkId(roleId,glinkId);
		}
		
		if (save != null) {
			save.setIsActive(linkAssignToLinkRequest.getIsActive());
			save = rolePermissionRepository.save(save);
			if(save!=null) {
				if(linkAssignToLinkRequest.getIsActive()==true) {
					message = "added";
				}else {
					message = "remove";
				}
			}			
		}else {
			message = "error";
		}	
	return message;
	}
	@Override
	public RolePermissionDO setGLinkToRole(List<LinkAssignToLinkRequest> linkAssignToLinkRequest) {
		 RolePermissionDO save=null;
		for (LinkAssignToLinkRequest linkAssignToLinkRequest2 : linkAssignToLinkRequest) {
			String roleId = linkAssignToLinkRequest2.getRoleID();
			String glinkId =linkAssignToLinkRequest2.getgLinkId();
			if(linkAssignToLinkRequest2.getpLinkId()!="") {
				String plinkId =linkAssignToLinkRequest2.getpLinkId();
				save= rolePermissionRepository.findByRoleIdAndGlinkIdAndPlinkId(roleId,glinkId,plinkId);
			}else if(linkAssignToLinkRequest2.getpLinkId()=="")  {
				save= rolePermissionRepository.findByRoleIdAndGlinkId(roleId,glinkId);
			}
			if (save != null) {
				save.setIsActive(linkAssignToLinkRequest2.getIsActive());
				  save = rolePermissionRepository.save(save);
			}
			
			/*
			 * else { RolePermissionDO save1 = new RolePermissionDO();
			 * save1.setGlinkId(glinkId); save1.setPlinkId(plinkId);
			 * save1.setRoleId(roleId); rolePermissionRepository.save(save1); }
			 */
			 
		}
		return save;

	}
}
