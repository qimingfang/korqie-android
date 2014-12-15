package com.korqie.models.user;

/**
 * A User API object.
 */
public class User {
  final String _id;
  final String name;
  final String email;
  final String pwd;
  final String thumb;
  final String created_at;
  final String updated_at;

  /**
   * TODO(qimingfang): parse @param created_at and @param updated_at to {@code Date}.
   */
  public User(String _id, String name, String email, String pwd, String thumb,
              String created_at, String updated_at) {
    this._id = _id;
    this.name = name;
    this.email = email;
    this.pwd = pwd;
    this.thumb = thumb;
    this.created_at = created_at;
    this.updated_at = updated_at;
  }
}
