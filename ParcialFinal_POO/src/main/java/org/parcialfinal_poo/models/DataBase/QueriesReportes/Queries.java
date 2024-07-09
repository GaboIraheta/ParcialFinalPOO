package org.parcialfinal_poo.models.DataBase.QueriesReportes;

import javafx.scene.control.Alert;
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

            connection.close();

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
    public void generarReporteC(int clienteID, ArrayList<String> tarjetasCredito, ArrayList<String> tarjetasDebito) {
        Connection connection = null;
        try {
            connection = getConnection();

            PreparedStatement stm = connection.prepareStatement(DataBaseQueries.getReportQuery(3));
            stm.setString(1, "Credito");
            stm.setInt(2, clienteID);
            ResultSet rs = stm.executeQuery();
            fillArraysTarjetas(rs, tarjetasCredito);

            stm.setString(1, "Debito");
            rs = stm.executeQuery();
            fillArraysTarjetas(rs, tarjetasCredito);


        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se encontro el cliente o hubo un error al ingresar los datos");
            alert.showAndWait();
        } finally {
            if (connection != null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Error al cerrar el archivo");
                    alert.showAndWait();
                }
            }
        }

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


    private void fillArraysTarjetas(ResultSet rs, ArrayList<String> tarjetas) throws SQLException {
        while (rs.next()){
            StringBuilder num = new StringBuilder();
            String[] Tarjeta = rs.getString("numTarjeta").split(" ");
            for (int i = 0; i < Tarjeta.length; i++){
                if (i == Tarjeta.length - 1){
                    num.append(Tarjeta[i]);
                } else {
                    num.append("XXXX ");
                }
            }

            tarjetas.add(String.valueOf(num));
        }
    }
}
