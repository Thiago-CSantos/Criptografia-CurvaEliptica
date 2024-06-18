package org.thc.EllipticCurveCryptography;

import java.math.BigInteger;

public class ECPoint {
    // TODO: classe para representar um ponto na curva

    public BigInteger x;
    public BigInteger y;

    public ECPoint(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }

    public ECPoint negate(BigInteger p) {
        // Retorna o ponto negativo na curva elíptica
        return new ECPoint(x, y.negate().mod(p));
    }

    @Override
    public String toString() {
        return "ECPoint{" +
                "x=" + x +
                ",\n y=" + y +
                '}';
    }
}
