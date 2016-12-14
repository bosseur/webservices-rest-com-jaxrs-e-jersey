package br.com.alura.loja;

import java.net.URISyntaxException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.CarrinhoBuilder;
import br.com.alura.loja.modelo.Projeto;

public class ClienteTest {
	
	private Servidor server;
	private WebTarget target;
	
	@Before
	public void setUp() throws URISyntaxException{
		server = new Servidor();
		server.startServer();
		
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		Client client = ClientBuilder.newClient( config );
		target = client.target("http://localhost:8080");

	}

	@After
	public void tearDown(){
		server.stopServer();
	}
	
	@Test
	public void testaCriarCarrinho() {
		
		Carrinho carrinho = new CarrinhoBuilder()
								.setId(2l)
								.setCidade("Rio de Janeiro")
								.build();
		
		Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML);
		Response response = target.path("/carrinhos").request().header("Content-Type", "application/xml").post(entity);

		Assert.assertEquals(201, response.getStatus());

	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		
		Carrinho carrinho = target.path("/carrinhos/1").request().accept(MediaType.APPLICATION_XML).get(Carrinho.class);

		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}
	
	@Test
	public void testaQueBuscarUmProdutoTrazOProdutoEsperado() {
		Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
		Assert.assertEquals("Minha loja", projeto.getNome());
	}
	
}
