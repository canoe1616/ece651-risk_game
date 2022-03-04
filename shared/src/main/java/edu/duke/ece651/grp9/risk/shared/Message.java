package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;

public class Message implements Serializable{
  private final String text;
  public Message(String text) {
        this.text = text;
    }
  public String getText() {
        return text;
    }
}
