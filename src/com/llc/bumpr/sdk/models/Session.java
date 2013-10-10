package com.llc.bumpr.sdk.models;


abstract public class Session {
	
	private static Session activeSession = new InactiveSession();
	
	/**
	 * Get the active user session
	 * @return a session object representing the active session 
	 */
	public static Session getSession() {
		return activeSession;
	}
	
	public static Session setSession(Session session) {
		activeSession = session;
		return session;
	}
}
