
package westbankapp;

public class CuentaCorriente extends CuentaBancaria {
    private static final int LIMITE_GIRO = 0;

    public CuentaCorriente(int numero) {
        super(numero);
        this.descripcion = "Cuenta Corriente";
    }

    @Override
    public boolean depositar(int monto) {
        if (monto > 0) {
            saldo += monto;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean girar(int monto) {
        if (saldo - monto >= LIMITE_GIRO) {
            saldo -= monto;
            return true;
        } else {
            return false;
        }
    }
}

