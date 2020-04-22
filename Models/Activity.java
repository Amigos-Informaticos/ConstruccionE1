package Models;

public class Activity {
    private String title;
    private String description;
    private String startDate;
    private String deliveryDate;
    private String file;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Activity(){
        title=null;
        description=null;
        startDate=null;
        file =null;
    }
    public Activity(String titulo, String descripcion, String fechaEntrega, String file) {
        this.title=titulo;
        this.description=descripcion;
        this.startDate=fechaEntrega;
        this.file = file;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
    public boolean isComplete() {
        return this.title != null &&
                this.description != null &&
                this.startDate != null &&
                this.file != null;
    }
}
