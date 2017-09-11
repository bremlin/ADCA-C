package service.xml;

import org.jdom2.Element;

import javax.swing.tree.DefaultMutableTreeNode;

public class ActivityCode {

    private String name;
    private String description;

    private int objectId;
    private Integer parentObjectId;
    private int codeTypeObjectId;

    public ActivityCode(Element activityCodeElement) {
        this.name = activityCodeElement.getChild(XMLTypes.VALUE).getText();
        this.description = activityCodeElement.getChild(XMLTypes.DESCRIPTION).getText();
        this.objectId = Integer.parseInt(activityCodeElement.getChild(XMLTypes.OBJECT_ID).getText());
        if (activityCodeElement.getChild(XMLTypes.PARENT_ID).getText().equals("null")) {
            parentObjectId = 0;
        } else {
            parentObjectId = Integer.parseInt(activityCodeElement.getChild(XMLTypes.PARENT_ID).getText());
        }
        this.codeTypeObjectId = Integer.parseInt(activityCodeElement.getChild(XMLTypes.CODE_TYPE_ID).getText());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getObjectId() {
        return objectId;
    }

    public Integer getParentObjectId() {
        return parentObjectId;
    }

    public int getCodeTypeObjectId() {
        return codeTypeObjectId;
    }
}