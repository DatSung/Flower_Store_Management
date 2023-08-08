package action;

import java.io.File;

public interface ISet {
    public abstract boolean saveFile(File fileName);

    public abstract boolean readFile(File fileName);

    public abstract void sortByDate(String type);

    public abstract void sortByTotalCost(String type);

    public abstract void sortByID(String type);

    public abstract void sortByName(String type);
}
