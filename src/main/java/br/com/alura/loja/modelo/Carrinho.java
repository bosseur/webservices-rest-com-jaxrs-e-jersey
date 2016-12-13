package br.com.alura.loja.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public class Carrinho {

	private List<Produto> produtos = new ArrayList<Produto>();
	private String rua;
	private String cidade;
	private long id;

	public Carrinho() {}
	
	public Carrinho(List<Produto> produtos, String rua, String cidade, long id) {
		super();
		this.produtos = produtos;
		this.rua = rua;
		this.cidade = cidade;
		this.id = id;
	}

	public Carrinho adiciona(Produto produto) {
		produtos.add(produto);
		return this;
	}

	public Carrinho para(String rua, String cidade) {
		this.rua = rua;
		this.cidade = cidade;
		return this;
	}

	public Carrinho setId(long id) {
		this.id = id;
		return this;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}
	
	public String getCidade() {
		return this.cidade;
	}
	
	public long getId() {
		return id;
	}
	
	public void remove(long id) {
		for (Iterator iterator = produtos.iterator(); iterator.hasNext();) {
			Produto produto = (Produto) iterator.next();
			if(produto.getId() == id) {
				iterator.remove();
			}
		}
	}
	
	public void troca(Produto produto) {
		remove(produto.getId());
		adiciona(produto);
	}

	public void trocaQuantidade(Produto produto) {
		for (Iterator iterator = produtos.iterator(); iterator.hasNext();) {
			Produto pr = (Produto) iterator.next();
			if(pr.getId() == produto.getId()) {
				pr.setQuantidade(produto.getQuantidade());;
			}
		}
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public String toXML() {
		return new XStream().toXML(this);
	}
	
	public String toJSON() {
		return new Gson().toJson(this);
	}

}
