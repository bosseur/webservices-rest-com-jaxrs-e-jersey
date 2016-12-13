package br.com.alura.loja.modelo;


import java.util.ArrayList;
import java.util.List;

public class CarrinhoBuilder {

	private List<Produto> produtos = new ArrayList<Produto>();
	private String rua;
	private String cidade;
	private long id;
	
	public CarrinhoBuilder() {
		rua = "Minha rua";
		cidade = "Minha cidade";
		id = 5;
	}
	
	
	
	public CarrinhoBuilder addProduto(Produto produto) {
		this.produtos.add(produto);
		return this;
	}



	public CarrinhoBuilder setRua(String rua) {
		this.rua = rua;
		return this;
	}



	public CarrinhoBuilder setCidade(String cidade) {
		this.cidade = cidade;
		return this;
	}



	public CarrinhoBuilder setId(long id) {
		this.id = id;
		return this;
	}



	public Carrinho build() {
		return new Carrinho(produtos, rua, cidade, id);
	}

}
