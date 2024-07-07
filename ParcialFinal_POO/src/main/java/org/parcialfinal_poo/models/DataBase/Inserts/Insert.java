package org.parcialfinal_poo.models.DataBase.Inserts;

import javafx.scene.control.Alert;
import org.parcialfinal_poo.models.Banco.Tarjetas.Facilitador;
import org.parcialfinal_poo.models.Banco.Tarjetas.TipoTarjeta;

import java.sql.Date;
import java.sql.SQLException;

public class Insert extends DataBaseInserts {

    private static Insert instance; //00021223 instancia unica de la clase (implementacion de Singleton)

    private Insert() { //00021223 constructor privado de la clase para que solo pueda ser instanciada por ella misma

    }

    public static Insert getInstance() { //00021223 metodo para obtener la instancia unica de la clase
        if(instance == null) { //00021223 se verifica que la instancia no sea nula
            instance = new Insert(); //00021223 si la instancia es nula entonces crea el unico objeto que existira de la clase
        }
        return instance; //00021223 retorna la instancia unica de la clase
    }

    @Override
    public void registrarCliente(String nombres, String apellidos, String direccion, String numTelefono) {
        //00021223 se define metodo abstracto que se encarga de registrar a un cliente en la base de datos, recibe todos los campos necesarios
        //que debe guardar la tabla de Cliente en la bbdd

        try { //00021223 se realiza el control de excepciones requerido para consultas a bases de datos

            connection = getConnection(); //00021223 se establece la conexion a la base de datos por medio del
            //metodo estatico getConnection

            preparedStatement = connection.prepareStatement(DataBaseInserts.getInsertQuery(0)); //00021223 se prepara la query que se va a ejecutar
            //dicha query espera parametros los cuales son los valores de los campos a insertar

            preparedStatement.setString(1, nombres); //00021223 se manda el primer valor (nombres del cliente)
            preparedStatement.setString(2, apellidos); //00021223 segundo valor (apellidos del cliente)
            preparedStatement.setString(3, direccion); //00021223 tercer valor (direccion del cliente)
            preparedStatement.setString(4, numTelefono); //00021223 cuarto valor (telefono del cliente)

            int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la query y se guarda el valor
            //de retorno en una variable de tipo entero, dado que retorna los el numero de campos afectados

            alerta = new Alert(Alert.AlertType.INFORMATION); //00021223 se inicializa la alerta
            alerta.setTitle("Registro de Cliente"); //00021223 se establece el titulo de la alerta
            alerta.setHeaderText(affectedRows > 0 ? "Cliente registrado exitosamente"
                    : "Registro de cliente fallido"); //00021223 se inserta la informacion de la ejecucion de la consulta en el cuerpo de la alerta
            alerta.setContentText(affectedRows + "campos afectados"); //0021223 se inserta el texto para el contenido de la alerta

        } catch (SQLException e) { //00021223 control de excepciones, cacha un SQLException que seria cualquier fallo en la ejecucion de la consulta
            //todo se debe mandar el mensaje de error por medio de una alerta
            System.out.println(e.getMessage()); //00021223 se imprime el mensaje de error atrapado
        }
    }

    @Override
    public void registrarTarjeta(int clienteID, String numTarjeta, Date fechaExp,
                                 TipoTarjeta tipo, int facilitadorID) {
        //00021223 se define metodo abstracto pare registrar una tarjeta en la base de datos, recibe todos los campos necesarios que
        //debe guardar la tabla Tarjeta en la bbdd

        try { //00021223 control de excepciones requerido para realizar consultas a bbdd

            connection = getConnection(); //00021223 se establece la conexion a la bbdd por medio del metodo estatico getConnection

            preparedStatement = connection.prepareStatement(DataBaseInserts.getInsertQuery(1)); //00021223 se prepara la query que se va a realizar
            //la query espera como parametros los valores que se van a insertar en la tabla Tarjeta

            preparedStatement.setInt(1, clienteID); //00021223 se manda el primer valor (ID del cliente al que esta asociada la tarjeta)
            preparedStatement.setString(2, numTarjeta); //00021223 segundo valor (numero de la tarjeta)
            preparedStatement.setDate(3, fechaExp); //00021223 tercer valor (fecha de expiracion)
            preparedStatement.setString(4, String.valueOf(tipo)); //00021223 cuarto valor (tipo de tarjeta: credito o debito)
           //todo para el facilitador se debe pasar un entero, hay que cambiar la logica de como se manda el ID del facilitador
            preparedStatement.setInt(5, facilitadorID); //00021223 quinto valor (ID del facilitador al que esta asociada la tarjeta)

            int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la consulta y se almacena en una variable de tipo entero

            alerta = new Alert(Alert.AlertType.INFORMATION); //0021223 se inicializa una alerta
            alerta.setTitle("Registro de Tarjeta"); //00021223 se establece el titulo de la alerta
            alerta.setHeaderText(affectedRows > 0 ? "Tarjeta registrada exitosamente"
                    : "Registro de tarjeta fallido"); //00021223 se inserta la informacion de la ejecucion de la consulta en el cuerpo de la alerta
            alerta.setContentText(affectedRows + "campos afectados"); //00021223 se insertan el numero de campos afectados en el contenido de la alerta

        } catch (SQLException e) { //00021223 control de excepciones para atrapar cualquier excepcion referente a las consultas
            //todo se debe agregar una alerta
            System.out.println(e.getMessage()); //00021223 se imprime el mensaje de error
        }
    }

    @Override
    public void registrarCompra(Date fechaCompra, double monto, String descripcion, int tarjetaID) {
        //00021223 se define metodo abstracto para registrar una compra en la base de datos, recibe todos los campos necesarios
        //que debe guardar la tabla Compra en la bbdd

        try { //00021223 control de excepciones requerido para realizar consultas a bbdd

            connection = getConnection(); //00021223 se establece la conexion a la bbdd por medio del metodo estatico getConnection

            preparedStatement = connection.prepareStatement(DataBaseInserts.getInsertQuery(2)); //00021223 se prepara la query que se va a realizar
            //la query espera como parametros los valores que se van a insertar en la tabla Compra

            preparedStatement.setDate(1, fechaCompra); //00021223 se manda el primer valor (fecha de la compra)
            preparedStatement.setDouble(2, monto); //00021223 segundo valor (monto)
            preparedStatement.setString(3, descripcion); //00021223 tercer valor (descripcion)
            preparedStatement.setInt(4, tarjetaID); //00021223 cuarto valor (ID de la tarjeta que realiza la compra)

            int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la consulta y se almacena en una variable de tipo entero

            alerta = new Alert(Alert.AlertType.INFORMATION); //0021223 se inicializa una alerta
            alerta.setTitle("Registro de Compra"); //00021223 se establece el titulo de la alerta
            alerta.setHeaderText(affectedRows > 0 ? "Compra registrada exitosamente"
                    : "Registro de compra fallido"); //00021223 se inserta la informacion de la ejecucion de la consulta en el cuerpo de la alerta
            alerta.setContentText(affectedRows + "campos afectados"); //00021223 se insertan el numero de campos afectados en el contenido de la alerta

        } catch (SQLException e) { //00021223 control de excepciones para atrapar cualquier excepcion referente a las consultas
            //todo se debe agregar una alerta
            System.out.println(e.getMessage()); //00021223 se imprime el mensaje de error
        }
    }
}
