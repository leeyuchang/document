class Solution {

  int solution(int number) {
    String binary = Integer.toBinaryString(number);
    System.out.println("" + number + " (" + binary + ")");
    int zero = 0;
    int max = 0;

    for (char b : binary.toCharArray()) {
      if (b == '1') {
        if (zero > max) {
          max = zero;
        }
        zero = 0;
      } else {
        zero++;
      }
    }
    return max;
  }

  public static void main(final String[] args) {
    final Solution sol = new Solution();
    System.out.println(sol.solution(1041));
  }
}
