package com.sparc.pccf.wildlife.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.sparc.pccf.wildlife.request.NewUpdateRequest;
import com.sparc.pccf.wildlife.request.NewsRequest;

public interface CmsService {

	String addNews(NewsRequest newsReq);

	List<NewsDO> getNews();
	
	String updateNews(NewsRequest newsReq, Integer id);
	
	/* public void deleteNews(Integer id); */
	
	String addNewContent(NewUpdateRequest newUpdateReq);

	List<NewUpdate> getAllNewContent();
	
	String updateNewContent(NewUpdateRequest newUpdateReq,Integer id);
	
    String addNewBlog(String title, String blogDesc,Integer userId, MultipartFile file,String type);

    List<BlogDto> getAllBlogData();
    
    List<BlogDto> getAllBlogById(Integer id);

	String updateBlog(Integer userId,String title,String blogDesc,MultipartFile file,Integer blogId,String type);
	
	String addPublication(String publicationTitle,MultipartFile file,MultipartFile pdfFile,Integer userid,String type);
	
     List<PublicationDO> getAllPublication();
     
     String updatepublication(Integer userId,String publicationTitle,MultipartFile file,MultipartFile pdfFile,Integer publicationid,String type);
     
     String addGallery(String gtype,MultipartFile file,Integer userid,String type);
     
     List<Gallery> getAllGallery();
     
     String addTender(String title,String tenderNum,String publicationDate,MultipartFile file,String userid,String type);
     
     List<TenderDO> getAllTender();
     
     String updateTender(String title,String tenderId,String publicationDate,MultipartFile file,String userid,String type);

	List<GalleryTypeMaster> getGalleryType();

	String addGalleryType(String gtype, Integer userid);

	String addNewBlogWithoutImg(String title, String blogDesc, Integer userId, String type);

	String getAllNews();

	String addFeedback(String name, String email, String phone, String comment);

	List<FeedbackDO> getAllFeedback();

	String updateFeedbackStatus(Integer userId, Integer feedbackId);

	String updateBlogWithoutImage(Integer userId, String blogTitle, String blogDesc, Integer blogId, String type);

	String deleteBlogById(Integer userId, Integer blogId);

	String deleteNewsById(Integer userId, Integer newsId);

	String deleteNewContentById(Integer userId, Integer contentId);

	String deletePublicationById(Integer userId, Integer publicationId);

	String deleteGalleryById(Integer userId, Integer publicationId);

	String deleteTenderById(Integer userId, Integer tenderId);

	String changeGalleryTypeStatus(Integer userId, Integer typeId, boolean isActive);

	List<GalleryTypeMaster> getActiveGalleryType();

	String changeNewsActiveStatus(Integer userId, Integer newsId, boolean isActive);

	String updatePublicationByIdWithoutFile(Integer userId, String publicationTitle, Integer publicationid,String type);

	List<Gallery> getGalleryImgByType(Integer gtype);

	String addNotice(String noticeNumber, String noticeName, MultipartFile file,String userid,String type,String noticeValidDate,String noticeDate);

	List<NoticeDO> getAllNotice();

	
}
