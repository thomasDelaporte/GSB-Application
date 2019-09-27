package fr.delaporte.laboratoireGSB.helper.enums;

public enum Role {

    VISITEURMEDICAL("Visiteur medical"),
    DELEGUE("Delegue");

    private String visualName;

    Role(String visualName){
        this.visualName = visualName;
    }

    public static Role valueOf(int ordinal) {
        return Role.values()[ordinal];
    }

    @Override
    public String toString() {
        return this.visualName;
    }
}
