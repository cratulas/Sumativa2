
package westbankapp;

public class CuentaAhorro extends CuentaBancaria {
    private static final int LIMITE_GIRO = 0;

    public CuentaAhorro(int numero) {
        super(numero);
        this.descripcion = "Cuenta de Ahorro";
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
    @Override
    public boolean transferir(CuentaBancaria cuentaDestino, int monto) {
        return false; // No se puede realizar la transferencia
    }
}
