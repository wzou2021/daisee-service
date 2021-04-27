package au.com.gsn.daisee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import au.com.gsn.daisee.model.TokenResponse;

@Service
public class SecurityService {
	
	@Value("${token.api.clientId}")
	private String clientId;

	@Value("${token.api.clientSecret}")
	private String clientSecret;

	@Value("${token.api.grantType}")
	private String grantType;

	@Value("${token.api.url}")
	private String tokenUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	
	public String getOAuthToken() {

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(clientId, clientSecret);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", grantType);

		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(map, headers);

		ResponseEntity<TokenResponse> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, body,
				TokenResponse.class);

		String token = response.getBody().getAccess_token();

		return token;
	}

	
	

}
