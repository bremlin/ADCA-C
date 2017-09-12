package sample.model;

public enum ItemType {
    Step        ("stepRow"),
    Activity    ("activityRow"),
    Code        ("codeRow"),
    CodeTwo     ("codeTwoRow"),
    CodeThree   ("codeThreeRow"),
    Project     ("projectRow");

    private final String style;

    ItemType(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }
}
