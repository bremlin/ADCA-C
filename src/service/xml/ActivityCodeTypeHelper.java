package service.xml;

import org.jdom2.Element;

import java.util.HashMap;

//<ActivityCodeTypeObjectId, ActivityCodeTypeName>
public class ActivityCodeTypeHelper extends HashMap<String, Integer> {
    public ActivityCodeTypeHelper(Element element) {
        for (Element activityCodeTypeElement : element.getChildren()) {
            ActivityCodeType activityCodeType = new ActivityCodeType(activityCodeTypeElement);
            this.put(activityCodeType.getName(), activityCodeType.getObjectId());
        }
    }
}
