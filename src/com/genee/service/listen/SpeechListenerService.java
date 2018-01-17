
package com.genee.service.listen;

import com.genee.event.Event;
import com.genee.event.EventBus;
import com.genee.event.EventData;
import com.genee.event.EventType;
import com.genee.listen.ListenerFactory;
import com.genee.listen.SpeechData;
import com.genee.listen.SpeechListener;
import com.genee.service.Service;
import com.genee.utils.Util;
import com.genee.utils.logging.Logger;
import com.genee.utils.logging.Logger.TYPE;

public class SpeechListenerService implements Service {

  public final String NAME = this.getClass().getSimpleName();
  public final String ID = Util.getUniqueId();

  private EventBus eventBus;
  SpeechListener speechListener;

  public SpeechListenerService() {
    speechListener = ListenerFactory.getDefaultSpeechListener();
  }

  @Override
  public String getName() {
    return this.NAME;
  }

  @Override
  public String getId() {
    return this.ID;
  }

  @Override
  public void register(EventBus eventBus) {
    eventBus.register(EventType.LISTEN_SPEECH, this);
    eventBus.register(EventType.STOP_LISTEN_SPEECH, this);
  }

  @Override
  public void onEventCaught(Event event) {
    if (event.getType() == EventType.LISTEN_SPEECH) {
      try {
        listenSpeech();
      } catch (Exception e) {
        Logger.log(TYPE.SERVERE, "SpeechListenerService while listenSpeech, " + e.getMessage());
      }
    }
    if (event.getType() == EventType.STOP_LISTEN_SPEECH) {
      this.shutDown();
    }
  }

  private void listenSpeech() throws Exception {
    speechListener.start();
    Event startListenEvent = new Event(this, EventType.LISTENING_SPEECH);
    eventBus.rise(startListenEvent);
    speechListener.onListen((speechData) -> {
      shutDown();
      SpeechData.Chunk speechChunk = null;
      if (speechData != null) {
        speechChunk = speechData.getChunck(0);
      }
      Event speechEvent = new Event(this, EventType.PROCESS_SPEECH);
      EventData data = new EventData();
      data.set("data", speechChunk);
      speechEvent.setData(data);
      eventBus.rise(speechEvent);
    });
  }

  @Override
  public void start() throws Exception {
  }

  @Override
  public void setEventBus(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  @Override
  public void shutDown() {
    speechListener.stop();
  }

}
