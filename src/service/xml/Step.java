package service.xml;

import org.jdom2.Element;
import sql.SQLController;

import javax.swing.tree.DefaultMutableTreeNode;

public class Step extends DefaultMutableTreeNode {

    private String name;

    private int objectId;
    private int activityObjectId;

    private double weight;
    private double weightPercent;
    private String percentComplete;

    public Step(Element stepElement, SQLController sqlController) {
        this.name = stepElement.getChild(XMLTypes.NAME).getText();
        this.objectId = Integer.parseInt(stepElement.getChild(XMLTypes.OBJECT_ID).getText());
        this.activityObjectId = Integer.parseInt(stepElement.getChild(XMLTypes.ACTIVITY_ID).getText());
        this.weight = Double.parseDouble(stepElement.getChild(XMLTypes.WEIGHT).getText());
        this.weightPercent = Double.parseDouble(stepElement.getChild(XMLTypes.WEIGHT_PERCENT).getText());



        this.percentComplete = stepElement.getChild(XMLTypes.PERCENT_COMPLETE).getText();
    }

    public String getName() {
        return name;
    }

    public int getObjectId() {
        return objectId;
    }

    public int getActivityObjectId() {
        return activityObjectId;
    }

    public double getWeight() {
        return weight;
    }

    public double getWeightPercent() {
        return weightPercent;
    }

    public String getPercentComplete() {
        return percentComplete;
    }
}
