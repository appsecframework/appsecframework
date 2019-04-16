package appsecframework;

import java.util.ArrayList;
import java.util.List;

public class DojoProduct {
	// Required
	private String name;
	private String description;
	private String platform;
	
	private int id;
	private int findings_count;
	private List<String> tags;
	private List<String> product_meta;
	private String created;
	private int prod_numeric_grade;
	private String business_criticality;
	private String lifecycle;
	private String origin;
	private int user_records;
	private String revenue;
	private boolean external_audience;
	private boolean internet_accessible;
	private int product_manager;
	private int technical_contact;
	private int team_manager;
	private int prod_type;
	private List<Integer> authorized_users;
	private List<Integer> regulations;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFindings_count() {
		return findings_count;
	}
	public void setFindings_count(int findings_count) {
		this.findings_count = findings_count;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public List<String> getProduct_meta() {
		return product_meta;
	}
	public void setProduct_meta(List<String> product_meta) {
		this.product_meta = product_meta;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public int getProd_numeric_grade() {
		return prod_numeric_grade;
	}
	public void setProd_numeric_grade(int prod_numeric_grade) {
		this.prod_numeric_grade = prod_numeric_grade;
	}
	public String getBusiness_criticality() {
		return business_criticality;
	}
	public void setBusiness_criticality(String business_criticality) {
		this.business_criticality = business_criticality;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getLifecycle() {
		return lifecycle;
	}
	public void setLifecycle(String lifecycle) {
		this.lifecycle = lifecycle;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public int getUser_records() {
		return user_records;
	}
	public void setUser_records(int user_records) {
		this.user_records = user_records;
	}
	public String getRevenue() {
		return revenue;
	}
	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}
	public boolean isExternal_audience() {
		return external_audience;
	}
	public void setExternal_audience(boolean external_audience) {
		this.external_audience = external_audience;
	}
	public boolean isInternet_accessible() {
		return internet_accessible;
	}
	public void setInternet_accessible(boolean internet_accessible) {
		this.internet_accessible = internet_accessible;
	}
	public int getProduct_manager() {
		return product_manager;
	}
	public void setProduct_manager(int product_manager) {
		this.product_manager = product_manager;
	}
	public int getTechnical_contact() {
		return technical_contact;
	}
	public void setTechnical_contact(int technical_contact) {
		this.technical_contact = technical_contact;
	}
	public int getTeam_manager() {
		return team_manager;
	}
	public void setTeam_manager(int team_manager) {
		this.team_manager = team_manager;
	}
	public int getProd_type() {
		return prod_type;
	}
	public void setProd_type(int prod_type) {
		this.prod_type = prod_type;
	}
	public List<Integer> getAuthorized_users() {
		return authorized_users;
	}
	public void setAuthorized_users(List<Integer> authorized_users) {
		this.authorized_users = authorized_users;
	}
	public List<Integer> getRegulations() {
		return regulations;
	}
	public void setRegulations(List<Integer> regulations) {
		this.regulations = regulations;
	}
}
