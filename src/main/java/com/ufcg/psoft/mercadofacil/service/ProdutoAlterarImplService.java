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
        return this.produtoRepository.update(produto);
    }

}
