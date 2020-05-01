package Models;

import DAO.DAOShift;
import javafx.collections.ObservableList;

public class Shift {
    public Shift(){

    }
    public boolean fillShift(ObservableList<String> listShift){
        boolean filled = false;
        DAOShift daoShift = new DAOShift(this);
        if(daoShift.fillShift(listShift)){
            filled = true;
        }
        return filled;
    }
}
