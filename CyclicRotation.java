class Solution {

  public int[] solution(int[] A, int K) {
    int N = A.length;
    int[] result = new int[N];

    for (int i = 0; i < N; i++) {
      result[(i + K) % N] = A[i];
    }
    return result;
  }

  public static void main(final String[] args) {
    Solution sol = new Solution();
    int[] numArr = { 3, 8, 9, 7, 6 };
    int[] result = sol.solution(numArr, 3);
    System.out.println(Arrays.toString(result));
  }
}
