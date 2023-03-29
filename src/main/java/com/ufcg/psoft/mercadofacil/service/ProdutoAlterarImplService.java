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

    private boolean validarCodigoDeBarra(String codigoDeBarras) {
        int somaImpares = 0;
        int somaPares = 0;
        String[] array = codigoDeBarras.split("");
        for(int i = 0; i < codigoDeBarras.length() - 2; i++) {
            int numero = Integer.parseInt(array[i]);
            if(i % 2 == 0) {
                somaPares += numero;
            } else {
                somaImpares += numero;
            }
        }
        int multi = somaImpares * 3;
        int soma = multi + somaPares;
        int digitoVerificador = 0;
    }



}
