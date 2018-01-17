package com.genee.utils.stt;

import java.io.IOException;
import java.util.logging.Logger;

import com.genee.Constants;
import com.genee.event.EventPool;
import com.genee.listen.SpeechData;
import com.genee.listen.WordListener;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

public class SphnixSpeech extends WordListener {

	LiveSpeechRecognizer recognizer;
	Configuration configuration;
	private Response response;

	public SphnixSpeech(WORD_TYPE wordType) throws IOException {
		super(wordType);
		Logger cmRootLogger = Logger.getLogger("default.config");
		cmRootLogger.setLevel(java.util.logging.Level.OFF);
		String conFile = System.getProperty("java.util.logging.config.file");
		if (conFile == null) {
			System.setProperty("java.util.logging.config.file", "ignoreAllSphinx4LoggingOutput");
		}
		configuration = new Configuration();
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setGrammarPath("resource:/grammer");
		if (wordType == WORD_TYPE.SPEECH_WORD) {
			configuration.setGrammarName("genie");
		} else if (wordType == WORD_TYPE.WAKEUP_WORD) {
			configuration.setGrammarName("hello_genie");
		}
		configuration.setUseGrammar(true);
	}

	@Override
	public void start() throws Exception {
		recognizer = new LiveSpeechRecognizer(configuration);
		recognizer.startRecognition(false);
		EventPool.newAsyncProcess(SphnixSpeech.class.getSimpleName(), "SphnixSpeech", () -> {
			String result = null;
			boolean isFound = false;
			while (true) {
				result = recognizer.getResult().getResult().toString();
				if (result != null && result.trim().length() > 0 && response != null) {
					isFound = filterWords(result);
					if (isFound) {
						SpeechData speechData = new SpeechData();
						SpeechData.Chunk chunk = speechData.createChunk();
						chunk.setTranscript(result);
						chunk.setFinalResponse(true);
						response.get(speechData);
						break;
					}
				}
				Thread.sleep(1000);
			}
		});
	}

	private boolean filterWords(String result) {
		if (super.wordType == WORD_TYPE.WAKEUP_WORD && (result.contains(Constants.LABEL_WAKEUP_WORD_1)
				|| result.contains(Constants.LABEL_WAKEUP_WORD_2) || result.contains(Constants.LABEL_WAKEUP_WORD_3))) {
			return true;
		}
		if (super.wordType == WORD_TYPE.SPEECH_WORD && (result.contains(Constants.LABEL_SPEECH_WORD))) {
			return true;
		}
		return false;
	}

	@Override
	public void stop() {
		if (recognizer != null) {
			recognizer.stopRecognition();
			recognizer = null;
		}
	}

	@Override
	public void onListen(Response response) {
		this.response = response;
	}

}
