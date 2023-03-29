package com.ufcg.psoft.mercadofacil.service;

public class ValidarCodigoDeBarrasImplService implements ValidarCodigoDeBarrarService {
    public boolean validarCodigoDeBarra(String codigoDeBarras) {
        int somaImpares = 0;
        int somaPares = 0;
        String[] array = codigoDeBarras.split("");
        int ultimoDigitoCodigo = Integer.parseInt(array[array.length - 1]);
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
        int ultimoDigito = soma % 10;
        int digitoVerificador = 10 - ultimoDigito;
        if(digitoVerificador > 5) {
            digitoVerificador = digitoVerificador - 5;
        }
        return digitoVerificador == ultimoDigitoCodigo;

    }
}
