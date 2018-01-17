
package com.genee;

import com.genee.event.Event;
import com.genee.event.EventBus;
import com.genee.event.EventType;
import com.genee.service.LEDService;
import com.genee.service.Service;
import com.genee.service.listen.SpeechListenerService;
import com.genee.service.listen.SpeechWordListenerService;
import com.genee.service.listen.WakupWordListenerService;
import com.genee.service.talk.TalkService;
import com.genee.utils.ServiceExecutor;
import com.genee.utils.Util;
import com.genee.utils.logging.Logger;
import com.genee.utils.logging.Logger.TYPE;

public class App implements Service {

  public final String NAME = this.getClass().getSimpleName();
  public final String ID = Util.getUniqueId();

  public void start() {
    Logger.log(TYPE.INFO, "App started");
    EventBus eventBus = new EventBus();
    ServiceExecutor.registerServices(eventBus, WakupWordListenerService.class, SpeechWordListenerService.class,
            SpeechListenerService.class, TalkService.class,
            LEDService.class);
    ServiceExecutor.startupServices(TalkService.class);

    Event event = new Event(this, EventType.APP_STARTED);
    eventBus.rise(event);

    // Enable ThreadMonitor when debugging/CPU monitoring is required
    // ThreadMonitor.start();
  }

  public static void main(String[] args) {
    App app = new App();
    app.start();
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
    // TODO Auto-generated method stub
  }

  @Override
  public void onEventCaught(Event event) {
    // TODO Auto-generated method stub
  }

  @Override
  public void setEventBus(EventBus eventBus) {
    // TODO Auto-generated method stub
  }

  @Override
  public void shutDown() {
    // TODO Auto-generated method stub
  }

}
