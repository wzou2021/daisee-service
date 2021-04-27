package au.com.gsn.recording.vo;

public class DaiseeRecording {
	
	private String recordingId;
		
	private String callStart;

	private String callType;
	
	private String agentId;
	
	private String mediaUri;
	
	private String path;
	
	private String mediaId;
	
	private String recordingStart;
	
	private String duration;

	public String getRecordingId() {
		return recordingId;
	}

	public void setRecordingId(String recordingId) {
		this.recordingId = recordingId;
	}

	public String getCallStart() {
		return callStart;
	}

	public void setCallStart(String callStart) {
		this.callStart = callStart;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getMediaUri() {
		return mediaUri;
	}

	public void setMediaUri(String mediaUri) {
		this.mediaUri = mediaUri;
	}

	public String getRecordingStart() {
		return recordingStart;
	}

	public void setRecordingStart(String recordingStart) {
		this.recordingStart = recordingStart;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
