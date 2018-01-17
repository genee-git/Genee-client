package com.genee.listen;

import java.io.IOException;

import com.genee.Constants;
import com.genee.listen.WordListener.WORD_TYPE;
import com.genee.utils.stt.GoogleSpeechListener;
import com.genee.utils.stt.SphnixSpeech;

public class ListenerFactory {

	private ListenerFactory() {
	}

	public static WordListener getDefaultWakeUpWordListener() throws IOException {
		if (Constants.DEFAULT_WORD_LISTENER == SphnixSpeech.class) {
			return new SphnixSpeech(WORD_TYPE.WAKEUP_WORD);
		}
		return null;
	}

	public static WordListener getDefaultSpeechWordInstance() throws Exception {
		if (Constants.DEFAULT_WORD_LISTENER == SphnixSpeech.class) {
			return new SphnixSpeech(WORD_TYPE.SPEECH_WORD);
		}
		return null;
	}

	public static SpeechListener getDefaultSpeechListener() {
		if (Constants.DEFAULT_SPEECH_LISTENER == GoogleSpeechListener.class) {
			return new GoogleSpeechListener();
		}
		return null;
	}
}
