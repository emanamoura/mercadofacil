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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes para a validação de um código de barras")
public class ValidarCodigoDeBarrasServiceTest {
    @Autowired
    ValidarCodigoDeBarrasService driver;
    @Test
    @DisplayName("Quando o código de barras é válido")
    public void testCodigoDeBarrasValido() {

        String codigoDeBarras = "7891234567895";
        assertTrue(driver.validarCodigoDeBarras(codigoDeBarras));
    }

    @Test
    @DisplayName("Quando o código de barras é inválido")
    public void testCodigoDeBarrasInvalido() {
        String codigoDeBarras = "7891234567894";
        assertFalse(driver.validarCodigoDeBarras(codigoDeBarras));
    }

    @Test
    @DisplayName("Quando o código de barras tem caracteres invalidos")
    public void testCodigoDeBarrasComCaracteresInvalidos() {
        String codigoDeBarras = "78912a456b895";
        assertFalse(driver.validarCodigoDeBarras(codigoDeBarras));
    }

    @Test
    @DisplayName("Quando o código de barras tem tamanho inválido")
    public void testCodigoDeBarrasComTamanhoInvalido() {
        String codigoDeBarras = "123456789012";
        assertFalse(driver.validarCodigoDeBarras(codigoDeBarras));
    }

    @Test
    @DisplayName("Quando o código de barras é nulo")
    public void testCodigoDeBarrasNulo() {
        String codigoDeBarras = null;
        assertFalse(driver.validarCodigoDeBarras(codigoDeBarras));
    }
}
