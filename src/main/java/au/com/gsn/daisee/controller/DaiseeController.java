package au.com.gsn.daisee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.gsn.daisee.service.DaiseeAPIService;
import au.com.gsn.daisee.service.SecurityService;

@RestController
public class DaiseeController {

	@Autowired
	private DaiseeAPIService daiseeAPIService;
	
	@Autowired
	private SecurityService securityService;

	@GetMapping("/token")
	public String getOAuthToken() {
		return securityService.getOAuthToken();
	}

	@PostMapping("/uploadRecording")
	public void uploadRecording() {
		daiseeAPIService.uploadRecording();
	}

	@GetMapping("/getDaiseeRecordings")
	public void getRecordings() {
		daiseeAPIService.getRecordingsFromAPI();
	}

}
