package com.bikki.cms.exception;

public class NoCabsAvailableException extends RuntimeException {
  public NoCabsAvailableException(String str) {
    super("No cabs available!" + str);
  }
}
