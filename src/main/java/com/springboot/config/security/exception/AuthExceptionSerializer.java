package com.springboot.config.security.exception;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * seven sins
 * 2017年5月25日 下午2:03:34
 */
public class AuthExceptionSerializer extends StdSerializer<AuthException>{

	private static final long serialVersionUID = 1L;

	public AuthExceptionSerializer(){
		super(AuthException.class);
	}

	@Override
	public void serialize(AuthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
        gen.writeStringField("code", String.valueOf(value.getCode()));
        gen.writeStringField("message", value.getMessage());
        if (value.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
	}
}
