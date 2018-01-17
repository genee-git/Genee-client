
package com.genee.talk;

import java.io.InputStream;

import com.genee.Constants;
import com.genee.utils.io.MediaPlayer;
import com.genee.utils.io.MediaPlayer.Listener;
import com.genee.utils.logging.Logger;
import com.genee.utils.logging.Logger.TYPE;

import resources.ResourceLoader;

public class Talker {

  public static final String PATH_AVAIABLE_TALKS = Constants.PATH_AVAIABLE_TALKS + Constants.DEFAULT_TALK_ASSISTANT;

  public static enum AVAIABLE_TALKS {
    CN_U_PLS_REPET_IT, HELLO_WORLD, HEY_HI, HI_IM_HERE_TO_HELP_U, HI, IM_THINKING_ABUT_IT_PLS_WAIT, OK_IM_THINKING_ABUT_IT, OK_LT_ME_THINK_ABUT_IT, SRY_IM_NT_BLE_TO_NDERSTAND_U_CN_U_PLS_RPET_IT, SRY_IM_NT_BLE_TO_NDERSTAND_U, YES_I_AM_HERE, YES_PLS_TEL_ME, YES, HELLO, BEEP1
  }

  public static final AVAIABLE_TALKS[] HI_HELLO_ARRAY = { AVAIABLE_TALKS.HEY_HI, AVAIABLE_TALKS.HI,
      AVAIABLE_TALKS.HELLO };

  public static final AVAIABLE_TALKS[] YES_ARRAY = { AVAIABLE_TALKS.YES, AVAIABLE_TALKS.YES_PLS_TEL_ME,
      AVAIABLE_TALKS.YES_I_AM_HERE };

  public static final AVAIABLE_TALKS[] IM_THINKING_ARRAY = { AVAIABLE_TALKS.IM_THINKING_ABUT_IT_PLS_WAIT,
      AVAIABLE_TALKS.OK_IM_THINKING_ABUT_IT,
      AVAIABLE_TALKS.OK_LT_ME_THINK_ABUT_IT };

  public static final AVAIABLE_TALKS[] PLS_REPET_ARRAY = { AVAIABLE_TALKS.CN_U_PLS_REPET_IT };

  private Talker() {
  }

  public static void talk(AVAIABLE_TALKS avaiableTalk, Callback callback) {
    String path = PATH_AVAIABLE_TALKS + avaiableTalk + Constants.FORMAT_MP3;
    InputStream stream = null;
    try {
      stream = ResourceLoader.getStream(path);
    } catch (NullPointerException e) {
      Logger.log(TYPE.SERVERE, "Talker while say, missing TALK file " + path);
      return;
    }
    try {
      MediaPlayer.play(stream, new Listener() {
        @Override
        public void started() {
          if (callback != null) callback.started();
        }

        @Override
        public void finished() {
          if (callback != null) callback.finished();
        }
      });

    } catch (Exception e) {
      Logger.log(TYPE.SERVERE, "Talker while say, missing TALK file " + path);
      return;
    }
  }

  public static void talk(String msg) {

  }

  public static void talk(InputStream stream) {

  }

  public interface Callback {
    public void started();

    public void finished();
  }

  public static void main(String[] args) {
    Talker.talk(AVAIABLE_TALKS.YES, null);
  }

}
