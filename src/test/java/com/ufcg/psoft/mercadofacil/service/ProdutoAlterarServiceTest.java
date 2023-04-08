package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes para a alteração de um produto")
public class ProdutoAlterarServiceTest {

    @Autowired
    ProdutoAlterarService driver;

    @MockBean
    ProdutoRepository<Produto, Long> produtoRepository;
    Produto produto;

    @BeforeEach
    void setup() {
        Mockito.when(produtoRepository.find(10L))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("4012345678901")
                        .nome("Produto Dez")
                        .fabricante("Empresa Dez")
                        .preco(450.00)
                        .build()
                );
        produto = produtoRepository.find(10L);
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7896649000050")
                        .nome("Nome Produto Alterado")
                        .fabricante("Nome Fabricante Alterado")
                        .preco(500.00)
                        .build()
                );
    }

    @Test
    @DisplayName("Quando altero o nome do produto com um nome válido")
    void alterarNomeDoProduto() {

        produto.setNome("Nome Produto Alterado");

        Produto resultado = driver.alterar(produto);

        assertEquals("Nome Produto Alterado", resultado.getNome());
    }

    @Test
    @DisplayName("Quando o preço é menor que zero")
    void precoMenorQueZero() {

        produto.setPreco(-1);

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );

        assertEquals("Preco invalido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o preço igual zero")
    void precoIgualZero() {

        produto.setPreco(0);

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );

        assertEquals("Preco invalido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando atributo nome é nulo")
    void modificarComValorDeNomeNulo() {
        produto.setNome(null);

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );

        assertEquals("Insira uma valor de nome valido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando atributo nome é vazio")
    void modificarComValorDeNomeVazio() {
        produto.setNome("");

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );

        assertEquals("Insira uma valor de nome valido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando atributo nome é tem apenas espaços")
    void modificarComValorDeNomeApenasComEspacos() {
        produto.setNome("     ");

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );

        assertEquals("Insira uma valor de nome valido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando atributo codigo de barras é nulo")
    void modificarComValorDeCodigoDeBarrasNulo() {
        produto.setCodigoBarra(null);

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );

        assertEquals("Insira uma valor de codigo de barras valido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando atributo codigo de barras é vazio")
    void modificarComValorDeCodigoDeBarrasVazio() {
        produto.setCodigoBarra("");

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );

        assertEquals("Insira uma valor de codigo de barras valido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando atributo codigo de barras tem apenas espaços vazios")
    void modificarComValorDeCodigoDeBarrasTemApenasEspacos() {
        produto.setCodigoBarra("  ");

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );

        assertEquals("Insira uma valor de codigo de barras valido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando atributo codigo de barras tem apenas espaços vazios com valor igual a 13")
    void modificarComValorDeCodigoDeBarrasTemApenasEspacosComValorIgualA13() {
        produto.setCodigoBarra("             ");

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );

        assertEquals("Insira uma valor de codigo de barras valido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando atributo fabricante é nulo")
    void modificarComValorDeFabricanteNulo() {
        produto.setFabricante(null);

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );

        assertEquals("Insira uma valor de fabricante valido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando atributo fabricante é vazio")
    void modificarComValorDeFabricanteVazio() {
        produto.setFabricante("");

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );

        assertEquals("Insira uma valor de fabricante valido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando atributo fabricante é apenas com espaços")
    void modificarComValorDeFabricanteApenasComEspacos() {
        produto.setFabricante("   ");

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );

        assertEquals("Insira uma valor de fabricante valido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o código de barras é valido ,é possível alterar o produto")
    void alterarProdutoComCodigoDeBarrasValido() {
        produto.setNome("Produto alterado");
        driver.alterar(produto);
    }

    @Test
    @DisplayName("Quando o código de barras é invalido e diferente de nulo")
    void alterarProdutoComCodigoDeBarrasInvalido() {
        produto.setCodigoBarra("1234567891234");
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Insira uma valor de codigo de barras valido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o tamanho do código de barras é menor que 13")
    void alterarProdutoComCodigoDeBarrasMenorQueTreze() {
        produto.setCodigoBarra("123456789123");
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Insira uma valor de codigo de barras valido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o tamanho do código de barras é menor que 13")
    void alterarProdutoComCodigoDeBarrasTemCaracteresAlfabeticos() {
        produto.setCodigoBarra("12345678912aa");
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Insira uma valor de codigo de barras valido!", thrown.getMessage());
    }
}
