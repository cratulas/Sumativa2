
package westbankapp;

public abstract class CuentaBancaria {
    int numero;
    int saldo;
    String descripcion;

    public CuentaBancaria(int numero) {
        this.numero = numero;
        this.saldo = 0;
    }

    public int getNumero() {
        return numero;
    }

    public int getSaldo() {
        return saldo;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public abstract boolean depositar(int monto);

    public abstract boolean girar(int monto);

    public boolean transferir(CuentaBancaria cuentaDestino, int monto) {
        if (girar(monto)) {
            cuentaDestino.depositar(monto);
            return true;
        } else {
            return false;
        }
    }
}
