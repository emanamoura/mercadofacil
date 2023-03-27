package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Produto;

public interface ProdutoRepository<Produto, Long> {
    public Produto find(Long id);

    public Produto update(Produto produto);
}
