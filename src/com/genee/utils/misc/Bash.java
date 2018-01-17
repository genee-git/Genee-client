
package com.genee.utils.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import com.genee.utils.logging.Logger;
import com.genee.utils.logging.Logger.TYPE;

public class Bash {

  private static final String NEW_LINE = "\n";

  public static String execute(String command) throws Exception {
    return execute(command, 0);
  }

  public static int readInputStreamWithTimeout(InputStream is, byte[] b, int timeoutMillis) throws IOException {
    int bufferOffset = 0;
    long maxTimeMillis = System.currentTimeMillis() + timeoutMillis;
    while (System.currentTimeMillis() < maxTimeMillis && bufferOffset < b.length) {
      int readLength = java.lang.Math.min(is.available(), b.length - bufferOffset);
      // can alternatively use bufferedReader, guarded by isReady():
      int readResult = is.read(b, bufferOffset, readLength);
      if (readResult == -1)
        break;
      bufferOffset += readResult;
    }
    return bufferOffset;
  }

  public static String execute(String command, long timoutSec) throws Exception {
    Process pr = null;
    BufferedReader buf = null;
    try {
      Runtime run = Runtime.getRuntime();
      pr = run.exec(command);
      if (timoutSec > 0) {
        pr.waitFor(timoutSec, TimeUnit.SECONDS);
      } else {
        pr.waitFor();
      }
      buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
      String line = "";
      StringBuilder stringBuilder = new StringBuilder();
      long startTime = System.currentTimeMillis();
      while ((line = buf.readLine()) != null) {
        stringBuilder.append(line).append(NEW_LINE);
      }
      if (stringBuilder.toString().trim().length() < 1) {
        buf = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
        while ((line = buf.readLine()) != null) {
          stringBuilder.append(line).append(NEW_LINE);
        }
      }
      long endTime = System.currentTimeMillis();
      long duration = (endTime - startTime) / 1000;
      Logger.log(TYPE.DEBUG, "Bash execute took appx." + duration + " sec.");
      return stringBuilder.toString();
    } finally {
      if (buf != null)
        buf.close();
      if (pr != null)
        pr.destroy();
    }

  }

}
