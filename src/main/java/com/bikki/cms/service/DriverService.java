package com.bikki.cms.service;

import com.bikki.cms.dto.SuccessDto;
import com.bikki.cms.entity.DriverLocation;
import com.bikki.cms.entity.DriverRegistration;

import java.util.List;

public interface DriverService {
  public DriverRegistration onBoardDriver(DriverRegistration driverRegistration);

  public SuccessDto sendDriverLocation(DriverLocation location);

  public boolean isValidDriverRegistration(DriverRegistration request);

  public List<DriverLocation> findAllDrivers();

  public DriverRegistration findRegisteredDriverById(Integer id);
}
