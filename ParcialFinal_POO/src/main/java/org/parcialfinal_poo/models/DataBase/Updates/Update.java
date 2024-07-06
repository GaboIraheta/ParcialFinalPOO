package org.parcialfinal_poo.models.DataBase.Updates;

public class Update extends DataBaseUpdate {

    private static Update instance;

    private Update() {

    }

    public static Update getInstance() {
        if (instance == null) {
            instance = new Update();
        }
        return instance;
    }

    @Override
    public void updateCliente(String nombres, int id) {

    }

    @Override
    public void updateTarjeta(String numTarjeta, int id) {

    }

    @Override
    public void updateCompra(double monto, int id) {

    }
}
