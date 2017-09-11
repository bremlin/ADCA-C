package service.xml;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

public class UDFValue {

    private int stepObjectId;
    private Double PV;

    public UDFValue(Element udfValueElement) {
        this.stepObjectId = Integer.parseInt(udfValueElement.getChild(XMLTypes.STEP_ID).getText());
        String udfValueString = udfValueElement.getChild(XMLTypes.VALUE).getText().replace(",", ".");
        int apache = StringUtils.countMatches(udfValueString, ".");
        if (apache > 1) {
            this.PV = Double.parseDouble(udfValueString.substring(0, udfValueString.indexOf(".")).replace(" ", ""));
        } else {
            this.PV = Double.parseDouble(udfValueString.replace(" ", ""));
        }
    }

    public int getStepObjectId() {
        return stepObjectId;
    }

    public Double getPV() {
        return PV;
    }
}
