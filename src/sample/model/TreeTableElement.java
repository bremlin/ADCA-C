package sample.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import service.xml.Activity;
import service.xml.ActivityCode;
import service.xml.Step;
import service.xml.XMLHelper;

import javax.swing.tree.DefaultMutableTreeNode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TreeTableElement {
    private SimpleStringProperty name;
    private SimpleStringProperty id;
    private SimpleStringProperty pvType;
    private SimpleStringProperty startDate;
    private SimpleStringProperty finishDate;

    private int objectId;
    private int parentId;
    private SimpleStringProperty weight;

    private SimpleDoubleProperty percent;
    private SimpleStringProperty pvVolume;

    private static SimpleStringProperty EMPTY = new SimpleStringProperty("");
    private static SimpleDoubleProperty EMPTY_DOUBLE = new SimpleDoubleProperty(0.00);


    public TreeTableElement(String projectName) {
        this.name = new SimpleStringProperty(projectName);
    }

    public TreeTableElement(Activity activity, XMLHelper xmlHelper) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        this.name = new SimpleStringProperty(activity.getName());
        this.id = new SimpleStringProperty(activity.getId());
        this.objectId = activity.getObjectId();
        this.startDate = new SimpleStringProperty(df.format(activity.getStartDate()));
        this.finishDate = new SimpleStringProperty(df.format(activity.getStartDate()));

        this.weight = new SimpleStringProperty("");
        this.percent = new SimpleDoubleProperty(0.00);
        this.pvVolume = new SimpleStringProperty("");
        if (xmlHelper.getActivityCodeAssignmentHelper().getListAssignmentByActivityId(objectId) != null) {
            pvType = new SimpleStringProperty(
                    xmlHelper.getActivityCodeHelper().getPvType(xmlHelper.getActivityCodeAssignmentHelper().getListAssignmentByActivityId(objectId)));
        } else {
            pvType = new SimpleStringProperty("---");
        }

    }

    public TreeTableElement(ActivityCode activityCode) {
        this.name = new SimpleStringProperty(activityCode.getDescription());
        this.id = new SimpleStringProperty(activityCode.getName());
        this.objectId = activityCode.getObjectId();
        this.parentId = activityCode.getParentObjectId();

        this.pvType = EMPTY;
        this.startDate = EMPTY;
        this.finishDate = EMPTY;
        this.weight = EMPTY;
        this.percent = EMPTY_DOUBLE;
        this.pvVolume = EMPTY;
    }

    public TreeTableElement(Step step) {
        this.name = new SimpleStringProperty(step.getName());
        this.id = EMPTY;
        this.objectId = step.getObjectId();
        this.parentId = step.getActivityObjectId();

        this.pvType = EMPTY;
        this.startDate = EMPTY;
        this.finishDate = EMPTY;
        this.weight = new SimpleStringProperty(String.valueOf(step.getWeight()));
        this.percent = new SimpleDoubleProperty(step.getPercentComplete());
        this.pvVolume = EMPTY;
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleStringProperty getId() {
        return id;
    }

    public SimpleStringProperty getPVType() {
        return pvType;
    }

    public int getObjectId() {
        return objectId;
    }

    public SimpleStringProperty getWeight() {
        return weight;
    }

    public SimpleStringProperty getStartDate() {
        return startDate;
    }

    public SimpleStringProperty getFinishDate() {
        return finishDate;
    }

    public SimpleDoubleProperty getPercent() {
        return percent;
    }

    public SimpleStringProperty getPvVolume() {
        return pvVolume;
    }

    public int getParentId() {
        return parentId;
    }
}
