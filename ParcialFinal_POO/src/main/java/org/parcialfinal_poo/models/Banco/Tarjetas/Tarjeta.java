package org.parcialfinal_poo.models.Banco.Tarjetas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.parcialfinal_poo.models.Banco.Compra;
import org.parcialfinal_poo.models.DataBase.Selects.Select;

import java.sql.Date;
import java.util.ArrayList;

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

    private ArrayList<Compra> compras; //00021223 lista para almacenar las compras realizadas por las tarjetas

    public Tarjeta() {
        compras = new ArrayList<>(); //00021223 inicializacion del array de compras
    }

    //todo temporal, tal vez se elimine si no se usa

    //00021223 este metodo es creado con el unico proposito de poder almacenar todos los campos de las compras en un objeto
    //compra concreto que se almacenara dentro de la lista de compras de cada tarjeta, verificando que la compra en efecto,
    //se encuentre asociada al this.tarjeta especificamente

    public void loadCompras(int tarjetaID) {
        //00021223 metodo se encarga de cargar todos los registros de compras de la base de datos en el sistema, recibe el entero
        //el cual es el ID de la tarjeta para verificar cuales son las compras asociadas a ese ID de tarjeta y almacenarlas
        //en la lista de compras de this.tarjeta

        Select select = Select.getInstance(); //00021223 se obtiene la instancia unica de select para obtener todos los registros
        //con cada uno de sus campos

        ArrayList<Compra> temp = select.selectCompra(); //00021223 se inicializa una lista temporal donde se almacenan todos los registrsos
        //de compras que retorno el metodo de select

        for(Compra compra : temp) { //00021223 se recorre la lista temporal de compras
            if(compra.getTarjetaID() == tarjetaID) { //00021223 se verifica que el tarjetaID parametro coincida con el tarjetaID almacenado para la compra
                compras.add(compra); //00021223 si se cumple la validacion entonces guarda la compra en la lista de compras de this.tarjeta
            }
        }

        temp.clear(); //00021223 se limpia la lista temporal para liberar espacio en memoria
    }
}
