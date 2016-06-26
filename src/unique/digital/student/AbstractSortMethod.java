package unique.digital.student;

public abstract class AbstractSortMethod<T> {

    private String name;
    protected Comparable<T>[] sortableArray;

    public AbstractSortMethod(String name, Comparable<T>[] array) {
        this.name = name;
        this.sortableArray = array;
    }
    
    public String getName() {
        return name;
    }

    public Comparable<T>[] getSortedArray() {
        sort();
        return sortableArray;
    }

    protected abstract void sort();

}
