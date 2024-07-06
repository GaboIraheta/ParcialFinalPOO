package org.parcialfinal_poo.models.DataBase.Deletes;

public class Delete extends DataBaseDelete {

    private static Delete instance;

    private Delete() {

    }

    public static Delete getInstance() {
        if (instance == null) {
            instance = new Delete();
        }
        return instance;
    }

    @Override
    public void deleteCliente(int id) {

    }

    @Override
    public void deleteTarjeta(int id) {

    }

    @Override
    public void deleteCompra(int id) {

    }
}
