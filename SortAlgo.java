package SortRelated;

import java.util.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortAlgo {
	
	public static <T extends Comparable<T>> void bubbleSort(T[] array){
		int l = array.length;
		for(int i=0;i<l;i++){
			for(int j=0;j<l-i-1;j++){
				if(array[j].compareTo(array[j+1])>0){
					T temp = array[j];
					array[j] = array[j+1];
					array[j+1]=temp;
				}
			}
		}
	}
	
	public static void mergeSort(int[] array){
		int[] ret = divide(array,0,array.length-1);
		for(int i=0;i<array.length;i++) array[i] = ret[i];
	}
	private static int[] divide(int[] array,int start,int end){
		if(start>end) return new int[0];
		else if(start==end){
			int[] ret = new int[1];
			ret[0] = array[start];
			return ret;
		}
		else{
			int mid = start + (end-start)/2;
			int[] left = divide(array,start,mid);
			int[] right= divide(array,mid+1,end);
			return conquer(left,right);
			
		}
	}
	private static int[] conquer(int[] left,int[] right){
		int[] ret = new int[left.length+right.length];
		int i=0,j=0,k=0;
		while(i<left.length && j<right.length){
			if(left[i]<right[j]) ret[k++] = left[i++];
			else ret[k++] = right[j++];
		}
		while(i<left.length) ret[k++] = left[i++];
		while(j<right.length) ret[k++] = right[j++];
		return ret;
	}
	public static <T extends Comparable<T>> void mergeSort(T[] array){
		List<T> ret= divide(array,0,array.length-1);
		for(int i=0;i<array.length;i++) array[i] = ret.get(i);
	}
	private static<T extends Comparable<T>> List<T> divide(T[] array, int start,int end){
		if(start>end) return new ArrayList<T>();
		if(start==end){
			List<T> ret= new ArrayList<>();
			ret.add(array[start]);
			return ret;
		}
		int mid = start+(end-start)/2;
		List<T> left = divide(array,start,mid);
		List<T> right = divide(array,mid+1,end);
		return conquer(left,right);
	}
	private static<T extends Comparable<T>> List<T> conquer(List<T> left,List<T> right){
		List<T> ret = new ArrayList<T>();
		int i=0,j=0;
		while(i<left.size() && j<right.size()){
			if(left.get(i).compareTo(right.get(j))<0){
				ret.add(left.get(i++));
			}
			else{
				ret.add(right.get(j++));
			}
		}
		while(i<left.size()){
			ret.add(left.get(i++));
		}
		while(j<right.size()){
			ret.add(right.get(j++));
		}
		return ret;
	}

	public static <T extends Comparable<T>> void insertSort(T[] array){
		int l = array.length;
		for(int i=1;i<l;i++){
			int j =i;
			while(j-1>=0 && array[j].compareTo(array[j-1])<0){
				T temp = array[j];
				array[j]=array[j-1];
				array[j-1]=temp;
				j--;
			}
		}
	}
	
	public static <T> void insertSort(T[] array,Comparator<T> com){
		int l = array.length;
		for(int i=1;i<l;i++){
			int j =i;
			while(j-1>=0 && com.compare(array[j],array[j-1])<0){
				T temp = array[j];
				array[j]=array[j-1];
				array[j-1]=temp;
				j--;
			}
		}
	}

	public static <T extends Comparable<T>> void quickSort(T[] array){
		quickSortHelper(array,0,array.length-1);
	}
	private static <T extends Comparable<T>> void quickSortHelper(T[] array, int start, int end){
		if(start>=end) return;
		int mid = start+(end-start)/2,i=start,j=end;
		T val = array[mid];
		while(i<=j){
			while(i<=j && array[i].compareTo(val)<0) i++;
			while(i<=j && array[j].compareTo(val)>0) j--;
			if(i<=j){
				T tem = array[i];
				array[i]=array[j];
				array[j]=tem;
				i++;
				j--;
			}
		}
		if(start<j) quickSortHelper(array,start,j);
		if(end>i) quickSortHelper(array,i,end);
	}

	public static  void countSort(int[] array){
		int max=findMax(array);
		for(int x:array){
			if(x>max) max = x;
		}
		int[] count = new int[max+1];
		for(int x:array) count[x]++;
		int pt=0;
		for(int i=0;i<max+1;i++){
			for(int j=0;j<count[i];j++) array[pt++]=i;
		}
		
	}
	public static void radixSort(int[] array){
		int max=findMax(array);
		for(int x:array){
			if(x>max) max = x;
		}
		int base = 1,count = (int)Math.ceil(Math.log10(max));
		for(int i=0;i<=count;i++){
			List<Integer>[] temp = new List[10];
			for(int j=0;j<10;j++) temp[j] = new ArrayList<Integer>();
			for(int x:array){
				int v = (x/base)%10;
				temp[v].add(x);
			}
			int pt = 0;
			for(List<Integer> l:temp){
				for(Integer v:l) array[pt++]=v;
			}
			base*=10;
		}
		
	}
	public static void bucketSort(int[] array){
		int max=findMax(array);
		int min=findMin(array);
		int bucketCount = (max-min)/array.length+1;
		List<Integer>[] bucket = new List[bucketCount];
		for(int i=0;i<bucketCount;i++) bucket[i] = new ArrayList<>();
		for(int x:array){
			bucket[(x-min)/array.length].add(x);
		}
		int p = 0;
		for(List<Integer> l:bucket){
			Collections.sort(l);
			for(Integer x:l) array[p++] = x;
		}
	}
	
	private static int findMax(int[] array){
		int max=Integer.MIN_VALUE;
		for(int x:array){
			if(x>max) max = x;
		}
		return max;
	}
	private static int findMin(int[] array){
		int min=Integer.MAX_VALUE;
		for(int x:array){
			if(x<min) min = x;
		}
		return min;
	}

	
	public static <T extends Comparable<T>> void heapSort(T[] array){
		buildheap(array);
		int n = array.length-1;
		for(int i=array.length-1;i>0;i--){
			exchange(array,0,i);
			n--;
			build(array,0,n);
			
		}
	}
	
	private static <T extends Comparable<T>> void buildheap(T[] array){
		int n = array.length-1;
		for(int i=n/2;i>=0;i--){
			build(array,i,n);
		}
	}
	private static <T extends Comparable<T>> void build(T[] array, int i, int n){
		int left = getLeft(i), right = getRight(i);
		int largest = i;
		if(left<=n && array[left].compareTo(array[i])>0) largest = left;
		if(right<=n && array[right].compareTo(array[largest])>0) largest = right;
		if(largest!=i){
			exchange(array,i,largest);
			build(array,largest,n);
		}
	}
	
	
	public static <T extends Comparable<T>> void heapSort(T[] array, Comparator<T> comp){
		int n = array.length-1;
		buildheap(array,comp);
		for(int i=n;i>0;i--){
			exchange(array,0,i);
			n--;
			build(array,0,n,comp);
		}
	}
	
	private static <T extends Comparable<T>> void buildheap(T[] array, Comparator<T> comp){
		int n = array.length-1;
		for(int i=n/2;i>=0;i--){
			build(array,i,n,comp);
		}
	}
	private static <T extends Comparable<T>> void build(T[] array, int i, int n, Comparator<T> comp){
		int left = getLeft(i), right = getRight(i);
		int largest = i;
		if(left<=n && comp.compare(array[i],array[left])<=0) largest = left;
		if(right<=n && comp.compare(array[largest], array[right])<=0) largest = right;
		if(largest!=i){
			exchange(array,largest,i);
			build(array,largest,n,comp);
		}
	}
	
	private static <T> void exchange(T[] a,int i,int j){
		T temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	private static int getParent(int i){
		return (i-1)/2;
	}
	private static int getLeft(int i){
		return 2*i+1;
	}
	private static int getRight(int i){
		return 2*i+2;
	}



}
