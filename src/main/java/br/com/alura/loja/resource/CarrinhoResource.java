package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

@Path("carrinhos")
public class CarrinhoResource {
	
	private CarrinhoDAO carrinhoDAO;
	
	public CarrinhoResource() {
		this.carrinhoDAO = new CarrinhoDAO();		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo){
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		carrinhoDAO.adiciona(carrinho);
		URI uri = URI.create("/carrinhos/" + carrinho.getId());
        return Response.created(uri).build();
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id){
		Carrinho carrinho = carrinhoDAO.busca(id);
		return carrinho.toXML();
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String buscaJSON(@PathParam("id") long id){
		Carrinho carrinho = carrinhoDAO.busca(id);
		return carrinho.toJSON();
	}

}
