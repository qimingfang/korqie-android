package com.korqie.models.login;

import com.korqie.models.ApiResponse;
import com.korqie.models.user.LoginUser;

import java.util.List;

/**
 * ApiResponse with list of {@ling String} as expected values
 */
public class LoginApiResponse extends ApiResponse<LoginUser> {
  public LoginApiResponse(List<String> errors, String message, int modified,
                          List<LoginUser> results) {
    super(errors, message, modified, results);
  }
}
