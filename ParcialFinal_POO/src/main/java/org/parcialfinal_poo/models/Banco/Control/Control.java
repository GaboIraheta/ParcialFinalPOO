package org.parcialfinal_poo.models.Banco.Control;

import org.parcialfinal_poo.models.Banco.Cliente;
import org.parcialfinal_poo.models.Banco.Compra;

import java.util.ArrayList;

public class Control { //00021223 esta clase solo es para llevar el control de las compras realizadas y los clientes del banco

    //ambas listas son para que el banco pueda acceder a los datos de clientes y compras desde el sistema
    //despues de cargar los datos de la base de datos
    //aunque creo que esta clase se puede omitir si todos el sistema va a funcionar a base de consultas a la bbdd

    private ArrayList<Cliente> clientes; //00021223 lista para almacenar todos los clientes del banco
    private ArrayList<Compra> compras; //00021223 lista para almacenar las compras realizadas por los clientes

    private static Control instance; //00021223 instancia unica de la clase (implementacion de Singleton)

    private Control() { //00021223 constructor privado de la clase para que pueda ser instanciada unicamente por ella misma
        clientes = new ArrayList<>(); //00021223 inicializa el array de clientes
        compras = new ArrayList<>(); //00021223 inicializa el array de compras
    }

    public static Control getInstance() { //00021223 metodo para obtener la instancia unica de la clase
        if (instance == null) { //00021223 se verifica si la instancia unica es nula
            instance = new Control(); //00021223 si la instancia es nula se crea el objeto unico que existira de la clase
        }
        return instance; //00021223 retorna la instancia unica
    }
}
