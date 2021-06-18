package mz.unilurio.solidermed.model;

import java.util.Stack;

public interface Observer {
    public boolean fireAlert();
    public Stack<Measure> getMeasure();
}
