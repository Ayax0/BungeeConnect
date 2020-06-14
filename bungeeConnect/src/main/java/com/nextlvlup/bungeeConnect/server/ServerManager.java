package com.nextlvlup.bungeeConnect.server;

import java.net.InetSocketAddress;

import com.nextlvlup.bungeeConnect.endpoint.Endpoint;
import com.sun.net.httpserver.HttpServer;

import lombok.Getter;

public class ServerManager {
	
	@Getter private HttpServer server;
	
	public ServerManager(int port) {
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		server.start();
	}
	
	public void stop() {
		server.stop(0);
	}
	
	public void registerEndpoint(String path, Endpoint endpoint) {
		getServer().createContext(path, endpoint);
	}

}
