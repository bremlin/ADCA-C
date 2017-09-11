package service.xml;

import org.jdom2.Element;

import javax.swing.tree.DefaultMutableTreeNode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Activity {

    private String name;
    private String id;

    private int objectId;

    private Date startDate;
    private Date finishDate;

    public Activity(Element activityElement) {
        this.name = activityElement.getChild(XMLTypes.NAME).getText();
        this.id = activityElement.getChild(XMLTypes.ID).getText();
        this.objectId = Integer.parseInt(activityElement.getChild(XMLTypes.OBJECT_ID).getText());

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            this.startDate = format.parse(activityElement.getChild(XMLTypes.START_DATE).getText());
            this.finishDate = format.parse(activityElement.getChild(XMLTypes.FINISH_DATE).getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getObjectId() {
        return objectId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }
}