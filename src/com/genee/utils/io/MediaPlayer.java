
package com.genee.utils.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.genee.utils.logging.Logger;
import com.genee.utils.logging.Logger.TYPE;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class MediaPlayer {

  public static void play(String filePath) throws FileNotFoundException {
    play(filePath, null);
  }

  public static void play(String filePath, Listener listener) throws FileNotFoundException {
    File file = new File(filePath);
    if (!file.exists()) {
      throw new FileNotFoundException("No such file is found to play " + filePath);
    }
    play(new File(filePath), listener);
  }

  public static void play(File file) throws FileNotFoundException {
    play(file, null);
  }

  public static void play(File file, Listener listener) throws FileNotFoundException {
    if (file == null || !file.exists()) {
      throw new FileNotFoundException("No file is found to play ");
    }
    play(new FileInputStream(file), listener);
  }

  public static void play(InputStream stream) {
    play(stream, null);
  }

  public static void play(InputStream stream, Listener listener) {
    try {
      AdvancedPlayer player = new AdvancedPlayer(stream,
              javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());
      if (listener != null) {
        player.setPlayBackListener(new PlaybackListener() {
          @Override
          public void playbackStarted(PlaybackEvent evt) {
            listener.started();
          }

          @Override
          public void playbackFinished(PlaybackEvent evt) {
            listener.finished();
          }
        });

      }
      player.play();
    } catch (JavaLayerException e) {
      Logger.log(TYPE.SERVERE, "MediaPlayer while play, " + e.getMessage());
    }
  }

  public static void writeStreamToFile(InputStream inputStream, String filePath) {
    byte[] buffer;
    OutputStream outStream = null;
    try {
      buffer = new byte[4096];
      int len;
      File targetFile = new File(filePath);
      if (targetFile.exists())
        targetFile.delete();
      else
        targetFile.createNewFile();
      outStream = new FileOutputStream(targetFile);
      while ((len = inputStream.read(buffer)) > 0) {
        outStream.write(buffer, 0, len);
      }
    } catch (IOException e) {
      Logger.log(TYPE.SERVERE, "MediaPlayer while writeStreamToFile, " + e.getMessage());
    } finally {
      try {
        if (inputStream != null) inputStream.close();
        if (outStream != null) outStream.close();
      } catch (IOException e) {
      }
    }
  }

  public interface Listener {
    public void started();

    public void finished();
  }

  public static void main(String[] args) throws Exception {
    String _s = "res/listening_beep.mp3";
    play(_s);
  }

}
