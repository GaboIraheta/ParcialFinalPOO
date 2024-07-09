package org.parcialfinal_poo.models.Banco;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.parcialfinal_poo.models.Banco.Tarjetas.Tarjeta;
import org.parcialfinal_poo.models.DataBase.Selects.Select;

import java.util.ArrayList;



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

    //todo los metodos load son en teoria para poder mostrar atributos como el clienteID como un objeto cliente concreto
    //todo en lugar de solo un entero cuando se realice la consulta a la tabla Tarjeta, por ejemplo, en caso que no importe
    //todo se deben eliminar incluyendo los arrayList y constructores que los inicializan

    //00021223 este metodo es creado con el unico proposito de poder almacenar todos los campos de las tarjetas en un objeto
    //tarjeta concreto que se almacenara dentro de la lista de tarjetas de cada cliente, verificando que la tarjeta en efecto,
    //se encuentre asociada al this.cliente especificamente

    public void loadTarjetas(int clienteID) {
        //00021223 metodo se encarga de cargar todos los registros de tarjetas de la base de datos en el sistema, recibe el entero
        //el cual es el ID del cliente para verificar cuales son las tarjetas asociadas a ese ID de cliente y almacenarlas
        //en la lista de tarjetas de this.cliente

        Select select = Select.getInstance(); //00021223 se obtiene la instancia unica de select para obtener todos los registros
        //con cada uno de sus campos

        ArrayList<Tarjeta> temp = select.selectTarjeta(); //00021223 se inicializa una lista temporal donde se almacenan todos los registrsos
        //de tarjetas que retorno el metodo de select

        for(Tarjeta tarjeta : temp) { //00021223 se recorre la lista temporal de tarjetas
            if(tarjeta.getClienteID() == clienteID) { //00021223 se verifica que el clienteID parametro coincida con el clienteID almacenado para la tarjeta
                tarjetas.add(tarjeta); //00021223 si se cumple la validacion entonces guarda la tarjeta en la lista de tarjetas de this.cliente
            }
        }

        temp.clear(); //00021223 se limpia la lista temporal para liberar espacio en memoria

        for(Tarjeta tarjeta : tarjetas) { //00021223 al haber obtenido las tarjetas del cliente se recorre dicha lista de tarjetas
            tarjeta.loadCompras(tarjeta.getId()); //00021223 a cada tarjeta se le manda a llamar su metodo de loadCompras para cargar
            //todas las compras de la base de datos asociadas a dicha tarjeta con el ID que se le pasa
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(ArrayList<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }
}
