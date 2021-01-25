package com.bikki.cms.exception;

public class InvalidDriverLocation extends RuntimeException {
  public InvalidDriverLocation(String str) {
    super("InvalidDriverLocation" + str);
  }
}
