package com.bikki.cms.service.impl;

import com.bikki.cms.Repository.DriverLocationRepository;
import com.bikki.cms.Repository.DriverRepository;
import com.bikki.cms.constant.Status;
import com.bikki.cms.dto.SuccessDto;
import com.bikki.cms.entity.DriverLocation;
import com.bikki.cms.entity.DriverRegistration;
import com.bikki.cms.service.DriverService;
import com.bikki.cms.validator.impl.DriverValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
  @Autowired private DriverRepository driverRepository;

  @Autowired private DriverLocationRepository driverLocationRepository;

  @Override
  public DriverRegistration onBoardDriver(DriverRegistration request) {
    return driverRepository.save(request);
  }

  @Override
  public SuccessDto sendDriverLocation(DriverLocation location) {
    driverLocationRepository.save(location);
    SuccessDto successDto = new SuccessDto();
    successDto.setStatus(Status.success.toString());
    return successDto;
  }

  @Override
  public boolean isValidDriverRegistration(DriverRegistration request) {
    DriverValidatorImpl validator = new DriverValidatorImpl();
    return validator.isValid(request);
  }

  @Override
  public List<DriverLocation> findAllDrivers() {
    return driverLocationRepository.findAll();
  }

  @Override
  public DriverRegistration findRegisteredDriverById(Integer id) {
    return driverRepository.findById(id).get();
  }
}
