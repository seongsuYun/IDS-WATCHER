package com.uwiseone.swp;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorksBotV2Test {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 생성자 : 설정파일 로드
	 * @throws IOException
	 */
	public WorksBotV2Test() {}
	
	/**
	 * httpEntity 설정정보
	 * @return
	 */
	private RequestConfig getBuilder() {
		Builder builder = RequestConfig.custom();
		builder.setConnectionRequestTimeout(3000);
		builder.setSocketTimeout(3000);
		return builder.build();
	}
	
	/**
	 * 공통 호출 API
	 * @param httpEntity
	 * @param requestBody
	 */
	private void callApi( HttpEntityEnclosingRequestBase httpEntity, String requestBody) {
		httpEntity.setConfig(getBuilder());
		httpEntity.setHeader("Authorization", "Bearer AAAA7En07uY9Ac6hkqyqiIa8YTUpJ/hkqMtlCVCf65/vdnzh47HpnxJnKuId+YWbLRCntUhJJRW0BrVhIx9XEBUwiXyXujXgJBHZUaDPikq+2o1gngrppcGyYEUoMfW/Vruk6VOGjHyYdOt6IVNwZ7VQADv9jQfDScoAKoV3CrySn+YBmgdLZ3+H7Qq7nqeLv8HYBV3Fm+zdkCBsZCRftjR+UBiM31NZoswWmE17Xy/CHWhyGCEN5tPdtmNxHBePjmCn9z/xg1Ro85zB6nxez9Bg4azsCTgVbHioArrZ9pyhUg+n+5Ycdb5oRvbbbOb8FW0B2w==");
		httpEntity.setHeader("consumerKey", "YAaUo3UEqHfbs7icVWoQ");
		httpEntity.addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		httpEntity.setEntity(new StringEntity(requestBody, "UTF-8"));

		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;

		try {
			httpclient = HttpClients.createDefault();
			response = httpclient.execute(httpEntity);
			HttpEntity entity = response.getEntity();
			
			logger.info("전송결과 : " + response.getStatusLine());
			logger.info("결과내용 : " + EntityUtils.toString(response.getEntity()));
            EntityUtils.consume(entity);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
				response.close();
			} catch(Exception e) {}
		}
	}
	
	/**
	 * 봇메세지 추가
	 * @param requestUrl
	 * @param requestBody
	 */
	public void sendBotMessage( String requestUrl, String requestBody) {	
		callApi(new HttpPost(requestUrl), requestBody);
	}
}
