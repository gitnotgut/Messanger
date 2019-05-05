package org.alikhan.apiprojects.messenger.database;

import java.util.HashMap;
import java.util.Map;

import org.alikhan.apiprojects.messenger.model.Message;
import org.alikhan.apiprojects.messenger.model.Profile;

public class DatabaseClass {
	
	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();
	
	public static Map<Long, Message> getAllMessages() {
		return messages;
	}
	
	public static Map<String, Profile> getAllProfiles() {
		return profiles;
	}
}
