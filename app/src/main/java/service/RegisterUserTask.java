package service;

import android.os.AsyncTask;

import api.UserService;
import api.model.Response;
import api.model.UserRegister;

/**
 * Async task to register users.
 */
public class RegisterUserTask extends AsyncTask<UserRegister, Integer, Response> {

  private UserService userService;

  public RegisterUserTask(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected Response doInBackground(UserRegister... userRegisters) {
    return userService.register(userRegisters[0]);
  }
}
