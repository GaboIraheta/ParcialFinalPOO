package org.parcialfinal_poo.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BancoController {

    @FXML
    private Button btnConsultar;

    @FXML
    private Tab btnConsultarRB;

    @FXML
    private Button btnGenerarReporte;

    @FXML
    private ComboBox<?> cmbFacilitador;

    @FXML
    private ComboBox<?> cmbTablaConsultada;

    @FXML
    private DatePicker dpFechaFinalRA;

    @FXML
    private Spinner<?> dpFechaFinalRB;

    @FXML
    private DatePicker dpFechaInicialRA;

    @FXML
    private Spinner<?> dpFechaInicialRB;

    @FXML
    private TextArea taMuestraReporte;

    @FXML
    private TextField tfIdClienteRA;

    @FXML
    private TextField tfIdClienteRB;

    @FXML
    private TextField tfIdClienteRC;

    @FXML
    private Label campoObligatorio1, campoObligatorio2, campoObligatorio3;

}