package au.com.gsn.recording.vo;

import java.util.ArrayList;
import java.util.List;

public class DaiseeRecordingList {
	
	private List<DaiseeRecording> recordings = new ArrayList<DaiseeRecording>();

	public List<DaiseeRecording> getRecordings() {
		return recordings;
	}

	public void setRecordings(List<DaiseeRecording> recordings) {
		this.recordings = recordings;
	}


}
