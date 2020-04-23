package Models;

public class Activity {
    private String title;
    private String description;
    private String startDate;
    private String deliveryDate;

    public Activity(){
        title=null;
        description=null;
        startDate=null;
    }
    public Activity(String title, String description, String deliveryDate, String file) {
        this.title=title;
        this.description=description;
        this.deliveryDate = deliveryDate;
    }

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
    public String getDeliveryDate() {
        return deliveryDate;
    }
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    public boolean isComplete() {
        return this.title != null &&
                this.description != null &&
                this.deliveryDate != null;
    }
}
