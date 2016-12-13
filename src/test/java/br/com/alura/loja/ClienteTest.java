package br.com.alura.loja;

import java.net.URISyntaxException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.CarrinhoBuilder;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Projeto;

public class ClienteTest {
	
	private Servidor server;
	private WebTarget target;
	
	@Before
	public void setUp() throws URISyntaxException{
		server = new Servidor();
		server.startServer();
		
		Client client = ClientBuilder.newClient();
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
		
		String xml = carrinho.toXML();
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		Response response = target.path("/carrinhos").request().header("Content-Type", "application/xml").post(entity);

		Assert.assertEquals(201, response.getStatus());

	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		
		String conteudo = target.path("/carrinhos/1").request().accept(MediaType.APPLICATION_XML).get(String.class);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}
	
	@Test
	public void testaQueBuscarUmProdutoTrazOProdutoEsperado() {

		String conteudo = target.path("/projetos/1").request().get(String.class);
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
		Assert.assertEquals("Minha loja", projeto.getNome());
	}
	
}
