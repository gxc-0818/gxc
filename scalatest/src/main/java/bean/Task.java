package bean;

import java.io.Serializable;

public class Task implements Serializable {
    private int count=888;

    @Override
    public String toString() {
        return "Task["+count+"]";
    }
}
