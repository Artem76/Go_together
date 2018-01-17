package app.entity.enums;

/**
 * Created by Олег on 29.08.2017.
 */
public enum TTStatuses {
    Opened, WaitingVendor, Closed, ClosesOurProblem;

    @Override
    public String toString() {
        String returnValue = "";
        if (name().equals("Opened") || name().equals("Closed") || name().equals("New")) {
            return name();
        } else if(name().equals("WaitingVendor")) {
            return "Forwarded to vendor";
        } else if(name().equals("ClosesOurProblem")) {
            return "Closed (problem on our side)";
        }
        return returnValue;
    }

}
