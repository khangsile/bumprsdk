package com.llc.bumpr.sdk.serializers;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.llc.bumpr.sdk.models.Registration;

public class RegistrationSerializer implements JsonSerializer<Registration> {

	@Override
	public JsonElement serialize(Registration src, Type arg1, JsonSerializationContext context) {
		JsonObject json = new JsonObject();
		json.addProperty("first_name", src.getFirstName());
		json.addProperty("last_name", src.getLastName());
		json.addProperty("password", src.getPassword());
		json.addProperty("password_confirmation", src.getPasswordConfirmation());
		json.addProperty("email", src.getEmail());
		json.addProperty("registration_id", src.getRegistrationId());
		json.addProperty("platform", src.getPlatform());
		
		return json;
	}

}
