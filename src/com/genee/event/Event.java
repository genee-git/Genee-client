
package com.genee.event;

import com.genee.service.Service;

public class Event {

  private EventData data;
  private EventType type;
  private Service owner;

  public Event(Service owner, EventType type) {
    this.owner = owner;
    this.type = type;
  }

  public Event(Service owner, EventType type, EventData data) {
    this.owner = owner;
    this.type = type;
    this.data = data;
  }

  public EventData getData() {
    return this.data;
  }

  public void setData(EventData data) {
    this.data = data;
  }

  public EventType getType() {
    return this.type;
  }

  public void setEventType(EventType type) {
    this.type = type;
  }

  public Service getOwner() {
    return owner;
  }

  public void setOwnerId(Service owner) {
    this.owner = owner;
  }

}
