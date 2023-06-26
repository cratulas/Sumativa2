package westbankapp;

import java.util.Scanner;

public class WestBankApp {
    private static Banco banco;

    public static void main(String[] args) {
        banco = new Banco();

        mostrarMenu();
    }

    private static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("=== West Bank ===");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Buscar cliente");
            System.out.println("3. Realizar depósito");
            System.out.println("4. Realizar giro");
            System.out.println("5. Realizar transferencia");
            System.out.println("0. Salir");
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarCliente(scanner);
                    break;
                case 2:
                    buscarCliente(scanner);
                    break;
                case 3:
                    realizarDeposito(scanner);
                    break;
                case 4:
                    realizarGiro(scanner);
                    break;
                case 5:
                    realizarTransferencia(scanner);
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static void registrarCliente(Scanner scanner) {
        System.out.println("=== Registrar Cliente ===");

        System.out.print("RUT: ");
        String rut = scanner.nextLine();

        if (!validarRut(rut)) {
            System.out.println("RUT inválido. Debe tener un mínimo de 11 y un máximo de 12 caracteres, incluyendo puntos y guion.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido Paterno: ");
        String apellidoPaterno = scanner.nextLine();

        System.out.print("Apellido Materno: ");
        String apellidoMaterno = scanner.nextLine();

        System.out.print("Domicilio: ");
        String domicilio = scanner.nextLine();

        System.out.print("Comuna: ");
        String comuna = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.println("Tipo de cuenta (Corriente/Ahorro): ");
        String tipoCuenta = scanner.nextLine();

        if (!tipoCuenta.equalsIgnoreCase("Corriente") && !tipoCuenta.equalsIgnoreCase("Ahorro")) {
            System.out.println("Tipo de cuenta inválido.");
            return;
        }

        CuentaBancaria cuenta;
        int numeroCuenta = generarNumeroCuenta();

        if (tipoCuenta.equalsIgnoreCase("Corriente")) {
            cuenta = new CuentaCorriente(numeroCuenta);
        } else {
            cuenta = new CuentaAhorro(numeroCuenta);
        }

        Cliente cliente = new Cliente(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono, cuenta);
        banco.agregarCliente(cliente);

        System.out.println("Cliente registrado exitosamente.");
        System.out.println("Número de cuenta: " + numeroCuenta);
    }

    private static void buscarCliente(Scanner scanner) {
        System.out.println("=== Buscar Cliente ===");

        System.out.print("RUT del cliente: ");
        String rut = scanner.nextLine();

        Cliente cliente = banco.buscarCliente(rut);

        if (cliente != null) {
            System.out.println("=== Información del Cliente ===");
            System.out.println("RUT: " + cliente.getRut());
            System.out.println("Nombre: " + cliente.getNombre());
            System.out.println("Apellido Paterno: " + cliente.getApellidoPaterno());
            System.out.println("Apellido Materno: " + cliente.getApellidoMaterno());
            System.out.println("Domicilio: " + cliente.getDomicilio());
            System.out.println("Comuna: " + cliente.getComuna());
            System.out.println("Teléfono: " + cliente.getTelefono());
            System.out.println("Tipo de cuenta: " + cliente.getCuenta().getDescripcion());
            System.out.println("Número de cuenta: " + cliente.getCuenta().getNumero());
            System.out.println("Saldo: $" + cliente.getCuenta().getSaldo());
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void realizarDeposito(Scanner scanner) {
        System.out.println("=== Realizar Depósito ===");

        System.out.print("RUT del cliente: ");
        String rut = scanner.nextLine();

        Cliente cliente = banco.buscarCliente(rut);

        if (cliente != null) {
            System.out.print("Monto a depositar: $");
            int monto = scanner.nextInt();
            scanner.nextLine();

            if (monto <= 0) {
                System.out.println("Monto inválido. Debe ser mayor a cero.");
                return;
            }

            boolean exito = cliente.getCuenta().depositar(monto);

            if (exito) {
                System.out.println("Depósito realizado exitosamente.");
                System.out.println("Nuevo saldo: $" + cliente.getCuenta().getSaldo());
            } else {
                System.out.println("No se pudo realizar el depósito.");
            }
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void realizarGiro(Scanner scanner) {
        System.out.println("=== Realizar Giro ===");

        System.out.print("RUT del cliente: ");
        String rut = scanner.nextLine();

        Cliente cliente = banco.buscarCliente(rut);

        if (cliente != null) {
            System.out.print("Monto a girar: $");
            int monto = scanner.nextInt();
            scanner.nextLine();

            boolean exito = cliente.getCuenta().girar(monto);

            if (exito) {
                System.out.println("Giro realizado exitosamente.");
                System.out.println("Nuevo saldo: $" + cliente.getCuenta().getSaldo());
            } else {
                System.out.println("No se pudo realizar el giro.");
            }
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void realizarTransferencia(Scanner scanner) {
        System.out.println("=== Realizar Transferencia ===");

        System.out.print("RUT del cliente de origen: ");
        String rutOrigen = scanner.nextLine();

        Cliente clienteOrigen = banco.buscarCliente(rutOrigen);

        if (clienteOrigen != null) {
            System.out.print("Número de cuenta de destino: ");
            int numeroCuentaDestino = scanner.nextInt();
            scanner.nextLine();

            Cliente clienteDestino = banco.buscarCliente(numeroCuentaDestino);

            if (clienteDestino != null) {
                System.out.print("Monto a transferir: $");
                int monto = scanner.nextInt();
                scanner.nextLine();

                boolean exito = clienteOrigen.getCuenta().transferir(clienteDestino.getCuenta(), monto);

                if (exito) {
                    System.out.println("Transferencia realizada exitosamente.");
                    System.out.println("Nuevo saldo de origen: $" + clienteOrigen.getCuenta().getSaldo());
                    System.out.println("Nuevo saldo de destino: $" + clienteDestino.getCuenta().getSaldo());
                } else {
                    System.out.println("No se pudo realizar la transferencia.");
                }
            } else {
                System.out.println("Cuenta de destino no encontrada.");
            }
        } else {
            System.out.println("Cliente de origen no encontrado.");
        }
    }

    private static boolean validarRut(String rut) {
        // Validar que el rut tenga un mínimo de 11 y un máximo de 12 caracteres, incluyendo puntos y guion.
        return rut.matches("^\\d{1,2}\\.\\d{3}\\.\\d{3}[-][0-9kK]{1}$");
    }

    private static int generarNumeroCuenta() {
        // Generar un número de cuenta aleatorio de 9 dígitos
        return (int) (Math.random() * 900000000) + 100000000;
    }
}