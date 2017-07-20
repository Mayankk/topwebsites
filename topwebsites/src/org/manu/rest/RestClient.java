package org.manu.rest;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.manu.exception.TopSiteException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestClient {
	private static ObjectMapper oMapper = new ObjectMapper();
	private static Logger logger =
			LogManager.getLogger(RestClient.class);
	
	public static String makeWebServiceCall(RestMethod method,
											String url,
											String params) 
							throws TopSiteException
											{
		return makeWebServiceCall(method,url,params,
				new TypeReference<String>(){});
	}

	public static <T> T makeWebServiceCall(RestMethod method,
										   String url,
										   String params,
										   TypeReference<T> responseClass) 
										throws TopSiteException{
		logger.info("makeWebServiceCall method: " + method
				+ ", url: "+url + ",responseClass: "+responseClass);
		Client client = Client.create();
		client.setConnectTimeout(5000);

		WebResource webResource = 
				client.resource(url);

		try{
			ClientResponse response = null;
			switch(method){
			case GET:
				response= 
						webResource.accept("application/json")
		                   .get(ClientResponse.class);
				break;
			}
	
			logger.info("response status : " + response.getStatus());
			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
	
			String output = response.getEntity(String.class);
			
			logger.debug("output: " + output);
			return oMapper.readValue(output,responseClass);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new TopSiteException(e);
		}
	}
}
