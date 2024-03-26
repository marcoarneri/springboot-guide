package eu.tasgroup.springbootguide.util;


import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

public class ParamsUtils {

  public static String sanitized(String string) {
    if(string == null) return null;
    return string.replaceAll("[\r\n]","");
  }

  public static String formatHeaders(MultiValueMap<String, String> headers) {
    return (String)headers.entrySet().stream().map((entry) -> {
      List<String> values = (List)entry.getValue();
      return (String)entry.getKey() + ":" + (values.size() == 1 ? "\"" + (String)values.get(0) + "\"" : (String)values.stream().map((s) -> {
        return "\"" + s + "\"";
      }).collect(Collectors.joining(", ")));
    }).collect(Collectors.joining(", ", "[", "]"));
  }
}
