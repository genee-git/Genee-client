
package com.genee;

import com.genee.utils.stt.GoogleSpeechListener;
import com.genee.utils.stt.SphnixSpeech;

public class Constants {

  public static final String URL_GOOGLE_DUPLEX_SPEECH_API_V1 = "https://www.google.com/speech-api/full-duplex/v1/";
  public static final String URL_GOOGLE_DOWN_SPEECH_API_V1 = URL_GOOGLE_DUPLEX_SPEECH_API_V1
          + "down?maxresults=1&pair=%s";
  public static final String URL_GOOGLE_UP_SPEECH_API_V1 = URL_GOOGLE_DUPLEX_SPEECH_API_V1
          + "up?lang=%s&lm=dictation&client=chromium&pair=%s&key=%s&continuous=true&interim=true";

  public static final String LABEL_BASH = "bash ";
  public static final String LABEL_RESULT = "result";
  public static final String LABEL_CONFIDENCE = "confidence";
  public static final String LABEL_TRANSCRIPT = "transcript";
  public static final String LABEL_TIMEOUT = "timeout";
  public static final String LABEL_FINAL = "final";
  public static final String LABEL_GET = "GET";
  public static final String LABEL_POST = "POST";
  public static final String TYPE_UTF = "UTF-8";

  public static final String SYMBOL_SLASH = "/";
  public static final String SYMBOL_OPEN_BRACKET = "{";

  public static final int DEFAULT_CONNECTION_TIMEOUT = 1000 * 15; // 15 sec

  public static final Class<?> DEFAULT_WORD_LISTENER = SphnixSpeech.class;
  public static final Class<?> DEFAULT_SPEECH_LISTENER = GoogleSpeechListener.class;
  public static final String LABEL_WAKEUP_WORD_1 = "hello genie";
  public static final String LABEL_WAKEUP_WORD_2 = "hey genie";
  public static final String LABEL_WAKEUP_WORD_3 = "hi genie";
  public static final String LABEL_SPEECH_WORD = "genie";

  public static final String FORMAT_MP3 = ".mp3";
  public static final String PATH_AVAIABLE_TALKS = "/records/";
  public static final String DEFAULT_TALK_ASSISTANT = "female/emma/";

  public static final String PATH_GOOGLE_SPEECH_TEXT_PYTHON = "/scripts/sh/google_speech_text.sh";

}
