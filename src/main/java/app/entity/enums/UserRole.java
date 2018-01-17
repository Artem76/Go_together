package app.entity.enums;

public enum UserRole {
    Administrator, Administrator_VoIP, Administrator_SMS, Manager_VoIP, Manager_SMS, NOC_VoIP, NOC_SMS, Report_User, Blocked;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }

}
