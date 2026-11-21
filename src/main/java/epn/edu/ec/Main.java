package epn.edu.ec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final HashMap<String, Envio> enTransito = new HashMap<>();
    private static final ArrayList<Envio> entregados = new ArrayList<>();
    private static int contador = 1001;
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int opcion = 0;
        do {
            System.out.println("\n---- MENU SERVIENTREGA ----\n");
            System.out.println("1. Registrar Pedido");
            System.out.println("2. Consultar Envio");
            System.out.println("3. Entregar envio");
            System.out.println("4. Listar Entregados");
            System.out.println("5. Salir");
            System.out.print("Ingrese una opcion: ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            }catch (NumberFormatException e){
                System.out.println("Ingrese una opcion valida");
                opcion = 0;
            }
            if (opcion == 1){
                registrarPedido();
            } else if (opcion == 2) {
                consultarEnvio();
            } else if (opcion == 3) {
                entregarPedido();
            } else if (opcion == 4) {
                listarEntregados();
            } else if (opcion == 5){
                System.out.println("Salinedo del programa... Gracias por utilizar el Program SERVIENTREGA"); 
            } else {
                System.out.println("Ingrese opcion valida");
            }

        }while (opcion != 5);
    }
    public static void registrarPedido(){
        int tipoServicio;
        boolean tipoEntrega = false;
        int horas;
        String palabra = "";
        System.out.println("-----------------");
        System.out.println("1. Nacional");
        System.out.println("2. Express");
        System.out.println("3. Internacional");
        System.out.print("Ingrese el tipo de servicio: ");
        try {
            tipoServicio = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Ingreso invalido.");
            return;
        }
        if (tipoServicio <1 || tipoServicio > 3){
            System.out.println("Error. Tipo de servicio invalido.");
            return;
        }
        System.out.print("Nombre del destinatario: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isBlank()){
            System.out.println("Error. Ingresar nombre del destinatario");
            return;
        }
        System.out.print("Ciudad destino: ");
        String ciudad = sc.nextLine().trim();
        if (ciudad.isBlank()){
            System.out.println("Error. Ingresar nombre de la ciudad");
            return;
        }
        System.out.print("Peso en (kg): ");
        double peso = 0.0;
        try {
            peso = Double.parseDouble(sc.nextLine().trim());
        }catch (NumberFormatException e){
            System.out.println("Ingreso invalido.");
            return;
        }
        if (peso < 0){
            System.out.println("Error. Debe de ser mayor a 0.");
            return;
        }

        if (tipoServicio == 1){
            palabra = "NAC";
            System.out.print("La entrega es a domicilio (s/n): ");
            boolean domicilio = sc.nextLine().trim().equalsIgnoreCase("s");
            String guia = palabra + String.valueOf(contador);
            Envio e = new EnvioNacional(guia, nombre,ciudad,peso,domicilio);
            enTransito.put(guia, e);
            contador++;
            System.out.println("Registro exitoso. Guia "+ guia+", costo: $"+e.calcularCosto());

        }else if (tipoServicio == 2){
            palabra = "EXP";
            System.out.print("La entrega es a domicilio (s/n): ");
            boolean domicilio = sc.nextLine().trim().equalsIgnoreCase("s");
            System.out.print("Entrega en 24 horas (s/n): ");
            boolean es24horas = sc.nextLine().trim().equalsIgnoreCase("s");
            String guia = palabra+String.valueOf(contador);
            Envio e;
            if (es24horas){
                e = new EnvioExpress(guia, nombre, ciudad, peso, domicilio, 24);
            } else {
                e = new EnvioExpress(guia, nombre, ciudad, peso, domicilio);
            }
            enTransito.put(guia,e);
            System.out.println("Registro exitoso: Guia: "+ guia +", costo: "+e.calcularCosto());
            contador++;
        }
        if (tipoServicio == 3){
            palabra = "INT";
            System.out.print("Valor de compra en USD: $");
            double valorFob = Double.parseDouble(sc.nextLine().trim());
            String guia = palabra + String.valueOf(contador);
            EnvioInternacional e = new EnvioInternacional(guia, nombre, ciudad, peso, valorFob);
            if(e.cumple4x4()){
                enTransito.put(guia, e);
                contador++;
                System.out.println("Registro exitoso: Guia: "+ guia +", costo: "+e.calcularCosto());
            }else {
                System.out.println("Rechazado por la regla 4x4, no se asigna guia.");
            }
        }
    }
    public static void consultarEnvio(){
        if (enTransito.isEmpty()){
            System.out.println("No hay ningun envio regresando al menu.");
            return;
        }
        System.out.print("Ingrese el codigo de GUIA: ");
        String codigo = sc.nextLine().toUpperCase();
        if (enTransito.containsKey(codigo)){
            Envio e = enTransito.get(codigo);
            System.out.println(e.describir());
            System.out.println("Costo: "+ e.calcularCosto());
        } else {
            System.out.println("No existe en transito un envio con es guia");
        }
    }
    public static void entregarPedido(){
        if (enTransito.isEmpty()){
            System.out.println("No hay ningun envio regresando al menu.");
            return;
        }
        System.out.print("Ingrese el codigo de GUIA: ");
        String codigo = sc.nextLine().toUpperCase();
        if (enTransito.containsKey(codigo)){
            Envio e = enTransito.remove(codigo);
            entregados.add(e);
            System.out.println("Entrega confirma para guia "+codigo+".");
        } else {
            System.out.println("No existe un envio con esa guia.");
        }

    }
    public static void listarEntregados(){
        if (entregados.isEmpty()){
            System.out.println("No hay ninguna entrega");
            System.out.println("Total recaudado: "+ 0.0);
            return;
        }
        double recaudado = 0.0;
        System.out.println("Lista de entregados");
        for(Envio e : entregados){
            double costo = e.calcularCosto();
            recaudado += costo;
            System.out.println(e.describir()+" | Costo: $"+ e.calcularCosto());
        }
        System.out.println("Total recuadado: $"+recaudado);
    }
}