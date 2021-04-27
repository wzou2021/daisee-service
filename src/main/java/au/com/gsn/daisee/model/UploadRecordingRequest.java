package au.com.gsn.daisee.model;

public class UploadRecordingRequest {
	
	private String direction;
	private String callStart;
	private String lengthOfWholeCall;
	private String agreeReference;
	private String stereoRec;
	private String callRef;
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getCallStart() {
		return callStart;
	}
	public void setCallStart(String callStart) {
		this.callStart = callStart;
	}
	public String getLengthOfWholeCall() {
		return lengthOfWholeCall;
	}
	public void setLengthOfWholeCall(String lengthOfWholeCall) {
		this.lengthOfWholeCall = lengthOfWholeCall;
	}
	public String getAgreeReference() {
		return agreeReference;
	}
	public void setAgreeReference(String agreeReference) {
		this.agreeReference = agreeReference;
	}
	public String getStereoRec() {
		return stereoRec;
	}
	public void setStereoRec(String stereoRec) {
		this.stereoRec = stereoRec;
	}
	public String getCallRef() {
		return callRef;
	}
	public void setCallRef(String callRef) {
		this.callRef = callRef;
	}
	
	
}
