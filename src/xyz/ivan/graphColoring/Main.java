package xyz.ivan.graphColoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

public class Main {
	
	private static int N = 500;
	
	public static void main(String[] args) {
		
		List<Integer>[] adjLists = new List[N];
		
		createEdgesRandomly(adjLists);
		
		greedy(adjLists);
		
		welchPowell(adjLists);
		
	}
	
	private static void welchPowell(List<Integer>[] adjLists) {
		System.out.println("The welch powell algorithm:");
		
//		printGraph(adjLists);
		
		int[] colored = new int[N];
		
		int[][] degreeAndVertex = new int[N][2];
		
		for (int i = 0; i < N; i++) {
			degreeAndVertex[i][0] = i;
			degreeAndVertex[i][1] = adjLists[i].size();
		}
		
		Arrays.sort(degreeAndVertex,(int[] arr1, int[] arr2)->{
			return arr2[1] > arr1[1] ? 1 : -1;
		});
		
		int colorIndex = 1;
		
		for (int i = 0; i < N; i++) {
			Set<Integer> set = new HashSet<>();
			if(!welshDfs(adjLists,set,colored, degreeAndVertex[i][0], colorIndex)) {
				++colorIndex;
			}
		}
		
		for (int i = 0; i < colored.length; i++) {
			System.out.println("Color#" + colored[i] + " was used to color vertex#" + i);
		}
		
		System.out.println("There are " + (colorIndex - 1) + " colors that have been used.");
		
	}
	
	private static boolean welshDfs(List<Integer>[] adjLists,Set<Integer> set, int[] colored, int cur, int curColor) {
		if( colored[cur] != 0 ) {
			return true;
		}
		colored[cur] = curColor;
		set.add(cur);
		for (int i = 0; i < N; i++) {
			boolean isBreak = false;
			for( int num : set ) {
				if( adjLists[num].contains(i) ) {
					isBreak = true;
					break;
				}
			}
			if( isBreak ) {
				continue;
			}
			if( !adjLists[cur].contains(i) && colored[i] == 0 && !set.contains(i) ) {
				welshDfs(adjLists,set, colored, i, curColor);
				break;
			}
		}
		set.remove(cur);
		return false;
	}
	
	private static void greedy(List<Integer>[] adjLists) {
		
		System.out.println("The greedy algorithm:");
		
		int colorNum = 0;
		
		int[] colored = new int[N];
		
//		printGraph(adjLists);
		
		for (int i = 0; i < N; i++) {
			if( colored[i] == 0 ) {
				
				List<Integer> curList = adjLists[i];
				int curLen = curList.size();
				boolean[] colorFlag = new boolean[colorNum + 1];
				
				for (int j = 0; j < curLen; j++) {
					if( colored[curList.get(j)] != 0 ) {
						colorFlag[colored[curList.get(j)]] = true;
					}
				}
				
				boolean flag = false;
				
				for (int j = 1; j < colorFlag.length; j++) {
					if( !colorFlag[j] ) {
						colored[i] = j;
						flag = true;
						break;
					}
				}
				
				if( !flag ) {
					++colorNum;
					colored[i] = colorNum;
				}
				
			}
		}
		
		for (int i = 0; i < colored.length; i++) {
			System.out.println("Color#" + colored[i] + " was used to color vertex#" + i);
		}
		
		System.out.println("There are " + colorNum + " colors that have been used.");
	}
	
	private static void createEdgesRandomly(List<Integer>[] adjLists) {
		
		for (int i = 0; i < N; i++) {
			adjLists[i] = new ArrayList<Integer>();
		}
		
		for (int i = 0; i < N; i++) {
			int edgesNum = (int)(Math.random() * N + 1);
			for (int j = 0; j < edgesNum; j++) {
				int vertex = (int)(Math.random() * N);
				if( vertex != i ) {
					if( !adjLists[i].contains(vertex) ) {
						adjLists[i].add(vertex);
					}
					if( !adjLists[vertex].contains(i) ) {
						adjLists[vertex].add(i);
					}
				}
			}
		}
		
	}
	
	private static void printGraph(List<Integer>[] adjLists) {
		for (int i = 0; i < adjLists.length; i++) {
			System.out.println(adjLists[i]);
		}
	}
	
	@Test
	public void test() {
		for (int i = 0; i < 500; i++) {
			int edgesNum = (int)(Math.random() * N + 1);
			System.out.println(edgesNum);
		}
	}
}
