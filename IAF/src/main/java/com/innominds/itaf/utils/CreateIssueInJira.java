package com.innominds.itaf.utils;

import javax.security.sasl.AuthenticationException;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;

/**
 * This class creates JSON object and create issue in JIRA through restful
 * service
 * 
 * @author Pradeep Reddy K
 *
 */
public class CreateIssueInJira {

	JsonParameters jsonParams = new JsonParameters();

	/**
	 * Forms JSON object for restful API input
	 * 
	 * @param failedMethodName
	 * @param failureMessage
	 * @throws JSONException
	 */
	public void formJson(String failedMethodName, String failureMessage) throws JSONException {

		JSONObject objFields = new JSONObject();

		JSONObject objProjID = new JSONObject();
		objProjID.put("id", jsonParams.getProjectID());

		objFields.put("project", objProjID);

		objFields.put("summary", failedMethodName);

		JSONObject objAssigneName = new JSONObject();
		objAssigneName.put("name", jsonParams.getAssignee());

		objFields.put("assignee", objAssigneName);

		JSONObject objReporterName = new JSONObject();
		objReporterName.put("name", jsonParams.getReporter());

/*		objFields.put("reporter", objReporterName);
*/
		objFields.put("description", failureMessage);

		JSONObject objIssuType = new JSONObject();
		objIssuType.put("id", jsonParams.getIssueType());

		objFields.put("issuetype", objIssuType);

		JSONObject obj = new JSONObject();
		obj.put("fields", objFields);

//		System.out.println(obj);

//		createIssueInJira(obj.toString());

	}

	/**
	 * Creates Issue in JIRA through Restful API
	 * 
	 * @param string
	 *            - json object
	 */
	private void createIssueInJira(String string) {
		try {
			String name = jsonParams.getUserName();
			String password = jsonParams.getPassWord();
			String authString = name + ":" + password;
			System.out.println("auth string: " + authString);
			byte[] authEncBytes = Base64.encode(authString.getBytes());
			String auth = new String(authEncBytes);
			String data = string;
			Client client = Client.create();
			String url = jsonParams.getURL();
			WebResource webResource = client.resource(url);
			ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
					.accept("application/json").post(ClientResponse.class, data);
			int statusCode = response.getStatus();
			if (statusCode == 401) {
				throw new AuthenticationException("Invalid Username or Password");
			}
			String resonse = response.getEntity(String.class);

			System.out.println(resonse);
			
			
			


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}