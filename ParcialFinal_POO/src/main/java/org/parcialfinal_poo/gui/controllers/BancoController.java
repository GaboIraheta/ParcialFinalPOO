package org.parcialfinal_poo.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.parcialfinal_poo.models.Banco.Compra;
import org.parcialfinal_poo.models.Banco.Tarjetas.Facilitador;
import org.parcialfinal_poo.models.Banco.Tarjetas.Tarjeta;
import org.parcialfinal_poo.models.Banco.Tarjetas.TipoTarjeta;
import org.parcialfinal_poo.models.DataBase.DataBase;
import org.parcialfinal_poo.models.DataBase.Inserts.Insert;
import org.parcialfinal_poo.models.DataBase.QueriesReportes.Queries;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

import org.parcialfinal_poo.models.DataBase.QueriesReportes.Queries;
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
    private ComboBox<?> cmbTablaConsultada;

    @FXML
    private DatePicker dpFechaInicial;
    private DatePicker dpFechaFinalRA;

    @FXML
    private DatePicker dpFechaInicialRB;

    @FXML
    private DatePicker dpFechaFinal;

    @FXML
    private DatePicker dpFechaFinalRB, dpExp, dpDate;

    @FXML
    private TextField tfIdClienteRA, tfMonto;

    @FXML
    private TextField tfIdClienteRB;

    @FXML
    private TextField tfIdClienteRC;

    @FXML
    private ChoiceBox<String> cbMes;

    @FXML
    private TextField tfAnio;

    @FXML
    private TextArea taMuestraReporte, taDescription;

    @FXML
    private Label campoObligatorio1, campoObligatorio2, campoObligatorio3;

    @FXML
    private TextField tfNames, tfLastNames, tfAdress, tfTelephone, tfCardNum;

    @FXML
    private ChoiceBox<String> cbOwner, cbProvider, cbType, cbCard;


    @FXML
    // 00022423 Método que se ejecuta cuando la interfaz gráfica se inicializa
    public void initialize() {

        campoObligatorio1.setVisible(false);
        campoObligatorio2.setVisible(false);
        campoObligatorio3.setVisible(false);

        cmbFacilitador.getItems().addAll(Facilitador.Visa, Facilitador.MasterCard, Facilitador.AmericanExpress);
        ObservableList<Facilitador> facilitadores = FXCollections.observableArrayList(cmbFacilitador.getItems()); //00042823 Se crea una lista observable con el único propósito de agregar opciones a cmbFacilitador
        cmbFacilitador.setItems(facilitadores); //00042823 Se definen las opciones de cmbFacilitadores por medio de la lista observable

        // 00022423 Asignar una acción al botón btnConsultar

        //00022423 Llenar el ChoiceBox cbMes con los nombres de los meses
        cbMes.setItems(FXCollections.observableArrayList("enero",
                "febrero", "marzo", "abril", "mayo", "junio", "agosto",
                "septiembre", "octubre", "noviembre", "diciembre"));
    }

    public void insertCustomers(){//00088023 Método encargado de realizar la validación de datos y de insertar nuevos clientes
        Alert alert = new Alert(Alert.AlertType.INFORMATION);//00088023 Se inicializa una alerta
        if (tfNames.getText().isEmpty() || tfLastNames.getText().isEmpty() || tfAdress.getText().isEmpty() || tfTelephone.getText().isEmpty()){ //00088023 Verifica que ningún campo se encuentre vacio
            alert.setAlertType(Alert.AlertType.WARNING); //00088023 En el caso de que alguno se encuentre vacio, informa mediante una alerta
            alert.setHeaderText(null);
            alert.setTitle("Campos incompletos");
            alert.setContentText("Por favor complete los campos incompletos");
            alert.showAndWait();
            return; //00088023 Termina la función para que no intente agregar
        }

        Insert.getInstance().registrarCliente(tfNames.getText(), tfLastNames.getText(), tfAdress.getText(), tfTelephone.getText()); //00088023 Toma los datos de los campos e ingresa al nuevo cliente
        alert.setTitle("Ingreso Realizado"); //00088023 Crea la alerta para confirmar el ingreso del nuevo cliente
        alert.setHeaderText(null);
        alert.setContentText("Se ha agregado un cliente exitosamente");
        alert.showAndWait();

    }

    public void insertCard(){ //00088023 Método encargado de verificar y agregar nuevas tarjetas
        Insert insert = Insert.getInstance(); //00088023 Solicita una instancia de la clase Insert
        Alert alert = new Alert(Alert.AlertType.WARNING);//00088023 Inicializa una alerta
        if (dpExp.getValue() == null || cbOwner.getValue() == null || tfCardNum.getText().isEmpty() || cbProvider.getValue() == null || cbType.getValue() == null){//00088023 Verifica que ningún campo se encuentre vacio
            alert.setHeaderText(null);//00088023 Si alguno esta vacio, se le hace saber al usuario mediante una alerta
            alert.setTitle("Campos incompletos");
            alert.setContentText("Por favor complete los campos incompletos");
            alert.showAndWait();
            return;//00088023 Termina el método
        }

        String[] cardNum = tfCardNum.getText().split(" ");//00088023 Toma el número de tarjeta ingresada en el textfield y lo divide separándolos por espacios para confirmar el formato de la tarjeta
        if (cardNum.length != 4){//00088023 verifíca si la tarjeta está separada por espacios es igual a 4 (por el formato de las tarjetas)
            alert.setAlertType(Alert.AlertType.WARNING);//00088023 Si no cumple con el formato, se lo hace saber al usuario
            alert.setHeaderText(null);
            alert.setTitle("Formato de tarjeta incorrecto");
            alert.setContentText("El formato de la tarjeta ingresada no es válida");
            alert.showAndWait();
            return;//00088023 termina el método
        }

        for (String s : cardNum) {//00088023 Bucle que busca verificar si cada separación de la tarjeta posee exactamente 4 dígitos
            if (s.length() != 4) {
                alert.setAlertType(Alert.AlertType.WARNING);//00088023 En el caso que no, se lo hace saber al usuario mediante una alerta
                alert.setHeaderText(null);
                alert.setTitle("Formato de tarjeta incorrecto");
                alert.setContentText("El formato de la tarjeta ingresada no es válida");
                alert.showAndWait();
                return;//00088023 Termina la función
            }
        }

        try {
            insert.registrarTarjeta(Integer.parseInt(String.valueOf(cbOwner.getValue().charAt(0))), tfCardNum.getText(),
                    Date.valueOf(dpExp.getValue()), TipoTarjeta.valueOf(cbType.getValue()),
                    Facilitador.valueOf(cbProvider.getValue()));
            //00088023 Si pasa todas las verificaciones, entonces toma los datos de los campos y los inserta a la base de datos

        }catch (Exception e){//00088023 Si un error ocurre (principalmente de meter letras donde deben ir números) Se lo hace saber al usuario y no inserta los datos
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Formato de datos incorrecto");
            alert.setHeaderText(null);
            alert.setContentText("El formato de algún dato ingresado no es válido");
            alert.showAndWait();
        }
    }

    public void insertCompra(){//00088023 Método encargado de verificar e insertar los datos de compras
        Alert alert = new Alert(Alert.AlertType.INFORMATION);//00088023 Se inicializa una alerta
        Insert insert = Insert.getInstance();//00088023 Se obtiene una instancia de la clase insert
        if (dpDate.getValue() == null || tfMonto.getText().isEmpty() || taDescription.getText().isEmpty() || cbCard.getValue() == null){//00088023 Verifica si los campos están vacios
            alert.setAlertType(Alert.AlertType.WARNING);//00088023 Si alguno está vacio, entonces se lo hace saber al usuario
            alert.setHeaderText(null);
            alert.setTitle("Campos incompletos");
            alert.setContentText("Por favor complete los campos incompletos");
            alert.showAndWait();
            return;//00088023 Termina el método
        }
        try {
            insert.registrarCompra(Date.valueOf(dpDate.getValue()), Double.parseDouble(tfMonto.getText()),taDescription.getText(), Integer.parseInt(String.valueOf(cbCard.getValue().charAt(0))));//00088023 Agrega los datos de los campos a la tabla de compras
        } catch (Exception e){//00088023 Si hubo un error con el formato de los datos, se lo hace saber al usuario
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Algunos datos no cumplen el formato\n\nNota: el monto no debe llevar el símbolo de dolar. Ejemplo: 23.50");
            alert.showAndWait();
        }
    }

    public void initializeBuyTab(){//00088023 Método encargado de inicializar los choice box al abrir la pestaña de agregar compras
        Select select = Select.getInstance();//00088023 solicita una instancia de select
        ArrayList<String> tarjetas = new ArrayList<>();//00088023 Crea una arraylist donde se guardarán las tarjetas habidas
        String[] digitos;//00088023 Se crea un array de strings donde serán separadas las tarjetas
        String censored;//00088023 String final donde se encontrarán las tarjetas censuradas
        for (Tarjeta tarjeta : select.selectTarjeta()){//00088023 Recorre la lista de tarjetas almacenadas en la base de datos
            censored = "";//00088023 Inicializa por cada iteración el string censored
            digitos = tarjeta.getNumeroTarjeta().split(" ");//00088023 Separa la tarjeta por espacios
            for (int i = 0; i < digitos.length; i++){//00088023 recorre la tarjeta separada
                if (i == digitos.length -1){
                    censored = censored.concat(digitos[i]);//00088023 Si el conjunto de dígitos es el último, ingresa los dígitos sin censura
                } else {
                    censored = censored.concat("XXXX ");//00088023 Si es cualquier otro conjunto de dígitos, entonces los censura
                }
            }
            tarjetas.add(tarjeta.getId() + " " + censored); //00088023 Finalmente agrega el id de la tarjeta junto a la tarjeta censurada
        }
        cbCard.getItems().addAll(tarjetas);//00088023 Se establecen como objetos la lista de tarjetas ya censuradas
        ObservableList<String> itemsToRender = FXCollections.observableArrayList(cbCard.getItems());//00088023 Se prepara el observable list
        cbCard.setItems(itemsToRender);//00088023 Se muestran los objetos en la choice box
    }
    public void initializeCardTab(){//00088023 método encargado de inicializar las choice box de la tab de agregar tarjetas
        Select select = Select.getInstance();//00088023 solicita una instancia de select
        cbOwner.getItems().addAll(select.selectCustomers());//00088023 Agrega a la choiceBox todos los clientes con sus respetivos id
        ObservableList<String> itemsToRender = FXCollections.observableArrayList(cbOwner.getItems());//00088023 Prepara la observable list
        cbOwner.setItems(itemsToRender);//00088023 coloca los items en la choiceBox y los muestra
        cbProvider.setItems(FXCollections.observableArrayList("Visa", "MasterCard", "American Express")); //00088023 Agrega y muestra los facilitadores a la choiceBox
        cbType.setItems(FXCollections.observableArrayList("Credito", "Debito"));//00088023 Agrega y muestra los tipos de tarjeta a la choiceBox
    }

    private void mostrarReporteA() {
        campoObligatorio1.setVisible(false);
        campoObligatorio2.setVisible(false);
        campoObligatorio3.setVisible(false);

        boolean flag = false;

        if (tfIdClienteRA.getText().isEmpty()) {
            campoObligatorio1.setVisible(true);
            flag = true;
        }

        if (dpFechaInicial.getValue() == null) {
            campoObligatorio2.setVisible(true);
            flag = true;
        }

        if (dpFechaFinal.getValue() == null) {
            campoObligatorio3.setVisible(true);
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

            taMuestraReporte.setText(text);
            TextFiles.createFile('A',text); //TODO: comentar
        }
    }

    private void mostrarReporteB() {
        try {
            // 00022423 Obtener los valores ingresados por el usuario
            int clienteID = Integer.parseInt(tfIdClienteRB.getText());
            String mes = cbMes.getValue();
            int anio = Integer.parseInt(tfAnio.getText());

            // 00022423 Definir el rango de años válidos
            int anioMinimo = 1900;
            int anioMaximo = 2100;

            // 00022423 Verificar que se hayan seleccionado mes y año válidos
            if (mes != null && !mes.isEmpty() && anio >= anioMinimo && anio <= anioMaximo) {
                // 00022423 Convertir el mes en índice (1-12)
                int mesIndex = cbMes.getItems().indexOf(mes) + 1;
                // 00022423 Obtener el total de gasto del cliente para el mes y año seleccionados
                double totalGasto = Queries.getInstance().generarReporteB(clienteID, anio, mesIndex);
                // 00022424 Mostrar el resultado en el TextArea

                String text = "El gasto total del Cliente ID " + clienteID + " en " + mes.toLowerCase() + " " + anio + " es: $" + totalGasto;

                taMuestraReporte.setText(text);
                TextFiles.createFile('B',text); //TODO: comentar
            } else {
                // 00022423 Mostrar una alerta si los datos no son válidos
                mostrarAlerta("Entrada Inválida", "Por favor, seleccione un mes y un año válidos entre " + anioMinimo + " y " + anioMaximo + ".");
            }

        } catch (NumberFormatException e) {
            // 00022423 Mostrar una alerta si los datos no son válidos (formato de número)
            mostrarAlerta("Entrada Inválida", "Por favor, ingrese un ID de cliente y un año válidos.");
        } catch (Exception e) {
            // 00022423 Mostrar una alerta si ocurre un error al generar el reporte
            mostrarAlerta("Error", "Ocurrió un error al generar el reporte: " + e.getMessage());
        }
    }

    private void mostrarReporteC() {//00088023 Método encargado de realizar el reporte C
        try {
            taMuestraReporte.setText("");//00088023 Primeramente se limpia el textArea
            int ClienteID = Integer.parseInt(tfIdClienteRC.getText());//00088023 Se Recibe el id del cliente del texArea y lo convierte en entero
            ArrayList<String> credito = new ArrayList<>(), debito = new ArrayList<>();//00088023 Inicializa dos arrays, uno que guarda las tarjetas de crédito, y el otro de débito
            Queries.getInstance().generarReporteC(ClienteID, credito, debito);//00088023 Llama al generaador de reporte que llene los arrays

            String text = "Tarjetas de crédito del cliente:\n"; //00088023 Empieza a darle formato al texto a mostrar
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
            TextFiles.createFile('C',text); //00088023 Llama a la función para crear un archivo con el reporte
        } catch (Exception e) {
            mostrarAlerta("Error", "Ingrese un valor válido");//00088023 Este error se recibe por el parseInteger, por lo tanto el usuario ingreso un valor no válido
        }
    }

    private void mostrarReporteD() {
        if (cmbFacilitador.getValue() != null) { //00042823 Si hay una opción selecciona por cmbFacilitador...
            String text = ""; //00042823 Es una cadena vacía que tiene como propósito concatenar el contenido completo del reporte D
            ResultSet rs = Queries.getInstance().generarReporteD(cmbFacilitador.getValue()); //00042823 Se llama al singleton Queries para hacer la consulta para el reporte D con el facilitador seleccionado, el cual se almacena en un ResultSet

            boolean flag = true; //00042823 Se crea una bandera que sirva para indicar si hay valores en el ResultSet
            try { //00042823 El método next() para ResultSet lanza SQLExceptions que no pueden dejarse irresolubles
                while (flag) { //00042823 Bucle para recorrer las "filas" de la tabla a consultar
                    if (rs.next()) {
                        text = text.concat( //00042823 Por cada cliente, se concatena la siguiente información
                                "Cliente: " + rs.getInt("id") + "\n" //00042823 El ID del cliente
                                        + rs.getString("nombres") + " " + rs.getString("apellidos") + "\n" //00042823 Nombre completo del cliente
                                        + "Teléfono: " + rs.getString("numTelefono") + "\n" //00042823 Número de teléfono del cliente
                                        + "Total de compras: " + rs.getString("compras") + "\n" //00042823 Cuántas compras ha realizado el cliente por medio del facilitador
                                        + "Gasto total: $" + rs.getDouble("total") + "\n\n" //00042823 Cuánto ha gastado en total el cliente por medio del facilitador
                        );
                    } else { //00042823 Si ya no hay filas por recorrer en el ResultSet
                        flag = false; //00042823 Baja la bandera

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
            //TODO: Crear una alerta, o bien un label error, ambas son opciones válidas
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
}