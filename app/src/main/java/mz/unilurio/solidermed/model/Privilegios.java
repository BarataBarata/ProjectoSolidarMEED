package mz.unilurio.solidermed.model;

public class Privilegios {

    private  static boolean viewAll;
    private  static boolean addUserNurse;
    private  static boolean removeUserNurse;
    private  static boolean viewTimerChildbirth;

    public boolean isAddUserNurse() {
        return addUserNurse;
    }

    public void setAddUserNurse(boolean addUserNurse) {
        this.addUserNurse = addUserNurse;
    }

    public boolean isRemoveUserNurse() {
        return removeUserNurse;
    }

    public void setRemoveUserNurse(boolean removeUserNurse) {
        this.removeUserNurse = removeUserNurse;
    }

    public boolean isViewAll() {
        return viewAll;
    }

    public void setViewAll(boolean viewAll) {
        this.viewAll = viewAll;
    }

    public boolean isViewTimerChildbirth() {
        return viewTimerChildbirth;
    }

    public void setViewTimerChildbirth(boolean viewTimerChildbirth) {
        this.viewTimerChildbirth = viewTimerChildbirth;
    }
}
