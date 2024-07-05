package org.parcialfinal_poo.models.Banco.Tarjetas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor //0021223 constructor sin parametros de la clase
@AllArgsConstructor //00021223 constructor que recibe todos los campos de la clase
@Getter //00021223 se declaran todos los setters de cada campo
@Setter //00021223 se declaran todos los getters de cada campo

public class Tarjeta {

    private int id; //00021223 campo para almacenar el id de la tarjeta
    private int clienteID; //00021223 campo para almacenar el ID del cliente al que esta asociada la tarjeta
    private String numeroTarjeta; //00021223 campo para almacenar el numero de la tarjeta
    private Date fechaExpiracion; //00021223 campo para almacenar la fecha de expiracion de la tarjeta
    private Facilitador facilitador; //00021223 campo para almacenar el facilitador de la tarjeta
    private TipoTarjeta tipo; //00021223 campo para almacenar el tipo de la tarjeta
}
