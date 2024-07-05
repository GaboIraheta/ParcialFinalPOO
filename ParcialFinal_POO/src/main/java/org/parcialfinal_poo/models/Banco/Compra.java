package org.parcialfinal_poo.models.Banco;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor //00021223 constructor de la clase sin parametros
@AllArgsConstructor //00021223 constructor de la clase que recibe todos los campos como parametros
@Getter //00021223 se declaran todos los metodos getters de cada campo
@Setter //00021223 se declaran todos los metodos setters de cada campo

public class Compra {

    private int codigo; //00021223 campo para almcenar el ID de las compras realizadas
    private Date fechaCompra; //00021223 campo para almacenar la fecha que se realizo la compra
    private double monto; //00021223 campo para almacenar el monto total de la compra
    private String descripcion; //00021223 campo para almacenar la descripcion de la compra
    private int tarjetaID; //00021223 campo para almacenar la tarjeta asociada con la que se realizo la compra
}
