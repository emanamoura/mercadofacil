package com.ufcg.psoft.mercadofacil.repository;

public interface LoteRepository <T, ID>{
    T save(T lote);
    T find(ID id);
}
