
package com.genee.service.talk;

import java.util.Random;

import com.genee.event.Event;
import com.genee.event.EventBus;
import com.genee.event.EventData;
import com.genee.event.EventPool;
import com.genee.event.EventType;
import com.genee.listen.SpeechData;
import com.genee.listen.SpeechData.Chunk;
import com.genee.service.Service;
import com.genee.talk.Talker;
import com.genee.talk.Talker.AVAIABLE_TALKS;
import com.genee.talk.Talker.Callback;
import com.genee.utils.Util;

public class TalkService implements Service {

  public final String NAME = this.getClass().getSimpleName();
  public final String ID = Util.getUniqueId();
  EventBus eventBus;

  public TalkService() {
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
    eventBus.register(EventType.APP_STARTED, this);
    eventBus.register(EventType.LISTENING_WAKEUP_WORD, this);
    eventBus.register(EventType.WAKE_UP, this);
    eventBus.register(EventType.RECEIVED_SPEECH_WORD, this);
    eventBus.register(EventType.PROCESS_SPEECH, this);
  }

  @Override
  public void onEventCaught(Event event) {
    if (event.getType() == EventType.APP_STARTED) {
      sayHelloWord();
    } else if (event.getType() == EventType.LISTENING_WAKEUP_WORD) {
      asyncBeep();
    } else if (event.getType() == EventType.WAKE_UP) {
      sayHiHello();
    } else if (event.getType() == EventType.RECEIVED_SPEECH_WORD) {
      sayYes();
    } else if (event.getType() == EventType.PROCESS_SPEECH) {
      sayLetMeThink(event.getData());
    }
  }

  private void asyncBeep() {
    EventPool.newAsyncProcess(TalkService.class.getSimpleName(), "asyncBeep", () -> {
      Talker.talk(AVAIABLE_TALKS.BEEP1, null);
    });
  }

  private void sayHelloWord() {
    final Event eventStarted = new Event(this, EventType.TALKING);
    final Event eventFinished = new Event(this, EventType.LISTEN_WAKEUP_WORD);
    Talker.talk(AVAIABLE_TALKS.HELLO_WORLD, new Callback() {
      @Override
      public void started() {
        eventBus.rise(eventStarted);
      }

      @Override
      public void finished() {
        eventBus.rise(eventFinished);
      }
    });
  }

  private void sayHiHello() {
    final Event eventStarted = new Event(this, EventType.TALKING);
    final Event eventFinished = new Event(this, EventType.LISTEN_SPEECH_WORD);
    AVAIABLE_TALKS[] _h = Talker.HI_HELLO_ARRAY;
    Talker.talk(getRandom(_h), new Callback() {
      @Override
      public void started() {
        eventBus.rise(eventStarted);
      }

      @Override
      public void finished() {
        eventBus.rise(eventFinished);
      }
    });
  }

  private void sayYes() {
    final Event eventStarted = new Event(this, EventType.TALKING);
    final Event eventFinished = new Event(this, EventType.LISTEN_SPEECH);
    AVAIABLE_TALKS[] _h = Talker.YES_ARRAY;
    Talker.talk(getRandom(_h), new Callback() {
      @Override
      public void started() {
        eventBus.rise(eventStarted);
      }

      @Override
      public void finished() {
        eventBus.rise(eventFinished);
      }
    });
  }

  private void sayLetMeThink(EventData data) {
    final Event eventStarted = new Event(this, EventType.TALKING);
    final Event eventFinished = new Event(this, EventType.LISTEN_SPEECH);
    AVAIABLE_TALKS[] _h = Talker.IM_THINKING_ARRAY;
    SpeechData.Chunk speechChunk = (Chunk) data.get("data");
    if (speechChunk == null) {
      _h = Talker.PLS_REPET_ARRAY;
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
      }
    }
    Talker.talk(getRandom(_h), new Callback() {
      @Override
      public void started() {
        eventBus.rise(eventStarted);
      }

      @Override
      public void finished() {
        if (speechChunk == null) {
          eventBus.rise(eventFinished);
        } else {
          System.out.println("PROCESS_SPEECH: " + speechChunk.getTranscript());
        }
      }
    });
  }

  private AVAIABLE_TALKS getRandom(AVAIABLE_TALKS[] array) {
    int rnd = new Random().nextInt(array.length);
    return array[rnd];
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
