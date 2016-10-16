package br.com.jsonphp;

/**
 * Created by Fernando on 15/10/2016.
 */
public class Potencia {
    private double motor;
    private int cavalos;

    public Potencia(){
        super();
    }

    public Potencia(int cavalos, double motor) {
        this.cavalos = cavalos;
        this.motor = motor;
    }

    public double getMotor() {
        return motor;
    }

    public void setMotor(double motor) {
        this.motor = motor;
    }

    public int getCavalos() {
        return cavalos;
    }

    public void setCavalos(int cavalos) {
        this.cavalos = cavalos;
    }
}
