package com.nextlvlup.bungeeConnect.endpoint;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class Endpoint implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String response = getResponse(exchange);
		
		exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
		exchange.sendResponseHeaders(200, response.length());
		OutputStream out = exchange.getResponseBody();
		out.write(response.getBytes());
		out.close();
	}
	
	public abstract String getResponse(HttpExchange exchange);

}
