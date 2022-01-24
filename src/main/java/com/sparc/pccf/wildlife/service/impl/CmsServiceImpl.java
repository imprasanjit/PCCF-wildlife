package com.sparc.pccf.wildlife.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sparc.pccf.wildlife.dto.BlogDto;
import com.sparc.pccf.wildlife.entity.BlogDO;
import com.sparc.pccf.wildlife.entity.FeedbackDO;
import com.sparc.pccf.wildlife.entity.Gallery;
import com.sparc.pccf.wildlife.entity.GalleryTypeMaster;
import com.sparc.pccf.wildlife.entity.NewUpdate;
import com.sparc.pccf.wildlife.entity.NewsDO;
import com.sparc.pccf.wildlife.entity.NoticeDO;
import com.sparc.pccf.wildlife.entity.PublicationDO;
import com.sparc.pccf.wildlife.entity.TenderDO;
import com.sparc.pccf.wildlife.entity.User;
import com.sparc.pccf.wildlife.repository.AuthRepository;
import com.sparc.pccf.wildlife.repository.CmsBlogRepository;
import com.sparc.pccf.wildlife.repository.CmsNewUpdateRepository;
import com.sparc.pccf.wildlife.repository.CmsNewsRepository;
import com.sparc.pccf.wildlife.repository.CmsPublicationRepository;
import com.sparc.pccf.wildlife.repository.FeedbackRepository;
import com.sparc.pccf.wildlife.repository.GalleryRepository;
import com.sparc.pccf.wildlife.repository.GalleryTypeMasterRepository;
import com.sparc.pccf.wildlife.repository.NoticeRepository;
import com.sparc.pccf.wildlife.repository.TenderRepository;
import com.sparc.pccf.wildlife.request.NewUpdateRequest;
import com.sparc.pccf.wildlife.request.NewsRequest;
import com.sparc.pccf.wildlife.response.FileUploadResponse;
import com.sparc.pccf.wildlife.service.CmsService;
import com.sparc.pccf.wildlife.service.FileStorageService;

@Service
public class CmsServiceImpl implements CmsService {
	
	@Autowired
	CmsNewsRepository cmsNewsRepo;
	
	@Autowired
	CmsNewUpdateRepository CmsNewUpdateRepo;
	
	@Autowired
	CmsBlogRepository CmsBlogRepo;
	
	@Autowired
	ServletContext context;
	
	
	@Value("${file.upload.directory}")
	private String fileUploadDirectory;
	
	@Autowired
	CmsPublicationRepository cmsPublicationRepo;
	
	@Autowired
	GalleryRepository galleryRepository;
	
	@Autowired
    GalleryTypeMasterRepository galleryTypeMasterRepository;
	
	@Autowired
	TenderRepository tenderRepository;
	
	
	@Autowired
	FileStorageService fileStorageService;
	
	@Autowired
	AuthRepository userRepo;
	
	
	@Autowired
	FeedbackRepository feedbackRepository;

	@Autowired
	NoticeRepository noticerepo;
	
	
	@Override
	public String addNews(NewsRequest newsReq) {
		NewsDO news = new NewsDO();
		String msg="";
		if (!newsReq.equals(null)) 
		{
			news.setCraetedOn(new Date());
			news.setUpdatedOn(new Date());
			//news.setIsActive(true);
			news.setDeletedStatus(false);
			if(newsReq.getUserid()!=null) {
				news.setCraetedBy(newsReq.getUserid());
			}
			if(newsReq.getUserid()!=null) {
				news.setUpdatedBy(newsReq.getUserid());
			}			
			if(newsReq.getContent()!=null) {
				news.setContent(newsReq.getContent());
			}
			long seq=cmsNewsRepo.count();
			news.setSeqNo((int)(seq+1));
			news=cmsNewsRepo.save(news);
			if(news!=null) {
				msg="success";
			}else {
				msg="fail";
			}
		 }
		return  msg;
		//return cmsNewsRepo.save(news);
	}

	@Override
	public List<NewsDO> getNews() {
		return cmsNewsRepo.findByDeletedStatus(false);
		//return cmsNewsRepo.findAllByOrderByCraetedOnDesc();
	}

	@Override
	public String getAllNews() {
		String newsString="";
		List<NewsDO> newslist=cmsNewsRepo.findByDeletedStatus(false);
		for (NewsDO newsDO : newslist) {
			
			if(newsString!="") {
				newsString=newsString+"|| "+newsDO.getContent();
			}else {
				newsString=newsDO.getContent();
			}
			
		}
		return newsString;
	}
	
	@Override
	public String deleteNewsById(Integer userId, Integer newsId) {
		String msg="fail";
		NewsDO newsDO = cmsNewsRepo.findById(newsId).get();
			if(newsDO !=null)
			{
				newsDO.setDeletedStatus(true);
				newsDO.setUpdatedBy(userId);
				newsDO.setUpdatedOn(new Date());	
				NewsDO save = cmsNewsRepo.save(newsDO);
				if(save != null)
				{
					msg="success";
				}
				else
				{
					msg="fail";
				}
			}
	    return msg;
	}
	@Override
	public String updateNews(NewsRequest newsReq,Integer id) 
	{
		String msg="fail";
		try {
		 NewsDO newsDO3 = cmsNewsRepo.findById(id).get();
		 if (newsDO3!=null)
		 {
			 if(newsReq.getContent() !=null) {
				 newsDO3.setContent(newsReq.getContent());
				 newsDO3.setUpdatedBy(newsReq.getUserid());
				 newsDO3.setUpdatedOn(new Date());
			 }
			 NewsDO save= cmsNewsRepo.save(newsDO3);
			 if(save!=null) {
					msg="success";
				}else {
					msg="fail";
				}
	     }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	@Override
	public String changeNewsActiveStatus(Integer userId, Integer newsId, boolean isActive) {
		 String msg = null;
		 NewsDO news=cmsNewsRepo.findById(newsId).orElse(null);
		  if(news!=null) {
			  if (isActive == true)
			   {
				 isActive = false; 
			   }else {
				isActive = true;
	           }  
			  news.setActive(isActive);
			  news.setUpdatedBy(userId);
			  news.setUpdatedOn(new Date());
			  NewsDO save = cmsNewsRepo.save(news);
				if (save != null) {
					msg = "success";
				} else {
					msg = "failed";
				}
		  }
		return msg;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String addNewContent(NewUpdateRequest newUpdateReq) {
		NewUpdate newUpdate = new NewUpdate();
		String msg="";
		if (!newUpdate.equals(null))
		{
			//setting default() values in below three fields
			newUpdate.setCreatedOn(new Date());
			newUpdate.setUpdatedOn(new Date());
			newUpdate.setIsactive(true);
			
			if(newUpdateReq.getUserId() != null)
			{
				newUpdate.setCreatedBy(newUpdateReq.getUserId());
				newUpdate.setUpdatedBy(newUpdateReq.getUserId());
			}
			if(newUpdateReq.getContent() !=null)
			{
				newUpdate.setContent(newUpdateReq.getContent());
			}
			long seq=CmsNewUpdateRepo.count();
			newUpdate.setSeqNo((int)(seq+1));
			newUpdate=CmsNewUpdateRepo.save(newUpdate);
			if(newUpdate!=null) {
				msg="success";
			}else {
				msg="fail";
			}
		}
		return msg;
	
	}

	@Override
	public List<NewUpdate> getAllNewContent() {
		
		return CmsNewUpdateRepo.findByDeletedStatus(false);
	}

	@Override
	public String updateNewContent(NewUpdateRequest newUpdateReq, Integer id) {
		String msg="fail";
		try {
		NewUpdate newUpdate = CmsNewUpdateRepo.findById(id).get();
		 if (newUpdate!=null)
		 {
			 if(newUpdateReq.getContent() !=null) {
				 newUpdate.setContent(newUpdateReq.getContent());
				 newUpdate.setUpdatedBy(newUpdateReq.getUserId());
				 newUpdate.setUpdatedOn(new Date());
			 }
			 NewUpdate save= CmsNewUpdateRepo.save(newUpdate);
			 if(save!=null) {
					msg="success";
				}else {
					msg="fail";
				}
	      }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	@Override
	public String deleteNewContentById(Integer userId, Integer contentId) {
		NewUpdate newDO = CmsNewUpdateRepo.findById(contentId).get();
		if(newDO !=null)
		{
			newDO.setDeletedStatus(true);
			newDO.setUpdatedBy(userId);
			newDO.setUpdatedOn(new Date());	
		}
		NewUpdate save = CmsNewUpdateRepo.save(newDO);
		String msg=null;
		if(save != null)
		{
			msg="success";
		}
		else
		{
			msg="fail";
		}
		return msg;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String addNewBlog(String title, String blogDesc,Integer userId ,MultipartFile file,String type) {
		
		BlogDO blogDO = new BlogDO();
		String msg=null;
		if(blogDO != null)
		{
			blogDO.setCraetedOn(new Date());
			blogDO.setUpdatedOn(new Date());
			blogDO.setIsActive(true);
			blogDO.setDeletedStatus(false);
			blogDO.setBlogDesc(blogDesc);
			blogDO.setTitle(title);
			blogDO.setUpdatedBy(userId);
			blogDO.setCraetedBy(userId);
			if(!file.isEmpty()) {
				FileUploadResponse uploadImage = fileStorageService.uploadImage(file, userId.toString(), type);
				if(!uploadImage.getFileName().isEmpty())
				{
					blogDO.setBlogImgPath(uploadImage.getFileName());
				}
			}
			long blogNo=CmsBlogRepo.count();
			blogDO.setBlogNum((int)(blogNo+1));
			
			long seq=CmsBlogRepo.count();
			blogDO.setSeqNo((int)(seq+1));
			
			blogDO=CmsBlogRepo.save(blogDO);
			
			if(blogDO!=null) 
			{
				msg="success";
			}
			else 
			{
				msg="fail";
			}
		}
		return msg;
		
	}
	@Override
	public String addNewBlogWithoutImg(String title, String blogDesc, Integer userId, String type) {
		BlogDO blogDO = new BlogDO();
		String msg=null;
		if(blogDO != null)
		{
			blogDO.setCraetedOn(new Date());
			blogDO.setUpdatedOn(new Date());
			blogDO.setIsActive(true);
			blogDO.setDeletedStatus(false);
			blogDO.setBlogDesc(blogDesc);
			blogDO.setTitle(title);
			blogDO.setUpdatedBy(userId);
			blogDO.setCraetedBy(userId);
			long count=CmsBlogRepo.count();
			blogDO.setBlogNum((int)(count+1));
			blogDO.setSeqNo((int)(count+1));
			
			blogDO=CmsBlogRepo.save(blogDO);
			
			if(blogDO!=null) 
			{
				msg="success";
			}
			else 
			{
				msg="fail";
			}
		}
		return msg;
	}

	@Override
	public List<BlogDto> getAllBlogData() 
	{
	    //List<BlogDO> findAll = CmsBlogRepo.findAll();
		List<BlogDO> findAll = CmsBlogRepo.findByDeletedStatus(false);
	    List<BlogDto> blogDtoList=new ArrayList<>();
	   if(!findAll.isEmpty()) {
	    for (BlogDO blogDO : findAll) 
	    {
	    	BlogDto blogDto = new BlogDto();
			User findUsernameById = userRepo.findById(blogDO.getCraetedBy()).get();
			blogDto.setCreatedByName(findUsernameById.getFirstName()+" "+((findUsernameById.getMiddleName()!=null)?findUsernameById.getMiddleName():"")+""+findUsernameById.getLastName());
			blogDto.setUserDesignation(findUsernameById.getRoles().get(0).getName().toString());
			blogDto.setBlog_id(blogDO.getBlogId());
			blogDto.setBlogImgPath(blogDO.getBlogImgPath());
			blogDto.setUpdatedOn(blogDO.getUpdatedOn());
			blogDto.setCraetedOn(blogDO.getCraetedOn());
			blogDto.setIsActive(blogDO.getIsActive());
			blogDto.setDeletedStatus(blogDO.getDeletedStatus());
			blogDto.setBlogNum(blogDO.getBlogNum());
			blogDto.setBlogDesc(blogDO.getBlogDesc());
			blogDto.setSeqNo(blogDO.getSeqNo());
			blogDto.setTitle(blogDO.getTitle());
			blogDto.setUpdatedBy(blogDO.getUpdatedBy());
			blogDto.setCraetedBy((blogDO.getCraetedBy()));
			blogDtoList.add(blogDto);
		  }
	    }
		return blogDtoList;
	}
	
	@Override
	public List<BlogDto> getAllBlogById(Integer id) {
		List<BlogDto> list=new ArrayList<BlogDto>();
		List<BlogDO> findBlogBycraetedBy = CmsBlogRepo.findByCraetedByAndDeletedStatus(id,false);
		for (BlogDO blogDO : findBlogBycraetedBy) 
		{
			BlogDto blogDto = new BlogDto();
			//System.out.println(blogDO.getBlogDesc());
			User findUsernameById = userRepo.findById(blogDO.getCraetedBy()).get();
			blogDto.setCreatedByName(findUsernameById.getFirstName()+" "+((findUsernameById.getMiddleName()!=null)?findUsernameById.getMiddleName():"")+""+findUsernameById.getLastName());
			blogDto.setUserDesignation(findUsernameById.getRoles().get(0).getName().toString());
			blogDto.setBlogImgPath(blogDO.getBlogImgPath());
			blogDto.setBlog_id(blogDO.getBlogId());
			blogDto.setUpdatedOn(blogDO.getUpdatedOn());
			blogDto.setCraetedOn(blogDO.getCraetedOn());
			blogDto.setIsActive(blogDO.getIsActive());
			blogDto.setDeletedStatus(blogDO.getDeletedStatus());
			blogDto.setBlogNum(blogDO.getBlogNum());
			blogDto.setBlogDesc(blogDO.getBlogDesc());
			blogDto.setSeqNo(blogDO.getSeqNo());
			blogDto.setTitle(blogDO.getTitle());
			blogDto.setUpdatedBy(blogDO.getUpdatedBy());
			blogDto.setCraetedBy((blogDO.getCraetedBy()));
			list.add(blogDto);
		
		}
		return list;
	}
	
	@Override
	public String updateBlog(Integer userId,String title, String blogDesc, MultipartFile file, Integer blogId,String type) 
	{		String msg=null;
		try {	
	    BlogDO blogDO = CmsBlogRepo.findById(blogId).get();
		if(blogDO != null)
		{
			blogDO.setBlogDesc(blogDesc);
		   
			FileUploadResponse uploadImage = fileStorageService.uploadImage(file, userId.toString(), type);
			if(!uploadImage.getFileName().isEmpty())
			{
				blogDO.setBlogImgPath(uploadImage.getFileName());
			}
			blogDO.setUpdatedBy(userId);
			blogDO.setUpdatedOn(new Date());
			blogDO.setTitle(title);
		}
			BlogDO save = CmsBlogRepo.save(blogDO);
		
			if(save!=null) {
				msg="success";
			}else {
				msg="fail";
			}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		return msg;
	}

	@Override
	public String updateBlogWithoutImage(Integer userId, String blogTitle, String blogDesc, Integer blogId,
			String type) {
		 BlogDO blogDO = CmsBlogRepo.findById(blogId).get();
			if(blogDO != null)
			{
				blogDO.setBlogDesc(blogDesc); 
				blogDO.setTitle(blogTitle);
				blogDO.setUpdatedBy(userId);
				blogDO.setUpdatedOn(new Date());
			}
				BlogDO save = CmsBlogRepo.save(blogDO);
				String msg=null;
				if(save!=null) {
					msg="success";
				}else {
					msg="fail";
				}
			
			return msg;
	}
	@Override
	public String deleteBlogById(Integer userId, Integer blogId) {
		 BlogDO blogDO = CmsBlogRepo.findById(blogId).get();
		if(blogDO !=null)
		{
			blogDO.setDeletedStatus(true);
			blogDO.setUpdatedBy(userId);
			blogDO.setUpdatedOn(new Date());	
		}
		BlogDO save = CmsBlogRepo.save(blogDO);
		String msg=null;
		if(save != null)
		{
			msg="success";
		}
		else
		{
			msg="fail";
		}
		return msg;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String addPublication(String publicationTitle, MultipartFile file,MultipartFile pdfFile, Integer userid,String type) {
		PublicationDO publicationDO=new PublicationDO();
		String msg=null;
		FileUploadResponse uploadImage =null;
		FileUploadResponse uploadPdf=null;
		if(file!=null) {
			 uploadImage = fileStorageService.uploadImage(file, userid.toString(),type);
		}
		if(pdfFile!=null) {
			 uploadPdf = fileStorageService.uploadImage(pdfFile, userid.toString(),type);
		}
		
		
		if(publicationDO != null)
		{
			publicationDO.setCraetedOn(new Date());
			publicationDO.setUpdatedOn(new Date());
			publicationDO.setIsActive(true);
			publicationDO.setDeletedStatus(false);
			publicationDO.setPublicationTitle(publicationTitle);
			publicationDO.setUpdatedBy(userid);
			publicationDO.setCraetedBy(userid);
			if(!uploadImage.getFileName().isEmpty())
			{
				publicationDO.setThumbnilImgPath(uploadImage.getFileName());
			}
			if(!uploadPdf.getFileName().isEmpty())
			{
				publicationDO.setPdfPath(uploadPdf.getFileName());
			}		
		}
		   
			long count = cmsPublicationRepo.count();
			publicationDO.setPublicationNum((int)(count+1));
			publicationDO.setSeqNo((int)(count+1));
			 PublicationDO save = cmsPublicationRepo.save(publicationDO);
			if(save!=null) 
			{
				msg="success";
			}
			else 
			{
				msg="fail";
			}
		
		return msg;
	}

	@Override
	public List<PublicationDO> getAllPublication() {
		
		//return cmsPublicationRepo.findAll();
		return cmsPublicationRepo.findByDeletedStatus(false);
	}

	@Override
	public String updatepublication(Integer userId,String publicationTitle, MultipartFile file, MultipartFile pdfFile,Integer publicationid,String type) 
	{
		String msg="failed";
		PublicationDO publicationDO = cmsPublicationRepo.findById(publicationid).get();
		if(publicationDO !=null)
		{
			if(publicationTitle!= null) 
			{
			publicationDO.setPublicationTitle(publicationTitle);
			}
			
			FileUploadResponse uploadImage =null;
			FileUploadResponse uploadPdf=null;
			if(file!=null) {
				 uploadImage = fileStorageService.uploadImage(file, userId.toString(),type);
			}
			if(pdfFile!=null) {
				 uploadPdf = fileStorageService.uploadImage(pdfFile, userId.toString(),type);
			}
			
			if(uploadImage!=null && !uploadImage.getFileName().isEmpty())
			{
				publicationDO.setThumbnilImgPath(uploadImage.getFileName());
			}
			if(uploadPdf!=null && !uploadPdf.getFileName().isEmpty())
			{
				publicationDO.setPdfPath(uploadPdf.getFileName());
			}
			publicationDO.setUpdatedBy(userId);
			publicationDO.setUpdatedOn(new Date());
			PublicationDO save = cmsPublicationRepo.save(publicationDO);
			if(save!=null)
			{
				msg="success";
			}
			else
			{
				msg="failed";
			}
		}
		
		return msg;
	}
	@Override
	public String updatePublicationByIdWithoutFile(Integer userId, String publicationTitle, Integer publicationid,
			String type) {
		String msg="failed";
		PublicationDO publicationDO = cmsPublicationRepo.findById(publicationid).get();
		if(publicationDO !=null)
		{
			if(publicationTitle!= null) 
			{
			publicationDO.setPublicationTitle(publicationTitle);
			}
			publicationDO.setUpdatedBy(userId);
			publicationDO.setUpdatedOn(new Date());
			PublicationDO save = cmsPublicationRepo.save(publicationDO);
			
			if(save!=null)
			{
				msg="success";
			}
			else
			{
				msg="failed";
			}
		}
		return msg;
	}
	@Override
	public String deletePublicationById(Integer userId, Integer publicationId) 
	{
		PublicationDO publicationDO = cmsPublicationRepo.findById(publicationId).orElse(null);
		if(publicationDO!=null)
		{
			publicationDO.setUpdatedBy(userId);
			publicationDO.setDeletedStatus(true);
			publicationDO.setUpdatedOn(new Date());
			
		}
		PublicationDO save = cmsPublicationRepo.save(publicationDO);
		String msg="";
		if(save!=null)
		{
			msg="success";
		}
		else{
			  msg="failed";
		     }
		return msg;
		}
		
		
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String addGallery(String gtype, MultipartFile file,Integer userid,String type) 
	{
		Gallery gallery = new Gallery();
		FileUploadResponse uploadImage=null;
		if(file!=null) {
			uploadImage = fileStorageService.uploadImage(file, userid.toString(),type);
		}
		if(gallery !=null)
		{
			gallery.setCreatedOn(new Date());
			gallery.setUpdatedOn(new Date());
			gallery.setCreatedBy(userid);
			gallery.setUpdatedBy(userid);
			GalleryTypeMaster galleryTypeMaster = galleryTypeMasterRepository.findById(Integer.parseInt(gtype)).get();
			if(galleryTypeMaster!=null)
			gallery.setTypemaster(galleryTypeMaster);
			if(!uploadImage.getFileName().isEmpty())
			{
				gallery.setImgPath(uploadImage.getFileName());
			}
			
		}
		Gallery save = galleryRepository.save(gallery);
		String msg="";
		if(save!=null)
		{
			msg="success";
		}
		else
		{
			msg="fail";
		}
		return msg;
	}

	@Override
	public List<Gallery> getAllGallery() {
		//List<Gallery> findAll = galleryRepository.findAll();
		List<Gallery> findAll = galleryRepository.findByDeletedStatusAndTypeMasterTrue();
		return findAll;
	}
	@Override
	public List<Gallery> getGalleryImgByType(Integer gtype) {
		
		List<Gallery> findAll = galleryRepository.findByDeletedStatusAndImgType(gtype);
		return findAll;
	}
	@Override
	  public String deleteGalleryById(Integer userId, Integer publicationId) {
		Gallery newGallery = galleryRepository.findById(publicationId).orElse(null);
		if(newGallery!=null)
		{
			newGallery.setDeletedStatus(true);
			newGallery.setUpdatedBy(userId);
			newGallery.setUpdatedOn(new Date());
		}
		Gallery save = galleryRepository.save(newGallery);
		String msg="";
		if(save!=null)
		{
			msg="success";
		}
		else
		{
			msg="fail";
		}
		return msg;
	}
	@Override
	public List<GalleryTypeMaster> getGalleryType() {
		List<GalleryTypeMaster> findAll = galleryTypeMasterRepository.findAll();
		return findAll;
	}
	@Override
	public List<GalleryTypeMaster> getActiveGalleryType() {
		List<GalleryTypeMaster> findAll = galleryTypeMasterRepository.findByActive(true);
		return findAll;
	}
	@Override
	public String changeGalleryTypeStatus(Integer userId, Integer typeId, boolean isActive) {
		    String msg = null;
		    GalleryTypeMaster gtm=galleryTypeMasterRepository.findById(typeId).orElse(null);
		  
			if (isActive == true)
			   {
				 isActive = false; 
			   }
			else {
				isActive = true;
	           }  
	         gtm.setActive(isActive);
		     gtm.setUpdatedBy(userId);
		     gtm.setUpdatedOn(new Date());
			GalleryTypeMaster save = galleryTypeMasterRepository.save(gtm);
			if (save != null) {
				msg = "success";
			} else {
				msg = "failed";
			}
		return msg;
	}
	@Override
	public String addGalleryType(String gtype, Integer userid) {
		String msg=null;
		GalleryTypeMaster gType=galleryTypeMasterRepository.findByType(gtype);
		  if(gType==null||!gType.getType().equals(gtype)) {
				GalleryTypeMaster galleryTypeMaster=new GalleryTypeMaster();
				galleryTypeMaster.setType(gtype);
				galleryTypeMaster.setCreatedOn(new Date());
				galleryTypeMaster.setUpdatedOn(new Date());
				galleryTypeMaster.setUpdatedBy(userid);
				galleryTypeMaster.setCreatedBy(userid);
				GalleryTypeMaster save = galleryTypeMasterRepository.save(galleryTypeMaster);
				if(save != null)
				{
					msg="success";
				}
				else
				{
					msg="Fail";
				}
		  }else {
			  msg="exists";
		  }
		return msg;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String addTender(String title, String tenderNum, String publicationDate,MultipartFile file,String userid,String type)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date publicationDated=null;
		try {
			publicationDated = dateFormat.parse(publicationDate);

		     } 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		TenderDO tenderDO=new TenderDO();
		FileUploadResponse uploadImage=null;
		if(file!=null) {
			uploadImage = fileStorageService.uploadImage(file, userid.toString(),type);
		}
		if(tenderDO !=null)
		{
			tenderDO.setCraetedOn(new Date());
			tenderDO.setUpdatedOn(new Date());
			tenderDO.setCraetedBy(Integer.parseInt(userid));
			tenderDO.setUpdatedBy(Integer.parseInt(userid));
			tenderDO.setIsActive(true);
			tenderDO.setTitle(title);
			tenderDO.setTenderNum(tenderNum);
			tenderDO.setPublicationDate(publicationDated);
			if(!uploadImage.getFileName().isEmpty())
			{
				tenderDO.setUploadPath(uploadImage.getFileName());
			}
			
			
		}
		TenderDO save = tenderRepository.save(tenderDO);
		String msg="";
		if(save!=null)
		{
			msg="success";
		}
		else
		{
			msg="failed";
		}
		return msg;
	}
	@Override
	public List<TenderDO> getAllTender() {
		//List<TenderDO> findAll = tenderRepository.findAll();
		List<TenderDO> findAll = tenderRepository.findByDeletedStatus(false);
		return findAll;
	}
	
	@Override
	  public String deleteTenderById(Integer userId, Integer tenderId) {
		TenderDO tender = tenderRepository.findById(tenderId).orElse(null);
		if(tender!=null)
		{
			tender.setDeletedStatus(true);
			tender.setUpdatedBy(userId);
			tender.setUpdatedOn(new Date());
		}
		TenderDO save = tenderRepository.save(tender);
		String msg="";
		if(save!=null)
		{
			msg="success";
		}
		else
		{
			msg="fail";
		}
		return msg;
	}
	@Override
	public String updateTender(String title, String tenderId, String publicationDate,
			MultipartFile file, String userid,String type) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date publicationDated=null;
		try {
			publicationDated = dateFormat.parse(publicationDate);

		     } 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		TenderDO tenderDO = tenderRepository.findById(Integer.parseInt(tenderId)).get();
		FileUploadResponse uploadPdf=null;
		if(file!=null) {
			uploadPdf = fileStorageService.uploadImage(file, userid.toString(),type);
		}
		if(tenderDO !=null)
		{
			tenderDO.setTitle(title);
			if(!uploadPdf.getFileName().isEmpty())
			{
				tenderDO.setUploadPath(uploadPdf.getFileName());
			}
			tenderDO.setPublicationDate(publicationDated);
			tenderDO.setCraetedBy(Integer.parseInt(userid));
			tenderDO.setUpdatedBy(Integer.parseInt(userid));
		}
		TenderDO save = tenderRepository.save(tenderDO);
		String msg=null;
		if(save != null)
		{
			msg="Update Successfully";
		}
		else
		{
			msg="Fail";
		}
		return msg;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	@Override
	public String addFeedback(String name, String email, String phone, String comment) {
		FeedbackDO feedback = new FeedbackDO();
		String msg=null;
			feedback.setComments(comment);
			feedback.setName(name);
			feedback.setEmailId(email);
			feedback.setPhoneNumber(Long.parseLong(phone));
			feedback.setCraetedOn(new Date());
			feedback.setUpdatedOn(new Date());
			feedback.setStatus(false);
			feedback=feedbackRepository.save(feedback);
			
			if(feedback!=null) 
			{
				msg="success";
			}
			else 
			{
				msg="fail";
			}
		return msg;
	}

	@Override
	public List<FeedbackDO> getAllFeedback() {
		List<FeedbackDO> findAll = feedbackRepository.findAll();
		return findAll;
	}

	@Override
	public String updateFeedbackStatus(Integer userId, Integer feedbackId) {
		FeedbackDO feedbackDo = feedbackRepository.findById(feedbackId).get();
		if(feedbackDo !=null)
		{
			feedbackDo.setStatus(true);
			feedbackDo.setUpdatedBy(userId);
			feedbackDo.setUpdatedOn(new Date());	
		}
		FeedbackDO save = feedbackRepository.save(feedbackDo);
		String msg=null;
		if(save != null)
		{
			msg="Success";
		}
		else
		{
			msg="Fail";
		}
		return msg;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String addNotice(String noticeNumber, String noticeName, MultipartFile file,String userid, String type,String noticeValidDate,String noticeDate)
	{ 
		   NoticeDO noticeDO=new NoticeDO();
		   SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				Date noticeValidDated= dateFormat.parse(noticeValidDate);
				Date noticeInsertDate= dateFormat.parse(noticeDate);
				noticeDO.setNoticeValidDate(noticeValidDated);
				noticeDO.setNoticeDate(noticeInsertDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			noticeDO.setCraetedOn(new Date());
			noticeDO.setUpdatedOn(new Date());
			noticeDO.setCraetedBy(Integer.parseInt(userid));
			noticeDO.setUpdatedBy(Integer.parseInt(userid));
			noticeDO.setIsActive(true);
			noticeDO.setNoticeNumber(noticeNumber);	
			noticeDO.setNoticeName(noticeName);
			NoticeDO save = noticerepo.save(noticeDO);
			
			FileUploadResponse uploadImage=null;
			if(file!=null) {
				uploadImage = fileStorageService.uploadImage(file, userid.toString(),type);
			}
			if(!uploadImage.getFileName().isEmpty())
			{
				save.setNoticePath(uploadImage.getFileName());
			}
			
		 NoticeDO save1 = noticerepo.save(save);
		String msg="";
		if(save!=null)
		{
			msg="success";
		}
		else
		{
			msg="failed";
		}
		return msg;
	}

	@Override
	public List<NoticeDO> getAllNotice() 
	{
		List<NoticeDO> findAll = noticerepo.findAll();
		return findAll;
	}

	
	
	

}

