package org.parcialfinal_poo.models.DataBase.QueriesReportes;

import org.parcialfinal_poo.models.Banco.Compra;

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

            Statement statement = connection.createStatement(); //00021223 se crea el statement para poder ejecutar la consulta

            resultSet = statement.executeQuery(DataBaseQueries.getReportQuery(1)); //00021223 se ejecuta la query requerida
            //y se guarda el resultado en un objeto ResultSet para poder acceder y manipular los datos obtenidos

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
    public double generarReporteB(int clienteID, int year, int month) {
        //00022423 se define metodo abstracto que se encarga de realizar la consulta a la base de datos para generar reporte A


        double total = 0;//00022423 se declara una variable llamada total y se inicializa en 0, esta almacenará el total gastado

        try {

            connection = getConnection(); //00022423 se establece la conexion a la base de datos a través del método getconnection()

            // 00022423 Se prepara una declaración SQL utilizando una consulta obtenida del método getReportQuery(2) de la clase DataBaseQueries.
            PreparedStatement prepareStatement = connection.prepareStatement(DataBaseQueries.getReportQuery(2));
            //00022423 Se establece el primer parámetro de la consulta preparada como el ID del cliente (clienteID).
            prepareStatement.setInt(1,clienteID);
            // 00022423 Se establece el segundo parámetro de la consulta preparada como el año de la fecha proporcionada.
            prepareStatement.setInt(2,year);
            //00022423 Se establece el tercer parámetro de la consulta preparada como el mes de la fecha proporcionada.
            prepareStatement.setInt(3,month);
            // 00022423 Se ejecuta la consulta y se obtiene el resultado en un objeto ResultSet.
            ResultSet rs = prepareStatement.executeQuery();

            if (rs.next()){
                //// 00022423 Si hay un resultado, se obtiene el valor de la primera columna y se asigna a la variable total.
                total = rs.getDouble(1);
            }

        }catch (SQLException e){
            // 00022423 En caso de que ocurra una excepción SQL, se imprime el mensaje de error.
            System.out.println(e.getMessage());
        }
        //00022423 Se retorna el valor total obtenido en la consulta
        return total;
    }

    @Override
    public ArrayList<String> generarReporteC(int clienteID) {
        return null;
    }
}
