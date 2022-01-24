package com.sparc.pccf.wildlife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sparc.pccf.wildlife.dto.BlogDto;
import com.sparc.pccf.wildlife.entity.FeedbackDO;
import com.sparc.pccf.wildlife.entity.Gallery;
import com.sparc.pccf.wildlife.entity.GalleryTypeMaster;
import com.sparc.pccf.wildlife.entity.NewUpdate;
import com.sparc.pccf.wildlife.entity.NewsDO;
import com.sparc.pccf.wildlife.entity.NoticeDO;
import com.sparc.pccf.wildlife.entity.PublicationDO;
import com.sparc.pccf.wildlife.entity.TenderDO;
import com.sparc.pccf.wildlife.request.NewUpdateRequest;
import com.sparc.pccf.wildlife.request.NewsRequest;
import com.sparc.pccf.wildlife.response.MessageResponse;
import com.sparc.pccf.wildlife.service.CmsService;


@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/cms")
public class CmsController 
{
	@Autowired
	CmsService cmsService;
	
/* addNews operations */
	
   @PostMapping("/addNews")
   public ResponseEntity<?> addNews(@RequestBody NewsRequest newsReq) {
	   
	  return ResponseEntity.ok(new MessageResponse(cmsService.addNews(newsReq)));
    }
   
   @GetMapping("/getNews")
   public List<NewsDO> getNews() {
	   
	  return cmsService.getNews();
    }
   @GetMapping("/getAllNews")
   public ResponseEntity<?> getAllNews() {
	   
	   
	  return ResponseEntity.ok(new MessageResponse(cmsService.getAllNews())); 
    }
   @PutMapping("/updateNewsById")
   public ResponseEntity<?> updateNewsById(@RequestBody NewsRequest newsReq,@RequestParam Integer id)
   {
	   String msg = cmsService.updateNews(newsReq, id) ;
	   return ResponseEntity.ok(new MessageResponse(msg));
	   
   }
   @PutMapping("/deleteNewsById")
   public ResponseEntity<?> deleteNewsById(@RequestParam Integer userId,@RequestParam Integer newsId) 
   {
	String status = cmsService.deleteNewsById(userId,newsId) ;
	return ResponseEntity.ok(new MessageResponse(status));
    }
   @PutMapping("/changeNewsActiveStatus")
	public String changeNewsActiveStatus(@RequestParam Integer userId, @RequestParam Integer newsId,@RequestParam boolean isActive) 
	{
		return cmsService.changeNewsActiveStatus(userId, newsId, isActive);
	}
   /* addNewContent operations */
   
   @PostMapping("/addNewContent")
   public ResponseEntity<?> addNewContent(@RequestBody NewUpdateRequest newUpdateReq)
   {
	return  ResponseEntity.ok(new MessageResponse(cmsService.addNewContent(newUpdateReq)));
	   
   }
   @GetMapping("/getAllNewContent")
   public List<NewUpdate> getAllNewContent() {
	   
	  return cmsService.getAllNewContent();
    }
   
   @PutMapping("/updateNewContentById")
   public ResponseEntity<?> updateNewContentById(@RequestBody NewUpdateRequest newUpdateReq,@RequestParam Integer id )
   {
	   String msg =cmsService.updateNewContent(newUpdateReq, id);
	   return ResponseEntity.ok(new MessageResponse(msg));
	   
   }
   @PutMapping("/deleteNewContentById")
   public ResponseEntity<?> deleteNewContentById(@RequestParam Integer userId,@RequestParam Integer contentId) 
   {
	String status = cmsService.deleteNewContentById(userId,contentId) ;
	return ResponseEntity.ok(new MessageResponse(status));
    }
   
 /* Blog operation */
   
   @PostMapping("/addNewBlog")
   public ResponseEntity<?> addNewBlog(@RequestParam String title,
		                               @RequestParam String blogDesc,
                                       @RequestParam Integer userId,
                                       @RequestParam("file") MultipartFile file,
                                       @RequestParam String type)
   {
	
	   String msg=cmsService.addNewBlog(title, blogDesc,userId, file,type);
	   return ResponseEntity.ok(new MessageResponse(msg));
   } 
   @PostMapping("/addNewBlogWithoutImg")
   public ResponseEntity<?> addNewBlogWithoutImg(@RequestParam String title,
		                                         @RequestParam String blogDesc,
		                                         @RequestParam Integer userId,
		                                         @RequestParam String type)
   {
	
	   String msg=cmsService.addNewBlogWithoutImg(title, blogDesc,userId,type);
	   return ResponseEntity.ok(new MessageResponse(msg));
   }

   @PutMapping("/updateBlog")
   public ResponseEntity<?> updateBlog(@RequestParam Integer userId,
		                               @RequestParam String blogTitle,
		                               @RequestParam String blogDesc,
		                               @RequestParam("file") MultipartFile file,
		                               @RequestParam Integer blogId,
		                               @RequestParam String type)
   {
	   String msg = cmsService.updateBlog(userId,blogTitle, blogDesc, file, blogId,type);
	   return ResponseEntity.ok(new MessageResponse(msg));
	   
   }
   @PutMapping("/updateBlogWithoutImage")
   public ResponseEntity<?> updateBlogWithoutImage(@RequestParam Integer userId,
		                               @RequestParam String blogTitle,
		                               @RequestParam String blogDesc,
		                               @RequestParam Integer blogId,
		                               @RequestParam String type)
   {
	   String msg = cmsService.updateBlogWithoutImage(userId,blogTitle, blogDesc, blogId,type);
	   return ResponseEntity.ok(new MessageResponse(msg));
	   
   }
   @GetMapping("/getAllBlogData")
   public List<BlogDto> getAllBlogData()
   {
	return cmsService.getAllBlogData();
	   
   }
   @GetMapping("/getAllBlogByUserId")
   public List<BlogDto> getAllBlogByUserId(@RequestParam Integer id)
   {
	   List<BlogDto> allBlogById = cmsService.getAllBlogById(id);
	   return allBlogById;
   }
   @PutMapping("/deleteBlogById")
   public ResponseEntity<?> deleteBlogById(@RequestParam Integer userId,@RequestParam Integer blogId) 
   {
	String status = cmsService.deleteBlogById(userId,blogId) ;
	return ResponseEntity.ok(new MessageResponse(status));
    }
/* publication operations */
   
   
   @PostMapping("/addPublication")
   public ResponseEntity<?> addPublication(@RequestParam String publicationTitle,
		                        @RequestParam("file") MultipartFile file ,
		                        @RequestParam ("pdfFile") MultipartFile pdfFile,
		                        @RequestParam Integer userid,@RequestParam String type)
   {
	   String addPublication = cmsService.addPublication(publicationTitle, file, pdfFile, userid,type);
	return ResponseEntity.ok(new MessageResponse(addPublication));
	   
   }
   
   @GetMapping("/getAllPublication")
   public List<PublicationDO> getAllPublication()
   {
	   List<PublicationDO> allPublication = cmsService.getAllPublication();
	return allPublication;
	   
   }
   @PutMapping("/updatePublicationById")
   public ResponseEntity<?> updatePublicationById(@RequestParam Integer userId,@RequestParam String publicationTitle,
		   @RequestParam(value ="file",required=false) MultipartFile file,
		   @RequestParam (value ="pdfFile",required=false) MultipartFile pdfFile,
		   @RequestParam Integer publicationid,@RequestParam String type) 
   {
	String addPublication = cmsService.updatepublication(userId,publicationTitle, file, pdfFile, publicationid,type) ;
	return ResponseEntity.ok(new MessageResponse(addPublication));
    }
   @PutMapping("/updatePublicationByIdWithoutFile")
   public ResponseEntity<?> updatePublicationByIdWithoutFile(@RequestParam Integer userId,@RequestParam String publicationTitle,
		   @RequestParam Integer publicationid,@RequestParam String type) 
   {
	String addPublication = cmsService.updatePublicationByIdWithoutFile(userId,publicationTitle, publicationid,type) ;
	return ResponseEntity.ok(new MessageResponse(addPublication));
    }
   @PutMapping("/deletePublicationById")
   public ResponseEntity<?> deletePublicationById(@RequestParam Integer userId,@RequestParam Integer publicationId) 
   {
	String status = cmsService.deletePublicationById(userId,publicationId);
	return ResponseEntity.ok(new MessageResponse(status));
    }
   
	/* Gallery operations */
   @GetMapping("/getGalleryType")
   public List<GalleryTypeMaster> getGalleryType()
   {
	   List<GalleryTypeMaster> allType = cmsService.getGalleryType();
	return allType;
	   
   }
   @GetMapping("/getActiveGalleryType")
   public List<GalleryTypeMaster> getActiveGalleryType()
   {
	   List<GalleryTypeMaster> allType = cmsService.getActiveGalleryType();
	return allType;
   }
	@PutMapping("/changeGalleryTypeStatus")
	public String changeGalleryTypeStatus(@RequestParam Integer userId, @RequestParam Integer typeId,@RequestParam boolean isActive) 
	{
		return cmsService.changeGalleryTypeStatus(userId, typeId, isActive);
	}
   @PostMapping("/addGalleryType") 
   public ResponseEntity<?> addGalleryType(@RequestParam String gtype,@RequestParam Integer userid)
   {
	String addGalleryType = cmsService.addGalleryType(gtype, userid);
 	return ResponseEntity.ok(new MessageResponse(addGalleryType));
   }
   @PostMapping("/addGallery") 
   public ResponseEntity<?> addGallery(@RequestParam String gtype,@RequestParam("file") MultipartFile file,@RequestParam Integer userid,@RequestParam String type)
   {
	String addGallery = cmsService.addGallery(gtype, file, userid,type);
 	return ResponseEntity.ok(new MessageResponse(addGallery));
   }
   
   @GetMapping("/getAllGallery")
   public List<Gallery> getAllGallery()
   {
	return cmsService.getAllGallery();
	   
   }
   @GetMapping("/getAllGalleryImgByType")
   public List<Gallery> getAllGalleryImgByType(@RequestParam Integer typeId)
   {
	return cmsService.getGalleryImgByType(typeId);
	   
   }
   @PutMapping("/deleteGalleryById")
   public ResponseEntity<?> deleteGalleryById(@RequestParam Integer userId,@RequestParam Integer publicationId)
   {
	String status = cmsService.deleteGalleryById(userId,publicationId);  
	return ResponseEntity.ok(new MessageResponse(status));
	   
   }
   @PutMapping("/deleteTenderById")
   public ResponseEntity<?> deleteTenderById(@RequestParam Integer userId,@RequestParam Integer tenderId)
   {
	String status = cmsService.deleteTenderById(userId,tenderId);  
	return ResponseEntity.ok(new MessageResponse(status));
	   
   }
 /* Tender operations */
   
   @PostMapping("/addTender")
   public ResponseEntity<?> addTender(@RequestParam String title,
		                              @RequestParam String tenderNum,
		                              @RequestParam String publicationDate,
		                              @RequestParam("file") MultipartFile file,
		                              @RequestParam String userid,@RequestParam String type)
   {
     String addTender = cmsService.addTender(title, tenderNum, publicationDate, file, userid,type);
	 return ResponseEntity.ok(new MessageResponse(addTender));
   }
   
	 @GetMapping("/getAllTender")
	 public List<TenderDO> getAllTender()
	 {
		 List<TenderDO> allTender = cmsService.getAllTender();
		return allTender;
		 
	 }
	  @PutMapping("/updateTender")
	  public ResponseEntity<?> updateTender(@RequestParam String title,
              @RequestParam String tenderId,
              @RequestParam String publicationDate,
              @RequestParam(value ="file",required=false) MultipartFile file,
              @RequestParam String userid,@RequestParam String type)
	  {
		  String updateTender = cmsService.updateTender(title, tenderId, publicationDate, file, userid,type);
		  return ResponseEntity.ok(new MessageResponse(updateTender));
		  
	  }
	  
	  
  /* feedback operation */
	  
	  @PostMapping("/addFeedback")
	   public ResponseEntity<?> addFeedback(@RequestParam String name,@RequestParam String email,@RequestParam String phone,@RequestParam String comment)
	   {
		   String msg=cmsService.addFeedback(name, email,phone,comment);
		   return ResponseEntity.ok(new MessageResponse(msg));
	   }
	  @GetMapping("/getAllFeedback")
		 public List<FeedbackDO> getAllFeedback()
		 {
			 List<FeedbackDO> allFeedback = cmsService.getAllFeedback();
			return allFeedback;
			 
		 }
	  @PutMapping("/updateFeedbackStatus")
	   public ResponseEntity<?> updateFeedbackStatus(@RequestParam Integer userId,@RequestParam Integer feedbackId) 
	   {
		String status = cmsService.updateFeedbackStatus(userId,feedbackId) ;
		return ResponseEntity.ok(new MessageResponse(status));
	    }

 /* Notice operation */	
   @PostMapping("/addNotice")
   public ResponseEntity<?> addNotice(@RequestParam String noticeNumber,
								      @RequestParam String noticeName,
								      @RequestParam("file") MultipartFile file,
								      @RequestParam String userid,
								      @RequestParam String type,
								      @RequestParam String noticeValidDate,
								      @RequestParam String noticeDate)
     {
	   String addNotice = cmsService.addNotice(noticeNumber, noticeName, file, userid, type, noticeValidDate, noticeDate);
	   return ResponseEntity.ok(new MessageResponse(addNotice));
	
      }
   
     @GetMapping("/getAllNotice")
	 public List<NoticeDO> getAllNotice()
	 {
		 List<NoticeDO> allNotice = cmsService.getAllNotice();
		return allNotice;
		 
	 }

}	
   

