package epn.edu.ec;

public class Envio {
    private String guia;
    private String destinatario;
    private String ciudadDestino;
    private double pesoKg;

    public Envio(String guia, String destinatario, String ciudadDestino, double pesoKg) {
        this.guia = guia;
        this.destinatario = destinatario;
        this.ciudadDestino = ciudadDestino;
        setPesoKg(pesoKg);
    }

    public String getGuia() {
        return guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(double pesoKg) {
        if (pesoKg > 0){
            this.pesoKg = pesoKg;
        } else {
            System.out.println("Error. Debe ser mayor a 0");
        }
    }
    public int kilosFacturables(){
        int peso = (int) pesoKg;

        if (peso < pesoKg){
            return peso += 1;
        }
        return peso;

    }
    public double calcularCosto(){
         return kilosFacturables() * 2.0;
    }
    public String describir(){
        return "| Guia: "+guia+" | Destinatario: "+destinatario+" | Ciudad: "+ciudadDestino+" | Peso: "+pesoKg+" kg";
    }

}
