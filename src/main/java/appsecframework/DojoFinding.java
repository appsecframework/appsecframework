package appsecframework;

import java.util.List;

import javax.swing.JTextArea;

public class DojoFinding {
	private String severity_justification;
	private String line_number;
	private String product; //related
	private String steps_to_reproduce;
	private String reporter; //related
	private Boolean under_review;
	private String sourcefile;
	private Boolean static_finding;
	private int thread_id;
	private String mitigated; //datetime
	private String references;
	private Boolean dynamic_finding;
	private String date;
	private int scanner_confidence;
	private int line;
	private Boolean active;
	private String payload;
	private Boolean under_defect_review;
	private String description;
	private String hash_code;
	private String impact;
	private Boolean false_p;
	private Boolean verified;
	private String severity;
	private Boolean is_template;
	private String title;
	private String url;
	private String finding_url;
	private String engagement; //related
	private Boolean duplicate;
	private String param;
	private int id;
	private String sourcefilepath;
	private String mitigation;
	private String created; //datetime
	private String numerical_severity;
	private String test; //related
	private Boolean out_of_scope;
	private String last_reviewed; //datetime
	private int cwe;
	private String file_path;
	private String resource_uri;
	
	public String getSeverity_justification() {
		return severity_justification;
	}
	public void setSeverity_justification(String severity_justification) {
		this.severity_justification = severity_justification;
	}
	public String getLine_number() {
		return line_number;
	}
	public void setLine_number(String line_number) {
		this.line_number = line_number;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getSteps_to_reproduce() {
		return steps_to_reproduce;
	}
	public void setSteps_to_reproduce(String steps_to_reproduce) {
		this.steps_to_reproduce = steps_to_reproduce;
	}
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public Boolean getUnder_review() {
		return under_review;
	}
	public void setUnder_review(Boolean under_review) {
		this.under_review = under_review;
	}
	public String getSourcefile() {
		return sourcefile;
	}
	public void setSourcefile(String sourcefile) {
		this.sourcefile = sourcefile;
	}
	public Boolean getStatic_finding() {
		return static_finding;
	}
	public void setStatic_finding(Boolean static_finding) {
		this.static_finding = static_finding;
	}
	public int getThread_id() {
		return thread_id;
	}
	public void setThread_id(int thread_id) {
		this.thread_id = thread_id;
	}
	public String getMitigated() {
		return mitigated;
	}
	public void setMitigated(String mitigated) {
		this.mitigated = mitigated;
	}
	public String getReferences() {
		return references;
	}
	public void setReferences(String references) {
		this.references = references;
	}
	public Boolean getDynamic_finding() {
		return dynamic_finding;
	}
	public void setDynamic_finding(Boolean dynamic_finding) {
		this.dynamic_finding = dynamic_finding;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getScanner_confidence() {
		return scanner_confidence;
	}
	public void setScanner_confidence(int scanner_confidence) {
		this.scanner_confidence = scanner_confidence;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public Boolean getUnder_defect_review() {
		return under_defect_review;
	}
	public void setUnder_defect_review(Boolean under_defect_review) {
		this.under_defect_review = under_defect_review;
	}
	public String getHash_code() {
		return hash_code;
	}
	public void setHash_code(String hash_code) {
		this.hash_code = hash_code;
	}
	public String getImpact() {
		return impact;
	}
	public void setImpact(String impact) {
		this.impact = impact;
	}
	public Boolean getFalse_p() {
		return false_p;
	}
	public void setFalse_p(Boolean false_p) {
		this.false_p = false_p;
	}
	public Boolean getVerified() {
		return verified;
	}
	public void setVerified(Boolean verified) {
		this.verified = verified;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public Boolean getIs_template() {
		return is_template;
	}
	public void setIs_template(Boolean is_template) {
		this.is_template = is_template;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEngagement() {
		return engagement;
	}
	public void setEngagement(String engagement) {
		this.engagement = engagement;
	}
	public Boolean getDuplicate() {
		return duplicate;
	}
	public void setDuplicate(Boolean duplicate) {
		this.duplicate = duplicate;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSourcefilepath() {
		return sourcefilepath;
	}
	public void setSourcefilepath(String sourcefilepath) {
		this.sourcefilepath = sourcefilepath;
	}
	public String getMitigation() {
		return mitigation;
	}
	public void setMitigation(String mitigation) {
		this.mitigation = mitigation;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getNumerical_severity() {
		return numerical_severity;
	}
	public void setNumerical_severity(String numerical_severity) {
		this.numerical_severity = numerical_severity;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public Boolean getOut_of_scope() {
		return out_of_scope;
	}
	public void setOut_of_scope(Boolean out_of_scope) {
		this.out_of_scope = out_of_scope;
	}
	public String getLast_reviewed() {
		return last_reviewed;
	}
	public void setLast_reviewed(String last_reviewed) {
		this.last_reviewed = last_reviewed;
	}
	public int getCwe() {
		return cwe;
	}
	public void setCwe(int cwe) {
		this.cwe = cwe;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getResource_uri() {
		return resource_uri;
	}
	public void setResource_uri(String resource_uri) {
		this.resource_uri = resource_uri;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFinding_url() {
		return finding_url;
	}
	public void setFinding_url(String finding_url) {
		this.finding_url = finding_url;
	}
	
}