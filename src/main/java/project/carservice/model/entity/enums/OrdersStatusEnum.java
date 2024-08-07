package project.carservice.model.entity.enums;
public enum OrdersStatusEnum {
    SCHEDULED ("Scheduled"),
    PENDING ("Pending"),
    IN_PROGRESS ("In Progress"),
    FINISHED ("Finished");
    private final String displayName;

    OrdersStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}
