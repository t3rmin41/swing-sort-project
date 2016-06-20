package unique.digital.student;

public enum SortMethod {

    BUBBLE("Bubble"),
    HEAP("Heap"),
    MERGE("Merge");
    
    private String method;
    
    private SortMethod(String method) {
        this.method = method;
    }
    
    public String getValue() {
        return method;
    }

    public static SortMethod getMethod(String value) {
        for(SortMethod method : values()) {
            if(method.getValue().equalsIgnoreCase(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException();
    }
}
