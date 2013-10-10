package com.llc.bumpr.sdk.models;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.llc.bumpr.sdk.interfaces.Sessions;
import com.llc.bumpr.sdk.interfaces.Users;
import com.llc.bumpr.sdk.lib.BumprClient;

public class ActiveSession extends Session {

	private User user;
	private String accessToken;
	
	public void logout(final Callback<InactiveSession> cb) {
		Sessions sessions = BumprClient.sessions();
		sessions.logout(accessToken, new Callback<InactiveSession>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				cb.failure(arg0);
			}

			@Override
			public void success(InactiveSession session, Response response) {
				// TODO Auto-generated method stub
				Session.setSession(session);
				cb.success(session, response);
			}
			
		});
	}

	public void request(Request request, final Callback<Request> cb) {
		Sessions sessions = BumprClient.sessions();
		sessions.request(accessToken, request, cb);
	}
	
	public void update(final User user, final Callback<User> cb) {
		Users users = BumprClient.users();
		users.update(accessToken, user, user.getId(), new Callback<Boolean>() {

			@Override
			public void failure(RetrofitError arg0) {
				cb.failure(arg0);
			}

			@Override
			public void success(Boolean success, Response response) {
				if (user != null) {
					Session session = Session.getSession();
					if (session.getClass() == ActiveSession.class) {
						((ActiveSession) session).updateUser(user); 
					}
				}
				cb.success(user, response);
			}
			
		});
	}
	
	/*********************** Private Methods ***********************/
	
	private void updateUser(User user) {
		this.user = user;
	}
}
