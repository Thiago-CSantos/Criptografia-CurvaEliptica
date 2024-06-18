package org.thc.EllipticCurveCryptography;

import java.math.BigInteger;

public class EllipticCurve {
    private BigInteger a;
    private BigInteger b;
    private BigInteger p;

    public EllipticCurve(BigInteger a, BigInteger b, BigInteger p) {
        this.a = a;
        this.b = b;
        this.p = p;
    }

    public ECPoint add(ECPoint P, ECPoint Q) {
        if (P == null) {
            return Q;
        }
        if (Q == null) {
            return P;
        }

        BigInteger lambda;
        if (!P.x.equals(Q.x)) {
            lambda = Q.y.subtract(P.y).multiply(Q.x.subtract(P.x).modInverse(p)).mod(p);
        } else {
            if (!P.y.equals(Q.y) || P.y.equals(BigInteger.ZERO)) {
                return null; // Ponto em infinito
            }
            lambda = P.x.pow(2).multiply(BigInteger.valueOf(3)).add(a)
                    .multiply(P.y.multiply(BigInteger.valueOf(2)).modInverse(p)).mod(p);
        }

        BigInteger xr = lambda.pow(2).subtract(P.x).subtract(Q.x).mod(p);
        BigInteger yr = lambda.multiply(P.x.subtract(xr)).subtract(P.y).mod(p);

        return new ECPoint(xr, yr);
    }

    public ECPoint multiply(BigInteger k, ECPoint P) {
        ECPoint result = null;
        ECPoint addend = P;

        while (k.signum() != 0) {
            if (k.testBit(0)) {
                result = add(result, addend);
            }
            addend = add(addend, addend);
            k = k.shiftRight(1);
        }

        return result;
    }


    public ECPoint encodeMessage(String mensagem, BigInteger p) {
        // Simulação simples: converter a mensagem num ponto na curva elíptica
        BigInteger messageBigInt = new BigInteger(mensagem.getBytes());
        return new ECPoint(messageBigInt, messageBigInt); // Apenas para fins ilustrativos
    }


    @Override
    public String toString() {
        return "EllipticCurve{" +
                "a=" + a +
                ", b=" + b +
                ", p=" + p +
                '}';
    }
}