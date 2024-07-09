package org.parcialfinal_poo.models.DataBase.QueriesReportes;

import org.parcialfinal_poo.models.Banco.Compra;
import org.parcialfinal_poo.models.Banco.Tarjetas.Facilitador;

import java.sql.*;
import java.util.ArrayList;

public class Queries extends DataBaseQueries {

    private static Queries instance; //00021223 instancia unica de la clase (implementacion de Singleton)

    private Queries() { //00021223 constructor privado de la clase para que solo pueda ser instanciada por ella misma

    }

    public static Queries getInstance() { //00021223 metodo para obtener la instancia unica de la clase
        if(instance == null) { //00021223 se verifica que la instancia no sea nula
            instance = new Queries(); //00021223 si la instancia es nula entonces crea el unico objeto que existira de la clase
        }
        return instance; //00021223 retorna la instancia unica de la clase
    }

    @Override
    public ArrayList<Compra> generarReporteA(int clienteID, Date fecha1, Date fecha2) {
        //00021223 se define metodo abstracto que se encarga de realizar la consulta a la base de datos para generar reporte A

        ArrayList<Compra> compras = new ArrayList<>(); //00021223 se inicializa un arraylist para almacenar los registros obtenidos de la base de datos

        try { //00021223 se realiza el control de excepciones requerido para consultas a bases de datos

            connection = getConnection(); //00021223 se establece la conexion a la base de datos por medio del
            //metodo estatico getConnection

            preparedStatement = connection.prepareStatement(DataBaseQueries.getReportQuery(1)); //00021223 se crea prepara a consulta para si ejecucion

            preparedStatement.setInt(1, clienteID);
            preparedStatement.setDate(2, fecha1);
            preparedStatement.setDate(3, fecha2);

            resultSet = preparedStatement.executeQuery(); //00021223 se ejecuta la consulta y el resultado de registros se almacena en el resultSet

            while(resultSet.next()) { //00021223 bucle while para recorrer el resultSet, la condicion es que el resultSet tenga un siguiente

                Compra compra = new Compra(); //00021223 se inicializa un objeto Compra para almacenarlo en el arraylist

                //00021223 se obtienen cada uno de sus campos del resultSet actual en la iteracion
                compra.setCodigo(resultSet.getInt("id")); //00021223 settea el ID de la compra
                compra.setFechaCompra(resultSet.getDate("fechaCompra")); //00021223 settea la fecha de la compra
                compra.setMonto(resultSet.getDouble("monto")); //00021223 settea el monto total de la compra
                compra.setDescripcion(resultSet.getString("descripcion")); //00021223 settea la descripcion de la compra
                compra.setTarjetaID(resultSet.getInt("tarjetaID")); //00021223 settea el ID de la tarjeta a la que esta asociada la compra

                compras.add(compra); //00021223 agrega el cliente obtenido a la lista de compras que se va a retornar
            }

           return compras; //00021223 luego de haber obtenido todos los registros de compras de la base de datos, retorna la lista con cada uno de los registros como objetos Compra

        } catch (SQLException e) { //00021223 control de excepciones, cacha un SQLException que seria cualquier fallo en la ejecucion de la consulta
            //todo se debe mandar el mensaje de error por medio de una alerta
            System.out.println(e.getMessage()); //00021223 se imprime el mensaje de error atrapado

            return null; //00021223 si ocurre una excepcion, retorna una lista de compras nula
        }
    }

    @Override
    public double generarReporteB(int clienteID, Date fecha) {
        return 0;
    }

    @Override
    public ArrayList<String> generarReporteC(int clienteID) {
        return null;
    }

    public ResultSet generarReporteD(Facilitador facilitador) { //00042823 General el resultSet del reporte D, que se usará directamente en el GUI
        try { //00042823 Para capturar errores que ocurran respecto a la conexión con la base de datos
            connection = getConnection(); //00042823 Se abre la conexión con la base de datos

            PreparedStatement preparedStatement = connection.prepareStatement(DataBaseQueries.getReportQuery(4)); //00042823 Crea una forma segura de agregar parámetros a las consultas, siendo la consulta la necesaria para el reporte D
            preparedStatement.setString(1, facilitador.toString()); //00042823 De manera segura se agrega el parámetro como atributo de un objeto, esto en el primer (y único) parámetro para la consulta

            return preparedStatement.executeQuery(); //00042823 El método devuelve un objeto ResultSet, el cual es el valor de la función. Esto para luego obtener datos y usarlos directamente
        }
        catch (SQLException e){ //00042823 Si llegara a haber algún tipo de error en la conexión o en lógica de la consulta...
            e.printStackTrace(); //00042823 ... Se imprime la cadena de excepciones que produjo el error
            return null; //00042823 Estoy forzado a devolver algo, así que si algo sale mal, la función devuelve nulo (rompemos el programa de todas formas)
        }

    }
}
