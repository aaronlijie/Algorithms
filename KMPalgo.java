package StringHandle;
import java.util.*;

public class KMPalgo {
	public static List<Integer> kmp(String target, String pattern){
		int tLen = target.length(),pLen = pattern.length();
		int[] result = preProcess(pattern);
		int j=0;
		List<Integer> ret = new ArrayList<>();
		for(int i=0;i<tLen;i++){
			while(j>0 && target.charAt(i)!=pattern.charAt(j)){
				j = result[j-1];
			}
			if(target.charAt(i)==pattern.charAt(j)){
				j++;
			}
			if(j==pLen){
				j = result[j-1];
				ret.add(i-pLen+1);
				
			}
		}
		return ret;
	}
	private static int[] preProcess(String s){
//		Partial Match Table
//	    It is an array of size (pattern_length+1) where, 
//	    for each position of i in pattern p, b[i] is defined 
//	    such that it takes the ‘length of the longest proper 
//	    suffix of P[1…i-1]’ that matches with the ‘prefix of P’.
		int size = s.length();
		int[] result = new int[size];
		result[0] = 0;
		int j=0;
		for(int i=1;i<size;i++){
			while(j>0 && s.charAt(j)!=s.charAt(i)){
				j =result[j-1];
			}
			if(s.charAt(j)==s.charAt(i)) j++;
			result[i] = j;
		}
		return result;
	}
}
