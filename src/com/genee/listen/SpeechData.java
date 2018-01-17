package com.genee.listen;

import java.util.ArrayList;
import java.util.List;

public class SpeechData {

	private List<Chunk> chunks = new ArrayList<>();

	public Chunk createChunk() {
		Chunk chunk = new Chunk();
		chunks.add(chunk);
		return chunk;
	}

	public List<Chunk> getAllChucks() {
		return chunks;
	}

	public Chunk getChunck(int index) {
		return chunks.get(index);
	}

	@Override
	public String toString() {
		return chunks.toString();
	}

	public class Chunk {
		private String confidence;
		private String transcript;
		private boolean finalResponse;

		public String getConfidence() {
			return confidence;
		}

		public void setConfidence(String confidence) {
			this.confidence = confidence;
		}

		public boolean isFinalResponse() {
			return finalResponse;
		}

		public void setFinalResponse(boolean finalResponse) {
			this.finalResponse = finalResponse;
		}

		public String getTranscript() {
			return transcript;
		}

		public void setTranscript(String transcript) {
			this.transcript = transcript;
		}

		@Override
		public String toString() {
			return transcript.toString();
		}
	}

}
