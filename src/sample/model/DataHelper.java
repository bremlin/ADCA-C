package sample.model;

import javafx.scene.control.TreeItem;
import service.xml.*;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DataHelper {

    private XMLHelper xmlHelper;
    private TreeItem<TreeTableElement> root;

    private ArrayList<TreeItem<TreeTableElement>> activityItems;
    private HashMap<Integer, TreeItem<TreeTableElement>> activityItemsMap;
    private HashMap<Integer, ArrayList<TreeTableElement>> firstGroupItems;
    private HashMap<Integer, ArrayList<TreeTableElement>> secondGroupItems;
    private HashSet<Integer> secondGroupActivityCode;
    private HashMap<Integer, ArrayList<TreeTableElement>> thirdGroupItems;

    private int secondFilterId;
    private int thirdFilterId;

    public DataHelper() {

    }

    public TreeItem<TreeTableElement> getRoot() {
        return root;
    }

    public void setData(XMLHelper xmlHelper) {
        this.xmlHelper = xmlHelper;
        root = new TreeItem<>(new TreeTableElement(xmlHelper.getProjectName()));

        activityItems = new ArrayList<>();
        activityItemsMap = new HashMap<>();
        for (Activity activity : xmlHelper.getActivityHelper()) {
            TreeItem activityItem = new TreeItem<>(new TreeTableElement(activity, xmlHelper));
            activityItems.add(activityItem);
            activityItemsMap.put(activity.getObjectId(), activityItem);
        }
        root.getChildren().setAll(activityItems);
    }

    public ArrayList<String> getGroupList() {
        ArrayList<String> groupList = new ArrayList<>();
        xmlHelper.getActivityCodeTypeHelper().forEach((k, v) -> groupList.add(k));
        return groupList;
    }

    public ArrayList<String> getGroupListTwo(String exception) {
        ArrayList<String> groupList = new ArrayList<>();
        xmlHelper.getActivityCodeTypeHelper().forEach((k, v) -> {
            if (!k.equals(exception)) groupList.add(k);
        });
        return groupList;
    }

    public ArrayList<String> getGroupListThree(String exception, String exception2) {
        ArrayList<String> groupList = new ArrayList<>();
        xmlHelper.getActivityCodeTypeHelper().forEach((k, v) -> {
            if (!k.equals(exception) && !k.equals(exception2)) groupList.add(k);
        });
        return groupList;
    }

    public TreeItem<TreeTableElement> getGroupDataRoot(String firstFilter, String filterTwo, String filterThree) {
        root = new TreeItem<>(new TreeTableElement(xmlHelper.getProjectName()));
        firstGroupItems = new HashMap<>();
        secondGroupItems = new HashMap<>();
        secondGroupActivityCode = new HashSet<>();
        thirdGroupItems = new HashMap<>();

        int firstFilterId = xmlHelper.getActivityCodeTypeHelper().get(firstFilter);
        secondFilterId = (filterTwo != null ? xmlHelper.getActivityCodeTypeHelper().get(filterTwo) : 0);
        thirdFilterId = (filterThree != null ? xmlHelper.getActivityCodeTypeHelper().get(filterThree) : 0);

        for (ActivityCode activityCode : xmlHelper.getActivityCodeHelper()) {
            if (activityCode.getCodeTypeObjectId() == firstFilterId) {
                fillGroupItems(firstGroupItems, activityCode);
            }
            if (secondFilterId > 0 && activityCode.getCodeTypeObjectId() == secondFilterId) {
                fillGroupItems(secondGroupItems, activityCode);
                secondGroupActivityCode.add(activityCode.getObjectId());
            }
            if (thirdFilterId > 0 && activityCode.getCodeTypeObjectId() == thirdFilterId) {
                fillGroupItems(thirdGroupItems, activityCode);
            }
        }
        addGroupedChild(root, 0, 0, firstGroupItems);

        return root;
    }

    private void fillGroupItems(HashMap<Integer, ArrayList<TreeTableElement>> groupItems, ActivityCode activityCode) {
        if (groupItems.containsKey(activityCode.getParentObjectId())) {
            groupItems.get(activityCode.getParentObjectId()).add(new TreeTableElement(activityCode));
        } else {
            ArrayList<TreeTableElement> tempList = new ArrayList<>();
            tempList.add(new TreeTableElement(activityCode));
            groupItems.put(activityCode.getParentObjectId(), tempList);
        }
    }

    private void addGroupedChild(TreeItem node, Integer parentId, int level, HashMap<Integer, ArrayList<TreeTableElement>> groupItems) {
        if (groupItems.get(parentId).size() > 0) {
            for (TreeTableElement treeTableElement : groupItems.get(parentId)) {
                TreeItem newItem = new TreeItem(treeTableElement);
                node.getChildren().add(newItem);
                addActivityToCode(newItem, treeTableElement.getObjectId(), level);
                if (groupItems.containsKey(treeTableElement.getObjectId()))
                    addGroupedChild(newItem, treeTableElement.getObjectId(), level, groupItems);
            }
        }
    }

    private void addActivityToCode(TreeItem codeNode, int codeId, int level) {
        HashSet<Integer> activitiesIdInNode = new HashSet<>();
        if (xmlHelper.getActivityCodeAssignmentHelper().containsKey(codeId)) {
            for (ActivityCodeAssignment activityCodeAssignment : xmlHelper.getActivityCodeAssignmentHelper().get(codeId)) {
                if (secondFilterId > 0 && level == 0) {
                    activitiesIdInNode.add(activityCodeAssignment.getActivityObjectId());
                } else {
                    codeNode.getChildren().add(activityItemsMap.get(activityCodeAssignment.getActivityObjectId()));
                    addStep(activityItemsMap.get(activityCodeAssignment.getActivityObjectId()), activityCodeAssignment.getActivityObjectId());
                }
            }

            if (secondFilterId > 0 && level == 0) {
                HashSet<Integer> activityCodeIdInNode = new HashSet<>();
                for (Integer activityId : activitiesIdInNode) {
                    for (Integer activityCodeId : xmlHelper.getActivityCodeAssignmentHelper().getListAssignmentByActivityId(activityId)) {
                        if (secondGroupActivityCode.contains(activityCodeId)) activityCodeIdInNode.add(activityCodeId);
                    }
                }

                for (Integer activityCodeId : activityCodeIdInNode) {
                    TreeItem newItem = new TreeItem(new TreeTableElement(xmlHelper.getActivityCodeHelper().getActivityCodeById(activityCodeId)));
                    codeNode.getChildren().add(newItem);
                    addActivityToCodeWithList(newItem, activityCodeId, activitiesIdInNode);
                }
            }
        }
    }

    private void addActivityToCodeWithList(TreeItem codeNode, int codeId, HashSet<Integer> usedActivity) {
        if (xmlHelper.getActivityCodeAssignmentHelper().containsKey(codeId)) {
            for (ActivityCodeAssignment activityCodeAssignment : xmlHelper.getActivityCodeAssignmentHelper().get(codeId)) {
                if (usedActivity.contains(activityCodeAssignment.getActivityObjectId())) {
                    codeNode.getChildren().add(activityItemsMap.get(activityCodeAssignment.getActivityObjectId()));
                    addStep(activityItemsMap.get(activityCodeAssignment.getActivityObjectId()), activityCodeAssignment.getActivityObjectId());
                }
            }
        }
    }

    private void addStep(TreeItem parentNode, int nodeId) {
        if (xmlHelper.getStepHelper().get(nodeId) != null) {
            for (Step step : xmlHelper.getStepHelper().get(nodeId)) {
                parentNode.getChildren().add(new TreeItem(new TreeTableElement(step)));
            }
        }
    }
}
