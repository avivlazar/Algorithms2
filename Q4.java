
public class Q4 {
	
	public static void main(String[] args) {
		int[] array1 = {10, 2, 1, 13, 1, 6, 7, 8, 3, 9, 3, 12, 7};
		
		//int[] array = {10, 2, 1, 13, 1};
		int numOfGroups = 3;
		Method(array1, numOfGroups);
		
	}
	
	/*
	 * W is the array of numbers 
	 * g is num of groups the user wish to divide W
	 * The algorithm print the groups which its biggest sum 
	 * which is the most minimize for any other devision that you could do.
	 * for example: W = {10, 2, 1, 13, 1}, g = 3
	 * solution:
	 * [10, 2, 1]  [13]  [1] 
	 * The biggest group's sum is: 13
	 * Runtime Complexity: O(g*(W.length)^2)  when  g <= W.length
	 * 
	 * Assumptions:
	 * g <= W.length AND g >= 1
	 */
	private static void Method(int[] W, int g) {
		
		// So that: Sums[i][j] = W[i] + W[i+1] + ... + W[j]
		int[][] Sums = new int[W.length][W.length];
		
		int[][] T = new int[g][W.length];
		
		// Runtime Complexity: O((W.length)^2)
		allZero(Sums);
		allZero(T);

		fillSums_Algorithm(W, Sums);
		
		//Runtime Complexity: O(g*(W.length)^2)
		int iterations = fillT_Algorithm(W, g, Sums, T);
		
		System.out.println("num Of iteration: " + iterations);
		
		System.out.println("Sums = ");
		printResult(Sums);
		
		System.out.println("\n\nT = ");
		printResult(T);
		
		// Runtime Complexity: O(W.length + g)
		findAndPrintSolution(W, T);
	}

	private static void fillSums_Algorithm(int[] W, int[][] Sums) {
		for(int m = 0; m < W.length; m++) 
			Sums[m][m] = W[m];
		
		for(int i = 0; i < W.length; i++)
			for(int j = i+1; j < W.length; j++)
				Sums[i][j] = Sums[i][j-1] + W[j];
	}

	private static int fillT_Algorithm(int[] W, int g, int[][] Sums, int[][] T) {
		for(int j = 0; j < W.length; j++)
			T[0][j] = Sums[0][j];

		int[] temp;
		int iterations = 0;
		for(int r=1; r < g; r++) {
			for(int c = r; c < W.length; c++) {
				temp = new int[c-(r-1)];
				for(int k = r-1; k < c; k++)
				{
					int min_sol = T[r-1][k];
					int sum = Sums[k+1][c];
					temp[k-(r-1)] = max(min_sol, sum);
					iterations++;
				}
				T[r][c] = min(temp);
			}
		}
		return iterations;
	}

	private static void findAndPrintSolution(int[] W, int[][] T) {
		int row = T.length - 1;
		int column = T[0].length - 1;
		int value = T[row][column];
		
		int[] solution = new int[row+1];
		solution[row] = column;
		
		// Runtime Complexity: O(W.length + g)
		while(row > 0) {
			row--;
			column--;
			
			while(T[row][column] > value) {
				column--;
			}
			
			// In this point: T[row][column] <= value
			solution[row] = column;
			value = T[row][column];
		}
		
		System.out.println("The solution: ");
		
		int index = 0;
		
		// Runtime Complexity: O(W.length)
		for(int i = 0; i < T[0].length;) {
			System.out.printf(" [");
			int j;
			for(j = i; j < solution[index]; j++) {
				System.out.printf("" + W[j] + ", ");
				i++;
			}
			System.out.printf("" + W[j] + "");
			System.out.printf("] ");
			
			i++;
			index++;
		}
		System.out.println("\nThe biggest group's sum is: " + T[T.length - 1][T[0].length - 1]);
	}


	private static void printResult(int[][] result) {
		for(int i = result.length-1; i >= 0; i--) {
			for(int j = 0; j < result[0].length; j++) {
				System.out.printf("%5d", result[i][j]);
			}
			System.out.println();
		}
	}

	private static int min(int[] temp) {
		int min = temp[0];
		for(int i = 1; i < temp.length; i++)
			min = min(min, temp[i]);
		return min;
	}


	private static int min(int a, int b) {
		return (a < b ? a : b);
	}


	private static int max(int a, int b) {
		return (a > b ? a : b);
	}


	private static void allZero(int[][] matrix) {
		
		for(int i = 0; i < matrix.length; i++)
			for(int j = 0; j < matrix[0].length; j++)
				matrix[i][j] = 0;	
	}
}


