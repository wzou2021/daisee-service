package au.com.gsn.daisee.service;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import au.com.gsn.daisee.model.TokenResponse;
import au.com.gsn.daisee.model.UploadRecordingResponse;
import au.com.gsn.recording.vo.DaiseeRecording;
import au.com.gsn.recording.vo.DaiseeRecordingList;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DaiseeAPIService {

	@Value("${daisee.api.url}")
	private String daiseeUrl;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private RestTemplate restTemplate;

	public void uploadRecording() {

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			headers.setBearerAuth(securityService.getOAuthToken());

			DaiseeRecordingList recordings = getRecordingsFromAPI();
			int total = 10;
			int count = 0;
			for (DaiseeRecording rec : recordings.getRecordings()) {
				doUpload(rec, headers);
				count++;
				if (count == total) {
					break;
				}
			}
		} catch (Exception e) {
			log.error("Failed");
		}
	}

	private void doUpload(DaiseeRecording rec, HttpHeaders headers) {
		try {
			String metaData = getJson(rec);
			Resource file = getFile(rec);
			if (StringUtils.isEmpty(metaData) || file == null) {
				return;
			}
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("files", file);
			map.add("metadata", metaData);
			HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<>(map, headers);

			ResponseEntity<UploadRecordingResponse> result = restTemplate.postForEntity(daiseeUrl, body,
					UploadRecordingResponse.class);
			UploadRecordingResponse response = result.getBody();
			log.info(String.format("Successful upload file [%s]", response.getCallId()));
		} catch (Exception e) {
			log.error(String.format("Failed to upload file [%s] due to [%s]", rec.getRecordingId(), e.getMessage()));
		}
	}

	public static Resource getFile(DaiseeRecording rec) {
		try {
			File copied = new File("C:\\temp\\daisee\\" + rec.getMediaId());

			String fileURL = rec.getPath();
			URL url = new URL(fileURL);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			FileUtils.copyURLToFile(url, copied);

			return new FileSystemResource(copied);

		} catch (Exception e) {

		}
		return null;

	}

	private String getJson(DaiseeRecording rec) {
		JsonObject obj = null;
		try {
			JsonArrayBuilder recordings = Json.createArrayBuilder();
			JsonObjectBuilder recording = Json.createObjectBuilder()
					.add("timestamp_of_recording_start", rec.getRecordingStart())
					.add("uploaded_filename", rec.getMediaId());
			recordings.add(recording);
			obj = Json.createObjectBuilder().add("direction", rec.getCallType()).add("call_start", rec.getCallStart())
					.add("length_of_whole_call", Double.parseDouble(rec.getDuration()))
					.add("agent_reference", rec.getAgentId()).add("stereo_recording_agent_side", "left")
					.add("call_reference", rec.getRecordingId()).add("recordings", recordings).build();

		} catch (Exception e) {
			log.error(String.format("Failed %s", e.getMessage()));
		}
		return obj.toString();
	}

	public DaiseeRecordingList getRecordingsFromAPI() {
		String url = "http://localhost:8091/getDaiseeRecordings";
		try {
			DaiseeRecordingList response = restTemplate.getForObject(url, DaiseeRecordingList.class);
			return response;

		} catch (Exception e) {
			log.info("Failed to getRecordings");
		}
		return null;
	}

}
