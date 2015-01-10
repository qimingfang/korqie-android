package com.korqie.models.login;

/**
 * Special User response from Login.
 * TODO(qimingfang): remove this shit.
 */
public class LoginUser {
  final String user;

  public LoginUser(String user) {
    this.user = user;
  }

  public String getUser() {
    return user;
  }
}
