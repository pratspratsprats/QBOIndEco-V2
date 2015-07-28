/**
 * 
 */
package com.intuit.incubation.helper;

import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.stereotype.Component;
import org.codehaus.jackson.map.SerializationConfig;

@Provider
@Component
public class JsonProvider extends JacksonJaxbJsonProvider {

	public JsonProvider() {
		super();
		ObjectMapper mapper = _mapperConfig.getDefaultMapper();
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
		mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
}
