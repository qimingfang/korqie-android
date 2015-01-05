package com.korqie.models;

import com.google.common.base.Optional;
import com.korqie.models.user.User;

import java.util.List;

import retrofit.client.Header;

/**
 * A Response API object.
 */
public final class ApiResponse {
  private static final String COOKIE_VALUE = "set-cookie";

  private Optional<List<Header>> headers;

  final List<String> errors;
  final String message;
  final int modified;
  final List<User> results;

  public ApiResponse(List<String> errors, String message, int modified, List<User> results) {
    this.errors = errors;
    this.message = message;
    this.modified = modified;
    this.results = results;
  }

  public ApiResponse setHeaders(List<Header> headers) {
    this.headers = Optional.of(headers);
    return this;
  }

  public Optional<List<Header>> getHeaders() {
    return this.headers;
  }

  public List<User> getResults() {
    return results;
  }

  public User getUser() {
    if (results.size() > 0) {
      return results.get(0);
    }

    return null;
  }

  public String getCookie() {
    if (headers.isPresent()) {
      for (Header header : headers.get()) {
        if (COOKIE_VALUE.equals(header.getName())) {
          return header.getValue();
        }
      }
    }

    return null;
  }
}
