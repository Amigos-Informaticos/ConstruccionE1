package Models;

import DAO.DAOSector;
import javafx.collections.ObservableList;

public class Sector {
    private String name;

    public Sector(){}

    public Sector(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean fillSector(ObservableList<String> listSector){
        boolean filled = false;
        DAOSector daoSector = new DAOSector(this);
        if(daoSector.fillSector(listSector)){
            filled = true;
        }
        return filled;
    }

    public String getId(){
        DAOSector daoSector = new DAOSector(this);
        return daoSector.getId();
    }
}
