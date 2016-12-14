package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

@Path("carrinhos")
public class CarrinhoResource {

	private CarrinhoDAO carrinhoDAO;

	public CarrinhoResource() {
		this.carrinhoDAO = new CarrinhoDAO();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(Carrinho carrinho) {
		carrinhoDAO.adiciona(carrinho);
		URI uri = URI.create("/carrinhos/" + carrinho.getId());
		return Response.created(uri).build();
	}

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Carrinho busca(@PathParam("id") long id) {
		Carrinho carrinho = carrinhoDAO.busca(id);
		return carrinho;
	}

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String buscaJSON(@PathParam("id") long id) {
		Carrinho carrinho = carrinhoDAO.busca(id);
		return carrinho.toJSON();
	}

	@Path("{id}/produtos/{produtoId}")
	@DELETE
	public Response removeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId) {
		Carrinho carrinho = carrinhoDAO.busca(id);
		carrinho.remove(produtoId);
		return Response.ok().build();
	}

	@Path("{id}/produtos/{produtoId}")
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response alteraProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId, String conteudo) {
		Carrinho carrinho = carrinhoDAO.busca(id);
        Produto produto = (Produto) new XStream().fromXML(conteudo);
        carrinho.troca(produto);
		return Response.ok().build();
	}
	
	@Path("{id}/produtos/{produtoId}/quantidade")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response alteraQuantidadeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId, String conteudo) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        Produto produto = (Produto) new XStream().fromXML(conteudo);
        carrinho.trocaQuantidade(produto);
        return Response.ok().build();
    }

}
