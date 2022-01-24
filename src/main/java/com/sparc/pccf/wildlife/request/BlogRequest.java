package com.sparc.pccf.wildlife.request;

public class BlogRequest 
{
private Integer userid;
private String blogTitle;
private String blogDesc;
//private Integer blogId;
//private String blogImgPath;

/*
 * public Integer getBlogId() { return blogId; } public void setBlogId(Integer
 * blogId) { this.blogId = blogId; }
 */
public Integer getUserid() {
	return userid;
}
public void setUserid(Integer userid) {
	this.userid = userid;
}
public String getBlogTitle() {
	return blogTitle;
}
public void setBlogTitle(String blogTitle) {
	this.blogTitle = blogTitle;
}

/*
 * public String getBlogImgPath() { return blogImgPath; } public void
 * setBlogImgPath(String blogImgPath) { this.blogImgPath = blogImgPath; }
 */
public String getBlogDesc() {
	return blogDesc;
}
public void setBlogDesc(String blogDesc) {
	this.blogDesc = blogDesc;
}


}
