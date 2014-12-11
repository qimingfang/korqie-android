package com.korqie.models.user;

/**
 * A Registration API object.
 */
public class UserRegister {
  final String name;
  final String email;
  final String pwd;
  final String thumb;

  public UserRegister(String name, String email, String pwd, String thumb) {
    this.name = name;
    this.email = email;
    this.pwd = pwd;
    this.thumb = thumb;
  }
}
