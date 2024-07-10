package org.parcialfinal_poo.models.Banco.Control;

import lombok.Getter;
import org.parcialfinal_poo.models.Banco.Cliente;
import org.parcialfinal_poo.models.Banco.Compra;
import org.parcialfinal_poo.models.DataBase.Selects.Select;

import java.util.ArrayList;

@Getter

public class Control { //00021223 esta clase solo es para llevar el control de las compras realizadas y los clientes del banco

    //ambas listas son para que el banco pueda acceder a los datos de clientes y compras desde el sistema
    //despues de cargar los datos de la base de datos
    //aunque creo que esta clase se puede omitir si todos el sistema va a funcionar a base de consultas a la bbdd

    private ArrayList<Cliente> clientes; //00021223 lista para almacenar todos los clientes del banco

    private static Control instance; //00021223 instancia unica de la clase (implementacion de Singleton)

    private Control() { //00021223 constructor privado de la clase para que pueda ser instanciada unicamente por ella misma
        clientes = new ArrayList<>(); //00021223 inicializa el array de clientes
    }

    public static Control getInstance() { //00021223 metodo para obtener la instancia unica de la clase
        if (instance == null) { //00021223 se verifica si la instancia unica es nula
            instance = new Control(); //00021223 si la instancia es nula se crea el objeto unico que existira de la clase
        }
        return instance; //00021223 retorna la instancia unica
    }

    //todo temporal, tal vez se elimine si no se usa
    public void loadClientes() {
        //00021223 metodo para cargar todos los clientes registrados en la bbdd en el sistema
        clientes.clear();
        Select select = Select.getInstance(); //00021223 se obtiene la instancia de select para obtener todos los registros
        //con cada uno de sus campos

        clientes = select.selectCliente(); //00021223 se ejecuta el metodo de consulta a la tabla cliente y la lista que retorna
        //se almacena en la lista de clientes de control

        for(Cliente cliente : clientes) { //00021223 se recorre la lista de clientes una vez obtenida

            cliente.loadTarjetas(cliente.getId()); //00021223 a cada cliente se le llama su metodo de loadTarjetas para que se carguen
            //todas las tarjetas que un cliente tenga registradas en la base de datos

        }

    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
}
