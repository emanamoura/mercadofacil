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
                        .codigoBarra("7899137500104")
                        .nome("Produto Dez")
                        .fabricante("Empresa Dez")
                        .preco(450.00)
                        .build()
                );
        produto = produtoRepository.find(10L);
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500104")
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
    @DisplayName("Quando o preço é menor ou igual a zero")
    void precoMenorIgualAZero() {

        produto.setPreco(0.0);

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
    @DisplayName("Quando atributo codigo de barrars é nulo")
    void modificarComValorDeCodigoDeBarrasNulo() {
        produto.setCodigoBarra(null);

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
    @DisplayName("Quando o código de barrar é valido ,é possível alterar o produto")
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
        assertEquals("Codigo de barras invalido", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o código de barras é menor que 13")
    void alterarProdutoComCodigoDeBarrasMenorQueTreze() {
        produto.setCodigoBarra("123456789123");
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Codigo de barras nao tem tamanho correto", thrown.getMessage());
    }
}
