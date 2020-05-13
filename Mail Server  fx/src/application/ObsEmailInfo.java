package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class ObsEmailInfo {
    private SimpleStringProperty subject;
    private SimpleStringProperty sender;
    private SimpleStringProperty date;
    private CheckBox select;
   

    public ObsEmailInfo( String subject, String sender , String date ) {
        this.subject = new SimpleStringProperty (subject);
        this.sender = new SimpleStringProperty (sender);
        this.date = new SimpleStringProperty(date);
        this.select = new CheckBox();
    }


    public String getSubject() {
        return this.subject.get();
    }


    public String getSender() {
        return this.sender.get();
    }

    public String getDate() {
        return this.date.get();
    }


	public CheckBox getSelect() {
		return this.select;
	}



}
