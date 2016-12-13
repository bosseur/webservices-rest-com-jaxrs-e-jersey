package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {
	
	private HttpServer server;

	public static void main(String[] args) throws URISyntaxException, IOException {
		Servidor servidor = new Servidor();
		servidor.startServer();
		
		System.out.println("Servidor rodando");
        System.in.read();
        servidor.stopServer();
	}

	public void stopServer() {
		server.stop();		
	}

	public void startServer() throws URISyntaxException {
		URI uri = new URI("http://localhost:8080");
		ResourceConfig configuration = new ResourceConfig().packages("br.com.alura.loja");
		this.server = GrizzlyHttpServerFactory.createHttpServer(uri, configuration);
	}
}
