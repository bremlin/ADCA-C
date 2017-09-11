package service.xml;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.HashMap;

//<activityCodeObjectId, activityCodeAssignment>
public class ActivityCodeAssignmentHelper extends HashMap<Integer, ArrayList<ActivityCodeAssignment>> {

    public HashMap<Integer, ArrayList<Integer>> assignmentMapActivityIdCodeId = new HashMap<>();

    public ActivityCodeAssignmentHelper(Element element) {
        for (Element activityCodeAssignmentElement : element.getChildren()) {
            ActivityCodeAssignment activityCodeAssignment = new ActivityCodeAssignment(activityCodeAssignmentElement);
            if (this.containsKey(activityCodeAssignment.getActivityCodeObjectId())) {
                this.get(activityCodeAssignment.getActivityCodeObjectId()).add(activityCodeAssignment);
            } else {
                ArrayList<ActivityCodeAssignment> tempList = new ArrayList<>();
                tempList.add(activityCodeAssignment);
                this.put(activityCodeAssignment.getActivityCodeObjectId(), tempList);
            }

            if (assignmentMapActivityIdCodeId.containsKey(activityCodeAssignment.getActivityObjectId())) {
                assignmentMapActivityIdCodeId.get(activityCodeAssignment.getActivityObjectId()).add(activityCodeAssignment.getActivityCodeObjectId());
            } else {
                ArrayList<Integer> tempList = new ArrayList<>();
                tempList.add(activityCodeAssignment.getActivityCodeObjectId());
                assignmentMapActivityIdCodeId.put(activityCodeAssignment.getActivityObjectId(), tempList);
            }
        }
    }

    public ArrayList<Integer> getListAssignmentByActivityId(Integer activityId) {
        return assignmentMapActivityIdCodeId.getOrDefault(activityId, null);
    }

}
