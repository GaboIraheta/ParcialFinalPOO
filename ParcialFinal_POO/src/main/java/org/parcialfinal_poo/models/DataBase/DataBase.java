package org.parcialfinal_poo.models.DataBase;

import java.sql.*;

public class DataBase { //00021223 clase para manejar todos los aspectos de consultas a la bbdd

    private static DataBase instance; //00021223 instancia unica de la clase (implementacion de Singleton)

    private DataBase() { //00021223 constructor privado de la clase para que solo pueda ser instanciada por ella misma

    }

    public static DataBase getInstance() { //00021223 metodo para obtener la instancia unica de la clase
        if (instance == null) { //00021223 se verifica que la instancia no sea nula
            instance = new DataBase(); //00021223 si la instancia es nula entonces crea el unico objeto que existira de la clase
        }

        return instance; //00021223 retorna la instancia unica de la clase
    }

    private static final String url = "ruta que utilizara cada una en su pc";
    private static final String user = "su usario";
    private static final String password = "su contraseña";

    private static Connection connection; //00021223 campo para almacenar la conexion a la base de datos

    private static ResultSet resultSet; //00021223 campo para almacenar los resultados de las consultas que lo requieran

    private static PreparedStatement preparedStatement; //00021223 campo para preparar y ejecutar cada consulta requerida

    public static Connection getConnection() throws SQLException { //00021223 metodo para obtener la conexion a la base de datos
        return DriverManager.getConnection(url, user, password); //00021223 retorna la conexion a la base de datos
    }

}
