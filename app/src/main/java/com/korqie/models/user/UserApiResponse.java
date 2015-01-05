package com.korqie.models.user;

import com.korqie.models.ApiResponse;

import java.util.List;

/**
 * ApiResponse with list of {@link User} as expected values.
 */
public class UserApiResponse extends ApiResponse<User> {

  public UserApiResponse(List<String> errors, String message, int modified, List<User> results) {
    super(errors, message, modified, results);
  }
}
