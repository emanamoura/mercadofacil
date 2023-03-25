package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VolatilLoteRepositoryTest {

    @Autowired
    VolatilLoteRepository driver;

    Lote lote;
    Lote resultado;
    Produto produto;

    @BeforeEach
    void setup() {
        produto = Produto.builder()
                .id(1L)
                .nome("Produto Base")
                .codigoBarra("123456789")
                .fabricante("Fabricante Base")
                .preco(125.36)
                .build();
        lote = Lote.builder()
                .id(1L)
                .numeroDeItens(100)
                .produto(produto)
                .build();
    }


    @AfterEach
    void tearDown() {
        produto = null;
        driver.deleteAll();
    }

    @Test
    @DisplayName("Adicionar o primeiro Lote no repositorio de dados")
        void salvarPrimeiroLote() {
        resultado = driver.save(lote);

        assertEquals(1,driver.findAll().size());
        assertEquals(resultado.getId().longValue(), lote.getId().longValue());
        assertEquals(resultado.getProduto(), produto);
    }

    @Test
    @DisplayName("Adicionar o segundo Lote (ou posterior) no repositorio de dados")
    void salvarSegundoLoteOuPosterior() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);
        resultado = driver.save(loteExtra);
        assertEquals(driver.findAll().size(), 2);
        assertEquals(resultado.getId().longValue(), loteExtra.getId().longValue());
        assertEquals(resultado.getProduto(), produtoExtra);
    }

    @Test
    @DisplayName("Encontrar um lote que foi adicionado")
    void findOneLote() {
        driver.save(lote);

        resultado = driver.find(lote.getId());

        assertEquals(lote.getId(), resultado.getId());
        assertEquals(resultado.getId().longValue(), lote.getId().longValue());
        assertEquals(resultado.getProduto(), produto);
    }

    @Test
    @DisplayName("Encontrar um lote que foi adicionado após outro")
    void findASecondLoteThatWasInsertAfterAFirstLote() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);
        driver.save(loteExtra);
        resultado = driver.find(loteExtra.getId());
        assertEquals(loteExtra.getId(), resultado.getId());
    }



    @Test
    @DisplayName("Encontrar todos os lotes")
    void findlAll() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);
        resultado = driver.save(loteExtra);
        assertEquals(driver.findAll().size(), 2);
    }

    @Test
    @DisplayName("Verificar se lotes são encontrados em repositórios sem lotes")
    void findAllLotesWhenThereAreNoLotsInsideTheRepository() {
        assertEquals(driver.findAll().size(), 0);
    }

    @Test
    void update() {
        driver.save(lote);
        Produto produtoAtualizado = Produto.builder()
                .id(2L)
                .nome("Produto Atualizado")
                .codigoBarra("987654321")
                .fabricante("Fabricante Atualizado")
                .preco(125.36)
                .build();
        Lote loteAtualizado = Lote.builder()
                .id(1L)
                .numeroDeItens(100)
                .produto(produtoAtualizado)
                .build();
        resultado = driver.update(loteAtualizado);
        assertEquals(lote.getId(), resultado.getId());
    }

    @Test
    @DisplayName("Deletar um lote quando apenas um lote foi adicionado")
    void deleteLoteWithJustOneLoteAdded() {
        driver.save(lote);
        driver.delete(lote);
        resultado = driver.find(lote.getId());
        assertNull(resultado);
    }

    @Test
    @DisplayName("Deletar um lote quando dois lotes estiveream no repositório")
    void delete() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);
        driver.save(loteExtra);
        driver.delete(lote);
        resultado = driver.find(lote.getId());
        Lote searchedExtraLote = driver.find(loteExtra.getId());
        assertNull(resultado);
        assertEquals(searchedExtraLote.getId(), loteExtra.getId());
    }

    @Test
    @DisplayName("Deleta todos os lotes salvos")
    void deleteAll() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);
        driver.save(loteExtra);
        driver.deleteAll();
        assertEquals(0, driver.findAll().size());
    }
}