package org.parcialfinal_poo.models.DataBase.Inserts;

import org.parcialfinal_poo.models.Banco.Tarjetas.Facilitador;
import org.parcialfinal_poo.models.Banco.Tarjetas.TipoTarjeta;
import org.parcialfinal_poo.models.DataBase.DataBase;

import java.sql.Date;

public abstract class DataBaseInserts extends DataBase {

    public static String getInsertQuery(int query) {
        //00021223 metodo estatico para obtener la query de insertar requerida en base a un parametro entero
        if(query == 0) { //00021223 se verifica si la query requerida es para insertar en la tabla Cliente
            return "INSERT INTO Cliente (nombres, apellidos, direccion, numTelefono)" +
                    "VALUES (?, ?, ?, ?)"; //00021223 retorna la query para insertar en la tabla query
        } else if(query == 1) { //00021223 se verifica si la query requerida es para insertar en la tabla tarjeta
            return "INSERT INTO Tarjeta (clienteID, numTarjeta, fechaExp, tipo, facilitadorID)" +
                    "VALUES(?, ?, ?, ?, ?)"; //00021223 retorna la query para insertar en la tabla Tarjeta
        } else if(query == 2) { //00021223 se verifica si la query requerida es para insertar en la tabla Compra
            return "INSERT INTO Compra (fechaCompra, monto, descripcion, tarjetaID)" +
                    "VALUES(?, ?, ?, ?)"; //00021223 retorna la query para insertar en la tabla Compra
        } else {
            return ""; //00021223 si no se le manda un parametro correcto retorna vacio
        }
    }

    public abstract void registrarCliente(String nombres, String apellidos, String direccion, String numTelefono);

    public abstract void registrarTarjeta(int clienteID, String numTarjeta, Date fechaExp, TipoTarjeta tipo, Facilitador facilitadorID);

    public abstract void registrarCompra(Date fechaCompra, double monto, String descripcion, int tarjetaID);
}
