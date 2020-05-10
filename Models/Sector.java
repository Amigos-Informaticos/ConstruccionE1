package Models;

import DAO.DAOSector;
import javafx.collections.ObservableList;

public class Sector {
    public boolean fillSector(ObservableList<String> listSector){
        boolean filled = false;
        DAOSector daoSector = new DAOSector(this);
        if(daoSector.fillSector(listSector)){
            filled = true;
        }
        return filled;
    }
}
