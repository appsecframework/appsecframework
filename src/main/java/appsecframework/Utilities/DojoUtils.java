package appsecframework.Utilities;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import appsecframework.DojoEngagement;
import appsecframework.DojoFinding;
import appsecframework.DojoProduct;
import appsecframework.MainController;
import appsecframework.Project;
import appsecframework.ScanEvent;
import appsecframework.ScanResult;


public class DojoUtils {
	
	// Default, if not specified
	private static String dojoURL = "http://localhost:8000/";
	private static String dojoApiAuthHeader;
	
	public DojoUtils(String _dojoURL) {
		dojoURL = _dojoURL;
	}
	
	public DojoUtils() {
	}
	
	/**
	 * Get a List of all DojoProducts from DefectDojo
	 * @return List of all DojoProducts
	 */
	public List<DojoProduct> getDojoProductList() {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(dojoURL + "api/v2/products/");
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");
		httpGet.setHeader("Authorization", dojoApiAuthHeader);
		CloseableHttpResponse response;
		try {
			response = client.execute(httpGet);
			List<DojoProduct> dojoProductList = null;
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				String responseBody = EntityUtils.toString(response.getEntity());
				// Convert response JSON to DojoProduct ArrayList
				Gson gson = new Gson();
				JsonObject jsonObject = new JsonParser().parse(responseBody).getAsJsonObject();
				Type datasetListType = new TypeToken<Collection<DojoProduct>>() {}.getType();
				dojoProductList = gson.fromJson(jsonObject.get("results"), datasetListType);
			}
			else {
				System.out.println("Failed to get DojoProductList. Status code: " + statusCode);
			}
			client.close();
			return dojoProductList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Get a DojoProduct object using dojoProductId
	 * @param dojoProductId Id of the DojoProduct you want to retrieve
	 * @param dojoProductList List of DojoProduct objects
	 * @return DojoProduct object with the id equal to the value of dojoProductId, null if not available
	 */
	public DojoProduct getDojoProductFromId(int productId) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(dojoURL + "api/v2/products/" + productId);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");
		httpGet.setHeader("Authorization", dojoApiAuthHeader);
		CloseableHttpResponse response;
		DojoProduct dojoProduct = null;
		try {
			response = client.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				String responseBody = EntityUtils.toString(response.getEntity());
				Gson gson = new Gson();
				JsonObject jsonObject = new JsonParser().parse(responseBody).getAsJsonObject();
				Type dataType = new TypeToken<DojoProduct>() {}.getType();
				dojoProduct = gson.fromJson(jsonObject, dataType);
			}
			else {
				String responseBody = EntityUtils.toString(response.getEntity());
				System.out.println("Failed to get DojoProduct. Status code: " + statusCode);
				System.out.println(responseBody);
			}
			client.close();
			return dojoProduct;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Add new product to DefectDojo
	 * @param dojoProduct DojoProduct object to be added to DefectDojo
	 */
	public int addDojoProduct(DojoProduct dojoProduct) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(dojoURL + "api/v2/products/");
	    httpPost.setHeader("Authorization", dojoApiAuthHeader);
		HttpEntity entity = MultipartEntityBuilder
				.create()
				.addTextBody("name", dojoProduct.getName())
				.addTextBody("description", dojoProduct.getDescription())
				.addTextBody("prod_type", "1")
				.addTextBody("platform", "")
				.build();
		try {
			httpPost.setEntity(entity);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		CloseableHttpResponse response;
		try {
			int productId = 0;
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200 || statusCode == 201) {
				String responseBody = EntityUtils.toString(response.getEntity());
				JsonObject jsonObject = new JsonParser().parse(responseBody).getAsJsonObject();
				productId = jsonObject.get("id").getAsInt();
				}
			else {
				String responseBody = EntityUtils.toString(response.getEntity());
				System.out.println("Failed to add DojoProduct. Status code: " + statusCode);
				System.out.println(responseBody);
				productId = -1;
			}
			httpClient.close();
			return productId;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Get a List of all DojoEngagements from DefectDojo
	 * @return List of all DojoEngagements
	 */
	public List<DojoEngagement> getDojoEngagementList() {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(dojoURL + "api/v2/engagements/");
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");
		httpGet.setHeader("Authorization", dojoApiAuthHeader);
		CloseableHttpResponse response;
		try {
			response = client.execute(httpGet);
			List<DojoEngagement> dojoEngagementList = null;
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				String responseBody = EntityUtils.toString(response.getEntity());
				// Convert response JSON to DojoProduct ArrayList
				Gson gson = new Gson();
				JsonObject jsonObject = new JsonParser().parse(responseBody).getAsJsonObject();
				Type datasetListType = new TypeToken<Collection<DojoEngagement>>() {}.getType();
				dojoEngagementList = gson.fromJson(jsonObject.get("results"), datasetListType);
			}
			else {
				String responseBody = EntityUtils.toString(response.getEntity());
				System.out.println("Failed to get DojoEngagementList. Status code: " + statusCode);
				System.out.println(responseBody);
			}
			
			client.close();
			return dojoEngagementList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public DojoEngagement getDojoEngagement(int engagementId) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(dojoURL + "api/v2/engagements/" + engagementId);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");
		httpGet.setHeader("Authorization", dojoApiAuthHeader);
		CloseableHttpResponse response;
		DojoEngagement dojoEngagement = null;
		try {
			response = client.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				String responseBody = EntityUtils.toString(response.getEntity());
				Gson gson = new Gson();
				JsonObject jsonObject = new JsonParser().parse(responseBody).getAsJsonObject();
				Type dataType = new TypeToken<DojoEngagement>() {}.getType();
				dojoEngagement = gson.fromJson(jsonObject, dataType);
			}
			else {
				String responseBody = EntityUtils.toString(response.getEntity());
				System.out.println("Failed to get DojoEngagement. Status code: " + statusCode);
				System.out.println(responseBody);
			}
			client.close();
			return dojoEngagement;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public int addDojoEngagement(DojoEngagement dojoEngagement) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(dojoURL + "api/v2/engagements/");
	    httpPost.setHeader("Authorization", dojoApiAuthHeader);
		HttpEntity entity = MultipartEntityBuilder
				.create()
				.addTextBody("product", dojoEngagement.getProduct() + "")
				.addTextBody("name", dojoEngagement.getName())
				.addTextBody("description", dojoEngagement.getDescription())
				.addTextBody("target_start", dojoEngagement.getTarget_start())
				.addTextBody("target_end", dojoEngagement.getTarget_end())
				.addTextBody("lead", "1")
				.addTextBody("status", dojoEngagement.getStatus())
				.build();
		try {
			httpPost.setEntity(entity);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		CloseableHttpResponse response;
		try {
			int engagementId;
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200 || statusCode == 201) {
				String responseBody = EntityUtils.toString(response.getEntity());
				JsonObject jsonObject = new JsonParser().parse(responseBody).getAsJsonObject();
				engagementId = jsonObject.get("id").getAsInt();
			}
			else {
				String responseBody = EntityUtils.toString(response.getEntity());
				System.out.println("Failed to add DojoEngagement. Status code: " + statusCode);
				System.out.println(responseBody);
				engagementId =  -1;
			}
			httpClient.close();
			return engagementId;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public List<DojoFinding> getDojoFindingList() {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(dojoURL + "api/v2/findings/?limit=1000");
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");
		httpGet.setHeader("Authorization", dojoApiAuthHeader);
		CloseableHttpResponse response;
		try {
			
			response = client.execute(httpGet);
			List<DojoFinding> dojoFindingList = null;
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				String responseBody = EntityUtils.toString(response.getEntity());
				Gson gson = new Gson();
				JsonObject jsonObject = new JsonParser().parse(responseBody).getAsJsonObject();
				Type datasetListType = new TypeToken<Collection<DojoFinding>>() {}.getType();
				dojoFindingList = gson.fromJson(jsonObject.get("results"), datasetListType);
			}
			else {
				String responseBody = EntityUtils.toString(response.getEntity());
				System.out.println("Failed to get DojoFindingList. Status code: " + statusCode);
				System.out.println(responseBody);
			}
			client.close();
			return dojoFindingList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<DojoFinding> getDojoFindingList(String productName) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(dojoURL + "api/v2/findings/?limit=1000");
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");
		httpGet.setHeader("Authorization", dojoApiAuthHeader);
		CloseableHttpResponse response;
		try {
			response = client.execute(httpGet);
			List<DojoFinding> dojoFindingList = null;
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				String responseBody = EntityUtils.toString(response.getEntity());
				Gson gson = new Gson();
				JsonObject jsonObject = new JsonParser().parse(responseBody).getAsJsonObject();
				Type datasetListType = new TypeToken<Collection<DojoFinding>>() {}.getType();
				dojoFindingList = gson.fromJson(jsonObject.get("results"), datasetListType);
			}
			else {
				String responseBody = EntityUtils.toString(response.getEntity());
				System.out.println("Failed to get DojoFindingList. Status code: " + statusCode);
				System.out.println(responseBody);
			}
			
			client.close();
			return dojoFindingList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<DojoFinding> getHighRisk(List<DojoFinding> findings) {
		List<DojoFinding> highRisk = new ArrayList<DojoFinding>();
		
		for (DojoFinding f : findings) {
			if (f.getSeverity().equals("High") || f.getSeverity().equals("high")) highRisk.add(f);
		}

		return highRisk;
	}
	
	public static List<DojoFinding> getMediumRisk(List<DojoFinding> findings) {
		List<DojoFinding> mediumRisk = new ArrayList<DojoFinding>();
		
		for (DojoFinding f : findings) {
			if (f.getSeverity().equals("Medium") || f.getSeverity().equals("medium")) mediumRisk.add(f);
		}

		return mediumRisk;
	}
	
	public static List<DojoFinding> getLowRisk(List<DojoFinding> findings) {
		List<DojoFinding> lowRisk = new ArrayList<DojoFinding>();
		
		for (DojoFinding f : findings) {
			if (f.getSeverity().equals("Low") || f.getSeverity().equals("low")) lowRisk.add(f);
		}

		return lowRisk;
	}
	
	public static List<DojoFinding> getInfoRisk(List<DojoFinding> findings) {
		List<DojoFinding> infoRisk = new ArrayList<DojoFinding>();
		
		for (DojoFinding f : findings) {
			if (f.getSeverity().equals("Info") || f.getSeverity().equals("info")) infoRisk.add(f);
		}

		return infoRisk;
	}
	
	/**
	 * Import scan result file to DefectDojo
	 * @param scanDate
	 * @param filePath
	 * @param fileName
	 * @param scanType
	 * @param lead
	 * @param engagement
	 */
	public void importScanResult(Project project, ScanEvent scanEvent) {
		long startTime = System.currentTimeMillis();
		
		List<DojoProduct> dojoProductList = this.getDojoProductList();
		int lead = 1; // UserId of Engagement Creator (1 = admin)
		
		// Check if product available, create new if not.
		int productId = getDojoProductId(scanEvent.getProjectName(), dojoProductList);
		if (productId == -1) {
			DojoProduct product = new DojoProduct();
			product.setName(scanEvent.getProjectName());
			product.setDescription(scanEvent.getProjectName());
			product.setPlatform(scanEvent.getProjectPlatform());
			addDojoProduct(product);
			productId = getDojoProductId(scanEvent.getProjectName(), dojoProductList);
		}

		// Check if engagement available, create new if not
		int engagementId = scanEvent.getEngagementId();
		if (engagementId == 0) {
			DojoEngagement dojoEngagement = new DojoEngagement();
			dojoEngagement.setProduct(productId);
			dojoEngagement.setName(scanEvent.getProjectName() + " - AppSecPipelineFramework");
			dojoEngagement.setDescription("Scanning results from Application Security Pipeline Framework");
			dojoEngagement.setTarget_start(scanEvent.getDojoStartDate());
			dojoEngagement.setTarget_end(scanEvent.getDojoFinishDate());
			dojoEngagement.setLead(1);
			dojoEngagement.setStatus("Completed");
			engagementId = addDojoEngagement(dojoEngagement);
			scanEvent.setEngagementId(engagementId);
		}
		
		// POST each ScanResult to Dojo
		for (ScanResult scanResult : scanEvent.getScanResultList()) {
			String fileName = scanResult.getScanResultFileName();
			String filePath = scanResult.getScanResultDirectory() + fileName;
			String scanType = scanResult.getScanType();

			File reportFile = new File(filePath);
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(dojoURL + "api/v2/import-scan/");
		    httpPost.setHeader("Authorization", dojoApiAuthHeader);
			HttpEntity entity = MultipartEntityBuilder
					.create()
					// Default
					.addTextBody("minimum_severity", "Info")
					.addTextBody("verified", "false")
					.addTextBody("active", "true")
					// Required
					.addTextBody("scan_date", scanEvent.getDojoFinishDate())
					.addBinaryBody("file", reportFile, ContentType.create("application/octet-stream"), fileName)
					.addTextBody("scan_type", scanType)
					.addTextBody("lead", String.valueOf(lead))					// User ID		
					.addTextBody("engagement", String.valueOf(engagementId))	// Engagement ID
					.build();
			try {
				httpPost.setEntity(entity);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			CloseableHttpResponse response;
			try {
				response = httpClient.execute(httpPost);
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != 201) {
					String responseBody = EntityUtils.toString(response.getEntity());
					System.out.println("Failed to import scan result. Status code: " + statusCode);
					System.out.println(responseBody);
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MMM d yyyy, HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
		String formattedDate = formatter.format(date);
		
		project.setRecent(true); //TODO: to be used ins. success or not in DB
		project.setLastScanDate(formattedDate);
	    MainController.setDbFetched(false); //to let Dashboard fetch new result
	    
	    long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("Import result to DefectDojo " + project.getName() + ": " + elapsedTime + " ms");
	}
	
	/**
	 * Get DefectDojo access token with username and password and save the token to dojoApiAuthHeader
	 * @param dojoUser Username for logging in to DefectDojo
	 * @param dojoPass Password for logging in to DefectDojo
	 */
	public void authenticate(String dojoUser, String dojoPass) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(dojoURL + "api/v2/api-token-auth/");
	    httpPost.setHeader("Accept", "application/json");
	    httpPost.setHeader("Content-type", "application/json");
		
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("username", dojoUser);
		jsonObj.addProperty("password", dojoPass);
		String json = jsonObj.toString();
	    StringEntity entity;
		try {
			entity = new StringEntity(json);
			httpPost.setEntity(entity);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		CloseableHttpResponse response;
		try {
			response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			String responseBody = EntityUtils.toString(response.getEntity());
			if (statusCode == 200) {
				JsonObject obj = new JsonParser().parse(responseBody).getAsJsonObject();
				dojoApiAuthHeader = "Token " + obj.get("token").getAsString();
				System.out.println("Authentication Successful: Token received!");
			}
			else {
				System.out.println("Authentication Failed: " + responseBody);
			}
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Find DojoProduct within productList with the name equal to the value of productName
	 * @param productName Name of DojoProduct to get Id from
	 * @param productList List of DojoProduct
	 * @return Id of DojoProduct with the name equal to the value of productName, return -1 if not found
	 */
	public static int getDojoProductId(String productName, List<DojoProduct> productList) {
		for (DojoProduct p : productList) {
			if (p.getName().equals(productName)) {
				return p.getId();
			}
		}
		return -1;
	}
	
	public String getManageLink(DojoProduct dojoProduct) {
		return dojoURL + "product/" + dojoProduct.getId() + "/finding/all";
	}
	
	public String getDownloadLink(DojoProduct dojoProduct, List<DojoEngagement> dojoEngagementList) {
		for(int i = dojoEngagementList.size() ; i >= 0 ; i --) {
			if(dojoProduct.getId() == (int)(dojoEngagementList.get(i).getProduct())) {
				return dojoURL + "engagement/" + dojoEngagementList.get(i).getId() + "/report";
			}
		}
		return null;
	}
	
}
