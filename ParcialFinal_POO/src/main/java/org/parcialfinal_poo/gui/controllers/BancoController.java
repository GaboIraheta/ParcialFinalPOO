package org.parcialfinal_poo.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.parcialfinal_poo.models.Banco.Cliente;
import org.parcialfinal_poo.models.Banco.Compra;
import org.parcialfinal_poo.models.Banco.Tarjetas.Facilitador;
import org.parcialfinal_poo.models.Banco.Tarjetas.Tarjeta;
import org.parcialfinal_poo.models.Banco.Tarjetas.TipoTarjeta;
import org.parcialfinal_poo.models.DataBase.DataBase;
import org.parcialfinal_poo.models.DataBase.Deletes.Delete;
import org.parcialfinal_poo.models.DataBase.Inserts.Insert;
import org.parcialfinal_poo.models.DataBase.QueriesReportes.Queries;

import java.sql.*;
import java.util.ArrayList;

import org.parcialfinal_poo.models.DataBase.Selects.Select;
import org.parcialfinal_poo.models.DataBase.Updates.Update;
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
    private TextField txtIdClienteEliminar, txtIdTarjetaEliminar, txtIdCompraEliminar; //00022423 id necesarios para la operación delete
    @FXML
    private Button btnEliminarCliente, btnEliminarTarjeta, btnEliminarCompra; //00022423 id necesarios para conectar los botones con la operación delete

    @FXML
    private Label campoObligatorioUpdateCliente1, campoObligatorioUpdateCliente2, campoObligatorioUpdateCliente3, campoObligatorioUpdateCliente4, campoObligatorioUpdateCliente5;
    //00021223 se rereferencian todos los labels de campos obligatorios en la pestaña de ctualizar de la pestaña clientes

    @FXML
    private TextField tfClienteID, tfNombresCliente, tfApellidosClientes, tfDireccionClientes, tfTelefonoClientes; //00021223 se referencian todos los textField utilizados en la pestaña de clientes en la pestaña de actualizar

    @FXML
    private TextField tfTarjetaID, tfNumeroTarjeta, tfIDCliente; //00021223 referencia a los textfield de actualizar registros de tarjetas

    @FXML
    private ComboBox<TipoTarjeta> cbTipoTarjeta; //00021223 referencia al combobox de tipo de tarjeta en actualizar registros de tarjetas

    @FXML
    private ComboBox<Facilitador> cbFacilitador; //00021223 referencia al combobox de facilitador de tarjeta en actualizar registros de tarjetas

    @FXML
    private DatePicker dpFechaExpiracion; //00021223 referencia al datepicker para la fecha de expiracion en actualizar registros de tarjetas

    @FXML
    private Label campoObligatorioUpdateTarjeta1, campoObligatorioUpdateTarjeta2, campoObligatorioUpdateTarjeta3, campoObligatorioUpdateTarjeta4,
            campoObligatorioUpdateTarjeta5, campoObligatorioUpdateTarjeta6; //00021223 referencia a los labels de control de seleccion en actualizar registros de tarjetas

    @FXML
    private Button btnActualizarRegistroCliente; //00021223 referencia al boton de actualizar registros de la pestaña de actualizar de clientes

    @FXML
    private Button btnActualizarTarjeta; //00021223 referencia al boton de actualizar registros de la tab tarjeta

    @FXML
    private Label campoObligatorioUpdateCompra1, campoObligatorioUpdate2, campoObligatorioUpdateCompra3, campoObligatorioUpdateCompra4,
            campoObligatorioUpdateCompra5;

    @FXML
    private TextField tfCompraID, tfIDTarjeta, tfConcepto, tfMontoCompra;

    @FXML
    private DatePicker dpFechaCompra;

    @FXML
    private Button btnActualizarCompra;

    @FXML
    private TabPane tabPaneClientes, tabPaneTarjetas, tabPaneCompras;

    @FXML
    private Tab tabUpdateClientes, tabUpdateTarjetas, tabUpdateCompras;

    @FXML
    private TextField tfNames, tfLastNames, tfAdress, tfTelephone, tfCardNum;

    @FXML
    private ChoiceBox<String> cbOwner, cbProvider, cbType, cbCard;



    @FXML
    // 00022423 Método que se ejecuta cuando la interfaz gráfica se inicializa
    public void initialize() {

        campoObligatorio1.setVisible(false); //00021223 se settea en false la visibilidad de los labels de control para los campos de reporte A
        campoObligatorio2.setVisible(false);
        campoObligatorio3.setVisible(false);

        campoObligatorioUpdateCliente1.setVisible(false); //00021223 se settea en false la visibilidad de los labels de control para los campos de actualizar registros de clientes
        campoObligatorioUpdateCliente2.setVisible(false);
        campoObligatorioUpdateCliente3.setVisible(false);
        campoObligatorioUpdateCliente4.setVisible(false);
        campoObligatorioUpdateCliente5.setVisible(false);

        campoObligatorioUpdateTarjeta1.setVisible(false); //00021223 se settea en false la visibilidad de los labels de control para los campos de actualizar registros de tarjetas
        campoObligatorioUpdateTarjeta2.setVisible(false);
        campoObligatorioUpdateTarjeta3.setVisible(false);
        campoObligatorioUpdateTarjeta4.setVisible(false);
        campoObligatorioUpdateTarjeta5.setVisible(false);
        campoObligatorioUpdateTarjeta6.setVisible(false);

        campoObligatorioUpdateCompra1.setVisible(false);
        campoObligatorioUpdate2.setVisible(false);
        campoObligatorioUpdateCompra3.setVisible(false);
        campoObligatorioUpdateCompra4.setVisible(false);
        campoObligatorioUpdateCompra5.setVisible(false);

        tabPaneClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //00021223 configuracion del tabPane de Clientes para desactivar ciertos componentes de la tab de actualizar clientes
            if (newValue == tabUpdateClientes) {
                tfNombresCliente.setDisable(true); //00021223 todos los texfield de campos son desactivados para que no se puedan utilizar mientras no seleccione un ID de cliente a actualizar
                tfApellidosClientes.setDisable(true);
                tfDireccionClientes.setDisable(true);
                tfTelefonoClientes.setDisable(true);
                btnActualizarRegistroCliente.setDisable(true); //00021223 se desaactiva el boton de actualizar registros de clientes meintras el ID de cliente no se selecciona
            }
        });

        tabPaneTarjetas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //00021223 configuracion del tabPane de tarjetas para desactivar ciertos componentes de la tab de actualizar tarjetas
            if (newValue == tabUpdateTarjetas) {
                tfNumeroTarjeta.setDisable(true); //00021223 todos los componenetes de campos son desactivados para que no se puedan utilizar mientras no seleccione un ID de tarjeta
                tfIDCliente.setDisable(true);
                dpFechaExpiracion.setDisable(true);
                cbFacilitador.setDisable(true);
                cbTipoTarjeta.setDisable(true);
                btnActualizarTarjeta.setDisable(true); //00021223 tambien se desactiva el boton de actualizar el registro
            }
        });

        tabPaneCompras.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //00021223 configuracion del tabPane de Compras para desactivar ciertos componentes de la tab de actualizar compras
            if (newValue == tabUpdateCompras) {
                tfIDTarjeta.setDisable(true); //00021223 todos los componenetes de campos son desactivados para que no se puedan utilizar mientras no seleccione un ID de compra
                tfMonto.setDisable(true);
                tfConcepto.setDisable(true);
                dpFechaCompra.setDisable(true);
                btnActualizarCompra.setDisable(true);  //00021223 tambien se desactiva el boton de actualizar el registro
            }
        });


        cmbFacilitador.getItems().addAll(Facilitador.Visa, Facilitador.MasterCard, Facilitador.AmericanExpress); //00021223 se cargan los items del combobox
        ObservableList<Facilitador> facilitadores = FXCollections.observableArrayList(cmbFacilitador.getItems()); //00042823 Se crea una lista observable con el único propósito de agregar opciones a cmbFacilitador
        cmbFacilitador.setItems(facilitadores); //00042823 Se definen las opciones de cmbFacilitadores por medio de la lista observable

        //00022423 Llenar el ChoiceBox cbMes con los nombres de los meses
        cbMes.setItems(FXCollections.observableArrayList("enero",
                "febrero", "marzo", "abril", "mayo", "junio", "agosto",
                "septiembre", "octubre", "noviembre", "diciembre"));


    }

    // 00022423 Método que se ejecuta cuando se hace clic en el botón para eliminar un cliente
    @FXML
    private void eliminarCliente() {
        //00022423  Llama al método eliminarRegistro con el parámetro "Cliente" para indicar que se va a eliminar un cliente
        eliminarRegistro("Cliente");
    }

    @FXML
    //00022423 Método que se ejecuta cuando se hace clic en el botón para eliminar una tarjeta
    private void eliminarTarjeta() {
        //00022423 Llama al método eliminarRegistro con el parámetro "Tarjeta" para indicar que se va a eliminar una tarjeta
        eliminarRegistro("Tarjeta");
    }

    //00022423 Método que se ejecuta cuando se hace clic en el botón para eliminar una compra
    @FXML
    private void eliminarCompra() {
        //00022423 Llama al método eliminarRegistro con el parámetro "Compra" para indicar que se va a eliminar una compra
        eliminarRegistro("Compra");
    }

    public void limpiarClienteTab(){
        tfNames.setText("");
        tfLastNames.setText("");
        tfAdress.setText("");
        tfTelephone.setText("");
    }

    public void limpiarCardTab(){
        cbOwner.setValue(null);
        tfCardNum.setText("");
        dpExp.setValue(null);
        cbProvider.setValue(null);
        cbType.setValue(null);
    }

    public void limpiarBuyTab(){
        dpDate.setValue(null);
        tfMonto.setText("");
        taDescription.setText("");
        cbCard.setValue(null);
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
        if (dpDate.getValue() == null || tfMontoCompra.getText().isEmpty() || taDescription.getText().isEmpty() || cbCard.getValue() == null){//00088023 Verifica si los campos están vacios
            alert.setAlertType(Alert.AlertType.WARNING);//00088023 Si alguno está vacio, entonces se lo hace saber al usuario
            alert.setHeaderText(null);
            alert.setTitle("Campos incompletos");
            alert.setContentText("Por favor complete los campos incompletos");
            alert.showAndWait();
            return;//00088023 Termina el método
        }
        try {
            insert.registrarCompra(Date.valueOf(dpDate.getValue()), Double.parseDouble(tfMontoCompra.getText()),taDescription.getText(), Integer.parseInt(String.valueOf(cbCard.getValue().charAt(0))));//00088023 Agrega los datos de los campos a la tabla de compras
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
        //00088023 Se limpian los registros para evitar acumulación de información
        cbCard.getItems().clear();
        cbCard.getItems().addAll(tarjetas);//00088023 Se establecen como objetos la lista de tarjetas ya censuradas
        ObservableList<String> itemsToRender = FXCollections.observableArrayList(cbCard.getItems());//00088023 Se prepara el observable list
        cbCard.setItems(itemsToRender);//00088023 Se muestran los objetos en la choice box
    }
    public void initializeCardTab(){//00088023 método encargado de inicializar las choice box de la tab de agregar tarjetas
        Select select = Select.getInstance();//00088023 solicita una instancia de select
        //00088023 Se limpian los registros para evitar acumulación de información
        cbOwner.getItems().clear();
        cbProvider.getItems().clear();
        cbType.getItems().clear();
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

        boolean flag = false; //00021223 se inicializa un booleano para el control la accion y validar que todos los campos esten llenos

        if (tfIdClienteRA.getText().isEmpty()) { //00021223 valida si el textfield de clienteID esta vacio
            campoObligatorio1.setVisible(true); //00021223 si esta vacion se hace visible el primer control de seleccion
            flag = true; //00021223 se levanta la bandera
        }

        if (dpFechaInicial.getValue() == null) { //00021223 valida si el datepicker de fecha inicial esta vacio
            campoObligatorio2.setVisible(true); //00021223 si esta vacio se hace visible el segundo control de seleccion
            flag = true; //00021223 se levanta la bandera
        }

        if (dpFechaFinal.getValue() == null) { //00021223 valida si el datepicker de fecha final esta vacio
            campoObligatorio3.setVisible(true); //00021223 si esta vacio se hace visible el tercer control de seleccion
            flag = true; //00021223 se levanta la bandera
        }

        if (!flag) { //0021223 valida si la bandera no esta arriba

            ArrayList<Compra> compras = Queries.getInstance().generarReporteA(Integer.parseInt(tfIdClienteRA.getText()),
                    Date.valueOf(dpFechaInicial.getValue()), Date.valueOf(dpFechaFinal.getValue())); //00021223 se inicializa un array de compras
            //en donde se almacena las compras que retorna la query para reporte A, la cual recibe como parametros el ID del cliente, una fecha inicial
            //y una fecha final dadas por el textfield de clienteID y por los datepickers de fechas

            String text = ""; //00021223 se inicializa una cadena para almacenar todos los registros almacenados en la lista de compras

            for (Compra compra : compras) { //00021223 se recorre la lista completa
                text = text.concat("Codigo: " + compra.getCodigo() + "\nFecha de compra: " + compra.getFechaCompra() +
                        "\nDescripcion: " + compra.getDescripcion() + "\nMonto: $" +
                        compra.getMonto() + "\nID de tarjeta: " + compra.getTarjetaID() + "\n\n"); //00021223 se concatena cada registro en la variable text
            }

            taMuestraReporte.setText(text); //00021223 settea el texto del textarea para mostrar el resultado de la consulta
            TextFiles.createFile('A', text); //00021223 genera el reporte de la consulta utilizando el metodo createFile, se le pasa un char que indica el reporte y el texto que es la consulta completa
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
                TextFiles.createFile('B', text); //TODO: comentar
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
                TextFiles.createFile('D', text); //TODO: comentar

            } catch (SQLException e) { //00042823 Si algo malo ocurre, entonces atrapa la excepción...
                e.printStackTrace(); //00042823 ... Para luego imprimir la cadena de errores de la excepción
            }

        } else { //00042823 Si no hay nada seleccionado por cmbFacilitador
            //TODO: Crear una alerta, o bien un label error, ambas son opciones válidas
        }
    }

    // 00022423 Método para mostrar una alerta con un título y mensaje específicos
    public void mostrarAlerta(String titulo, String mensaje) {
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
        if (tabReporteA.isSelected()) { //00021223 se valida si la tab del reporte A esta seleccionada
            mostrarReporteA(); //00021223 se muestra el reporte A
        } else if (tabReporteB.isSelected()) { //00022423 Si la pestaña para el reporte B se encuentra activa
            mostrarReporteB(); //00021223 se muestra el reporte B
        } else if (tabReporteC.isSelected()) { //00021223 se valida si la tab del reporte C esta seleccionada
            mostrarReporteC(); //00021223 se muestra el reporte C
        } else if (tabReporteD.isSelected()) { //00042823 Si la pestaña para el reporte D se encuentra activa
            mostrarReporteD(); //00021223 se muestra el reporte D
        }
    }

    public void seleccionarClienteUpdate() { //00021223 metodo de accion para el boton de seleccionar registro en la pestaña de actualizar cliente

        campoObligatorioUpdateCliente1.setVisible(false); //00021223 se settea en false el control de seleccion del clienteID

        boolean flag = false; //00021223 se inicializa abajo una bandera

        if (tfClienteID.getText().isEmpty()) { //00021223 valida si el textfield de clienteID esta vacio
            campoObligatorioUpdateCliente1.setVisible(true); //00021223 si esta vacio se hace visible el control de seleccion de clienteID
            flag = true; //00021223 se levanta la bandera
        }

        if (!flag) { //00021223 valida si la bandera esta abajo

            try {


                Cliente cliente = Select.getInstance().selectCliente().get(Integer.parseInt(tfClienteID.getText()) - 1); //00021223 se obtiene el cliente que se ha seleccionado

                tfNombresCliente.setDisable(false); //00021223 si coincide activa de nuevo todos los textfield de campos
                tfApellidosClientes.setDisable(false);
                tfDireccionClientes.setDisable(false);
                tfTelefonoClientes.setDisable(false);
                btnActualizarRegistroCliente.setDisable(false); //00021223 activa tambien el boton de actualizar registro de cliente

                tfNombresCliente.setPromptText(cliente.getNombres()); //00021223 settea un prompt para el textfield de nombres con el nombre del cliente seleccionado
                tfApellidosClientes.setPromptText(cliente.getApellidos()); //00021223 settea un prompt para el textfield de apellidos con el apellido del cliente seleccionado
                tfDireccionClientes.setPromptText(cliente.getDireccion()); //00021223 settea un prompt para el textfield de direccion con la direccion del cliente seleccionado
                tfTelefonoClientes.setPromptText(cliente.getTelefono()); //00021223 settea un prompt para el textfield de telefono con el telefono del cliente seleccionado
            }catch (Exception e){
                mostrarAlerta("Error", "Ingrese un ID válido");
            }
        }
    }

    public void actualizarRegistroCliente() { //00021223 metodo que se encarga de actualizar un registro en la base de datos
        campoObligatorioUpdateCliente2.setVisible(false); //00021223 se hacen invisibles todos los labels de control de seleccion
        campoObligatorioUpdateCliente3.setVisible(false);
        campoObligatorioUpdateCliente4.setVisible(false);
        campoObligatorioUpdateCliente5.setVisible(false);

        boolean flag = false; //00021223 se inicializa una bandera abjo

        if (tfNombresCliente.getText().isEmpty()) { //00021223 se valida si el textfield de nombres esta vacio
            campoObligatorioUpdateCliente2.setVisible(true); //00021223 si esta vacio se hace visible el control de seleccion de nombres
            flag = true; //00021223 se levanta la bandera
        }

        if (tfApellidosClientes.getText().isEmpty()) { //00021223 se valida si el textfield de apellidos esta vacio
            campoObligatorioUpdateCliente3.setVisible(true); //00021223 si esta vacio se hace visible el control de seleccion de apellidos
            flag = true; //00021223 se levanta la bandera
        }

        if (tfDireccionClientes.getText().isEmpty()) { //00021223 se valida si el textfield de direccion esta vacio
            campoObligatorioUpdateCliente4.setVisible(true); //00021223 se hace visible el control de seleccion de direccion
            flag = true; //00021223 se levanta la bandera
        }

        if (tfTelefonoClientes.getText().isEmpty()) { //00021223 se valida si el textfield de telefonos esta vacio
            campoObligatorioUpdateCliente5.setVisible(true); //00021223 se hace visible el control de seleccion de telefonos
            flag = true; //00021223 se levanta la bandera
        }

        if (!flag) { //00021223 se valida si la bandera esta abajo

            Update.getInstance().updateCliente(tfNombresCliente.getText(), tfApellidosClientes.getText(), tfDireccionClientes.getText(),
                    tfTelefonoClientes.getText(), Integer.parseInt(tfClienteID.getText())); //00021223 se llama al metodo de updateCliente
            //para ejecutar la query de update cliente en la base de datos, se le pasa cada uno de los valores nuevos de cada campo y el ID
            //del cliente que se debe actualizar

            tfClienteID.setText(""); //00021223 se settea cada textfield de campos con una cadena vacia
            tfNombresCliente.setText("");
            tfApellidosClientes.setText("");
            tfDireccionClientes.setText("");
            tfTelefonoClientes.setText("");

            tfClienteID.setPromptText(""); //00021223 se settean todos los prompts
            tfNombresCliente.setPromptText("");
            tfApellidosClientes.setPromptText("");
            tfDireccionClientes.setPromptText("");
            tfTelefonoClientes.setPromptText("");

            tfNombresCliente.setDisable(true); //00021223 se desactivan de nuevo todos los componentes
            tfApellidosClientes.setDisable(true);
            tfDireccionClientes.setDisable(true);
            tfTelefonoClientes.setDisable(true);
        }
    }

    @FXML
    public void seleccionarRegistroTarjeta() { //00021223 metodo de accion para el boton de seleccionar registro en la pestaña de actualizar tarjeta

        campoObligatorioUpdateTarjeta1.setVisible(false); //00021223 se settea en false el control de seleccion de la tarjetaID

        boolean flag = false; //00021223 se inicializa abajo una bandera

        if (tfTarjetaID.getText().isEmpty()) { //00021223 valida si el textfield de tarjetaID esta vacio
            campoObligatorioUpdateTarjeta1.setVisible(true); //00021223 si esta vacio se hace visible el control de seleccion de tarjetaID
            flag = true; //00021223 se levanta la bandera
        }

        if (!flag) { //00021223 valida si la bandera esta abajo

            try {

                Tarjeta tarjeta = Select.getInstance().selectTarjeta().get(Integer.parseInt(tfTarjetaID.getText()) - 1); //00021223 se obtiene la tarjeta que se ha seleccionado por el ID

                tfNumeroTarjeta.setDisable(false); //00021223 se activan el textfield de numero tarjeta
                tfIDCliente.setDisable(false); //00021223 se activa el textield de clienteID
                cbFacilitador.setDisable(false); //00021223 se activa el combobox de facilitador
                cbTipoTarjeta.setDisable(false); //00021223 se activa el combobox de tipo tarjeta
                dpFechaExpiracion.setDisable(false); //00021223 se activa el datepicker de fecha de expiracion
                btnActualizarTarjeta.setDisable(false); //00021223 se activa el boton de actualizar registro de tarjetas

                assert tarjeta != null; //00021223 control de excepcion
                tfNumeroTarjeta.setPromptText(tarjeta.getNumeroTarjeta()); //00021223 se settea como prompt la informacion almacenada del objeto para cada textfield
                dpFechaExpiracion.setPromptText(String.valueOf(tarjeta.getFechaExpiracion()));
                tfIDCliente.setPromptText(String.valueOf(tarjeta.getClienteID()));

                cbTipoTarjeta.getItems().addAll(TipoTarjeta.Credito, TipoTarjeta.Debito); //00021223 se carga una lista de tipos de tarjeta como items del combobox
                ObservableList<TipoTarjeta> itemsTipo = FXCollections.observableArrayList(cbTipoTarjeta.getItems()); //00021223 se crea la lista observable
                cbTipoTarjeta.setItems(itemsTipo); //00021223 se settean los items del combobox con los datos de la lista observable

                cbTipoTarjeta.setPromptText(tarjeta.getTipo().toString()); //00021223 se settea el prompt para el combobox

                cbFacilitador.getItems().addAll(Facilitador.Visa, Facilitador.MasterCard, Facilitador.AmericanExpress); //00021223 se carga una lista de facilitadores como items del combobox
                ObservableList<Facilitador> itemsFacilitador = FXCollections.observableArrayList(cbFacilitador.getItems()); //00021223 se crea la lista observable
                cbFacilitador.setItems(itemsFacilitador); //00021223 se settean los items del combobox con los datos de la lista observable

                cbFacilitador.setPromptText(tarjeta.getFacilitador().toString()); //00021223 se settea el prompt para el combobox

            } catch (Exception e) { //00021223 atrapa un puntero nulo
                mostrarAlerta("Error", "Ha ocurrido un error con la seleccion de la tarjeta"); //00021223 muestra una alerta de error si ocurre un nullpointerexception
            }
        }
    }

    @FXML
    public void updateRegistroTarjeta() { //00021223 metodo que se encarga de actualizar un registro en la base de datos
        campoObligatorioUpdateTarjeta2.setVisible(false); //00021223 se hacen invisibles todos los labels de control de seleccion
        campoObligatorioUpdateTarjeta3.setVisible(false);
        campoObligatorioUpdateTarjeta4.setVisible(false);
        campoObligatorioUpdateTarjeta5.setVisible(false);
        campoObligatorioUpdateTarjeta6.setVisible(false);

        boolean flag = false; //00021223 se inicializa una bandera abjo

        if (tfNumeroTarjeta.getText().isEmpty()) { //00021223 se valida si el textfield de numero de tarjeta esta vacio
            campoObligatorioUpdateTarjeta2.setVisible(true); //00021223 si esta vacio se hace visible el control de seleccion de numero tarjeta
            flag = true; //00021223 se levanta la bandera
        }

        if (cbTipoTarjeta.getValue() == null) { //00021223 se valida si el combobox de tipo no ha sido seleccionado
            campoObligatorioUpdateTarjeta3.setVisible(true); //00021223 si esta vacio se hace visible el control de seleccion de tipos
            flag = true; //00021223 se levanta la bandera
        }

        if (dpFechaExpiracion.getValue() == null) { //00021223 se valida si el datepicker de fecha de expiracion esta vacio
            campoObligatorioUpdateTarjeta4.setVisible(true); //00021223 se hace visible el control de seleccion de fecha de expiracion
            flag = true; //00021223 se levanta la bandera
        }

        if (cbFacilitador.getValue() == null) { //00021223 se valida si el combobox de facilitadores esta vacio
            campoObligatorioUpdateTarjeta5.setVisible(true); //00021223 se hace visible el control de seleccion de facilitadores
            flag = true; //00021223 se levanta la bandera
        }

        if (tfIDCliente.getText().isEmpty()) { //00021223 se valida si el combobox de IDcliente esta vacio
            campoObligatorioUpdateTarjeta6.setVisible(true); //00021223 si esta vacio se hace visible el control de seleccion de IDcliente
            flag = true; //00021223 se levanta la bandera
        }

        if (!flag) {

            Update.getInstance().updateTarjeta(tfNumeroTarjeta.getText(), Date.valueOf(dpFechaExpiracion.getValue().toString()), cbTipoTarjeta.getValue(),
                    cbFacilitador.getValue(), Integer.parseInt(tfIDCliente.getText()), Integer.parseInt(tfTarjetaID.getText())); //00021223 se llama al metodo de updateTarjeta
            //para ejecutar la query de update tarjeta en la base de datos, se le pasa cada uno de los valores nuevos de cada campo y el ID
            //de la tarjeta que se debe actualizar

            tfTarjetaID.setText(""); //00021223 se settean los text y prompts de los componentes
            tfNumeroTarjeta.setText("");
            tfIDCliente.setText("");
            cbTipoTarjeta.setPromptText("");
            cbFacilitador.setPromptText("");
            dpFechaExpiracion.setPromptText("");

            tfNumeroTarjeta.setDisable(true); //00021223 se desactivan de nuevo todos los componentes
            tfIDCliente.setDisable(true);
            dpFechaExpiracion.setDisable(true);
            cbFacilitador.setDisable(true);
            cbTipoTarjeta.setDisable(true);
            btnActualizarTarjeta.setDisable(true);
        }
    }

    @FXML
    public void seleccionarCompra() { //00021223 metodo para seleccionar un registro de compra

        campoObligatorioUpdateCompra1.setVisible(false); //00021223 se hace invisible el control de seleccion

        boolean flag = false; //00021223 inicializa una bandera abajo

        if (tfCompraID.getText().isEmpty()) { //00021223 se valida si el ID de compra esta vacio
            campoObligatorioUpdateCompra1.setVisible(true); //00021223 se hace visible el control de seleccion
            flag = true; //00021223 se sube la bandera
        }

        if (!flag) { //00021223 se valida si la bandera esta abajo

            Compra compra = Select.getInstance().selectCompra().get(Integer.parseInt(tfCompraID.getText()) - 1); //00021223 se obtiene el registro de compra seleccionado por el ID

            dpFechaCompra.setDisable(false); //00021223 se activan todos los componentes
            tfIDTarjeta.setDisable(false);
            tfMonto.setDisable(false);
            tfConcepto.setDisable(false);
            btnActualizarCompra.setDisable(false); //00021223 tambien se activa el boton

            dpFechaCompra.setPromptText(compra.getFechaCompra().toString()); //00021223 se settea un prompt para todos los componentes con los datos del registro seleccionado
            tfMonto.setPromptText(String.valueOf(compra.getMonto()));
            tfConcepto.setPromptText(compra.getDescripcion());
            tfIDTarjeta.setPromptText(String.valueOf(compra.getTarjetaID()));

        }
    }

    @FXML
    public void actualizarRegistroCompra() { //00021223 metodo que se encarga de actualizar un registro de compra
        campoObligatorioUpdate2.setVisible(false); //00021223 se hacen invisible los control de seleccion
        campoObligatorioUpdateCompra3.setVisible(false);
        campoObligatorioUpdateCompra4.setVisible(false);
        campoObligatorioUpdateCompra5.setVisible(false);

        boolean flag = false; //00021223 inicializa una bandera abajo

        if (dpFechaCompra.getValue() == null) { //00021223 se valida si la fecha de compra es vacia
            campoObligatorioUpdate2.setVisible(true); //00021224 se hace visible el control de seleccion
            flag = true; //00021223 se levanta la bandera
        }

        if (tfConcepto.getText().isEmpty()) { //00021223 se valida si el concepto es vacio
            campoObligatorioUpdateCompra3.setVisible(true); //00021223 se hace visible el control de seleccion
            flag = true; //00021223 se levanta la bandera
        }

        if (tfMonto.getText().isEmpty()) { //00021223 se valida si el monto es vacio
            campoObligatorioUpdateCompra4.setVisible(true); //00021223 se hace visible el control de seleccion
            flag = true; //00021223 se levanta la bandera
        }

        if (tfIDTarjeta.getText().isEmpty()) { //00021223 se valida si el ID de tarjeta es vacio
            campoObligatorioUpdateCompra5.setVisible(true); //00021223 se hace visible el control de seleccion
            flag = true; //00021223 se levanta la bandera
        }

        if (!flag) { //00021223 se valida si la bandera esta arriba

            Update.getInstance().updateCompra(Date.valueOf(dpFechaCompra.getValue().toString()), Double.parseDouble(tfMonto.getText()),
                    tfConcepto.getText(), Integer.parseInt(tfIDTarjeta.getText()), Integer.parseInt(tfCompraID.getText()));
            //00021223 se ejecuta la query para actualizar un registro de compra pasandole como paramtros los campos requeridos

            tfMonto.setText(""); //00021223 se settean los textos en vacio
            tfConcepto.setText("");
            tfIDTarjeta.setText("");
            tfCompraID.setText("");
            dpFechaCompra.setPromptText("");

            tfMonto.setDisable(true); //00021223 se desactivan de nuevo todos los componentes
            tfConcepto.setDisable(true);
            tfIDTarjeta.setDisable(true);
            dpFechaCompra.setDisable(true);
        }

    }


    //00022423 Método para eliminar registro de la base de datos
    private void eliminarRegistro(String tipo) {
        try {
            // 00022423 Declaración de la variable para almacenar el ID del registro
            int id;
            // 00022423 Variable para indicar si la eliminación fue exitosa o no
            boolean exito = false;
            // 00022423 Estructura switch para determinar el tipo de registro a eliminar
            switch (tipo) {
                case "Cliente":
                    // 00022423 Obtiene el ID del cliente desde el campo de texto correspondiente y lo convierte a entero
                    id = Integer.parseInt(txtIdClienteEliminar.getText());
                    // 000224234 Llama al método deleteCliente de la instancia de Delete para eliminar el cliente
                    // 00022423 La variable exito se actualiza con el resultado de la operación (true si se eliminó, false si no)
                    exito = Delete.getInstance().deleteCliente(id);
                    break;
                case "Tarjeta":
                    // 00022423 Obtiene el ID de la tarjeta desde el campo de texto correspondiente y lo convierte a entero
                    id = Integer.parseInt(txtIdTarjetaEliminar.getText());
                    // 00022423 Llama al método deleteTarjeta de la instancia de Delete para eliminar la tarjeta
                    // 00022423 La variable exito se actualiza con el resultado de la operación (true si se eliminó, false si no)
                    exito = Delete.getInstance().deleteTarjeta(id);
                    break;
                case "Compra":
                    // 00022423 Obtiene el ID de la compra desde el campo de texto correspondiente y lo convierte a entero
                    id = Integer.parseInt(txtIdCompraEliminar.getText());
                    // 00022423 Llama al método deleteCompra de la instancia de Delete para eliminar la compra
                    // 00022423 La variable exito se actualiza con el resultado de la operación (true si se eliminó, false si no)
                    exito = Delete.getInstance().deleteCompra(id);
                    break;
            }

            if (exito) { // 00022423 Si la eliminación fue exitosa, muestra un mensaje de éxito
                // 00022423 Crea una alerta de tipo información
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                //00022423 Establece el título de la alerta
                alerta.setTitle("Eliminado");
                // 00022423 Establece el encabezado de la alerta como nulo para que no haya encabezado
                alerta.setHeaderText(null);
                // 00022423 Establece el contenido del mensaje de la alerta con el tipo de registro eliminado
                alerta.setContentText("Se ha eliminado el registro exitosamente");
                //00022423 Muestra la alerta y espera hasta que el usuario la cierre
                alerta.showAndWait();
            } else {
                //00022423 Muestra una alerta indicando que no se puedo eliminar el registro
                mostrarAlerta("Error", "No se pudo eliminar el registro.");
            }
        } catch (
                NumberFormatException e) { // 00022423 Si se produce una excepción por formato de número (por ejemplo, si el ID no es un número válido)
            // 00022423 Crea una alerta de tipo error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // 00022423 Establece el título de la alerta
            alert.setTitle("Error");
            // 00022423 Establece el encabezado de la alerta como nulo para que no haya encabezado
            alert.setHeaderText(null);
            // 00022423 Establece el contenido del mensaje de la alerta indicando que el ID no es válido
            alert.setContentText("Error, ingrese un id valido");
            // 00022423 Muestra la alerta y espera hasta que el usuario la cierre
            alert.showAndWait();

        }
    }
}
