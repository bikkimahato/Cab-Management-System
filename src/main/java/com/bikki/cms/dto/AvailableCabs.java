package com.bikki.cms.dto;

import java.util.List;

public class AvailableCabs {
  List<CabDto> available_cabs;

  public List<CabDto> getAvailable_cabs() {
    return available_cabs;
  }

  public void setAvailable_cabs(List<CabDto> available_cabs) {
    this.available_cabs = available_cabs;
  }
}
