package com.moneytransfer.common;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpServer;


class Application {

    public static void main(String[] args) throws IOException {
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/api/hello", (exchange -> {
        	
        	 Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
        	 String noNameText = "Anonymous";
        	
        	 for(String name:params.get("name"))
        	 {
        		// String name = params.get("name")
        	        	 String respText = String.format("Hello %s!", name);
        	        	  exchange.sendResponseHeaders(200, respText.getBytes().length);
        	              OutputStream output = exchange.getResponseBody();
        	              output.write(respText.getBytes());
        	              output.flush();
        	 }
        	            
        /*	  if ("GET".equals(exchange.getRequestMethod())) {
                  String respText = "Hello!";
                  exchange.sendResponseHeaders(200, respText.getBytes().length);
                  OutputStream output = exchange.getResponseBody();
                  output.write(respText.getBytes());
                  output.flush();
              } else {
                  exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
              }*/
            exchange.close();
        }));
        server.setExecutor(null); // creates a default executor
        server.start();
    }


    public static Map<String, List<String>> splitQuery(String query) {
    	 if (query == null || "".equals(query)) {
             return Collections.emptyMap();
         }
         
        return Arrays.stream(query.split("&"))
                .map(Application ::splitQueryParameter)
                .collect(Collectors.groupingBy(AbstractMap.SimpleImmutableEntry::getKey, LinkedHashMap::new, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

    public static AbstractMap.SimpleImmutableEntry<String, String> splitQueryParameter(String it) {
        final int idx = it.indexOf("=");
        final String key = idx > 0 ? it.substring(0, idx) : it;
        final String value = idx > 0 && it.length() > idx + 1 ? it.substring(idx + 1) : null;
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }
    
/** JDK 7 
 *   public static Map<String, List<String>> splitQuery(String query) throws UnsupportedEncodingException {
    	  final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();
    	  final String[] pairs = query.split("&");
    	  for (String pair : pairs) {
    	    final int idx = pair.indexOf("=");
    	    final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
    	    if (!query_pairs.containsKey(key)) {
    	      query_pairs.put(key, new LinkedList<String>());
    	    }
    	    final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
    	    query_pairs.get(key).add(value);
    	  }
    	  return query_pairs;
    	}*/
}
