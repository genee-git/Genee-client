package com.genee.utils.tts;
import org.json.JSONObject;

import com.genee.listen.SpeechData;
import com.genee.utils.logging.Logger;
import com.genee.utils.logging.Logger.TYPE;
import com.genee.utils.misc.Bash;

import resources.ResourceLoader;

public class EnvMap {
    public static void main (String[] args) throws Exception {
       try {
         String file = ResourceLoader.class.getResource("/scripts/bash/google_speech_text.sh").getPath();
         String output = Bash.execute("bash " + file);
         if (output != null && output.trim().length() > 0 && output.startsWith("{")) {
           JSONObject jsonObject = new JSONObject(output);
           if (jsonObject.has("transcript")) {
             SpeechData speechData = new SpeechData();
             SpeechData.Chunk chunck = speechData.createChunk();
             String _t = (String) jsonObject.get("transcript");
             chunck.setTranscript(_t);
             if (jsonObject.has("confidence")) {
               chunck.setConfidence(String.valueOf(jsonObject.get("confidence")));
             }
             System.out.println(speechData);
             return;
           } else if (jsonObject.has("timeout")) {
             Logger.log(TYPE.DEBUG, "GoogleLiveSpeech while listen timout frame has occured!!!");
             return;
           } else {
             return;
           }
         } else {
           Logger.log(TYPE.SERVERE, "GoogleLiveSpeech while listen, " + output);
           return;
         }
       } catch (Exception e) {
         Logger.log(TYPE.SERVERE, "GoogleLiveSpeech while listen, " + e.getMessage());
         return;
       }
    }
}