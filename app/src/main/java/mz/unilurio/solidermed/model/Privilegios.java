package mz.unilurio.solidermed.model;

public class Privilegios {

    private  boolean addUserNurse;
    private  boolean removeUserNurse;
    private  boolean viewTimerChildbirth;

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

    public boolean isViewTimerChildbirth() {
        return viewTimerChildbirth;
    }

    public void setViewTimerChildbirth(boolean viewTimerChildbirth) {
        this.viewTimerChildbirth = viewTimerChildbirth;
    }
}
