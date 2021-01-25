package com.bikki.cms.validator;

import java.math.BigInteger;

public interface Validator {
  public boolean isValidEmail(String email);

  public boolean isValidMobile(BigInteger mobile);
}
