package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Produtos")
public class ProdutoV1ControllerTests {

    @Autowired
    MockMvc driver;

    @Autowired
    ProdutoRepository<Produto, Long> produtoRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Produto produto;

    @BeforeEach
    void setup() {
        produto = produtoRepository.find(10L);
    }

    @AfterEach
    void tearDown() {
        produto = null;
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de campos obrigatórios")
    class ProdutoValidacaoCamposObrigatorios {
        @Test
        @DisplayName("Quando alteramos o nome do produto com dados válidos")
        void quandoAlteramosNomeDoProdutoValido() throws Exception {
            produto.setNome("Produto Dez Alterado");

            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            assertEquals(resultado.getNome(), "Produto Dez Alterado");
        }

        @Test
        @DisplayName("Quando alteramos o nome do produto com dados vazios")
        void quandoAlteramosNomeDoProdutoInvalidoVazio() throws Exception {
            produto.setNome("");

            try {
                driver.perform(put("/v1/produtos/" + produto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            } catch (ServletException error) {
                Throwable e = error.getCause();
                assertEquals("Insira uma valor de nome valido!", e.getMessage());
            }
        }

        @Test
        @DisplayName("Quando alteramos o nome do produto com dados vazios espaçados")
        void quandoAlteramosNomeDoProdutoInvalidoVazioEspacados() throws Exception {
            produto.setNome("   ");

            try {
                driver.perform(put("/v1/produtos/" + produto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            } catch (ServletException error) {
                Throwable e = error.getCause();
                assertEquals("Insira uma valor de nome valido!", e.getMessage());
            }
        }

        @Test
        @DisplayName("Quando alteramos o nome do fabricante com dados válidos")
        void quandoAlteramosNomeDoFabricanteValido() throws Exception {
            produto.setFabricante("Empresa Dez 10");

            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            assertEquals(resultado.getFabricante(), "Empresa Dez 10");
        }
        @Test
        @DisplayName("Quando alteramos o nome do fabricante com dados nulos")
        void quandoAlteramosNomeDoFabricanteInvalidoNulo() throws Exception {
            produto.setFabricante(null);

            try {
                driver.perform(put("/v1/produtos/" + produto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            } catch (ServletException error) {
                Throwable e = error.getCause();
                assertEquals("Insira uma valor de fabricante valido!", e.getMessage());
            }
        }

        @Test
        @DisplayName("Quando alteramos o nome do produto com dados vazios")
        void quandoAlteramosNomeDoFabricanteInvalidoVazio() throws Exception {
            produto.setFabricante("");

            try {
                driver.perform(put("/v1/produtos/" + produto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            } catch (ServletException error) {
                Throwable e = error.getCause();
                assertEquals("Insira uma valor de fabricante valido!", e.getMessage());
            }
        }

        @Test
        @DisplayName("Quando alteramos o nome do produto com dados apenas com espacos")
        void quandoAlteramosNomeDoFabricanteInvalidoComEspacos() throws Exception {
            produto.setFabricante("       ");

            try {
                driver.perform(put("/v1/produtos/" + produto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            } catch (ServletException error) {
                Throwable e = error.getCause();
                assertEquals("Insira uma valor de fabricante valido!", e.getMessage());
            }
        }

        @Test
        @DisplayName("Quando alteramos o nome do produto com dados nulos")
        void quandoAlteramosNomeDoProdutoInvalidoNulo() throws Exception {
            produto.setNome(null);

            try {
                driver.perform(put("/v1/produtos/" + produto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            } catch (ServletException error) {
                Throwable e = error.getCause();
                assertEquals("Insira uma valor de nome valido!", e.getMessage());
            }
        }
    }
    @Nested
    @DisplayName("Conjunto de casos de verificação da regra sobre o preço")
    class ProdutoValidacaoRegrasDoPreco {
        @Test
        @DisplayName("Quando alteramos o preço do produto com um valor igual a zero")
        void quandoAlteramosPrecoDoProdutoComValorAbaixoDoMinimoIgualZero() throws Exception {
            produto.setPreco(0);

            try {
                driver.perform(put("/v1/produtos/" + produto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            } catch (ServletException error) {
                Throwable e = error.getCause();
                assertEquals("Preco invalido!", e.getMessage());
            }
        }

        @Test
        @DisplayName("Quando alteramos o preço do produto com um valor abaixo de 0")
        void quandoAlteramosPrecoDoProdutoComValorAbaixoDoMinimoMenorQueZero() throws Exception {
            produto.setPreco(-1);

            try {
                driver.perform(put("/v1/produtos/" + produto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            } catch (ServletException error) {
                Throwable e = error.getCause();
                assertEquals("Preco invalido!", e.getMessage());
            }
        }

        @Test
        @DisplayName("Quando alteramos o preço para um valor válido")
        void quandoAlteramosPrecoParaValorValido() throws Exception {
            produto.setPreco(500.00);

            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            assertEquals(500.00, resultado.getPreco());
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação da validação do código de barras")
    class ProdutoValidacaoCodigoDeBarras {
        @Test
        @DisplayName("Quando alteramos o código de barras para um valor inválido")
        void quandoAlteramosCodigoDeBarrasParaValorInvalido() throws Exception {
            produto.setCodigoBarra("1234567890");

            try {
                driver.perform(put("/v1/produtos/" + produto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            } catch (ServletException error) {
                Throwable e = error.getCause();
                assertEquals("Insira uma valor de codigo de barras valido!", e.getMessage());
            }
        }

        @Test
        @DisplayName("Quando alteramos o código de barras para um valor inválido")
        void quandoAlteramosCodigoDeBarrasParaValorComCaracteresAlfabeticos() throws Exception {
            produto.setCodigoBarra("1234567890aaa");

            try {
                driver.perform(put("/v1/produtos/" + produto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            } catch (ServletException error) {
                Throwable e = error.getCause();
                assertEquals("Insira uma valor de codigo de barras valido!", e.getMessage());
            }
        }

        @Test
        @DisplayName("Quando alteramos o código de barras para um valor de código de barras menor que 13")
        void quandoAlteramosCodigoDeBarrasParaValorComTamanhoMenorQue13() throws Exception {
            produto.setCodigoBarra("123456789012");

            try {
                driver.perform(put("/v1/produtos/" + produto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            } catch (ServletException error) {
                Throwable e = error.getCause();
                assertEquals("Insira uma valor de codigo de barras valido!", e.getMessage());
            }
        }

        @Test
        @DisplayName("Quando alteramos o código de barras para um valor nulo")
        void quandoAlteramosCodigoDeBarrasParaValorNulo() throws Exception {
            produto.setCodigoBarra(null);

            try {
                driver.perform(put("/v1/produtos/" + produto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            } catch (ServletException error) {
                Throwable e = error.getCause();
                assertEquals("Insira uma valor de codigo de barras valido!", e.getMessage());
            }
        }

        @Test
        @DisplayName("Quando alteramos o código de barras para um valor válido")
        void quandoAlteramosCodigoDeBarrasParaValorValido() throws Exception {
            produto.setCodigoBarra("7891234567895");

            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            assertEquals(resultado.getCodigoBarra(), "7891234567895");
        }
    }

    @Test
    @DisplayName("Quando alteramos o código de barras para espaços vazios igual a 13")
    void quandoAlteramosCodigoDeBarrasParaValorIgualA13MasComEspacosVazios() throws Exception {

        produto.setCodigoBarra("             ");

        try {
            driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        } catch (ServletException error) {
            Throwable e = error.getCause();
            assertEquals("Insira uma valor de codigo de barras valido!", e.getMessage());
        }
    }
}