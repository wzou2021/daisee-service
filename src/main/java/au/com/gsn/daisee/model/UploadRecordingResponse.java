package au.com.gsn.daisee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadRecordingResponse {
	
	@JsonProperty("call_id")
	private String callId;

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}
	
	

}
