package service.xml;

import org.jdom2.Element;

import java.util.HashMap;

public class UDFValueHelper extends HashMap<Integer, UDFValue> {
    public UDFValueHelper(Element element) {
        for (Element udfValueElement : element.getChildren()) {
            UDFValue udfValue = new UDFValue(udfValueElement);
            this.put(udfValue.getStepObjectId(), udfValue);
        }
    }
}
