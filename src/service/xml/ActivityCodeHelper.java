package service.xml;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityCodeHelper extends ArrayList<ActivityCode> {
    private int pvTypeId = 28271;
    private HashMap<Integer, ArrayList<ActivityCode>> typeMap = new HashMap<>();
    private HashMap<Integer, ActivityCode> idMap = new HashMap<>();

    public ActivityCodeHelper(Element element) {
        for (Element activityCodeElement : element.getChildren()) {
            ActivityCode activityCode = new ActivityCode(activityCodeElement);
            this.add(activityCode);
            if (typeMap.containsKey(activityCode.getCodeTypeObjectId())) {
                typeMap.get(activityCode.getCodeTypeObjectId()).add(activityCode);
            } else {
                ArrayList<ActivityCode> tempList = new ArrayList<>();
                tempList.add(activityCode);
                typeMap.put(activityCode.getCodeTypeObjectId(), tempList);
            }
            idMap.put(activityCode.getObjectId(), activityCode);
        }
    }

    public ArrayList<ActivityCode> getListCodeByType(Integer activityCodeTypeId) {
        return typeMap.getOrDefault(activityCodeTypeId, null);
    }

    public ActivityCode getActivityCodeById(Integer activityCodeId) {
        return idMap.getOrDefault(activityCodeId, null);
    }

    public String getPvType(ArrayList<Integer> codeIds) {
        for (Integer codeId : codeIds) {
            if (idMap.get(codeId).getCodeTypeObjectId() == pvTypeId) return idMap.get(codeId).getName();
        }
        return "---";
    }

    public ArrayList<ActivityCode> getSortParentsType(int groupId) {
        ArrayList<ActivityCode> sortedFilteredList = new ArrayList<>();
        for (ActivityCode activityCode : this) {
            if (activityCode.getCodeTypeObjectId() == groupId) sortedFilteredList.add(activityCode);
        }
        sortedFilteredList.sort((o1, o2) -> o2.getParentObjectId().compareTo(o1.getParentObjectId()));
        return sortedFilteredList;
    }

}
