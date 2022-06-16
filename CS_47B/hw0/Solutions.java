/** Solutions to the HW0 Java101 exercises.
 *  @author Allyson Park and Pranav Eranki
 */
public class Solutions {

    /** Returns whether or not the input x is even.
     */
    public static boolean isEven(int x) {
        return x%2 == 0;
    }

    // TODO: Fill in the method signatures for the other exercises
    // Your methods should be static for this HW. DO NOT worry about what this means.
    // Note that "static" is not necessarily a default, it just happens to be what
    // we want for THIS homework. In the future, do not assume all methods should be
    // static.

    /**
     * Returns the largest number in a list of numbers
     * @param a integer list of numbers to search
     * @return the largest number in the array
     */
    public static int max(int[] a) {
        int big = a[0];
        for (int i = 0 ; i < a.length ; i ++) {
            if (a[i] > big) {
                big = a[i];
            }
        }
        return big;
    }

    /**
     * Returns whether a given word is in a string array.
     * @param word Word we are looking for.
     * @param bank Array of strings we search through
     * @return Boolean value for if we found the string or not.
     */
    public static boolean wordBank(String word, String[] bank) {
        for (int i = 0 ; i < bank.length ; i ++) {
            if (word.equals(bank[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean threeSum(int[] a) {
        for (int i = 0 ; i < a.length ; i ++) {
            for (int j = 0 ; j < a.length ; j ++) {
                for (int n = 0 ; n < a.length ; n ++) {
                    if ((a[i] + a[j] + a[n]) == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
