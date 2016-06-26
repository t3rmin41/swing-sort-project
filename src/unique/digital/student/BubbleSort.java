package unique.digital.student;

public class BubbleSort extends AbstractSortMethod<Object> {

    public BubbleSort(String name, Comparable<Object>[] array) {
        super(name, array);
    }

    @Override
    public void sort() {
        Comparable<Object> temp = new StudentStats("", 0.0);
        for (int i = 0; i < sortableArray.length-1; i++ ) {
            for (int j = 1; j < sortableArray.length-i; j++) {
//                if (studentStats[j-1].getScore() < studentStats[j].getScore()) {
//                    temp = studentStats[j-1];
//                    studentStats[j-1] = studentStats[j];
//                    studentStats[j] = temp;
//                }
                if (sortableArray[j-1].compareTo(sortableArray[j]) == -1) {
                    temp = sortableArray[j-1];
                    sortableArray[j-1] = sortableArray[j];
                    sortableArray[j] = temp;
                }
            }
        }
    }

}
