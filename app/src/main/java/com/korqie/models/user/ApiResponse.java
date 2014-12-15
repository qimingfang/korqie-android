package com.korqie.models.user;

import com.google.common.base.Optional;

import java.util.List;

import retrofit.client.Header;

/**
 * A Response API object.
 */
public class ApiResponse {
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
}
