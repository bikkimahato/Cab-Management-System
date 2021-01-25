package com.bikki.cms.service.impl;

import com.bikki.cms.service.DistanceCalculationService;
import org.springframework.stereotype.Service;

@Service
public class DistanceCalculationServiceImpl implements DistanceCalculationService {

  @Override
  public Boolean isWithingFourKilometer(Double lat1, Double lon1, Double lat2, Double lon2) {
    double distance = 4;
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);

    lat1 = Math.toRadians(lat1);
    lat2 = Math.toRadians(lat2);

    double a =
        Math.pow(Math.sin(dLat / 2), 2)
            + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
    double rad = 6371;
    double c = 2 * Math.asin(Math.sqrt(a));
    return distance > rad * c;
  }
}
