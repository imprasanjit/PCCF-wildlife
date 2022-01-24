package com.sparc.pccf.wildlife.service;

import java.util.List;

import com.sparc.pccf.wildlife.entity.RolePermissionDO;
import com.sparc.pccf.wildlife.request.LinkAssignToLinkRequest;
import com.sparc.pccf.wildlife.response.GlobalLinkResponse;

public interface RoleAssignService {

	RolePermissionDO setGLinkToRole(List<LinkAssignToLinkRequest> linkAssignToLinkRequest);

	List<GlobalLinkResponse> getAllGlinkByRoleId(String roleId);

	String setLinkToRole(LinkAssignToLinkRequest linkAssignToLinkRequest);

	

}
