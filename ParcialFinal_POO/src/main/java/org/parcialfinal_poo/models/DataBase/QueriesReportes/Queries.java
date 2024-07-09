package org.parcialfinal_poo.models.DataBase.QueriesReportes;

import javafx.scene.control.Alert;
import org.parcialfinal_poo.models.Banco.Compra;
import org.parcialfinal_poo.models.Banco.Tarjetas.TipoTarjeta;

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

            PreparedStatement statement = connection.prepareStatement(DataBaseQueries.getReportQuery(1)); //00088023 Se crea el prepared statement para prepararlo para su ejecución
            statement.setDate(1, fecha1);//00088023 Se ajusta el primer parámetro de la query
            statement.setDate(2, fecha2);//00088023 Se ajusta el segundo parámetro de la query
            statement.setInt(3, clienteID);//00088023 Se ajusta el tercer parámetro de la query


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
