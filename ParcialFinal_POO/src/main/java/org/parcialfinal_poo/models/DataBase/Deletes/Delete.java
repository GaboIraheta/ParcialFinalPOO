package org.parcialfinal_poo.models.DataBase.Deletes;

import javafx.scene.control.Alert;

import java.sql.SQLException;

public class Delete extends DataBaseDelete {

    private static Delete instance; //0021223 instancia unica de la clase

    private Delete() { //00021223 constructor privado para que la clase pueda ser instanciada unicamente por ella misma

    }

    public static Delete getInstance() { //00021223 metodo estatico para obtener la instancia unica de la clase
        if (instance == null) { //00021223 se verifica si la instancia es nula
            instance = new Delete(); //00021223 si es nula entonces se crea el objeto
        }
        return instance; //00021223 retorna la instancia unica
    }

    @Override
    public void deleteCliente(int id) {
        //00021223 se define metodo abstracto para ejecutar la consulta para eliminar registros en la tabla cliente en la bbdd
        //unicamente recibe el ID del cliente que se desea eliminar

        try { //00021223 control de excepciones requerido para ejecutar consultas a bbdd

            connection = getConnection(); //00021223 se establece la conexion a la base de datos

            preparedStatement = connection.prepareStatement(DataBaseDelete.getDeleteQuery(0)); //00021223 se prepara la query
            //la cual espera un unico parametro que es el ID del cliente que se desea eliminar

            preparedStatement.setInt(1, id); //00021223 se le manda el unico parametro que espera la query (ID del cliente)

            int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la query y se guarda el valor de retorno
            //en una variable de tipo entero, retorna el numero de campos afectados

            alerta = new Alert(Alert.AlertType.INFORMATION); //00021223 se inicializa la alerta
            alerta.setTitle("Registro de Cliente"); //00021223 se establece el titulo de la alerta
            alerta.setHeaderText(affectedRows > 0 ? "Registro de cliente eliminado exitosamente"
                    : "Eliminacion de registro de cliente fallido"); //00021223 se inserta la informacion de la ejecucion de la consulta en el cuerpo de la alerta
            alerta.setContentText(affectedRows + "campos afectados"); //0021223 se inserta el texto para el contenido de la alerta
            alerta.showAndWait(); //00021223 muestra la alerta

        } catch (SQLException ex) { //00021223 control de excepciones, cacha un SQLException que seria cualquier fallo en la ejecucion de la consulta
            //todo se debe mandar el mensaje de error por medio de una alerta
            System.out.println(ex.getMessage()); //00021223 se imprime el mensaje de error atrapado
        }
    }

    @Override
    public void deleteTarjeta(int id) {
        //00021223 se define metodo abstracto para ejecutar la consulta para eliminar registros en la tabla tarjeta en la bbdd
        //unicamente recibe el ID de la tarjeta que se desea eliminar

        try { //00021223 control de excepciones requerido para ejecutar consultas a bbdd

            connection = getConnection(); //00021223 se establece la conexion a la base de datos

            preparedStatement = connection.prepareStatement(DataBaseDelete.getDeleteQuery(1)); //00021223 se prepara la query
            //la cual espera un unico parametro el cual es el ID de la tarjeta que se desea eliminar

            preparedStatement.setInt(1, id); //00021223 se le manda el unico parametro que espera la query (ID de la tarjeta)

            int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la query y se guarda el valor de retorno
            //en una variable de tipo entero, retorna el numero de campos afectados

            alerta = new Alert(Alert.AlertType.INFORMATION); //00021223 se inicializa la alerta
            alerta.setTitle("Registro de Tarjetas"); //00021223 se establece el titulo de la alerta
            alerta.setHeaderText(affectedRows > 0 ? "Registro de tarjeta eliminado exitosamente"
                    : "Eliminacion de registro de tarjeta fallido"); //00021223 se inserta la informacion de la ejecucion de la consulta en el cuerpo de la alerta
            alerta.setContentText(affectedRows + "campos afectados"); //0021223 se inserta el texto para el contenido de la alerta
            alerta.showAndWait(); //00021223 muestra la alerta

        } catch (SQLException ex) { //00021223 control de excepciones, cacha un SQLException que seria cualquier fallo en la ejecucion de la consulta
            //todo se debe mandar el mensaje de error por medio de una alerta
            System.out.println(ex.getMessage()); //00021223 se imprime el mensaje de error atrapado
        }
    }

    @Override
    public void deleteCompra(int id) {
        //00021223 se define metodo abstracto para ejecutar la consulta para eliminar registros en la tabla compra en la bbdd
        //unicamente recibe el ID de la compra que se desea eliminar

        try { //00021223 control de excepciones requerido para ejecutar consultas a bbdd

            connection = getConnection(); //00021223 se establece la conexion a la base de datos

            preparedStatement = connection.prepareStatement(DataBaseDelete.getDeleteQuery(2)); //00021223 se prepara la query
            //la cual espera un unico parametro el cual es el ID de la compra que se desea eliminar

            preparedStatement.setInt(1, id); //00021223 se le manda el unico parametro que espera la query (ID de la compra)

            int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la query y se guarda el valor de retorno
            //en una variable de tipo entero, retorna el numero de campos afectados

            alerta = new Alert(Alert.AlertType.INFORMATION); //00021223 se inicializa la alerta
            alerta.setTitle("Registro de Compras"); //00021223 se establece el titulo de la alerta
            alerta.setHeaderText(affectedRows > 0 ? "Registro de compra eliminado exitosamente"
                    : "Eliminacion de compra de tarjeta fallido"); //00021223 se inserta la informacion de la ejecucion de la consulta en el cuerpo de la alerta
            alerta.setContentText(affectedRows + "campos afectados"); //0021223 se inserta el texto para el contenido de la alerta
            alerta.showAndWait(); //00021223 muestra la alerta

        } catch (SQLException ex) { //00021223 control de excepciones, cacha un SQLException que seria cualquier fallo en la ejecucion de la consulta
            //todo se debe mandar el mensaje de error por medio de una alerta
            System.out.println(ex.getMessage()); //00021223 se imprime el mensaje de error atrapado
        }
    }
}
