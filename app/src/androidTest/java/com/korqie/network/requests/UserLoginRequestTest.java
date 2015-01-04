package com.korqie.network.requests;

import com.google.common.collect.ImmutableList;
import com.korqie.RobolectricGradleTestRunner;
import com.korqie.models.login.UserLogin;
import com.korqie.models.user.ApiResponse;
import com.korqie.models.user.User;
import com.korqie.network.endpoints.UsersEndpoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.lang.reflect.Type;
import java.util.List;

import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@Link UserLoginRequest}.
 */
@Config(emulateSdk=18)
@RunWith(RobolectricGradleTestRunner.class)
public class UserLoginRequestTest {

  private static final String HEADER_NAME = "korqie";
  private static final String HEADER_VALUE = "isCool";

  private static final List<Header> HEADERS = ImmutableList.of(
      new Header(HEADER_NAME, HEADER_VALUE));

  private UsersEndpoint mockService;
  private TypedInput mockTypedInput;
  private Converter mockConverter;
  private UserLoginRequest userLoginRequest;

  @Before
  public void setUp() throws Exception {
    mockTypedInput = mock(TypedInput.class);

    mockConverter = mock(Converter.class);
    when(mockConverter.fromBody(any(TypedInput.class), any(Type.class))).thenReturn(
        new ApiResponse(ImmutableList.<String>of(), // errors
            "Api service message", // message
            0, // num modified
            ImmutableList.<User>of()));// ))

    mockService = mock(UsersEndpoint.class);
    when(mockService.login(any(UserLogin.class))).thenReturn(
      new Response("http://url", 200 /* status */, "transport reason", HEADERS, mockTypedInput));

    userLoginRequest = new UserLoginRequest(mockConverter, new UserLogin("email", "pwd"));
    userLoginRequest.setService(mockService);
  }

  @Test
  public void testLoadDataFromNetwork() {
    ApiResponse response = userLoginRequest.loadDataFromNetwork();
    assertThat(response.getHeaders().get().get(0).getName(), equalTo(HEADER_NAME));
    assertThat(response.getHeaders().get().get(0).getValue(), equalTo(HEADER_VALUE));
  }
}
