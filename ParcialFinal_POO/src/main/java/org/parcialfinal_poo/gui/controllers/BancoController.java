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

public class BancoController {

    @FXML
    private Button btnConsultar;

    @FXML
    private Tab btnConsultarRB;

    @FXML
    private Tab tabReporteA = new Tab();

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

    @FXML
    private DatePicker dpFechaFinal;

    @FXML
    private Spinner<?> spnMonth;

    @FXML
    private Spinner<?> spnYear;

    @FXML
    private TextField tfIdClienteRA;

    @FXML
    private TextField tfIdClienteRB;

    @FXML
    private TextField tfIdClienteRC;

    @FXML
    private TextArea taMuestraReporte;

    @FXML
    private Label campoObligatorio1, campoObligatorio2, campoObligatorio3;

    @FXML
    public void initialize(){ //00042823 Función que inicializa los componentes y cosas varias de la interfaz gráfica

        campoObligatorio1.setVisible(false);
        campoObligatorio2.setVisible(false);
        campoObligatorio3.setVisible(false);

        cmbFacilitador.getItems().addAll(Facilitador.Visa, Facilitador.MasterCard, Facilitador.AmericanExpress);
        ObservableList<Facilitador> facilitadores = FXCollections.observableArrayList(cmbFacilitador.getItems()); //00042823 Se crea una lista observable con el único propósito de agregar opciones a cmbFacilitador
        cmbFacilitador.setItems(facilitadores); //00042823 Se definen las opciones de cmbFacilitadores por medio de la lista observable
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
                        Date.valueOf(dpFechaFinal.getValue()), Date.valueOf(dpFechaFinal.getValue()));

                String text = "";

                for(Compra compra : compras) {
                    System.out.println(compra.getCodigo());
                    text = text.concat(compra.getCodigo() + "\n" + compra.getFechaCompra() + "\n" + compra.getDescripcion() + "\n" +
                            compra.getMonto() + "\n" + compra.getTarjetaID() + "\n\n");
                }

                taMuestraReporte.setText(text);

                System.out.println(text);
            }
        }
        else if (tabReporteD.isSelected()) { //00042823 Si la pestaña para el reporte D se encuentra activa

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
                            DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBCN", "gabo7", "Afb092ebbf$").close(); //00042823 Y cierra la conexión con la base de datos, esto de una manera poco ortodoxa
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