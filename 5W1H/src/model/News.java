package model;

import java.util.ArrayList;
import java.util.List;

/*
 * Project: 5W1H
 * Author: Mengdi Zhang
 * Date: 2014-12-31 下午4:48:03
 * Version: 
 **/

public class News {
	//original info
	private String url="";
	private String title="";
	private String content="";
	private String uploadedTime="";
	private String uploadedBy="";
	private String Originated="";
	private String category="";
	private String journalist="";
	private String subject="";
	private String image="";
	private String video="";
	
	//preprocessed info
	private String seggedTitle="";
	private List<String> seggedPListOfContent=new ArrayList<String>();
	private List<String> keywords=new ArrayList<String>();
	private List<String> persons=new ArrayList<String>();
	private List<String> locations=new ArrayList<String>();
	private List<String> organizations=new ArrayList<String>();
	public int wordCountsOfTitle;
	public int wordCountsOfContent;
	
	public Event event=null; // event: 5W1H
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUploadedTime() {
		return uploadedTime;
	}
	public void setUploadedTime(String uploadedTime) {
		this.uploadedTime = uploadedTime;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getOriginated() {
		return Originated;
	}
	public void setOriginated(String originated) {
		Originated = originated;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getJournalist() {
		return journalist;
	}
	public void setJournalist(String journalist) {
		this.journalist = journalist;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getSeggedTitle() {
		return seggedTitle;
	}
	public void setSeggedTitle(String seggedTitle) {
		this.seggedTitle = seggedTitle;
	}
	public List<String> getSeggedPListOfContent() {
		return seggedPListOfContent;
	}
	public void setSeggedPListOfContent(List<String> seggedPListOfContent) {
		this.seggedPListOfContent = seggedPListOfContent;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	public List<String> getPersons() {
		return persons;
	}
	public void setPersons(List<String> persons) {
		this.persons = persons;
	}
	public List<String> getLocations() {
		return locations;
	}
	public void setLocations(List<String> locations) {
		this.locations = locations;
	}
	public List<String> getOrganizations() {
		return organizations;
	}
	public void setOrganizations(List<String> organizations) {
		this.organizations = organizations;
	}
	
	
}


