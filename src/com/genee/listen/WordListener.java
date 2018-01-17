package com.genee.listen;

public abstract class WordListener implements Listener {
	
	public static enum WORD_TYPE {
		WAKEUP_WORD, SPEECH_WORD
	}

	public WORD_TYPE wordType;

	protected WordListener(WORD_TYPE wordType) {
		this.wordType = wordType;
	}

}
