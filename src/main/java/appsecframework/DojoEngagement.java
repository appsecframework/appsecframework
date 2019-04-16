package appsecframework;

import java.util.List;

public class DojoEngagement {
	private int id;
	private List<String> tags;
	private String name;
	private String description;
	private String version;
	private String first_contacted;
	private String target_start;
	private String target_end;
	private String reason;
	private String updated;
	private String created;
	private boolean active;
	private String tracker;
	private String test_strategy;
	private boolean threat_modle;
	private boolean api_test;
	private boolean pen_test;
	private boolean check_list;
	private String status;
	private String progress;
	private String tmodel_path;
	private String risk_path;
	private boolean done_testing;
	private String engagement_type;
	private String build_id;
	private String commit_hash;
	private String branch_tag;
	private String source_code_management_uri;
	private String eng_type;
	private int lead;
	private String requester;
	private String preset;
	private String report_type;
	private int product;					// Product Id
	private String build_server;
	private String source_code_management_server;
	private String orchestration_engine;
	private List<String> risk_acceptance;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getFirst_contacted() {
		return first_contacted;
	}
	public void setFirst_contacted(String first_contacted) {
		this.first_contacted = first_contacted;
	}
	public String getTarget_start() {
		return target_start;
	}
	public void setTarget_start(String target_start) {
		this.target_start = target_start;
	}
	public String getTarget_end() {
		return target_end;
	}
	public void setTarget_end(String target_end) {
		this.target_end = target_end;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getTracker() {
		return tracker;
	}
	public void setTracker(String tracker) {
		this.tracker = tracker;
	}
	public String getTest_strategy() {
		return test_strategy;
	}
	public void setTest_strategy(String test_strategy) {
		this.test_strategy = test_strategy;
	}
	public boolean isThreat_modle() {
		return threat_modle;
	}
	public void setThreat_modle(boolean threat_modle) {
		this.threat_modle = threat_modle;
	}
	public boolean isApi_test() {
		return api_test;
	}
	public void setApi_test(boolean api_test) {
		this.api_test = api_test;
	}
	public boolean isPen_test() {
		return pen_test;
	}
	public void setPen_test(boolean pen_test) {
		this.pen_test = pen_test;
	}
	public boolean isCheck_list() {
		return check_list;
	}
	public void setCheck_list(boolean check_list) {
		this.check_list = check_list;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getTmodel_path() {
		return tmodel_path;
	}
	public void setTmodel_path(String tmodel_path) {
		this.tmodel_path = tmodel_path;
	}
	public String getRisk_path() {
		return risk_path;
	}
	public void setRisk_path(String risk_path) {
		this.risk_path = risk_path;
	}
	public boolean isDone_testing() {
		return done_testing;
	}
	public void setDone_testing(boolean done_testing) {
		this.done_testing = done_testing;
	}
	public String getEngagement_type() {
		return engagement_type;
	}
	public void setEngagement_type(String engagement_type) {
		this.engagement_type = engagement_type;
	}
	public String getBuild_id() {
		return build_id;
	}
	public void setBuild_id(String build_id) {
		this.build_id = build_id;
	}
	public String getCommit_hash() {
		return commit_hash;
	}
	public void setCommit_hash(String commit_hash) {
		this.commit_hash = commit_hash;
	}
	public String getBranch_tag() {
		return branch_tag;
	}
	public void setBranch_tag(String branch_tag) {
		this.branch_tag = branch_tag;
	}
	public String getSource_code_management_uri() {
		return source_code_management_uri;
	}
	public void setSource_code_management_uri(String source_code_management_uri) {
		this.source_code_management_uri = source_code_management_uri;
	}
	public String getEng_type() {
		return eng_type;
	}
	public void setEng_type(String eng_type) {
		this.eng_type = eng_type;
	}
	public int getLead() {
		return lead;
	}
	public void setLead(int lead) {
		this.lead = lead;
	}
	public String getRequester() {
		return requester;
	}
	public void setRequester(String requester) {
		this.requester = requester;
	}
	public String getPreset() {
		return preset;
	}
	public void setPreset(String preset) {
		this.preset = preset;
	}
	public String getReport_type() {
		return report_type;
	}
	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}
	public int getProduct() {
		return product;
	}
	public void setProduct(int product) {
		this.product = product;
	}
	public String getBuild_server() {
		return build_server;
	}
	public void setBuild_server(String build_server) {
		this.build_server = build_server;
	}
	public String getSource_code_management_server() {
		return source_code_management_server;
	}
	public void setSource_code_management_server(String source_code_management_server) {
		this.source_code_management_server = source_code_management_server;
	}
	public String getOrchestration_engine() {
		return orchestration_engine;
	}
	public void setOrchestration_engine(String orchestration_engine) {
		this.orchestration_engine = orchestration_engine;
	}
	public List<String> getRisk_acceptance() {
		return risk_acceptance;
	}
	public void setRisk_acceptance(List<String> risk_acceptance) {
		this.risk_acceptance = risk_acceptance;
	}
}
