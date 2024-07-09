package org.parcialfinal_poo.models.Banco;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


public class Compra {

    private int codigo; //00021223 campo para almcenar el ID de las compras realizadas
    private Date fechaCompra; //00021223 campo para almacenar la fecha que se realizo la compra
    private double monto; //00021223 campo para almacenar el monto total de la compra
    private String descripcion; //00021223 campo para almacenar la descripcion de la compra
    private int tarjetaID; //00021223 campo para almacenar la tarjeta asociada con la que se realizo la compra

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTarjetaID() {
        return tarjetaID;
    }

    public void setTarjetaID(int tarjetaID) {
        this.tarjetaID = tarjetaID;
    }
}
