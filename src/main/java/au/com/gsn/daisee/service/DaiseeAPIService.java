package au.com.gsn.daisee.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import au.com.gsn.daisee.model.TokenResponse;
import au.com.gsn.daisee.model.UploadRecordingResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DaiseeAPIService {

	@Value("${daisee.api.url}")
	private String daiseeUrl;

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

	public UploadRecordingResponse uploadRecording() {
		UploadRecordingResponse response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			headers.setBearerAuth(getOAuthToken());

			String metaData = getJson().toString();
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("files", getFile());
			map.add("metadata", metaData);

			HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<>(map, headers);

			ResponseEntity<UploadRecordingResponse> result = restTemplate.postForEntity(daiseeUrl, body,
					UploadRecordingResponse.class);

			System.out.println("result==" + result);

		} catch (Exception e) {
			log.error("Failed");
		}
		return response;
	}

	public static Resource getFile() {
		try {
			String recordingUrl = "http://webdav.racq.gsn.cloud/01VNIFGJ2K885154180FE2LAES00002Q_2021-04-23_05-24-00-057A0208-1000831A-00000001.mp3";
			URL url = new URL(recordingUrl);
			//File file = Paths.get(url.toURI()).toFile();
			
			URLConnection conn = url.openConnection();
			OutputStream outputStream = conn.getOutputStream();
			
			/*
			 * File file = new File(
			 * "C:\\temp\\failed-recordings\\00OES8LORKF35BTF190FE2LAES0005K4_2020-01-31_02-39-30-029D01E3-1046226E-00000002.wav"
			 * );
			 */
		
			return new FileSystemResource(outputStream.get);
		} catch (Exception e) {
			log.error("Failed");
		}
		return null;
	}

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

	private JsonObject getJson() {
		JsonObject obj = null;
		try {
			obj = Json.createObjectBuilder().add("direction", "outbound")
					.add("call_start", "2017-03-01T13:00:00.007+10:00").add("length_of_whole_call", 25.3)
					.add("agent_reference", "mikaela.jones").add("stereo_recording_agent_side", "left")
					.add("call_reference", "bE5451826ed283fb852edb25bc32929a1").add("recordings", getRecordings())
					.build();

		} catch (Exception e) {
			log.error(String.format("Failed %s", e.getMessage()));
		}
		return obj;
	}

	private JsonArrayBuilder getRecordings() {
		JsonArrayBuilder recordings = Json.createArrayBuilder();

		JsonObjectBuilder recording = Json.createObjectBuilder()
				.add("timestamp_of_recording_start", "2017-03-01T13:00:27.845+10:00").add("uploaded_filename",
						"00OES8LORKF35BTF190FE2LAES0005K4_2020-01-31_02-39-30-029D01E3-1046226E-00000002.wav");
		recordings.add(recording);
		return recordings;
	}

}
