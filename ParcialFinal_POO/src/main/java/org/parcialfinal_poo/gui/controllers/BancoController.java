package org.parcialfinal_poo.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.parcialfinal_poo.models.Banco.Cliente;
import org.parcialfinal_poo.models.Banco.Compra;
import org.parcialfinal_poo.models.Banco.Tarjetas.Facilitador;
import org.parcialfinal_poo.models.DataBase.DataBase;
import org.parcialfinal_poo.models.DataBase.QueriesReportes.Queries;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;
import org.parcialfinal_poo.models.DataBase.QueriesReportes.Queries;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

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
    private Button btnGenerarReporte;

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
    private DatePicker dpFechaFinalRB;

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
    private Label campoObligatorio1, campoObligatorio2, campoObligatorio3;



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
        cbMes.setItems(FXCollections.observableArrayList("enero" ,
                "febrero","marzo","abril","mayo","junio","agosto",
                "septiembre","octubre","noviembre","diciembre"));


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
                taMuestraReporte.setText("El gasto total del Cliente ID " + clienteID + " en " + mes.toLowerCase() + " " + anio + " es: " + totalGasto);
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

    public void handleBtnConsultar(){ //00042823 Se define la función que se realizará cuando se haga una acción con btnGenerarReporte
        if(tabReporteA.isSelected()) {

            campoObligatorio1.setVisible(false);
            campoObligatorio2.setVisible(false);
            campoObligatorio3.setVisible(false);

            boolean flag = false;

            if(tfIdClienteRA.getText().isEmpty()) {
                campoObligatorio1.setVisible(true);
                flag = true;
            }

            if(dpFechaInicial.getValue() == null) {
                campoObligatorio2.setVisible(true);
                flag = true;
            }

            if(dpFechaFinal.getValue() == null) {
                campoObligatorio3.setVisible(true);
                flag = true;
            }

            if(!flag) {

                ArrayList<Compra> compras = Queries.getInstance().generarReporteA(Integer.parseInt(tfIdClienteRA.getText()),
                        Date.valueOf(dpFechaInicial.getValue()), Date.valueOf(dpFechaFinal.getValue()));

                String text = "";

                for(Compra compra : compras) {
                    text = text.concat(compra.getCodigo() + "\n" + compra.getFechaCompra() + "\n" + compra.getDescripcion() + "\n" +
                            compra.getMonto() + "\n" + compra.getTarjetaID() + "\n\n");
                }

                taMuestraReporte.setText(text);
            }

        } else if (tabReporteB.isSelected()) {
            mostrarReporteB();

        } else if (tabReporteD.isSelected()) { //00042823 Si la pestaña para el reporte D se encuentra activa

            if(cmbFacilitador.getValue() != null){ //00042823 Si hay una opción selecciona por cmbFacilitador...
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
                            DriverManager.getConnection("jdbc:mysql://localhost/dbBCN", "root", "Mrdl2005").close(); //00042823 Y cierra la conexión con la base de datos, esto de una manera poco ortodoxa
                        }
                    }
                    taMuestraReporte.setText(text); //00042823 Escribe el texto entero concatenado en el TextArea donde se ven los reportes
                }
                catch (SQLException e) { //00042823 Si algo malo ocurre, entonces atrapa la excepción...
                    e.printStackTrace(); //00042823 ... Para luego imprimir la cadena de errores de la excepción
                }

            }
            else{ //00042823 Si no hay nada seleccionado por cmbFacilitador
                //TODO: Crear una alerta, o bien un label error, ambas son opciones válidas
            }
        }
        else{
            System.out.println("No le sabes");
        }
    }
}