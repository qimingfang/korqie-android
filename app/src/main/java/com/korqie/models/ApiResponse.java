package com.korqie.models;

import java.util.List;

import retrofit.client.Header;

/**
 * A generic response API object.
 */
public class ApiResponse<T> {
  private static final String COOKIE_VALUE = "set-cookie";

  private List<Header> headers;

  protected final List<String> errors;
  protected final String message;
  protected final int modified;
  protected final List<T> results;

  public ApiResponse(List<String> errors, String message, int modified, List<T> results) {
    this.errors = errors;
    this.message = message;
    this.modified = modified;
    this.results = results;
  }

  public ApiResponse setHeaders(List<Header> headers) {
    this.headers = headers;
    return this;
  }

  public List<Header> getHeaders() {
    return this.headers;
  }

  public List<T> getResults() {
    return results;
  }

  public T getFirstValue() {
    if (results.size() > 0) {
      return results.get(0);
    }

    return null;
  }

  /*
   * @return cookie if it exists.
   */
  public String getCookie() {
    if (headers != null) {
      for (Header header : headers) {
        if (COOKIE_VALUE.equals(header.getName())) {
          return header.getValue();
        }
      }
    }

    return null;
  }
}
