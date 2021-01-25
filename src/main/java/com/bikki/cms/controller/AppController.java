package com.bikki.cms.controller;

import com.bikki.cms.dto.AvailableCabs;
import com.bikki.cms.dto.CabDto;
import com.bikki.cms.dto.DriverLocationRequest;
import com.bikki.cms.dto.SuccessDto;
import com.bikki.cms.entity.DriverLocation;
import com.bikki.cms.entity.DriverRegistration;
import com.bikki.cms.exception.InvalidDriverLocation;
import com.bikki.cms.exception.InvalidRegistrationDetail;
import com.bikki.cms.exception.NoCabsAvailableException;
import com.bikki.cms.service.DistanceCalculationService;
import com.bikki.cms.service.DriverService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping
public class AppController {

  @Autowired private DriverService driverService;

  @Autowired private DistanceCalculationService distanceCalculationService;

  @ApiOperation("This is the hello world api")
  @GetMapping("/")
  public String hello() {
    return "Hello World!!";
  }

  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation("This is the hello world api")
  @PostMapping("/api/v1/driver/register")
  public DriverRegistration add(@RequestBody DriverRegistration request) {
    Boolean isDriverOnBoardingSuccessful = driverService.isValidDriverRegistration(request);
    if (isDriverOnBoardingSuccessful) {
      try {
        return driverService.onBoardDriver(request);
      } catch (DataIntegrityViolationException dataIntegrityViolationException) {
        throw new InvalidRegistrationDetail("error while registraion");
      } catch (Exception e) {
        throw new InvalidRegistrationDetail("error while registraion");
      }
    } else {
      throw new InvalidRegistrationDetail("error while registraion");
    }
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @ApiOperation("This is the hello world api")
  @PostMapping("/api/v1/driver/{id}/sendLocation")
  public SuccessDto sendLocation(@PathVariable int id, @RequestBody DriverLocation request) {
    request.setDriverID(id);
    if (Objects.isNull(request.getLatitude()) || Objects.isNull(request.getLongitude())) {
      throw new InvalidDriverLocation("Invalid Driver location");
    }
    return driverService.sendDriverLocation(request);
  }

  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("This is the hello world api")
  @PostMapping("/api/v1/passenger/available_cabs")
  public AvailableCabs getCabs(@RequestBody DriverLocationRequest request) {
    if (Objects.isNull(request.getLatitude()) || Objects.isNull(request.getLongitude())) {
      throw new InvalidDriverLocation("Invalid Passenger location");
    }
    AvailableCabs availableCabs = new AvailableCabs();
    ArrayList<Integer> nearbyDrivers = new ArrayList<>();
    ArrayList<CabDto> cab = new ArrayList<>();

    List<DriverLocation> driverLocationList = driverService.findAllDrivers();
    for (DriverLocation driverLocation : driverLocationList) {
      if (distanceCalculationService.isWithingFourKilometer(
          driverLocation.getLatitude(),
          driverLocation.getLongitude(),
          request.getLatitude(),
          request.getLongitude())) {
        nearbyDrivers.add(driverLocation.getDriverID());
      }
    }
    for (Integer driverID : nearbyDrivers) {
      DriverRegistration driverRegistration = driverService.findRegisteredDriverById(driverID);
      if (Objects.nonNull(driverRegistration)) {
        CabDto cabDto = new CabDto();
        cabDto.setName(driverRegistration.getName());
        cabDto.setCar_number(driverRegistration.getCar_number());
        cabDto.setPhone_number(driverRegistration.getPhone_number().toString());
        cab.add(cabDto);
      }
    }
    availableCabs.setAvailable_cabs(cab);
    if (availableCabs.getAvailable_cabs().size() <= 0) {
      throw new NoCabsAvailableException("No cabs available!");
    }
    return availableCabs;
  }
}
