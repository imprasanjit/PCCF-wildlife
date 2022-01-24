package com.sparc.pccf.wildlife.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.Key;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparc.pccf.wildlife.constant.ERole;
import com.sparc.pccf.wildlife.custom.repository.SmstriggerRepositoryCustom;
import com.sparc.pccf.wildlife.custom.repository.UserRepositoryCustom;
import com.sparc.pccf.wildlife.dto.CitizenUserInfoDto;
import com.sparc.pccf.wildlife.dto.ProfileDto;
import com.sparc.pccf.wildlife.dto.UserInfoDto;
import com.sparc.pccf.wildlife.entity.BeatMasterDO;
import com.sparc.pccf.wildlife.entity.CircleMasterDO;
import com.sparc.pccf.wildlife.entity.CitizenUser;
import com.sparc.pccf.wildlife.entity.DivisionMasterDO;
import com.sparc.pccf.wildlife.entity.LoginActivity;
import com.sparc.pccf.wildlife.entity.RangeMasterDO;
import com.sparc.pccf.wildlife.entity.Role;
import com.sparc.pccf.wildlife.entity.SectionMasterDO;
import com.sparc.pccf.wildlife.entity.User;
import com.sparc.pccf.wildlife.repository.AuthRepository;
import com.sparc.pccf.wildlife.repository.BeatMasterRepository;
import com.sparc.pccf.wildlife.repository.BlockRepository;
import com.sparc.pccf.wildlife.repository.CircleMasterRepository;
import com.sparc.pccf.wildlife.repository.CitizenUserRepository;
import com.sparc.pccf.wildlife.repository.DistrictRepository;
import com.sparc.pccf.wildlife.repository.DivMasterRepository;
import com.sparc.pccf.wildlife.repository.GpRepository;
import com.sparc.pccf.wildlife.repository.LoginActivityRepository;
import com.sparc.pccf.wildlife.repository.RangeMasterRepository;
import com.sparc.pccf.wildlife.repository.RoleRepository;
import com.sparc.pccf.wildlife.repository.SectionMasterRepository;
import com.sparc.pccf.wildlife.repository.StateHQRepository;
import com.sparc.pccf.wildlife.repository.VillageRepository;
import com.sparc.pccf.wildlife.request.ChangeUsernameRequest;
import com.sparc.pccf.wildlife.request.CitizenFilterRequest;
import com.sparc.pccf.wildlife.request.CitizenSignUpRequest;
import com.sparc.pccf.wildlife.request.FilterRequest;
import com.sparc.pccf.wildlife.request.LoginRequest;
import com.sparc.pccf.wildlife.request.SignupRequest;
import com.sparc.pccf.wildlife.response.AssignResponse;
import com.sparc.pccf.wildlife.response.JuridicitionResponse;
import com.sparc.pccf.wildlife.response.JwtResponse;
import com.sparc.pccf.wildlife.response.MessageResponse;
import com.sparc.pccf.wildlife.response.ReportResponse;
import com.sparc.pccf.wildlife.security.jwt.JwtUtils;
import com.sparc.pccf.wildlife.security.services.UserDetailsImpl;
import com.sparc.pccf.wildlife.service.AuthService;
import com.sparc.pccf.wildlife.service.MasterService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthRepository authRepository;

	
	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	StateHQRepository statehqRepository;

	@Autowired
	CircleMasterRepository circleRepository;
	
	@Autowired
	private DivMasterRepository divRepository;

	@Autowired
	private RangeMasterRepository rangeRepository;
	
	@Autowired
	private SectionMasterRepository sectionRepository;
	
	@Autowired
	private BeatMasterRepository beatRepository;
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
    LoginActivityRepository loginActivityRepo;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private MasterService masterService;
	
	@Autowired
	private CitizenUserRepository citizenUserRepo;
	
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private  BlockRepository blockRepository;
	
	@Autowired
	private GpRepository gpRepository;
	
	@Autowired
	private VillageRepository villRepository;

	@Autowired
	private SmstriggerRepositoryCustom smstriggerRepositoryCustom;

	@Value("${example.app.jwtSecret}")
	private String key;

	@Value("${example.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	@Override
	public Optional<User> findByUsername(String username) {
		return authRepository.findByUsername(username);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return authRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return authRepository.existsByEmail(email);
	}
	@Override
	public Boolean existsByPhone(String phone) {
		return  authRepository.existsByMobile(phone);
	}
	@Override
	public Boolean existsCitizenUserByPhone(String phone) {
		return  citizenUserRepo.existsByMobile(phone);
	}

	@Override
	public User save(User user) {
		User save = authRepository.save(user);
		return save;
	}

	@Override
	public User createUser(SignupRequest signupRequest) 
	{
		User user = new User();
		user.setFirstName(signupRequest.getFirstName());
		user.setLastName(signupRequest.getLastName());
		if (!signupRequest.equals(null))
		user.setMiddleName(signupRequest.getMiddleName());
		user.setUsername(signupRequest.getUsername());
		user.setEmail(signupRequest.getEmail());
		String encode = encoder.encode(signupRequest.getPassword());
		user.setPassword(encode);
		user.setUserCreationDate(new Date());
		user.setCraetedBy(Integer.parseInt(signupRequest.getCreatedBy()));
		user.setUpdatedBy(Integer.parseInt(signupRequest.getCreatedBy()));
		user.setUpdatedOn(new Date());
		user.setMobile(signupRequest.getUserPhoneNumber());
		if (!signupRequest.getJurdiction().isEmpty())
		user.setJuridictionId(Integer.parseInt(signupRequest.getJurdiction()));
		user.setActive(true);
		
		List<Role> roles = new ArrayList<>();
		String strRoles = roleRepository.findById(Integer.parseInt(signupRequest.getRole().toLowerCase())).get().getName().toString();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else
			switch (strRoles) {
			case "ADMIN":
				Role adminRole = roleRepository.findByName(ERole.ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(adminRole);

				break;
			case "CWLW":
				Role clwlRole = roleRepository.findByName(ERole.CWLW)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(clwlRole);

				break;
			case "WL_OFFICIALS":
				Role wl_officialsRole = roleRepository.findByName(ERole.WL_OFFICIALS)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(wl_officialsRole);

				break;
			case "DFO":
				Role dfomodRole = roleRepository.findByName(ERole.DFO)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(dfomodRole);

				break;
			case "ACF":
				Role acfmodRole = roleRepository.findByName(ERole.ACF)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(acfmodRole);

				break;
			case "RANGER":
				Role rangermodRole = roleRepository.findByName(ERole.RANGER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(rangermodRole);

				break;
			case "RCCF":
				Role rccfmodRole = roleRepository.findByName(ERole.RCCF)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(rccfmodRole);

				break;
			case "FORESTER":
				Role forestermodRole = roleRepository.findByName(ERole.FORESTER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(forestermodRole);

				break;
			case "ForestGuard":
				Role ForestGuardmodRole = roleRepository.findByName(ERole.ForestGuard)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(ForestGuardmodRole);

				break;
			case "SQUAD":
				Role SuardmodRole = roleRepository.findByName(ERole.SQUAD)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(SuardmodRole);

				break;
			case "WATCHER":
				Role watchermodRole = roleRepository.findByName(ERole.WATCHER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(watchermodRole);

				break;
			default:
				Role userRole = roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			}

		user.setRoles(roles);
		return authRepository.save(user);
	}
	@Override
	public CitizenUser createCitizenUser(CitizenSignUpRequest citizenSignUpRequest) {
		CitizenUser user=new CitizenUser();
		try {
			
		user.setDistrict(districtRepository.findById(Integer.parseInt(citizenSignUpRequest.getDistId())).get());
		user.setBlock(blockRepository.findById(Integer.parseInt(citizenSignUpRequest.getBlockId())).get());
		user.setGp(gpRepository.findById(Integer.parseInt(citizenSignUpRequest.getGpId())).get());
		user.setVillage(villRepository.findById(Integer.parseInt(citizenSignUpRequest.getVillageId())).get());
		user.setFullname(citizenSignUpRequest.getFullname());
		user.setMobile(citizenSignUpRequest.getMobile());
		user.setCraetedBy(Integer.parseInt(citizenSignUpRequest.getCreatedBy()));
		user.setUpdatedBy(Integer.parseInt(citizenSignUpRequest.getCreatedBy()));
		user.setUpdatedOn(new Date());
		user.setUserCreationDate(new Date());
		user.setIsActive(true);
		user.setDeletedStatus(false);
		CitizenUser save = citizenUserRepo.save(user);
		if(save!=null) {
			user=save;
		   }
		}catch (Exception e) {
			user=null;
		}
		 return user;
	}
	@Override
	public User updateUser(@Valid SignupRequest signupRequest) {
		User user = authRepository.findByUsername(signupRequest.getUsername()).get();
		// int role =
		// roleRepository.findroleIDByLoginID(Integer.parseInt(signupRequest.getLoginId()));
		if (user != null) {
			if (signupRequest.getRoleId() != null && Integer.parseInt(signupRequest.getRoleId()) == 1) {
				if (signupRequest.getEmail() != null)
					user.setEmail(signupRequest.getEmail());
				if (signupRequest.getFirstName() != null)
					user.setFirstName(signupRequest.getFirstName());
//				if (signupRequest.getLastName() != null)
//					user.setLastName(signupRequest.getLastName());
				if (signupRequest.getMiddleName() != null)
					user.setMiddleName(signupRequest.getMiddleName());
				if (signupRequest.getUserPhoneNumber() != null)
					user.setMobile(signupRequest.getUserPhoneNumber());
				if (signupRequest.getUserAddress() != null)
					user.setUserAddress(signupRequest.getUserAddress());
//				if (signupRequest.getImgPath() != null)
//					user.setPath(signupRequest.getImgPath());
				if (signupRequest.getUpdatedBy()!= null)
					user.setUpdatedBy(Integer.parseInt(signupRequest.getUpdatedBy()));
				user.setUpdatedOn(new Date());
				List<Role> roles = new ArrayList<>();
				if (signupRequest.getRole() != null) {
					String strRoles = signupRequest.getRole().toLowerCase();
					if (strRoles == null) {
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
					} else
						switch (strRoles) {
						case "admin":
							Role adminRole = roleRepository.findByName(ERole.ADMIN)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(adminRole);

							break;
						case "clwl":
							Role clwlRole = roleRepository.findByName(ERole.CWLW)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(clwlRole);

							break;
						case "wl_officials":
							Role wl_officialsRole = roleRepository.findByName(ERole.WL_OFFICIALS)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(wl_officialsRole);

							break;
						case "dfo":
							Role dfomodRole = roleRepository.findByName(ERole.DFO)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(dfomodRole);

							break;
						case "acf":
							Role acfmodRole = roleRepository.findByName(ERole.ACF)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(acfmodRole);

							break;
						case "ranger":
							Role rangermodRole = roleRepository.findByName(ERole.RANGER)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(rangermodRole);

							break;
						case "rccf":
							Role rccfmodRole = roleRepository.findByName(ERole.RCCF)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(rccfmodRole);

							break;
						case "forester":
							Role forestermodRole = roleRepository.findByName(ERole.FORESTER)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(forestermodRole);

							break;
						case "forestguard":
							Role ForestGuardmodRole = roleRepository.findByName(ERole.ForestGuard)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(ForestGuardmodRole);

							break;
						case "squard":
							Role SuardmodRole = roleRepository.findByName(ERole.SQUAD)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(SuardmodRole);

							break;
						case "watcher":
							Role watchermodRole = roleRepository.findByName(ERole.WATCHER)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(watchermodRole);

							break;
						default:
							Role userRole = roleRepository.findByName(ERole.ROLE_USER)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(userRole);
						}
					user.setRoles(roles);
				}
			} else {
				if (!signupRequest.equals(null)) {
					if (signupRequest.getEmail() != null)
						user.setEmail(signupRequest.getEmail());
					if (signupRequest.getMiddleName() != null)
						user.setMiddleName(signupRequest.getMiddleName());
					if (signupRequest.getUserAddress() != null)
						user.setUserAddress(signupRequest.getUserAddress());
					if (signupRequest.getImgPath() != null)
						user.setPath(signupRequest.getImgPath());
					// user = authRepository.save(user);
				}
			}
			return authRepository.save(user);
		} else {
			throw new UsernameNotFoundException("Username not found!!! please check with username...");
		}

	}

	@Override
	public List<UserInfoDto> getAllUser() {
		
		List<UserInfoDto> user = new ArrayList<UserInfoDto>();
		try {
			List<Object[]> userList = userRepositoryCustom.getAllUserList();
			for (Object[] objects : userList) {
				int j=0;
				int roleid=0;
				UserInfoDto reportResponse = new UserInfoDto();
				if (objects[j] != null)
					reportResponse.setUserDesignation(objects[j].toString());
				if (objects[++j] != null)
					roleid=Integer.parseInt(objects[j].toString());
					reportResponse.setRoleId(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setUserid(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setMail(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setFirstName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setActive(Boolean.parseBoolean(objects[j].toString()));
				++j;//for juricdiction id
				++j;//for juricdiction name
				if (objects[++j] != null)
					reportResponse.setLastName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setMiddleName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setUserPhoneNumber(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setUserName(objects[j].toString());
				++j;//for StateHQ Name
				++j;//for circle id
				if (objects[++j] != null) 
				{
					reportResponse.setCircleName(objects[j].toString());
					if(roleid==6)
					{
						reportResponse.setJurdictionName(objects[j].toString());
					}
				}
				++j;//for division id
				if (objects[++j] != null)
				{
					reportResponse.setDivisionName(objects[j].toString());
					if(roleid==2) {
						reportResponse.setJurdictionName(objects[j].toString());
					}
				}
				++j;//for range id
				if (objects[++j] != null)
				{
					reportResponse.setRangeName(objects[j].toString());
					if(roleid==5) {
						reportResponse.setJurdictionName(objects[j].toString());
					}
				}
				++j;//for section id
				if (objects[++j] != null)
				{
					reportResponse.setSectionName(objects[j].toString());
					if(roleid==4) {
						reportResponse.setJurdictionName(objects[j].toString());
					}
				}
				++j;//for beat id
				if (objects[++j] != null)
				{
					reportResponse.setBeatName(objects[j].toString());
					if(roleid==7 || roleid==8 || roleid==9) {
						reportResponse.setJurdictionName(objects[j].toString());
					}
				}
				user.add(reportResponse);
			}	
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return user;
	}
	//Get All User List By Login Id
	@Override
	public  List<UserInfoDto> getAllUserByCreatedBy(Integer id) 
	{
		List<UserInfoDto> user = new ArrayList<UserInfoDto>();
		try {
			List<Object[]> findByCraetedBy =  userRepositoryCustom.getAllUserListByCreatedBy(id);			
		for (Object[] objects  : findByCraetedBy) {
			int j=0;
			int roleid=0;
			
			UserInfoDto reportResponse = new UserInfoDto();
			if (objects[j] != null)
				reportResponse.setUserDesignation(objects[j].toString());
			if (objects[++j] != null)
				roleid=Integer.parseInt(objects[j].toString());
				reportResponse.setRoleId(objects[j].toString());
			if (objects[++j] != null)
				reportResponse.setUserid(objects[j].toString());
			if (objects[++j] != null)
				reportResponse.setMail(objects[j].toString());
			if (objects[++j] != null)
				reportResponse.setFirstName(objects[j].toString());
			if (objects[++j] != null)
				reportResponse.setActive(Boolean.parseBoolean(objects[j].toString()));
			++j;//for juricdiction id
			if (objects[++j] != null)
				reportResponse.setLastName(objects[j].toString());
			if (objects[++j] != null)
				reportResponse.setMiddleName(objects[j].toString());
			if (objects[++j] != null)
				reportResponse.setUserPhoneNumber(objects[j].toString());
			if (objects[++j] != null)
				reportResponse.setUserName(objects[j].toString());
			++j;//for circle id
			if (objects[++j] != null) 
			{
				reportResponse.setCircleName(objects[j].toString());
				if(roleid==6)
				{
					reportResponse.setJurdictionName(objects[j].toString());
				}
			}
			++j;//for division id
			if (objects[++j] != null)
			{
				reportResponse.setDivisionName(objects[j].toString());
				if(roleid==2) {
					reportResponse.setJurdictionName(objects[j].toString());
				}
			}
			++j;//for range id
			if (objects[++j] != null)
			{
				reportResponse.setRangeName(objects[j].toString());
				if(roleid==5) {
					reportResponse.setJurdictionName(objects[j].toString());
				}
			}
			++j;//for section id
			if (objects[++j] != null)
			{
				reportResponse.setSectionName(objects[j].toString());
				if(roleid==4) {
					reportResponse.setJurdictionName(objects[j].toString());
				}
			}
			++j;//for beat id
			if (objects[++j] != null)
			{
				reportResponse.setBeatName(objects[j].toString());
				if(roleid==7 || roleid==8 || roleid==9) {
					reportResponse.setJurdictionName(objects[j].toString());
				}
			}
			user.add(reportResponse);
		   }
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return user;
	}
	//User List by Circle,Division,Range,Section,Beat,Role
	@Override
	public List<UserInfoDto> getAllUserByFilter(FilterRequest req) {
		Integer roleId=0;
		Integer [] role=req.getArray();
		List<UserInfoDto> list = new ArrayList<UserInfoDto>();
		if(role.length!=0) {
			for (int i=0;i<role.length;i++) {
				List<Object[]> userReports = userRepositoryCustom.getAllUserListByFilter(req.getCircle_id().toString(),req.getDivision_id().toString(),req.getRange_id().toString(),req.getSection_id().toString(),req.getBeat_id().toString(),role[i].toString());			
				for (Object[] objects : userReports) {
					int j=0;
					int roleid=0;
					
					UserInfoDto reportResponse = new UserInfoDto();
					if (objects[j] != null)
						reportResponse.setUserDesignation(objects[j].toString());
					if (objects[++j] != null)
						roleid=Integer.parseInt(objects[j].toString());
						reportResponse.setRoleId(objects[j].toString());
					if (objects[++j] != null)
						reportResponse.setUserid(objects[j].toString());
					if (objects[++j] != null)
						reportResponse.setMail(objects[j].toString());
					if (objects[++j] != null)
						reportResponse.setFirstName(objects[j].toString());
					if (objects[++j] != null)
						reportResponse.setActive(Boolean.parseBoolean(objects[j].toString()));
					++j;//for juricdiction id
					if (objects[++j] != null)
						reportResponse.setLastName(objects[j].toString());
					if (objects[++j] != null)
						reportResponse.setMiddleName(objects[j].toString());
					if (objects[++j] != null)
						reportResponse.setUserPhoneNumber(objects[j].toString());
					if (objects[++j] != null)
						reportResponse.setUserName(objects[j].toString());
					++j;//for circle id
					if (objects[++j] != null) 
					{
						reportResponse.setCircleName(objects[j].toString());
						if(roleid==6)
						{
							reportResponse.setJurdictionName(objects[j].toString());
						}
					}
					++j;//for division id
					if (objects[++j] != null)
					{
						reportResponse.setDivisionName(objects[j].toString());
						if(roleid==2) {
							reportResponse.setJurdictionName(objects[j].toString());
						}
					}
					++j;//for range id
					if (objects[++j] != null)
					{
						reportResponse.setRangeName(objects[j].toString());
						if(roleid==5) {
							reportResponse.setJurdictionName(objects[j].toString());
						}
					}
					++j;//for section id
					if (objects[++j] != null)
					{
						reportResponse.setSectionName(objects[j].toString());
						if(roleid==4) {
							reportResponse.setJurdictionName(objects[j].toString());
						}
					}
					++j;//for beat id
					if (objects[++j] != null)
					{
						reportResponse.setBeatName(objects[j].toString());
						if(roleid==7 || roleid==8 || roleid==9) {
							reportResponse.setJurdictionName(objects[j].toString());
						}
					}
					list.add(reportResponse);
				}	
			}
		}
		
		return list;
	}
	@Override
	public List<CitizenUserInfoDto> getAllCitizenUserByCreatedBy(Integer id) {
		List<CitizenUserInfoDto> user = new ArrayList<CitizenUserInfoDto>();
		try {
			List<Object[]> userList = userRepositoryCustom.getAllCitizenUserListByCreatedBy(id);	
			for (Object[] objects : userList) {
				int j=0;////for user id
				int roleid=0;
				CitizenUserInfoDto reportResponse = new CitizenUserInfoDto();
				++j;//for created byid
				if (objects[++j] != null)
					reportResponse.setCreatedBy(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setFullName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setActive(Boolean.parseBoolean(objects[j].toString()));
				if (objects[++j] != null)
					reportResponse.setMobile(objects[j].toString());
				++j;//for updateby id
				++j;//for update on
				if (objects[++j] != null)
					reportResponse.setCreatedOn(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setBlockName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setDistrictName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setGpName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setVillName(objects[j].toString());
				++j;//block id
				++j;//dist id
				++j;//gp id
				++j;//vill id
				if (objects[++j] != null)
					reportResponse.setDivisionName(objects[j].toString());
				user.add(reportResponse);
			}	
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return user;
	}
	@Override
	public List<CitizenUserInfoDto> getAllCitizenUser() {
		List<CitizenUserInfoDto> user = new ArrayList<CitizenUserInfoDto>();
		try {
			List<Object[]> userList = userRepositoryCustom.getAllCitizenUserList();
			for (Object[] objects : userList) {
				CitizenUserInfoDto reportResponse = new CitizenUserInfoDto();
				int j=0;////for user id
				++j;//for created byid
				if (objects[++j] != null)
					reportResponse.setCreatedBy(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setFullName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setActive(Boolean.parseBoolean(objects[j].toString()));
				if (objects[++j] != null)
					reportResponse.setMobile(objects[j].toString());
				++j;//for updateby id
				++j;//for update on
				if (objects[++j] != null)
					reportResponse.setCreatedOn(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setBlockName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setDistrictName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setGpName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setVillName(objects[j].toString());
				++j;//block id
				++j;//dist id
				++j;//gp id
				++j;//vill id
				if (objects[++j] != null)
					reportResponse.setDivisionName(objects[j].toString());
				user.add(reportResponse);
				
			}	
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return user;
	}
	@Override
	public List<CitizenUserInfoDto> getAllCitizenUserByFilter(CitizenFilterRequest req) {
		List<CitizenUserInfoDto> user = new ArrayList<CitizenUserInfoDto>();
		try {
			List<Object[]> userList = userRepositoryCustom.getAllCitizenUserListByFilter(req.getDistrict_id().toString(),req.getBlock_id().toString(),req.getGp_id().toString(),req.getVillage_id().toString());
			for (Object[] objects : userList) {
				int j=0;////for user id
				int roleid=0;
				CitizenUserInfoDto reportResponse = new CitizenUserInfoDto();
				++j;//for created byid
				if (objects[++j] != null)
					reportResponse.setCreatedBy(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setFullName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setActive(Boolean.parseBoolean(objects[j].toString()));
				if (objects[++j] != null)
					reportResponse.setMobile(objects[j].toString());
				++j;//for updateby id
				++j;//for update on
				if (objects[++j] != null)
					reportResponse.setCreatedOn(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setBlockName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setDistrictName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setGpName(objects[j].toString());
				if (objects[++j] != null)
					reportResponse.setVillName(objects[j].toString());
				user.add(reportResponse);
			}	
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return user;
	}

	//Profile
	@Override
	public ProfileDto getUserById(String loginId) {
		User user = authRepository.findByusername(loginId);
		Integer id = user.getId();
		int findroleIDByLoginID = roleRepository.findroleIDByLoginID(id);
		Role role = roleRepository.findById(findroleIDByLoginID).get();

		ProfileDto profile = new ProfileDto();
		profile.setFirstName(user.getFirstName());
		profile.setMiddleName(user.getMiddleName());
		profile.setLastName(user.getLastName());
		profile.setUserPhoneNumber(user.getMobile());
		profile.setEmail(user.getEmail());
		profile.setUserAddress(user.getUserAddress());
		profile.setUserName(user.getUsername());
		profile.setImgPath(user.getPath());
		profile.setUserDesignation(role.getName().toString());
		String juridicitionName = getJuridctionNameByRole(role.getName().toString(),
				user.getJuridictionId().toString());
		profile.setJuridicitionName(juridicitionName);
		profile.setActive(user.isActive());
		return profile;
	}

	@Override
	public String deactivateUser(User userById, String role, boolean isActive) {
		String success = null;
		if (role.equals("ADMIN")) 
		{
			if (isActive == true)
			   {
				 isActive = false; 
			   }
			else
				isActive = true;
			userById.setActive(isActive);
			userById.setUpdatedOn(new Date());
			User save = authRepository.save(userById);
			if (save != null) {
				success = "deactivated successfully";
			} else {
				success = "deactivated unsuccessfull";
			}
		} else {
			success = "You are not authorized";
		}
		return success;
	}

	public List<JuridicitionResponse> getJuridicitionByRoleId(String roleId) {
		int l = Integer.parseInt(roleId);
		List<JuridicitionResponse> list = new ArrayList<JuridicitionResponse>();
		if (l == 6) {
			List<CircleMasterDO> findAll = circleRepository.findAll();
			for (CircleMasterDO circleMasterDO : findAll) {
				JuridicitionResponse jResponse = new JuridicitionResponse();
				jResponse.setId(circleMasterDO.getCircleId());
				jResponse.setName(circleMasterDO.getCircleName());
				list.add(jResponse);
			}
		} else if (l == 2) {
			List<DivisionMasterDO> findAll = divRepository.findAll();
			for (DivisionMasterDO divisionMasterDO : findAll) {
				JuridicitionResponse jResponse = new JuridicitionResponse();
				jResponse.setId(divisionMasterDO.getDivisionId());
				jResponse.setName(divisionMasterDO.getDivisionName());
				list.add(jResponse);
			}
		} else if ( l == 5 ) {
			List<RangeMasterDO> findAll = rangeRepository.findAll();
			for (RangeMasterDO rangeMasterDO : findAll) {
				JuridicitionResponse jResponse = new JuridicitionResponse();
				jResponse.setId(rangeMasterDO.getRangeId());
				jResponse.setName(rangeMasterDO.getRangeName());
				list.add(jResponse);
			}
		}
		else if (l == 4) {
			List<SectionMasterDO> findAll = sectionRepository.findAll();
			for (SectionMasterDO sectionMasterDO : findAll) {
				JuridicitionResponse jResponse = new JuridicitionResponse();
				jResponse.setId(sectionMasterDO.getSecId());
				jResponse.setName(sectionMasterDO.getSecName());
				list.add(jResponse);
			}
		}
		else if (l == 7 || l == 8 || l== 9) {
			List<BeatMasterDO> findAll = beatRepository.findAll();
			for (BeatMasterDO beatMasterDO : findAll) {
				JuridicitionResponse jResponse = new JuridicitionResponse();
				jResponse.setId(beatMasterDO.getBeatId());
				jResponse.setName(beatMasterDO.getBeatName());
				list.add(jResponse);
			}
		}
		return list;

	}

//	@Override
//	public User resetPassword(String username, String newPassword, String oldPassword) 
//	{
//		User findByusername = authRepository.findByusername(username);
//		findByusername.setPassword(encoder.encode(newPassword));
//		//User save = authRepository.save(findByusername);
//		return findByusername;
//	}

	public String getJuridctionNameByRole(String role, String id) {
		String name = null;
		try {
		if (role.equals("ADMIN") || role.equals("CWLW")) {
			 String stname = statehqRepository.findById(Integer.parseInt(id)).get().getStateHQName().toString();
			 if(stname!=null) {
				 name =stname;
			 }else {
				 name="";
			 }
			
		}
		if (role.equals("RCCF") ) {
			String circlename = circleRepository.findById(Integer.parseInt(id)).get().getCircleName().toString();
			 if(circlename!=null) {
				 name =circlename;
			 }else {
				 name="";
			 }
		}
		if (role.equals("DFO")) {
			String divisionname = divRepository.findById(Integer.parseInt(id)).get().getDivisionName().toString();
			 if(divisionname!=null) {
				 name =divisionname;
			 }else {
				 name="";
			 }
		}
		if (role.equals("RANGER")) {
			String rngname = rangeRepository.findById(Integer.parseInt(id)).get().getRangeName().toString();
			 if(rngname!=null) {
				 name =rngname;
			 }else {
				 name="";
			 }
		}
		if (role.equals("FORESTER")) {
			String secname = sectionRepository.findById(Integer.parseInt(id)).get().getSecName().toString();
			 if(secname!=null) {
				 name =secname;
			 }else {
				 name="";
			 }
		}
		if (role.equals("ForestGuard") || role.equals("SQUAD") || role.equals("WATCHER")) {
			String beatname = beatRepository.findById(Integer.parseInt(id)).get().getBeatName().toString();
			 if(beatname!=null) {
				 name =beatname;
			 }else {
				 name="";
			 }
		}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return name;
	}
	public Integer getJuridctionIdByRole(String role, String id) {
		Integer jid = 0;
		if (role.equals("ADMIN") || role.equals("CWLW")) {
			jid = statehqRepository.findById(Integer.parseInt(id)).get().getStateHQId();
		}
		if (role.equals("RCCF")) {
			jid = circleRepository.findById(Integer.parseInt(id)).get().getCircleId();
		}
		if (role.equals("DFO")) {
			jid = divRepository.findById(Integer.parseInt(id)).get().getDivisionId();
		}
		if (role.equals("RANGER")) {
			jid = rangeRepository.findById(Integer.parseInt(id)).get().getRangeId();
		}
		if (role.equals("FORESTER")) {
			jid = sectionRepository.findById(Integer.parseInt(id)).get().getSecId();
		}
		if (role.equals("ForestGuard") || role.equals("SQUAD") || role.equals("WATCHER")) {
			jid = beatRepository.findById(Integer.parseInt(id)).get().getBeatId();
		}
		return jid;
	}

	@Override
	public Integer getRoleIdByUserName(String username) {

		User user = authRepository.findByusername(username);
		Integer id = user.getId();
		return roleRepository.findroleIDByLoginID(id);
	}
	public Integer getSeqByRoleId(Integer roleId) {

		return roleRepository.findSeqNoById(roleId);
	}

	@Override
	public String changePassWord(String username, String oldpassword, String newPassword) {
		String message = "";
		User user = authRepository.findByUsername(username).get();
		if(user!=null) {
			String pass = user.getPassword();
			if (encoder.matches(oldpassword, pass)) 
			{
				user.setPassword(encoder.encode(newPassword));
				User save = authRepository.save(user);
				if (save != null) 
				{
					message = "Success";
				}else {
					message = "failed";
				}
			} 
			else {
				message = "passwordmismatch";
			}
		}else {
			message = "invalidUser";
		}

		return message;
	}
	@Override
	public String resetPasswordWithMobile(String mobile, String newPasswod) {
		String message = "";
		User user=authRepository.findByMobile(mobile).orElse(null);
		if (user!=null)
		{
			String encode = encoder.encode(newPasswod);
			user.setPassword(encode);
			user.setUpdatedBy(user.getId());
			user.setUpdatedOn(new Date());
			User save = authRepository.save(user);
			if (save != null) 
			{
			      message = "Success";
			}else {
				 message = "failed";
			}
		}else {
				 message ="invalidMobile";
			}

		return message;
	}
	
	@Override
	public String changeUsername(ChangeUsernameRequest URequest) 
	{
		String message = "";
		
		//Boolean user = authRepository.existsByUsername(URequest.getUserName());
		//Optional<User> Username = authRepository.findByUsername(URequest.getUserName());
		
		 User username = authRepository.findByusername(URequest.getUserName());
		
		if(!username.equals(null)) 
		{
			    username.setUsername(URequest.getNewUsername());
				User save = authRepository.save(username);
				if (save != null) 
				{
					message = "Success";
				}else {
					message = "failed";
				}
			} 
			else {
				message = "user not matched !!";
			}

		return message;
}
	
	@Override
	public String resetUsernameWithMobile(String username, String newUsername) 
	{
		String message = "";
		User user=authRepository.findByUsername(newUsername).orElse(null);
		if (user!=null)
		{
			user.setUsername(newUsername);
			user.setUpdatedBy(user.getId());
			user.setUpdatedOn(new Date());
			User save = authRepository.save(user);
			if (save != null) 
			{
			      message = "Success";
			}else {
				 message = "failed";
			}
		}else {
				 message ="invalidUsername";
			}

		return message;
	}

	
	@Override
	public ResponseEntity<?> signIn(@Valid LoginRequest loginRequest,HttpServletRequest request) 
	{
		try {
			    User user = authRepository.findByUsername(loginRequest.getLogin_id()).orElse(null);
			    if(user!=null) 
			    {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getLogin_id(),loginRequest.getPassword() );
						try {
						Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
						
						if(authentication!=null) {
						SecurityContextHolder.getContext().setAuthentication(authentication);
						String jwt = jwtUtils.generateJwtToken(authentication);
				
						UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
						List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
						Integer roleId = getRoleIdByUserName(userDetails.getUsername());
						//Integer seqNo=getSeqByRoleId(roleId);
						String juridctionNameByRole = getJuridctionNameByRole(roles.get(0), userDetails.getJuridictionName());
						Integer juridctionIdByRole = getJuridctionIdByRole(roles.get(0), userDetails.getJuridictionName());
				 
						AssignResponse assignResponse = masterService.getAssignCircleDivisionRangeByUserId(roleId.toString(),juridctionIdByRole.toString());
						Integer circleId = assignResponse.getCircleId();
						Integer divisionId = assignResponse.getDivisonId();
						Integer rangeId = assignResponse.getRangeId();
						Integer sectionId = assignResponse.getSectionId();
						Integer beatId = assignResponse.getBeatId();
						Integer seqNo=4;
					   	
						LoginActivity log=new LoginActivity();
						log.setLoginDateTime(new Date());
						log.setIp(request.getRemoteAddr());
						log.setUser(authRepository.findByusername(loginRequest.getLogin_id()));
						loginActivityRepo.save(log);  
						
						return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), 
																	  userDetails.getUsername(),
																	  userDetails.getEmail(), 
																	  roles, 
																	  roleId, 
																	  circleId, 
																	  divisionId, 
																	  rangeId, 
																	  sectionId, 
																	  beatId,
																	  juridctionNameByRole,
																	  juridctionIdByRole,
																	  seqNo));
						}else {
							return ResponseEntity.badRequest().body(new MessageResponse("invalidPassword"));
						}
					}catch (Exception e) {
						e.printStackTrace();
						return ResponseEntity.badRequest().body(new MessageResponse("BadCredentials"));
					}
			    }else {
			    	return ResponseEntity.badRequest().body(new MessageResponse("invalidUser"));
			    }
		} catch (Exception e) {
		}
		return null;
	}
     public Integer getSeqNoByRoleId(int roleId) {
	   int seqno=4;
	   seqno=roleRepository.findSeqNoById(roleId);
	   return seqno;
   }
	@Override
	public String generateotp(String mobileNumber) {
		if (mobileNumber != null && mobileNumber != "") {
			User findUserNameAndPasswordByMobile = authRepository.findUserNameAndPasswordByMobile(mobileNumber);
			if (findUserNameAndPasswordByMobile != null) {
				return generateOneTimePassword(findUserNameAndPasswordByMobile);
			} else {
				return "notValid";
			}
		} else {
			return "notValid";
		}
	}

	public String generateOneTimePassword(User user) {
		//String OTP = Integer.toString(new Random().nextInt(100000));
		String OTP= new DecimalFormat("000000").format(new Random().nextInt(999999));
		System.err.println(OTP);
		String encodedOTP = encoder.encode(OTP);
		user.setOneTimePassword(encodedOTP);
		user.setOtpRequestedTime(new Date());
		authRepository.save(user);
		sendOTPPhone(user, OTP);
		return "success";
	}

	public void sendOTPPhone(User user, String otp) {
		try {
			String Username = "suru.mistry007@gmail.com";
			String apiKey = "&apikey=" + URLEncoder.encode("UOMDAJ+z8HI-kFedKgMqcKVC8xpOolCny9DxlRtbiT", "UTF-8");
			String message = "&message=" + URLEncoder.encode("Your Login Verification Code is " + otp + "");
			String sender = "&sender=" + URLEncoder.encode("SPARCS", "UTF-8");
			String numbers = "&numbers=" + URLEncoder.encode(user.getMobile(), "UTF-8");
			// Send data
			String data = "http://sms.webadd.in/api2/send/?username=" + Username + apiKey + numbers + message + sender;
			URL url = new URL(data);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			String sResult = "";
			while ((line = rd.readLine()) != null) {
				// Process line...
				sResult = sResult + line + " ";
			}
			rd.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	@Override
	public ResponseEntity<?> otpVerified(String mobileNumber, String otp) {
		User findUserNameAndPasswordByMobile = authRepository.findUserNameAndPasswordByMobile(mobileNumber);
		if (findUserNameAndPasswordByMobile != null) {
			if (encoder.matches(otp, findUserNameAndPasswordByMobile.getOneTimePassword())) {
				       clearOTP(findUserNameAndPasswordByMobile);
				return ResponseEntity.ok(new MessageResponse("verified"));
			} else {
				return ResponseEntity.ok(new MessageResponse("invalidOtp"));
			}
		} else {
			return ResponseEntity.ok(new MessageResponse("invalidMobile"));
		}
	}
	@Override
	public ResponseEntity<?> otpBasedLogin(String mobileNumber, String otp) {
		User findUserNameAndPasswordByMobile = authRepository.findUserNameAndPasswordByMobile(mobileNumber);
		if (findUserNameAndPasswordByMobile != null) {
			if (encoder.matches(otp, findUserNameAndPasswordByMobile.getOneTimePassword())) {
				String createJWT = createJWT(findUserNameAndPasswordByMobile.getUsername());
				Integer roleId = getRoleIdByUserName(findUserNameAndPasswordByMobile.getUsername());
				List<Role> roles = findUserNameAndPasswordByMobile.getRoles();
				List<String> rolez = new ArrayList<String>();
				for (Role role : roles) {
					rolez.add(role.getName().toString());
				}
				String juridctionNameByRole = getJuridctionNameByRole(rolez.get(0),
						findUserNameAndPasswordByMobile.getJuridictionId().toString());
				Integer juridctionIdByRole = getJuridctionIdByRole(rolez.get(0),
						findUserNameAndPasswordByMobile.getJuridictionId().toString());

				AssignResponse assignResponse = masterService.getAssignCircleDivisionRangeByUserId(roleId.toString(),juridctionIdByRole.toString());
				Integer circleId = assignResponse.getCircleId();
				Integer divisionId = assignResponse.getDivisonId();
				Integer rangeId = assignResponse.getRangeId();
				Integer sectionId = assignResponse.getSectionId();
				Integer beatId = assignResponse.getBeatId();
				Integer seqNo=getSeqNoByRoleId(roleId);
				clearOTP(findUserNameAndPasswordByMobile);
				
				return ResponseEntity.ok(new JwtResponse(createJWT, findUserNameAndPasswordByMobile.getId(),
						findUserNameAndPasswordByMobile.getUsername(), findUserNameAndPasswordByMobile.getEmail(),
						rolez, roleId, circleId, divisionId, rangeId, sectionId, beatId, juridctionNameByRole,
						juridctionIdByRole,seqNo));
			} else {
				return ResponseEntity.ok(new MessageResponse("invalidOtp"));
			}
		} else {
			return ResponseEntity.ok(new MessageResponse("invalidMobile"));
		}

	}

	private String createJWT(String userName) {

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims
		String compact = Jwts.builder().setSubject((userName)).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, key).compact();

		// if it has been specified, let's add the expiration
		/*
		 * if (ttlMillis >= 0) { long expMillis = nowMillis + ttlMillis; Date exp = new
		 * Date(expMillis); builder.setExpiration(exp); }
		 */
		// Builds the JWT and serializes it to a compact, URL-safe string
		return compact;
	}

	public void clearOTP(User user) {
		user.setOneTimePassword(null);
		user.setOtpRequestedTime(null);
		authRepository.save(user);
	}

	@Override
	public Optional<User> findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return authRepository.findByEmail(email);
	}

	@Override
	public Optional<User> findUserByResetToken(String resetToken) {
		// TODO Auto-generated method stub
		return authRepository.findByResetToken(resetToken);
	}

	




	

	

	


	





	

	


}
