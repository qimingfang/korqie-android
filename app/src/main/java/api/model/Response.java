package api.model;

import java.util.Arrays;
import java.util.List;

/**
 * A Response API object.
 */
public class Response {
  final List<String> errors;
  final String message;
  final int modified;
  final List<User> results;

  public Response(List<String> errors, String message, int modified, List<User> results) {
    this.errors = errors;
    this.message = message;
    this.modified = modified;
    this.results = results;
  }

  @Override
  public String toString() {
    return "Message: " + message + " | results: " + Arrays.toString(results.toArray());
  }
}
