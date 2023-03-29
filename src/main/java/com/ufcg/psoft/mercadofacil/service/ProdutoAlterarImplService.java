package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoAlterarImplService implements  ProdutoAlterarService{

    @Autowired
    ProdutoRepository<Produto, Long> produtoRepository;

    public Produto alterar(Produto produto) {
        if(produto.getPreco() <= 0) {
            throw new RuntimeException("Preco invalido!");
        }
        if(produto.getNome() == null) {
            throw new RuntimeException("Insira uma valor de nome valido!");
        }
        if(produto.getCodigoBarra() == null) {
            throw new RuntimeException("Insira uma valor de codigo de barras valido!");
        }
        if(produto.getFabricante() == null) {
            throw new RuntimeException("Insira uma valor de fabricante valido!");
        }
        return this.produtoRepository.update(produto);
    }

}
