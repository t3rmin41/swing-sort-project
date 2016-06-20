package unique.digital.student;

public enum SortMethod {

    BUBBLE("Bubble"),
    HEAP("Heap"),
    MERGE("Merge");
    
    private String method;
    
    private SortMethod(String method) {
        this.method = method;
    }
    
    public String getMethod() {
        return method;
    }
}
