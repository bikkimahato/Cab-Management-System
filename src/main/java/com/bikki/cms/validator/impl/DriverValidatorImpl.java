package com.bikki.cms.validator.impl;

import com.bikki.cms.Repository.DriverRepository;
import com.bikki.cms.Utility;
import com.bikki.cms.entity.DriverRegistration;
import com.bikki.cms.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DriverValidatorImpl implements Validator {
  @Autowired private DriverRepository driverRepository;

  @Override
  public boolean isValidEmail(String email) {
    boolean isValid = false;
    if (Objects.isNull(email)) {
      return false;
    }
    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    CharSequence inputStr = email;

    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(inputStr);
    if (matcher.matches()) {
      isValid = true;
    }
    return isValid;
  }

  @Override
  public boolean isValidMobile(BigInteger mobile) {
    if (Objects.isNull(mobile)) {
      return false;
    }
    Pattern pattern = Pattern.compile("^\\d{10}$");
    Matcher matcher = pattern.matcher(mobile.toString());
    return matcher.matches();
  }

  public boolean isValid(DriverRegistration request) {
    if (Utility.isNullOrEmpty(request.getName())
        || Objects.isNull(request.getPhone_number())
        || Utility.isNullOrEmpty(request.getPhone_number().toString())
        || Utility.isNullOrEmpty(request.getEmail())
        || Utility.isNullOrEmpty(request.getCar_number())
        || Utility.isNullOrEmpty(request.getLicense_number())) {
      return false;
    }
    if (!isValidEmail(request.getEmail())) {
      return false;
    }
    if (!isValidMobile(request.getPhone_number())) {
      return false;
    }

    return true;
  }
}
