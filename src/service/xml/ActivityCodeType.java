package service.xml;

import org.jdom2.Element;

import javax.swing.tree.DefaultMutableTreeNode;

public class ActivityCodeType {
    private String name;
    private int objectId;

    public ActivityCodeType(Element activityCodeTypeElement) {
        this.name = activityCodeTypeElement.getChild(XMLTypes.NAME).getText();
        this.objectId = Integer.parseInt(activityCodeTypeElement.getChild(XMLTypes.OBJECT_ID).getText());
    }

    public String getName() {
        return name;
    }

    public int getObjectId() {
        return objectId;
    }
}
