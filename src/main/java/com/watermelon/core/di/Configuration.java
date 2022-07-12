package com.watermelon.core.di;

import java.net.MalformedURLException;
import java.net.URL;

import lombok.Data;

@Data
public class Configuration {
	private Server server;

	@Data
	public static class Server {
		private String protocol;
		private String host;
		private int port;
		private String resource;

		public URL getURL() throws MalformedURLException {
			return new URL(protocol, host, port, resource);
		}
	}
}
