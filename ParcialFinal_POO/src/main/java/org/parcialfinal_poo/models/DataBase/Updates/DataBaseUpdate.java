package org.parcialfinal_poo.models.DataBase.Updates;

import org.parcialfinal_poo.models.DataBase.DataBase;

public abstract class DataBaseUpdate extends DataBase {

    public static String getInsertQuery(int query) {
        //00021223 metodo estatico para obtener la query de actualizar requerida en base a un parametro entero
        if(query == 0) { //00021223 se verifica si la query requerida es para actualizar en la tabla Cliente
            return "UPDATE Cliente SET nombres = ? WHERE id = ?"; //00021223 retorna la query para actualizar en la tabla query
        } else if(query == 1) { //00021223 se verifica si la query requerida es para actualizar en la tabla tarjeta
            return "UPDATE Tarjeta SET numTarjeta = ? WHERE id = ?"; //00021223 retorna la query para actualizar en la tabla Tarjeta
        } else if(query == 2) { //00021223 se verifica si la query requerida es para actualizar en la tabla Compra
            return "UPDATE Compra SET monto = ? WHERE id = ?"; //00021223 retorna la query para actualizar en la tabla Compra
        } else {
            return ""; //00021223 si no se le manda un parametro correcto retorna vacio
        }
    }

    public abstract void updateCliente(String nombres, int id);

    public abstract void updateTarjeta(String numTarjeta, int id);

    public abstract void updateCompra(double monto, int id);
}
