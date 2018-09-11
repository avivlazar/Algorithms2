import java.util.ArrayList;

public class knapsackProblem {

	public static void main(String[] args) {
		
		Item[] items = new Item[5];
		int weightLimit = 7;
		
		int[] weights = {6, 1, 3, 4, 5};
		int[] values = {10, 1, 4, 5, 7};
		
		for(int i = 0; i < items.length; i++) {
			items[i] = new Item(weights[i], values[i]);
		}
		
		int[] solution = knapsack(items, weightLimit);
		System.out.println();
		printSolution(solution);
		
		int basketWeight = 8; 
		int minimumNumOfBasket = minimizeBaskets(weights, basketWeight);
		System.out.printf("\nThe minimum baskets that you need\nunder limit of %d weight each, are: %d", basketWeight, minimumNumOfBasket);
	}
	
	private static int minimizeBaskets(int[] weights, int basketLimitWeight) {
		
		System.out.println("\n\nMinimize Baskets Algorithm has been started!");
		// if there is a n item which can't be in a basket because it's too heavy,
		// there is not a solution
		if(max(weights) > basketLimitWeight)
			return -1;
		
		Item[] items = new Item[weights.length];
		
		for(int i = 0; i < items.length; i++) {
			items[i] = new Item(weights[i], weights[i]);
		}
		
		int[] indexItemsInBasket;
		int basketCounter = 0;
		Item[] remainItems = items;
		System.out.println();
		while(remainItems.length != 0) {
			System.out.printf("\nAfter %d iterations: \n", (basketCounter));
			indexItemsInBasket = knapsack(remainItems, basketLimitWeight);
			remainItems = removeItems(remainItems, indexItemsInBasket);
			basketCounter++;
		}
		
		return basketCounter;
	}
	
	private static int[] knapsack(Item[] items, int weightLimit) {
		
		System.out.println("\nKnapsack Algorithm has been started!");
		int[][] T = new int[items.length+1][weightLimit+1];
		
		fillT_Algorithm(items, T);
		
		printMatrix(T);
		
		int[] oppositeSol = getArraySolution(items, T);
		return swichPlaces(oppositeSol);
	}
	
	private static int[] swichPlaces(int[] oppositeSol) {
		
		int[] sol = new int[oppositeSol.length];
		for(int i = oppositeSol.length-1; i >= 0; i--) {
			sol[(oppositeSol.length-1) - i] = oppositeSol[i];
		}
		return sol;
	}
	
	private static Item[] removeItems(Item[] items, int[] indexItemsInBasket) {
		
		Item[] remainItems = new Item[items.length - indexItemsInBasket.length];
		int j = 0;
		for(int i = 0; i < items.length; i++) {
			if(!isContains(indexItemsInBasket, i)) {
				remainItems[j] = items[i];
				j++;
			}
		}
		return remainItems;
	}

	private static boolean isContains(int[] indexItemsInBasket, int index) {
		
		for(int i = 0; i < indexItemsInBasket.length; i++) {
			if(indexItemsInBasket[i] == index)
				return true;
		}
		return false;
	}
	
	/*
	 * Assumptions:
	 * T is empty (with garbage inside, without zeros)
	 */
	private static void fillT_Algorithm(Item[] items, int[][] T) {
		
		for(int i=0; i<T.length; i++) {
			T[i][0] = 0;
		}
		
		for(int j=0; j<T[0].length; j++) {
			T[0][j] = 0;
		}
		
		int itemIndex;
		Item item;
		
		for(int i=1; i<T.length; i++) {
			itemIndex = i-1;
			for(int j=1; j<T[0].length; j++) {
				item = items[itemIndex];
						
				if(j < item.weight()) 
					T[i][j] = T[i-1][j];
				else
					T[i][j] = max(item.value() + T[i-1][j-item.weight()], T[i-1][j]);
			}
		}
	}
	
	private static void printSolution(int[] solution) {
		
		System.out.println("The index of the items of the solution are: ");
		System.out.printf("[");
		for(int i = 0; i < solution.length-1; i++)
			System.out.printf(solution[i] + " ,");
		System.out.printf(solution[solution.length-1] + "]\n");
	}
	
	private static void printMatrix(int[][] M) {
		System.out.println("\n T =");
		for(int i = 0; i<M.length; i++) {
			for(int j = 0; j<M[0].length; j++) {
				System.out.printf("%5d", M[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private static int[] getArraySolution(Item[] items, int[][] T) {
		
		ArrayList<Integer> solution = new ArrayList<>();
		int itemIndex;
		int j = T[0].length-1;
		int i = T.length-1;
		int arrayListIndex = 0;
		
		while(i > 0 && j > 0) {
			while(T[i][j] == T[i-1][j]) {
				i--;
			}
			itemIndex = i-1;
			//
			solution.add(arrayListIndex, itemIndex);
			arrayListIndex++;
			j -= items[itemIndex].weight();
			i--;
		}
		
		return transferArrayListToArray(solution);
	}
	
	private static int[] transferArrayListToArray(ArrayList<Integer> list) {
		
		int[] array = new int[list.size()];
		for (int k=0; k<list.size(); k++) {
			array[k] = list.get(k);
		}
		return array;
	}

	private static int max(int[] temp) {
		
		int max = temp[0];
		for(int i = 1; i < temp.length; i++)
			max = max(max, temp[i]);
		return max;
	}

	private static int max(int a, int b) {
		return (a > b ? a : b);
	}
}

class Item {
	private int value;
	private int weight;
	
	public Item(int weight, int value) {
		this.value = value;
		this.weight = weight;
	}
	
	public int value() {
		return this.value;
	}
	
	public int weight() {
		return this.weight;
	}
}
