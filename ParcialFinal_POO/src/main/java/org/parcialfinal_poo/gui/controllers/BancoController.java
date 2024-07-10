package org.parcialfinal_poo.gui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.parcialfinal_poo.models.Banco.Cliente;
import org.parcialfinal_poo.models.Banco.Compra;
import org.parcialfinal_poo.models.Banco.Tarjetas.Facilitador;
import org.parcialfinal_poo.models.Banco.Tarjetas.Tarjeta;
import org.parcialfinal_poo.models.Banco.Tarjetas.TipoTarjeta;
import org.parcialfinal_poo.models.DataBase.DataBase;
import org.parcialfinal_poo.models.DataBase.QueriesReportes.Queries;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

import org.parcialfinal_poo.models.DataBase.Selects.Select;
import org.parcialfinal_poo.models.TextFiles.TextFiles;

import java.sql.Date;
import java.sql.SQLException;

public class BancoController {
    //00022423 // Definición de los elementos de la interfaz de usuario (botones, cuadros combinados, campos de texto, etc.)
    @FXML
    private Button btnConsultar;

    @FXML
    private Tab tabReporteA = new Tab();

    @FXML
    private Tab tabReporteB = new Tab();

    @FXML
    private Tab tabReporteD = new Tab(); //00042823 Objeto de tipo Tab, la pestaña se crea para verificar si está activa

    @FXML
    private Tab tabReporteC = new Tab(); //00088023 Objeto tab para verificación

    @FXML
    private ComboBox<Facilitador> cmbFacilitador = new ComboBox<>(); //00042823 Crea un ComboBox que servirá para elegir el tipo de facilitador de la tarjeta para el reporte D

    @FXML
    private DatePicker dpFechaInicial;

    @FXML
    private DatePicker dpFechaFinal;

    @FXML
    private TextField tfIdClienteRA;

    @FXML
    private TextField tfIdClienteRB;

    @FXML
    private TextField tfIdClienteRC;

    @FXML
    private ChoiceBox<String> cbMes;

    @FXML
    private TextField tfAnio;

    @FXML
    private TextArea taMuestraReporte;

    @FXML
    private ListView<Integer> lvClienteID = new ListView<>(); //00042823 Crea un ListView para el ID del cliente

    @FXML
    private ListView<String> lvClienteNombres = new ListView<>(); //00042823 Crea un ListView para los nombres del cliente

    @FXML
    private ListView<String> lvClienteApellidos = new ListView<>(); //00042823 Crea un ListView para los apellidos del cliente

    @FXML
    private ListView<String> lvClienteDireccion = new ListView<>(); //00042823 Crea un ListView para la dirección del cliente

    @FXML
    private ListView<String> lvClienteNumTelefono = new ListView<>(); //00042823 Crea un ListView para el teléfono del cliente

    @FXML
    private ListView<Integer> lvTarjetaID = new ListView<>(); //00042823 Crea un ListView para el ID de la tarjeta

    @FXML
    private ListView<Integer> lvTarjetaClienteID = new ListView<>(); //00042823 Crea un ListView para el ID del cliente dueño de la tarjeta

    @FXML
    private ListView<String> lvTarjetaNum = new ListView<>(); //00042823 Crea un ListView para el número de la tarjeta, sin censura

    @FXML
    private ListView<Date> lvTarjetaFechaExp = new ListView<>(); //00042823 Crea un ListView para la fecha de expiración de la tarjeta

    @FXML
    private ListView<Facilitador> lvTarjetaFacilitador = new ListView<>(); //00042823 Crea un ListView para el facilitador de la tarjeta

    @FXML
    private ListView<TipoTarjeta> lvTarjetaTipo = new ListView<>(); //00042823 Crea un ListView para el tipo de la tarjeta (crédito o débito)

    @FXML
    private ListView<Integer> lvCompraID = new ListView<>(); //00042823 Crea un ListView para el ID de la compra

    @FXML
    private ListView<Date> lvCompraFecha = new ListView<>(); //00042823 Crea un ListView para la fecha de la compra

    @FXML
    private ListView<Double> lvCompraMonto = new ListView<>(); //00042823 Crea un ListView para el monto de la compra

    @FXML
    private ListView<Integer> lvCompraTarjetaID = new ListView<>(); //00042823 Crea un ListView para el ID de la Tarjeta con la que se hizo la compra

    @FXML
    private ListView<String> lvCompraDescripcion = new ListView<>(); //00042823 Crea un ListView para la descripción de la compra (debatí si la tabla debería mostrar esto, legítimamente)

    @FXML
    private Label
            errorLabel1,
            errorLabel2,
            errorLabel3,
            errorLabel4, //00042823 Label para el reporte B que indicará al cliente algún error en el ingreso de datos
            errorLabel5, //00042823 Otro label, para otro campo, siempre del reporte B
            errorLabel6, //00042823 Y un último label para la validación de datos para el reporte B
            errorLabel7, //00042823 Label para validar datos para el reporte C
            errorLabel8; //00042823 Label para validar datos para el reporte D


    @FXML
    // 00022423 Método que se ejecuta cuando la interfaz gráfica se inicializa
    public void initialize() {

        errorLabel1.setVisible(false);
        errorLabel2.setVisible(false);
        errorLabel3.setVisible(false);
        errorLabel4.setVisible(false);
        errorLabel5.setVisible(false);
        errorLabel6.setVisible(false);
        errorLabel7.setVisible(false);
        errorLabel8.setVisible(false);

        cmbFacilitador.getItems().addAll(Facilitador.Visa, Facilitador.MasterCard, Facilitador.AmericanExpress); //00042823 Se meten las opciones para el ComboBox de facilitadores para generar el reporte D

        fillTablaClientes(); //00042823 Se llena la tabla de clientes para el READ del CRUD
        fillTablaTarjetas(); //00042823 Se llena la tabla de tarjeta, parte del READ
        fillTablaCompras(); //00042823 Se llena la tabla de compras, parte del READ para compras

        //00022423 Llenar el ChoiceBox cbMes con los nombres de los meses
        cbMes.setItems(FXCollections.observableArrayList("enero",
                "febrero", "marzo", "abril", "mayo", "junio", "agosto",
                "septiembre", "octubre", "noviembre", "diciembre"));
    }

    private void mostrarReporteA() {
        errorLabel1.setVisible(false);
        errorLabel2.setVisible(false);
        errorLabel3.setVisible(false);


        boolean flag = false; //TODO: Comentar (Gabo)

        try{ //00042823 Nueva validación, que el ID sea un entero
            Integer.parseInt(tfIdClienteRA.getText()); //00042823 Hace la transformación solo para ver si lanza una excepción
        }
        catch (NumberFormatException e){ //00042823 Si se lanza la excepción, entonces...


            if (tfIdClienteRA.getText().isEmpty()) {
                errorLabel1.setText("Campo obligatorio");
            }
            else {
                errorLabel1.setText("Ingrese un ID válido"); //00042823 Se cambia el label para hacerle ver el problema al usuario
            }
            errorLabel1.setVisible(true); //00042823 El label, invisible por defecto, se hace visible para resolver el error de capa 8
            flag = true; //00042823 Se levanta la bandera
        }

        if (dpFechaInicial.getValue() == null) {
            errorLabel2.setText("Campo obligatorio");
            errorLabel2.setVisible(true);
            flag = true;
        }

        if (dpFechaFinal.getValue() == null) {
            errorLabel3.setText("Campo obligatorio");
            errorLabel3.setVisible(true);
            flag = true;
        }

        if (!flag) {

            ArrayList<Compra> compras = Queries.getInstance().generarReporteA(Integer.parseInt(tfIdClienteRA.getText()),
                    Date.valueOf(dpFechaInicial.getValue()), Date.valueOf(dpFechaFinal.getValue()));

            String text = "";

            for (Compra compra : compras) {
                text = text.concat(compra.getCodigo() + "\n" + compra.getFechaCompra() + "\n" + compra.getDescripcion() + "\n" +
                        compra.getMonto() + "\n" + compra.getTarjetaID() + "\n\n");
            }

            if (!text.isEmpty()) { //00042823 Esto solo debería ocurrir si el ID del cliente no existe, que daría como resultado un ResultSet vacío y, por tanto, nada que reportar
                taMuestraReporte.setText(text);
                TextFiles.createFile('A', text); //TODO: Comentar (Gabo)
            } else { //00042823 Si, en efecto, no hay nada que reportar, pues entonces no se crea el reporte (¿para qué tener un .txt vacío?)
                taMuestraReporte.setText("Nada que reportar..."); //00042823 Muestra en el TextArea que no hay nada que reportar
            }
        }
    }

    private void mostrarReporteB() {
        errorLabel4.setVisible(false); //00042823 Por defecto...
        errorLabel5.setVisible(false); //00042823 Los labels de error...
        errorLabel6.setVisible(false); //00042823 Son invisibles

        // 00022423 Definir el rango de años válidos
        int anioMinimo = 1900;
        int anioMaximo = 2100;
        String mes = cbMes.getValue();

        boolean flag = false; //00042823 Se crea una bandera para validar datos

        try { //00042823 Se verifica que el año ingresado sea un número para empezar
            Integer.parseInt(tfIdClienteRB.getText()); //00042823 Si no es número, lanza una excepción
        } catch (NumberFormatException e) { //00042823 Se atrapa la excepción, si ocurre
            if (tfIdClienteRB.getText().isEmpty()) { //00042823 Se verifica si el usuario llenó el campo
                errorLabel4.setText("Campo obligatorio"); //00042823 El usuario no llenó el campo
                errorLabel4.setVisible(true); //00042823 Se hace visible el label
                flag = true; //00042823 Se levanta la bandera
            }
            else {
                errorLabel4.setText("Ingrese un año válido"); //00042823 Establece el tipo de error
                errorLabel4.setVisible(true); //00042823 Y se lo muestra al usuario
                flag = true; //00042823 Se levanta la bandera
            }
        }

        if (mes == null) {//00042823 La única forma de que mes sea null es que no ha seleccionada nada
            errorLabel5.setText("Campo obligatorio"); //00042823 Se le olvidó llenar este campo también
            errorLabel5.setVisible(true); //00042823 Se hace visible el label
            flag = true; //00042823 Se levanta la bandera
        }

        try { //00042823 Se verifica que el año ingresado sea un número para empezar
            int anio = Integer.parseInt(tfAnio.getText()); //00042823 Si no es número, lanza una excepción

            if (anio >= anioMaximo || anio < anioMinimo) { //00042823 Verifica si el año ingresado se encuentra dentro del rango establecido arbitrariamente
                errorLabel6.setText("Año fuera de rango (1900 - 2100)"); //00042823 Es medianamente realista, además de permitir evitar números negativos
                errorLabel6.setVisible(true); //00042823 Se hace visible el label
                flag = true; //00042823 Se levanta la bandera
            }
        } catch (NumberFormatException e) { //00042823 Se atrapa la excepción, si ocurre
            if (tfAnio.getText().isEmpty()) { //00042823 Si el campo está vacío
                errorLabel6.setText("Campo obligatorio"); //00042823 Y también se le olvidó llenar este campo
            }
            else { //00042823 Entonces, el usuario ingreso algo que no es un número
                errorLabel6.setText("Ingrese un año válido"); //00042823 Establece el tipo de error
            }
            errorLabel6.setVisible(true); //00042823 Se hace visible el label
            flag = true;//00042823 Se levanta la bandera
        }

        if (!flag) { //00042823 Si la bandera NO está levantada

            // 00022423 Obtener los valores ingresados por el usuario
            int clienteID = Integer.parseInt(tfIdClienteRB.getText());
            int anio = Integer.parseInt(tfAnio.getText());

            // 00022423 Convertir el mes en índice (1-12)
            int mesIndex = cbMes.getItems().indexOf(mes) + 1;
            // 00022423 Obtener el total de gasto del cliente para el mes y año seleccionados
            double totalGasto = Queries.getInstance().generarReporteB(clienteID, anio, mesIndex);
            // 00022424 Mostrar el resultado en el TextArea

            String text = "El gasto total del Cliente ID " + clienteID + " en " + mes.toLowerCase() + " " + anio + " es: $" + totalGasto;

            if (totalGasto != -1) { //00042823 Si el gasto es -1, entonces el cliente no existe
                taMuestraReporte.setText(text);
                TextFiles.createFile('A', text); //TODO: Comentar (Rocía)
            } else { //00042823 Si, en efecto, no hay nada que reportar, pues entonces no se crea el reporte (¿para qué tener un .txt vacío?)
                taMuestraReporte.setText("Nada que reportar..."); //00042823 Muestra en el TextArea que no hay nada que reportar
            }
        }
    }

    private void mostrarReporteC() {//00088023 Método encargado de realizar el reporte C
        errorLabel7.setVisible(false);

        boolean flag = false; //00042823 Se crea una bandera para validar datos

        try { //00042823 Verifica si el TextField tiene un número entero
            Integer.parseInt(tfIdClienteRC.getText()); //00042823 Lanza una excepción si el número no es entero
        } catch (NumberFormatException e) { //00042823 En caso de que se haya lanzado una excepción...
            if (tfIdClienteRC.getText().isEmpty()) { //00042823 Se verifica si el usuario llenó el campo
                errorLabel4.setText("Campo obligatorio"); //00042823 El usuario no llenó el campo
            } else { //00042823 Entonces, el usuario ingreso algo que no es un número
                errorLabel4.setText("Ingrese un año válido"); //00042823 Establece el tipo de error
            }
            errorLabel4.setVisible(true); //00042823 Se hace visible el label
            flag = true; //00042823 Se levanta la bandera
        }

        if (!flag) { //00042823 Si la bandera no está levantada...
            String text = "";
            taMuestraReporte.setText("");//00088023 Primeramente se limpia el textArea
            int ClienteID = Integer.parseInt(tfIdClienteRC.getText());//00088023 Se Recibe el id del cliente del texArea y lo convierte en entero
            ArrayList<String> credito = new ArrayList<>(), debito = new ArrayList<>();//00088023 Inicializa dos arrays, uno que guarda las tarjetas de crédito, y el otro de débito
            Queries.getInstance().generarReporteC(ClienteID, credito, debito);//00088023 Llama al generaador de reporte que llene los arrays

            for (Cliente c : Select.getInstance().selectCliente()) { //00042823 Llamamos a la base de datos para verificar si, en efecto, existe el cliente
                if (ClienteID == c.getId()) { //00042823 Verifica si clienteID coincide con el ID de algún cliente,

                    text = "Tarjetas de crédito del cliente:\n"; //00088023 Empieza a darle formato al texto a mostrar
                    if (credito.isEmpty()) {//00088023 Revisa si el array está vacio
                        text = text.concat("N/A");//00088023 Si está vacio entonces le pone como texto "N/A"
                    } else {
                        for (String tarjeta : credito) { //00088023 Si no está vacio entonces empieza a ingresar todas las tarjetas de crédito
                            text = text.concat(tarjeta + "\n");
                        }
                    }

                    text = text.concat("\n\nTarjetas de débito del cliente:\n");//00088023 Le da formato al texto para mostrar las tarjetas de débito

                    if (debito.isEmpty()) {//00088023 Revisa si el array no está vacio
                        text = text.concat("N/A");//00088023 Si lo está, entonces escribe "N/A"
                    } else {
                        for (String tarjeta : debito) {//00088023 Si no esta vacio, entonces escribe todas las tarjetas en el texto
                            text = text.concat(tarjeta + "\n");
                        }
                    }

                    taMuestraReporte.setText(text);//00088023 Muestra el texto en el TextArea
                    TextFiles.createFile('C', text); //TODO: comentar

                    break; //00042823 Ya no continuará recorriendo la lista de IDs

                } else { //Si no encuentra una coincidencia, entonces no hay nada que reportar
                    taMuestraReporte.setText("Nada que reportar..."); //00042823 Muestra en el TextArea que no hay nada que reportar
                }
            }
        }
    }

    private void mostrarReporteD() {
        errorLabel8.setVisible(false);

        if (cmbFacilitador.getValue() != null) { //00042823 Si hay una opción selecciona por cmbFacilitador...
            String text = ""; //00042823 Es una cadena vacía que tiene como propósito concatenar el contenido completo del reporte D
            ResultSet rs = Queries.getInstance().generarReporteD(cmbFacilitador.getValue()); //00042823 Se llama al singleton Queries para hacer la consulta para el reporte D con el facilitador seleccionado, el cual se almacena en un ResultSet

            boolean flag = false; //00042823 Se crea una bandera que sirva para indicar si hay valores en el ResultSet
            try { //00042823 El método next() para ResultSet lanza SQLExceptions que no pueden dejarse irresolubles
                while (!flag) { //00042823 Bucle para recorrer las "filas" de la tabla a consultar
                    if (rs.next()) {
                        text = text.concat( //00042823 Por cada cliente, se concatena la siguiente información
                                "Cliente: " + rs.getInt("id") + "\n" //00042823 El ID del cliente
                                        + rs.getString("nombres") + " " + rs.getString("apellidos") + "\n" //00042823 Nombre completo del cliente
                                        + "Teléfono: " + rs.getString("numTelefono") + "\n" //00042823 Número de teléfono del cliente
                                        + "Total de compras: " + rs.getString("compras") + "\n" //00042823 Cuántas compras ha realizado el cliente por medio del facilitador
                                        + "Gasto total: $" + rs.getDouble("total") + "\n\n" //00042823 Cuánto ha gastado en total el cliente por medio del facilitador
                        );
                    } else { //00042823 Si ya no hay filas por recorrer en el ResultSet
                        flag = true; //00042823 Se levanta la bandera

                        DataBase dataBase = new DataBase() {}; //00042823 Se instancia la clase abstracta (this is so wrong) para poder tener acceso a la conexión de la base de datos
                        dataBase.getConnection().close(); //00042823 Se cierra la conexión, que quedaba abierta por usar el ResultSet
                    }
                }
                taMuestraReporte.setText(text); //00042823 Escribe el texto entero concatenado en el TextArea donde se ven los reportes
                TextFiles.createFile('D',text); //TODO: comentar

            } catch (SQLException e) { //00042823 Si algo malo ocurre, entonces atrapa la excepción...
                e.printStackTrace(); //00042823 ... Para luego imprimir la cadena de errores de la excepción
            }

        } else { //00042823 Si no hay nada seleccionado por cmbFacilitador
            errorLabel8.setText("Campo obligatorio");
            errorLabel8.setVisible(true);
        }
    }

    // 00022423 Método para mostrar una alerta con un título y mensaje específicos
    private void mostrarAlerta(String titulo, String mensaje) {
        // 00022423 Crear una nueva alerta de tipo ERROR
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        // 00022423 Establecer el título de la alerta con el valor pasado como parámetro
        alerta.setTitle(titulo);
        // 00022423 Establecer el encabezado de la alerta como null para no mostrar ningún encabezado
        alerta.setHeaderText(null);
        // 00022423 Establecer el contenido de la alerta con el mensaje pasado como parámetro
        alerta.setContentText(mensaje);
        // 00022423 Mostrar la alerta y esperar hasta que el usuario la cierre
        alerta.showAndWait();
    }

    public void handleBtnConsultar() { //00042823 Se define la función que se realizará cuando se haga una acción con btnGenerarReporte
        if (tabReporteA.isSelected()) {
            mostrarReporteA();
        } else if (tabReporteB.isSelected()) {
            mostrarReporteB();
        } else if (tabReporteC.isSelected()) {
            mostrarReporteC();
        } else if (tabReporteD.isSelected()) { //00042823 Si la pestaña para el reporte D se encuentra activa
            mostrarReporteD();
        } else { //00042823 Si no hay nada seleccionado por cmbFacilitador
            //TODO: Crear una alerta, o bien un label error, ambas son opciones válidas
        }

    }

    private void fillTablaClientes() { //Función para inicializar la tabla de clientes en la interfaz, un conjunto de ListViews que hacen de columnas

        for(Cliente c : Select.getInstance().selectCliente()){ //00042823 Con la colección de clientes, por cada uno de los que exista...
            lvClienteID.getItems().add(c.getId()); //00042823 ... Agrega el ID del cliente al ListView...
            lvClienteApellidos.getItems().add(c.getApellidos()); //00042823 ... Y los apellidos del cliente a otra ListView...
            lvClienteNombres.getItems().add(c.getNombres()); //00042823 ... Y los nombres del cliente...

            if (c.getDireccion() == null){ //00042823 No debería de haber nulos, pero es una columna que se agregó después, así que solo por eso...
                lvClienteDireccion.getItems().add("No hay dirección guardada"); //00042823 Si la dirección es nula, entonces se mete este texto en vez de mostrar la dirección
            }
            else lvClienteDireccion.getItems().add(c.getDireccion()); //00042823 En caso de que no sea nula (como debe ser), muestra la dirección...

            lvClienteNumTelefono.getItems().add(c.getTelefono()); //00042823 Ya de último se agrega el teléfono del cliente
        }
    }

    private void fillTablaTarjetas() { //00042823 Función para llenar la tabla de tarjetas en la interfaz

        for(Tarjeta t : Select.getInstance().selectTarjeta()){ //00042823 Se obtiene la lista de tarjetas y por cada uno de los elementos en la lista...
            lvTarjetaID.getItems().add(t.getId()); //00042823 ... Se agrega el ID a la primera columna de la tabla (el ListView)...
            lvTarjetaClienteID.getItems().add(t.getClienteID()); //00042823 ... Se agrega el ID del cliente (sería mejor con el nombre completo del cliente, pero así se va) a la segunda columna...
            lvTarjetaNum.getItems().add(t.getNumeroTarjeta()); //00042823 ... Se agrega el número de tarjeta, sin censurar...
            lvTarjetaFechaExp.getItems().add(t.getFechaExpiracion()); //00042823 ... Se agrega la fecha de expiración de la tarjeta...
            lvTarjetaFacilitador.getItems().add(t.getFacilitador()); //000042823 ... Se agrega el facilitador de la tarjeta...
            lvTarjetaTipo.getItems().add(t.getTipo()); //00042823 ... Y se agrega el tipo de la tarjeta
        }
    }

    private void fillTablaCompras(){ //00042823 Función para llenar la tabla de compras en la interfaz

        for(Compra c : Select.getInstance().selectCompra()){
            lvCompraID.getItems().add(c.getCodigo());
            lvCompraFecha.getItems().add(c.getFechaCompra());
            lvCompraMonto.getItems().add(c.getMonto());
            lvCompraTarjetaID.getItems().add(c.getTarjetaID());
            lvCompraDescripcion.getItems().add(c.getDescripcion());
        }
    }
}