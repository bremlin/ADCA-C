package service.xml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLHelper {

    private ActivityHelper activityHelper;
    private ActivityCodeTypeHelper activityCodeTypeHelper;
    private ActivityCodeHelper activityCodeHelper;
    private ActivityCodeAssignmentHelper activityCodeAssignmentHelper;
    private StepHelper stepHelper;
    private UDFValueHelper udfValueHelper;

    private String projectName;

    public XMLHelper(File xmlFile) {
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            Document document = saxBuilder.build(xmlFile);
            Element root = document.getRootElement();
            projectName = root.getAttributeValue(XMLTypes.PROJECT_ELEMENT);
            List<Element> rootChild = root.getChildren();

            getElements(rootChild);
            if (checkElements()) {

            }

        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
    }

    private void getElements(List<Element> elements) {
        for (Element element : elements) {
            String elementName = element.getName();
            if (elementName.equals(XMLTypes.ACTIVITIES_ELEMENT)) {
                activityHelper = new ActivityHelper(element);
            } else if (elementName.equals(XMLTypes.ACTIVITY_CODE_TYPES_ELEMENT)) {
                activityCodeTypeHelper = new ActivityCodeTypeHelper(element);
            } else if (elementName.equals(XMLTypes.ACTIVITY_CODES)) {
                activityCodeHelper = new ActivityCodeHelper(element);
            } else if (elementName.equals(XMLTypes.ACTIVITY_CODE_ASSIGNMENTS)) {
                activityCodeAssignmentHelper = new ActivityCodeAssignmentHelper(element);
            } else if (elementName.equals(XMLTypes.ACTIVITY_STEPS)) {
                stepHelper = new StepHelper(element);
            } else if (elementName.equals(XMLTypes.UDF_VALUES)) {
                udfValueHelper = new UDFValueHelper(element);
            } else {
                System.out.println("Тут какой-то покемон в XML: " + elementName);
            }
        }
    }

    private boolean checkElements() {
        boolean check = true;
        if (activityHelper == null) {
            System.out.println("Ошибка! Не найден раздел activityHelper");
            check = false;
        }
        if (activityCodeTypeHelper == null) {
            System.out.println("Ошибка! Не найден раздел activityCodeTypeHelper");
            check = false;
        }
        if (activityCodeHelper == null) {
            System.out.println("Ошибка! Не найден раздел activityCodeHelper");
            check = false;
        }
        if (activityCodeAssignmentHelper == null) {
            System.out.println("Ошибка! Не найден раздел activityCodeAssignmentHelper");
            check = false;
        }
        if (stepHelper == null) {
            System.out.println("Ошибка! Не найден раздел stepHelper");
            check = false;
        }
        if (udfValueHelper == null) {
            System.out.println("Ошибка! Не найден раздел udfValueHelper");
            check = false;
        }
        return check;
    }

    public ActivityHelper getActivityHelper() {
        return activityHelper;
    }

    public ActivityCodeTypeHelper getActivityCodeTypeHelper() {
        return activityCodeTypeHelper;
    }

    public ActivityCodeHelper getActivityCodeHelper() {
        return activityCodeHelper;
    }

    public ActivityCodeAssignmentHelper getActivityCodeAssignmentHelper() {
        return activityCodeAssignmentHelper;
    }

    public StepHelper getStepHelper() {
        return stepHelper;
    }

    public UDFValueHelper getUdfValueHelper() {
        return udfValueHelper;
    }

    public String getProjectName() {
        return projectName;
    }
}
