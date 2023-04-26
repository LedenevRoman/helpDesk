package com.training.rledenev.enums;

import static com.training.rledenev.enums.Status.*;

public enum Action {
    SUBMIT("Submit", NEW),
    APPROVE("Approve", APPROVED),
    DECLINE("Decline", DECLINED),
    CANCEL("Cancel", CANCELED),
    ASSIGN_TO_ME("Assign to me", IN_PROGRESS),
    DONE("Done", Status.DONE),
    LEAVE_FEEDBACK("Leave feedback", Status.DONE);

    private final String name;

    private final Status status;

    Action(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return getName();
    }
}
