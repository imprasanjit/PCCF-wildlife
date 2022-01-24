
package com.sparc.pccf.wildlife.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparc.pccf.wildlife.entity.GlobalLinkMaster;
import com.sparc.pccf.wildlife.entity.Role;
import com.sparc.pccf.wildlife.entity.RolePermissionDO;
import com.sparc.pccf.wildlife.repository.GlobalLinkRepo;
import com.sparc.pccf.wildlife.repository.RoleAssignRepository;
import com.sparc.pccf.wildlife.request.LinkAssignToLinkRequest;
import com.sparc.pccf.wildlife.response.GlobalLinkResponse;
import com.sparc.pccf.wildlife.response.SuccessResponse;
import com.sparc.pccf.wildlife.service.RoleAssignService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/link")
public class LinkController {
	
	@Autowired
	private GlobalLinkRepo globalLinkRepo;
	
	@Autowired
	private RoleAssignService roleAssignService;
	
	@Autowired
	private RoleAssignRepository rolerepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/getAllGlink")
	public List<GlobalLinkMaster> getGLink() {
		return globalLinkRepo.findAllByOrderByGlobalSeqAsc();
	}
	
	@GetMapping("/getAllRole")
	public List<Role> getRole() {
		return rolerepository.findAll();
	}
	
	@GetMapping("/getAllRoleByRoleId")
	public List<Role> getAllRoleByRoleId(Integer roleId) 
	{
		return rolerepository.findRoleBySeqNo(roleId);
	}
	
	@GetMapping("/getAllLinkByRoleID")
	public Role getAllLinkByRoleID(@RequestParam String roleId) {
		return rolerepository.findById(Integer.parseInt(roleId)).get();
	}
	
	/*get all permission added to role*/
	@GetMapping("/getAllGlinkByRoleId")
	public List<GlobalLinkResponse> getAllGlinkByRoleId(@RequestParam String roleId) {
		return roleAssignService.getAllGlinkByRoleId(roleId);
	}
	
	/*add permission to role*/
	@PostMapping("/setGLinkToRole")
	public SuccessResponse<String> setGLinkToRole(@RequestBody List<LinkAssignToLinkRequest> linkAssignToLinkRequest) {
		RolePermissionDO setGLinkToRole = roleAssignService.setGLinkToRole(linkAssignToLinkRequest);
		if(setGLinkToRole!=null) {
		return new SuccessResponse<String>(messageSource.getMessage("add.permission.successfully", null, Locale.getDefault()));
		}
		else {
			return new  SuccessResponse<String>(messageSource.getMessage("add.permission.unsuccessfull", null, Locale.getDefault()));	
		}
	
	}
	@PutMapping("/setLinkToRole")
	public String setLinkToRole(@RequestBody LinkAssignToLinkRequest linkAssignToLinkRequest) {
		return roleAssignService.setLinkToRole(linkAssignToLinkRequest);
	}
	
}
