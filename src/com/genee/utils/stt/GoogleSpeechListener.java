
package com.genee.utils.stt;

import org.json.JSONObject;

import com.genee.Constants;
import com.genee.listen.SpeechData;
import com.genee.listen.SpeechListener;
import com.genee.utils.logging.Logger;
import com.genee.utils.logging.Logger.TYPE;
import com.genee.utils.misc.Bash;

import resources.ResourceLoader;

public class GoogleSpeechListener implements SpeechListener {

  @Override
  public void start() throws Exception {
  }

  @Override
  public void stop() {
  }

  @Override
  public void onListen(Response response) {
    try {
      String filePath = ResourceLoader.class.getResource(Constants.PATH_GOOGLE_SPEECH_TEXT_PYTHON).getPath();
      String output = Bash.execute(Constants.LABEL_BASH + filePath);
      if (output != null && output.trim().length() > 0 && output.startsWith(Constants.SYMBOL_OPEN_BRACKET)) {
        JSONObject jsonObject = new JSONObject(output);
        if (jsonObject.has(Constants.LABEL_TRANSCRIPT)) {
          SpeechData speechData = new SpeechData();
          SpeechData.Chunk chunck = speechData.createChunk();
          String _t = (String) jsonObject.get(Constants.LABEL_TRANSCRIPT);
          chunck.setTranscript(_t);
          if (jsonObject.has(Constants.LABEL_CONFIDENCE)) {
            chunck.setConfidence(String.valueOf(jsonObject.get(Constants.LABEL_CONFIDENCE)));
          }
          response.get(speechData);
          return;
        } else if (jsonObject.has(Constants.LABEL_TIMEOUT)) {
          Logger.log(TYPE.DEBUG, "GoogleLiveSpeech while listen timout frame has occured!!!");
          response.get(null);
          return;
        } else {
          response.get(null);
          return;
        }
      } else {
        Logger.log(TYPE.SERVERE, "GoogleLiveSpeech while listen, " + output);
        response.get(null);
        return;
      }
    } catch (Exception e) {
      Logger.log(TYPE.SERVERE, "GoogleLiveSpeech while listen, " + e.getMessage());
      response.get(null);
      return;
    }
  }

}