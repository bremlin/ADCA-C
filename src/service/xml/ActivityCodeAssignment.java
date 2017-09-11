package service.xml;

import org.jdom2.Element;

import javax.swing.tree.DefaultMutableTreeNode;

public class ActivityCodeAssignment {

    private int activityObjectId;
    private int activityCodeObjectId;

    public ActivityCodeAssignment(Element activityCodeAssignmentElement) {
        this.activityObjectId = Integer.parseInt(activityCodeAssignmentElement.getChild(XMLTypes.ACTIVITY_ID).getText());
        this.activityCodeObjectId = Integer.parseInt(activityCodeAssignmentElement.getChild(XMLTypes.ACTIVITY_CODE_ID).getText());
    }

    public int getActivityObjectId() {
        return activityObjectId;
    }

    public int getActivityCodeObjectId() {
        return activityCodeObjectId;
    }
}
