package epn.edu.ec;

public class EnvioExpress extends EnvioNacional{
    private int horasEntrega;

    public EnvioExpress(String guia, String destinatario, String ciudadDestino, double pesoKg, boolean entregaDomicilio, int horasEntrega) {
        super(guia, destinatario, ciudadDestino, pesoKg, entregaDomicilio);
        this.horasEntrega = horasEntrega;
    }
    public EnvioExpress(String guia, String destinatario, String ciudadDestino, double pesoKg, boolean entregaDomicilio) {
        super(guia, destinatario, ciudadDestino, pesoKg, entregaDomicilio);
        this.horasEntrega = 48;
    }

    public int getHorasEntrega() {
        return horasEntrega;
    }

    public void setHorasEntrega(int horasEntrega) {
        this.horasEntrega = horasEntrega;
    }

    @Override
    public double calcularCosto() {
        double costoTotal = 0.0;
        if (horasEntrega == 24 || horasEntrega == 48) {
             costoTotal= super.calcularCosto() + (super.calcularCosto() * 0.40);
        }
        return costoTotal;
    }

    @Override
    public String describir() {
        return super.describir()+" | Hora de entrega: "+horasEntrega+" Horas";
    }
}
