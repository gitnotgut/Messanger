package org.alikhan.apiprojects.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.alikhan.apiprojects.messenger.database.DatabaseClass;
import org.alikhan.apiprojects.messenger.model.Message;
import org.alikhan.apiprojects.messenger.model.Profile;

public class ProfileService {
	
	private Map<String, Profile> profiles = DatabaseClass.getAllProfiles();
	
	public ProfileService() {
		profiles.put("Ali", new Profile(1L, "Ali", "Ali", "Khan"));
		profiles.put("Sandeep", new Profile(2L, "Sandeep", "Sandeep", "Daida"));
		profiles.put("Kiran", new Profile(3L, "Kiran", "Kiran", "Marapatla"));
	}
	
	public List<Profile> getAllProfiles() {
		
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile) {
		if(profile.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}
}
