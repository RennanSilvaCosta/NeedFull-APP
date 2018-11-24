package tcc.etec.needful.view.view.api;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import org.joda.time.format.ISODateTimeFormat;

import tcc.etec.needful.view.view.model.ChamadosVO;

public class MyGsonBuilder {

    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (null == json) {
            return null;
        }
        Gson gson = build();
        return gson.fromJson(json, new TypeToken<T>() {
        }.getType());
    }

    //	public static <T> List<T> toList(String json, Type listChamadoType) {
//		if (null == json) {
//			return null;
//		}
//		Gson gson = build();
//		return gson.fromJson(json, new TypeToken<T>() {
//		}.getType());
//	}
    public static List<ChamadosVO> toList(String json, Type type) {
        if (null == json) {
            return null;
        }
        Gson gson = build();
        return gson.fromJson(json, type);
    }

    private static boolean enableLog = false;

    private static void log(String log) {
        // if (enableLog) Log.d("DEBUG_GSON_TIME", log);
    }

    static List<SimpleDateFormat> knownPatterns = new ArrayList<>(
            Arrays.asList(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"),
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"), new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss"),
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")));

    static JsonSerializer<Date> ser = new JsonSerializer<Date>() {
        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new GsonBuilder().create().toJsonTree(buildIso8601Format().format(src));
        }
    };

    static JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {

        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            Date date = null;

            try {
                // Take a try
                String dateString = json.getAsJsonPrimitive().getAsString();
                log("deserialize date string = " + dateString);
                date = buildOddFormat().parse(dateString);
                log(" pattern (yyyy-MM-dd  HH:mm:ss) =  SUCCESS " + dateString + " = " + date.toString());
            } catch (Throwable t) {
                // Loop on
                log(" pattern (yyyy-MM-dd  HH:mm:ss) = error = " + t.getMessage());
            }

            if (date == null) {
                try {
                    // Take a try
                    String dateString = json.getAsJsonPrimitive().getAsString();
                    date = buildOldFormat().parse(dateString);
                    log(" pattern (MMM dd, yyyy HH:mm:ss) =  SUCCESS " + dateString + " = " + date.toString());
                } catch (Throwable t) {
                    // Loop on
                    log(" pattern (MMM dd, yyyy HH:mm:ss) = error = " + t.getMessage());
                }

            }
            if (date == null) {
                try {
                    // Take a try
                    String dateString = json.getAsJsonPrimitive().getAsString();
                    date = buildVeryOldFormat().parse(dateString);
                    log(" pattern (MMM d, yyyy HH:mm:ss) =  SUCCESS " + dateString + " = " + date.toString());
                } catch (Throwable t) {
                    // Loop on
                    log(" pattern (MMM d, yyyy HH:mm:ss) = error = " + t.getMessage());
                }

            }
            if (date == null)

                for (SimpleDateFormat pattern : knownPatterns) {
                    try {
                        // Take a try
                        if (!pattern.toPattern().contains("Z"))
                            pattern.setTimeZone(TimeZone.getTimeZone("UTC"));
                        String dateString = json.getAsJsonPrimitive().getAsString();
                        if (!pattern.toPattern().contains("Z"))
                            pattern.setTimeZone(TimeZone.getTimeZone("UTC"));
                        date = new Date(pattern.parse(dateString).getTime());
                        log(" pattern (" + pattern.toPattern() + ") =  SUCCESS " + dateString + " = "
                                + date.toString());
                        break;
                    } catch (Throwable t) {
                        // Loop on
                        log(" pattern (" + pattern.toPattern() + ") = error = " + t.getMessage());
                    }
                }

//	            }
            if (date == null) {
                try {
                    date = new Date(json.getAsJsonPrimitive().getAsLong());
                    log(" Joda =  SUCCESS " + json.getAsJsonPrimitive().getAsString() + " = " + date.toString());
                } catch (Throwable t) {
                    log(" pattern (Long) = error = " + t.getMessage());
                }
            }
            if (date == null) {
                try {
                    date = DateFormat.getInstance().parse(json.getAsJsonPrimitive().getAsString());
                    log(" Joda =  SUCCESS " + json.getAsJsonPrimitive().getAsString() + " = " + date.toString());
                } catch (Throwable t) {
                    log(" pattern ( DateFormat.getInstance().parse()) = error = " + t.getMessage());
                }
            }
            if (date == null) {
                org.joda.time.format.DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
                try {
                    String dateString = json.getAsJsonPrimitive().getAsString();
                    date = fmt.parseDateTime(dateString).toDate();
                    log(" Joda =  SUCCESS " + dateString + " = " + date.toString());
                } catch (Throwable t) {
                    // Loop on
                    log(" Joda error = " + t.getMessage());

                    new Throwable("NON PARSABLE DATE!!! = " + json.toString());
                }
            }

            if (date == null)
                date = new Date();

            return date;
        }
    };

    private static DateFormat buildIso8601Format() {
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return iso8601Format;
    }

    private static DateFormat buildOddFormat() {
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return iso8601Format;
    }

    private static DateFormat buildOldFormat() {
        DateFormat iso8601Format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
        iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return iso8601Format;
    }

    private static DateFormat buildVeryOldFormat() {
        DateFormat iso8601Format = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
        iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return iso8601Format;
    }

    static public Gson build() {
        Gson gson = new GsonBuilder()
                // .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .registerTypeAdapter(Date.class, deser).registerTypeAdapter(Date.class, ser)
                .excludeFieldsWithoutExposeAnnotation().create();
        return gson;
    }


}
