import java.util.Arrays;

public class PermMissingElem {

  public int solution(int[] A) {

    int size = A.length;
    int count = 1;

    Arrays.sort(A);

    for (int i = 0; i < size; i++) {
        if (A[i] != count)
            return count;
        count++;
    }
    return count;
}

  public static void main(String[] args) {
    PermMissingElem sol = new PermMissingElem();
    final int[] arr = { 2, 3, 1, 5 };
    System.out.println(sol.solution(arr));
  }
}
