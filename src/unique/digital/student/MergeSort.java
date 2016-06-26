package unique.digital.student;

public class MergeSort extends AbstractSortMethod<Object> {

    public MergeSort(String name, Comparable<Object>[] array) {
        super(name, array);
    }

    @Override
    protected void sort() {
        sortMerge(sortableArray);
    }
    
    private Comparable<Object>[] sortMerge(Comparable<Object>[] array) {
        //If list is empty; no need to do anything
        if (array.length <= 1) {
            return array;
        }
         
        //Split the array in half in two parts
        Comparable<Object>[] first = new Comparable[array.length / 2];
        Comparable<Object>[] second = new Comparable[array.length - first.length];
        System.arraycopy(array, 0, first, 0, first.length);
        System.arraycopy(array, first.length, second, 0, second.length);
         
        //Sort each half recursively
        sortMerge(first);
        sortMerge(second);
         
        //Merge both halves together, overwriting to original array
        merge(first, second, array);
        return array;
    }
    
    private Comparable<Object>[] merge(Comparable<Object>[] first, Comparable<Object>[] second, Comparable<Object>[] result) {
        //Index Position in first array - starting with first element
        int iFirst = 0;
         
        //Index Position in second array - starting with first element
        int iSecond = 0;
         
        //Index Position in merged array - starting with first position
        int iMerged = 0;
         
        //Compare elements at iFirst and iSecond, 
        //and move smaller element at iMerged
        while (iFirst < first.length && iSecond < second.length) {
//            if (first[iFirst].getScore() > second[iSecond].getScore()) {
            if (first[iFirst].compareTo(second[iSecond]) == 1) {
                result[iMerged] = first[iFirst];
                iFirst++;
            } else {
                result[iMerged] = second[iSecond];
                iSecond++;
            }
            iMerged++;
        }
        //copy remaining elements from both halves - each half will have already sorted elements
        System.arraycopy(first, iFirst, result, iMerged, first.length - iFirst);
        System.arraycopy(second, iSecond, result, iMerged, second.length - iSecond);
        return result;
    }

}
