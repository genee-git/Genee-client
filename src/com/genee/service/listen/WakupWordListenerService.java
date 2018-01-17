
package com.genee.service.listen;

import java.io.IOException;

import com.genee.event.Event;
import com.genee.event.EventBus;
import com.genee.event.EventType;
import com.genee.listen.ListenerFactory;
import com.genee.listen.WordListener;
import com.genee.service.Service;
import com.genee.utils.Util;
import com.genee.utils.logging.Logger;
import com.genee.utils.logging.Logger.TYPE;

public class WakupWordListenerService implements Service {

  public final String NAME = this.getClass().getSimpleName();
  public final String ID = Util.getUniqueId();

  private EventBus eventBus;
  private WordListener wakeupWordListener;

  public WakupWordListenerService() {
    try {
      this.wakeupWordListener = ListenerFactory.getDefaultWakeUpWordListener();
    } catch (IOException e) {
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
    eventBus.register(EventType.LISTEN_WAKEUP_WORD, this);
    eventBus.register(EventType.STOP_LISTEN_WAKEUP_WORD, this);
  }

  @Override
  public void onEventCaught(Event event) {
    if (event.getType() == EventType.LISTEN_WAKEUP_WORD) {
      try {
        listenWakeupWord();
      } catch (Exception e) {
        Logger.log(TYPE.SERVERE, "WakupWordListenerService while START_WAKEUP_EVENT, " + e.getMessage());
      }
    } else if (event.getType() == EventType.STOP_LISTEN_WAKEUP_WORD) {
      shutDown();
    }
  }

  private void listenWakeupWord() throws Exception {
    wakeupWordListener.start();
    Event listeningEvent = new Event(this, EventType.LISTENING_WAKEUP_WORD);
    eventBus.rise(listeningEvent);
    wakeupWordListener.onListen((speechData) -> {
      if (speechData != null) {
        shutDown();
        Event wakeupEvent = new Event(this, EventType.WAKE_UP);
        eventBus.rise(wakeupEvent);
      }
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
    if (wakeupWordListener != null) {
      wakeupWordListener.stop();
    }
  }

}
