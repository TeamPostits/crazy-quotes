package se.awesome.messagebodyreader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import com.google.gson.stream.JsonReader;

import se.awesome.data.Quote;

@Provider
@Consumes("application/json")
public class JsonBodyReader implements MessageBodyReader<Quote> {

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return type.isAssignableFrom(Quote.class);
	}

	@Override
	public Quote readFrom(Class<Quote> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {

		JsonReader reader = new JsonReader(new InputStreamReader(entityStream));

		try {
			return readQuote(reader);
		} finally {
			reader.close();
		}
	}

	public Quote readQuote(JsonReader reader) throws IOException {
		String text = null;
		String author = null;
		String year = null;
		String createdBy = null;

		reader.beginObject();

		while (reader.hasNext()) {
			String name = reader.nextName();

			if (name.equals("text")) {
				text = reader.nextString();
			} else if (name.equals("author")) {
				author = reader.nextString();
			} else if (name.equals("year")) {
				year = reader.nextString();
			} else if (name.equals("createdBy")) {
				createdBy = reader.nextString();
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		return new Quote(text, author, Integer.parseInt(year), createdBy);
	}

}
