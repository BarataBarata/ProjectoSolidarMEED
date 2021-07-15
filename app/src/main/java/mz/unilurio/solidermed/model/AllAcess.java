package mz.unilurio.solidermed.model;

public class AllAcess {
    private static boolean allAcess;

    public static boolean isAllAcess() {
        return allAcess;
    }

    public static void setAllAcess(boolean allAcess) {
        AllAcess.allAcess = allAcess;
    }
}
