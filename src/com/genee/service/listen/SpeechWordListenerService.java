
package com.genee.service.listen;

import com.genee.event.Event;
import com.genee.event.EventBus;
import com.genee.event.EventType;
import com.genee.listen.ListenerFactory;
import com.genee.listen.WordListener;
import com.genee.service.Service;
import com.genee.utils.Util;
import com.genee.utils.logging.Logger;
import com.genee.utils.logging.Logger.TYPE;

public class SpeechWordListenerService implements Service {

  public final String NAME = this.getClass().getSimpleName();
  public final String ID = Util.getUniqueId();

  private EventBus eventBus;
  private WordListener speechWordListener;

  public SpeechWordListenerService() {
    try {
      this.speechWordListener = ListenerFactory.getDefaultSpeechWordInstance();
    } catch (Exception e) {
      Logger.log(TYPE.SERVERE, "WakupWordListenerService while constructor, " + e.getMessage());
    }
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getId() {
    return ID;
  }

  @Override
  public void register(EventBus eventBus) {
    eventBus.register(EventType.LISTEN_SPEECH_WORD, this);
    eventBus.register(EventType.STOP_LISTEN_SPEECH_WORD, this);
  }

  @Override
  public void onEventCaught(Event event) {
    if (event.getType() == EventType.LISTEN_SPEECH_WORD) {
      try {
        listenSpeechWord();
      } catch (Exception e) {
        Logger.log(TYPE.SERVERE, "SpeechWordListenerService while LISTEN_SPEECH_WORD, " + e.getMessage());
      }
    } else if (event.getType() == EventType.STOP_LISTEN_SPEECH_WORD) {
      shutDown();
    }
  }

  private void listenSpeechWord() throws Exception {
    speechWordListener.start();
    Event listeningEvent = new Event(this, EventType.LISTENING_SPEECH_WORD);
    eventBus.rise(listeningEvent);
    speechWordListener.onListen((speechData) -> {
      if (speechData != null) {
        shutDown();
        Event wakeupEvent = new Event(this, EventType.RECEIVED_SPEECH_WORD);
        eventBus.rise(wakeupEvent);
      }
    });
  }

  @Override
  public void start() throws Exception {
    listenSpeechWord();
  }

  @Override
  public void setEventBus(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  @Override
  public void shutDown() {
    if (speechWordListener != null) {
      speechWordListener.stop();
    }
  }

}
