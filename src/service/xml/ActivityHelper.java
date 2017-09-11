package service.xml;

import org.jdom2.Element;

import java.util.ArrayList;

public class ActivityHelper extends ArrayList<Activity> {
    public ActivityHelper(Element element) {
        for (Element activity : element.getChildren()) {
            this.add(new Activity(activity));
        }
    }
}
