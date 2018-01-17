
package com.genee.service;

import com.genee.event.Event;
import com.genee.event.EventBus;
import com.genee.event.EventType;
import com.genee.utils.Util;

public class LEDService implements Service {

  public final String NAME = this.getClass().getSimpleName();
  public final String ID = Util.getUniqueId();
  EventBus eventBus;

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
    eventBus.register(EventType.LISTEN_WAKEUP_WORD, this);
    eventBus.register(EventType.LISTENING_WAKEUP_WORD, this);

    eventBus.register(EventType.LISTEN_SPEECH_WORD, this);
    eventBus.register(EventType.LISTENING_SPEECH_WORD, this);

    eventBus.register(EventType.LISTEN_SPEECH, this);
    eventBus.register(EventType.LISTENING_SPEECH, this);

    eventBus.register(EventType.PROCESS_SPEECH, this);

  }

  @Override
  public void onEventCaught(Event event) {
    if (event.getType() == EventType.LISTEN_WAKEUP_WORD) {
     // System.out.println("LED: LISTEN_WAKEUP_WORD");
    } else if (event.getType() == EventType.LISTENING_WAKEUP_WORD) {
     // System.out.println("LED: LISTENING_WAKEUP_WORD");
    } else if (event.getType() == EventType.LISTEN_SPEECH_WORD) {
     // System.out.println("LED: LISTEN_SPEECH_WORD");
    } else if (event.getType() == EventType.LISTENING_SPEECH_WORD) {
     // System.out.println("LED: LISTENING_SPEECH_WORD");
    } else if (event.getType() == EventType.LISTEN_SPEECH) {
    //  System.out.println("LED: LISTEN_SPEECH");
    } else if (event.getType() == EventType.LISTENING_SPEECH) {
    //  System.out.println("LED: LISTENING_SPEECH");
    } else if (event.getType() == EventType.PROCESS_SPEECH) {
    //  System.out.println("LED: PROCESS_SPEECH");
    }
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

  }

}
