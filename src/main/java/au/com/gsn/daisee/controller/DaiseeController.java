package au.com.gsn.daisee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.gsn.daisee.service.DaiseeAPIService;

@RestController
public class DaiseeController {

	@Autowired
	private DaiseeAPIService service;

	@GetMapping("/token")
	public String getOAuthToken() {
		return service.getOAuthToken();
	}

	@PostMapping("/uploadRecording")
	public void uploadRecording() {
		service.uploadRecording();
	}

}
