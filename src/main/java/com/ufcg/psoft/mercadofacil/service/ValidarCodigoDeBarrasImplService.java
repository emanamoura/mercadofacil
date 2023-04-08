package com.ufcg.psoft.mercadofacil.service;

import org.springframework.stereotype.Service;

@Service
public class ValidarCodigoDeBarrasImplService implements ValidarCodigoDeBarrasService {
    public boolean validarCodigoDeBarras(String codigoDeBarras) {
        if (codigoDeBarras == null || codigoDeBarras.length() != 13) {
            return false;
        }

        try {
            int soma = 0;
            for (int i = 0; i < 12; i += 2) {
                soma += Integer.parseInt(codigoDeBarras.substring(i, i + 1));
            }
            for (int i = 1; i < 12; i += 2) {
                soma += 3 * Integer.parseInt(codigoDeBarras.substring(i, i + 1));
            }
            int digitoVerificador = (10 - (soma % 10)) % 10;
            return digitoVerificador == Integer.parseInt(codigoDeBarras.substring(12));
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
