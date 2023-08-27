package com.infina.lifotrade.services.common;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;

@ManagedBean
@SessionScoped
public class LifotradeApi<E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1869300692440745439L;

	public static String token = "";

	private final ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	private CloseableHttpClient createHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom()
				.setDefaultHeaders(
						Collections.singletonList(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json")))
				.build();
		return httpClient;
	}

	public Result get(String url) {
		String apiUrl = getApiUrlFromEnv();
		CloseableHttpClient httpClient = createHttpClient();
		// GET isteği
		HttpGet httpGet = new HttpGet(apiUrl + url);

		if (LifotradeApi.getToken() != "") {
			httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + LifotradeApi.getToken());
		}

		CloseableHttpResponse getResponse = executeGetRequest(httpClient, httpGet);
		Result result = isError(getResponse);
		if (!result.isSuccess()) {
			closeHttpClient(httpClient);
			return result;
		} else {
			String getResponseBody = getResponseAsString(getResponse.getEntity());
			result.setData(getResponseBody);
			closeHttpClient(httpClient);
			return result;
		}

	}

	public <T> Result post(String url, T body) {
		String apiUrl = getApiUrlFromEnv();
        System.out.println(apiUrl);
		CloseableHttpClient httpClient = createHttpClient();

		HttpPost httpPost = new HttpPost(apiUrl + url);

		if (LifotradeApi.getToken() != "") {
			httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + LifotradeApi.getToken());
		}

		StringEntity requestEntity = createStringEntity(body);
		httpPost.setEntity(requestEntity);

		CloseableHttpResponse postResponse = executePostRequest(httpClient, httpPost);
		Result result = isError(postResponse);
		if (!result.isSuccess()) {
			closeHttpClient(httpClient);
			return result;
		} else {
			String postResponseBody = getResponseAsString(postResponse.getEntity());
			result.setData(postResponseBody);
			closeHttpClient(httpClient);
			return result;
		}
	}

	public <T> Result put(String url, T body) {
		String apiUrl = getApiUrlFromEnv();

		CloseableHttpClient httpClient = createHttpClient();

		HttpPut httpPut = new HttpPut(apiUrl + url);

		if (LifotradeApi.getToken() != "") {
			httpPut.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + LifotradeApi.getToken());
		}

		StringEntity requestEntity = createStringEntity(body);
		httpPut.setEntity(requestEntity);

		CloseableHttpResponse putResponse = executePutRequest(httpClient, httpPut);
		Result result = isError(putResponse);
		if (!result.isSuccess()) {
			closeHttpClient(httpClient);
			return result;
		} else {
			String putResponseBody = getResponseAsString(putResponse.getEntity());
			result.setData(putResponseBody);
			closeHttpClient(httpClient);
			return result;
		}
	}

	public Result delete(String url) {
		String apiUrl = getApiUrlFromEnv();

		CloseableHttpClient httpClient = createHttpClient();

		HttpDelete httpDelete = new HttpDelete(apiUrl + url);

		if (LifotradeApi.getToken() != "") {
			httpDelete.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + LifotradeApi.getToken());
		}

		CloseableHttpResponse deleteResponse = executeDeleteRequest(httpClient, httpDelete);
		Result result = isError(deleteResponse);
		if (!result.isSuccess()) {
			closeHttpClient(httpClient);
			return result;
		} else {
			String deleteResponseBody = getResponseAsString(deleteResponse.getEntity());
			result.setData(deleteResponseBody);
			closeHttpClient(httpClient);
			return result;
		}
	}

	private String getApiUrlFromEnv() {
		Dotenv dotenv = Dotenv.load();

		// Çevresel değişkenlere erişim
		String apiUrl = dotenv.get("BACKEND_API_URL");
		return apiUrl;
	}

	private <T> StringEntity createStringEntity(T body) {
		try {
			return new StringEntity(objectMapper.writeValueAsString(body), ContentType.APPLICATION_JSON);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Body deki nesne dönüştürülemedi.", e);
		}
	}

	private CloseableHttpResponse executePostRequest(CloseableHttpClient httpClient, HttpPost httpPost) {
		try {
			return httpClient.execute(httpPost);
		} catch (IOException e) {
			throw new RuntimeException("POST isteğinde bir hata oluştu.", e);
		}
	}

	private CloseableHttpResponse executeGetRequest(CloseableHttpClient httpClient, HttpGet httpGet) {
		try {
			return httpClient.execute(httpGet);
		} catch (IOException e) {
			throw new RuntimeException("GET isteğinde bir hata oluştu.", e);
		}
	}

	private CloseableHttpResponse executePutRequest(CloseableHttpClient httpClient, HttpPut httpPut) {
		try {
			return httpClient.execute(httpPut);
		} catch (IOException e) {
			throw new RuntimeException("PUT isteğinde bir hata oluştu.", e);
		}
	}

	private CloseableHttpResponse executeDeleteRequest(CloseableHttpClient httpClient, HttpDelete httpDelete) {
		try {
			return httpClient.execute(httpDelete);
		} catch (IOException e) {
			throw new RuntimeException("DELETE isteğinde bir hata oluştu.", e);
		}
	}

	private String getResponseAsString(HttpEntity responseData) {
		try {
			return EntityUtils.toString(responseData, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException("Response stringe çevrilirken bir hata ile karşılaşıldı.", e);
		}
	}

	private void closeHttpClient(CloseableHttpClient httpClient) {
		try {
			httpClient.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing HttpClient", e);
		}
	}

	private Result isError(CloseableHttpResponse response) {
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode >= 400) {
			try {
				return new Result(false, EntityUtils.toString(response.getEntity()), statusCode);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new Result(true, null, statusCode);
	}

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		LifotradeApi.token = token;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
