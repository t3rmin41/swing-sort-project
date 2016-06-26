package unique.digital.student;

public class HeapSort {

    private StudentStats[] studentStatsArray = null;
    private int n;
    private int left;
    private int right;
    private int lowest;
    
    public HeapSort(StudentStats[] a) {
        this.studentStatsArray = a;
    }
    
    public void sort() {
        buildheap(studentStatsArray);
        for(int i=n; i>0; i--) {
           exchange(0, i);
           n=n-1;
           minheap(studentStatsArray, 0);
        }
    }
    
    public StudentStats[] getSortedArray() {
        return studentStatsArray;
    }
    
    private void buildheap(StudentStats[]a) {
        n = a.length-1;
        for(int i=n/2; i>=0; i--){
            minheap(a,i);
        }
     }
   
    private void minheap(StudentStats[] a, int i) { 
        left = 2*i;
        right = 2*i+1;
   
        if(left <= n && a[left].getScore() < a[i].getScore()){
           lowest=left;
        } else {
           lowest=i;
        }
   
        if(right <= n && a[right].getScore() < a[lowest].getScore()) {
            lowest=right;
        }
        if(lowest!=i) {
           exchange(i, lowest);
           minheap(a, lowest);
        }
     }
   
     private void exchange(int i, int j) {
         StudentStats t = studentStatsArray[i];
          studentStatsArray[i] = studentStatsArray[j];
          studentStatsArray[j] = t; 
     }

}
