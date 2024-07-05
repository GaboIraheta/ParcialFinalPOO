package org.parcialfinal_poo.models.Banco;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.parcialfinal_poo.models.Banco.Tarjetas.Tarjeta;

import java.util.ArrayList;

@AllArgsConstructor //00021223 constructor que recibe todos los campos del objeeto
@Setter //00021223 se declaran todos los setters de la clase
@Getter //00021223 se declaran todos los getters de la clase

public class Cliente {

    private int id; //00021223 campo para almacenar el id de los clientes
    private String nombres; //00021223 campo para almacenar los nombres de cada cliente
    private String apellidos; //00021223 campo para almacenar los apellidos de cada cliente
    private String direccion; //00021223 campo para almacenar la direccion de cada cliente
    private String telefono; //00021223 campo para almacenar el telefono de cada cliente

    private ArrayList<Tarjeta> tarjetas; //00021223 lista para almacenar todas las tarjetas que tenga cada cliente

    public Cliente() { //00021223 constructor sin parametros de la clase que inicializa el array de tarjetas

        tarjetas = new ArrayList<>(); //00021223 inicializacion del array de tarjetas
    }
}
