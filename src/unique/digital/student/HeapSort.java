package unique.digital.student;

public class HeapSort extends AbstractSortMethod<Object> {

    public HeapSort(String name, Comparable<Object>[] array) {
        super(name, array);
    }

    private int n;
    private int left;
    private int right;
    private int lowest;

    @Override
    public void sort() {
        buildheap(sortableArray);
        for(int i=n; i>0; i--) {
           exchange(0, i);
           n=n-1;
           minheap(sortableArray, 0);
        }
    }

    private void buildheap(Comparable<Object>[]a) {
        n = a.length-1;
        for(int i=n/2; i>=0; i--){
            minheap(a,i);
        }
     }
   
    private void minheap(Comparable<Object>[] a, int i) { 
        left = 2*i;
        right = 2*i+1;
   
//        if(left <= n && a[left].getScore() < a[i].getScore()){
//           lowest=left;
//        } else {
//           lowest=i;
//        }
//   
//        if(right <= n && a[right].getScore() < a[lowest].getScore()) {
//            lowest=right;
//        }
        if (left <= n && a[left].compareTo(a[i]) == -1) {
            lowest = left;
        } else {
            lowest = i;
        }

        if (right <= n && a[right].compareTo(a[lowest]) == -1) {
            lowest = right;
        }
        if(lowest!=i) {
           exchange(i, lowest);
           minheap(a, lowest);
        }
     }
   
     private void exchange(int i, int j) {
         Comparable<Object> t = sortableArray[i];
         sortableArray[i] = sortableArray[j];
         sortableArray[j] = t; 
     }

}
