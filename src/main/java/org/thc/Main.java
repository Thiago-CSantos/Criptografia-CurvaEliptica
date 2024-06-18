package org.thc;

import org.thc.EllipticCurveCryptography.ECPoint;
import org.thc.EllipticCurveCryptography.EllipticCurve;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        try {
            // Parâmetros da curva elíptica
            BigInteger a = BigInteger.valueOf(17);
            BigInteger b = new BigInteger("7");
            BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F", 16);
            EllipticCurve curve = new EllipticCurve(a, b, p);

            // Ponto gerador da curva (coordenadas em hexadecimal)
            BigInteger gx = new BigInteger("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798", 16);
            BigInteger gy = new BigInteger("483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8", 16);
            ECPoint G = new ECPoint(gx, gy); // Ponto Gerador G: Este ponto na curva elíptica
            // É usado para gerar chaves públicas e privadas. Geralmente é um ponto predefinido na curva elíptica, também conhecido como "ponto base".

            SecureRandom random = new SecureRandom();

            // Aline gera sua chave privada e pública
            BigInteger privateKeyAline = new BigInteger(256, random).mod(p);
            ECPoint publicKeyAline = curve.multiply(privateKeyAline, G);

            // Simulação do envio da chave pública da Aline para Bernardo
            BigInteger pontoPartidaAline = a;
            System.out.println("\nAline envia chave publica para Bernardo e o ponto de partida A: " + publicKeyAline +
                    "\nPonto de partida A:" + pontoPartidaAline.toString(10));


            // Bernardo gera a sua chave privada e pública
            // Parâmetros da curva elíptica
            String mensagemParaAline = "Mensagem Thiago Dev Back-end";
            BigInteger k = new BigInteger(256, random).mod(p); // Número aleatorio K
            ECPoint K = curve.multiply(k, G); // Chave efémera de Bernardo, criando a sua propria chave K = kA

            // Calcula C = kP + M
            ECPoint M = curve.encodeMessage(mensagemParaAline, p); // converter a mensagem num ponto na curva elíptica
            ECPoint C = curve.add(curve.multiply(k, publicKeyAline), M); // o símbolo C: geralmente representa o criptograma
                                                                         // ou cifra resultante da operação de criptografia

            // Bernardo envia C e K para Aline
            System.out.println("\nBernardo envia para Aline:");
            System.out.println("C: " + C);
            System.out.println("K: " + K);

            // Quando Aline receber esses dois pontos (C, K), basta que ela compute "C - nK"
            // Aline decifra a mensagem usando a sua chave privada e a chave efêmera de Bernardo
            ECPoint nK = curve.multiply(privateKeyAline,K);// n*K
            ECPoint decifrado = curve.add(C,nK.negate(p));// Decifra C - n*K

            // Conversão do ponto decifrado para mensagem
            BigInteger decifradoBigInt = decifrado.x;
            String mensagemDecifrada = new String(decifradoBigInt.toByteArray());
            System.out.println("\nMensagem decifrada por Aline: " + mensagemDecifrada);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}