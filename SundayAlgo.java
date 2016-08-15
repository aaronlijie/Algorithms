package StringHandle;
import java.util.*;

public class SundayAlgo {
	public static List<Integer> sunday(String str, String pat){
		List<Integer> ret = new ArrayList<>();
		if(pat== null || str== null || pat.length()>str.length()){
			return ret;
		}
		int lpat = pat.length(), lstr = str.length();
		Map<Character, Integer> table = new HashMap<>();
		for(int i=0;i<lpat;i++) table.put(pat.charAt(i), i);
		int start = 0;
		while(start+lpat<=lstr){
			int m=0;
			while(m<lpat && str.charAt(m+start)==pat.charAt(m)) m++;
			if(m==lpat){ // find the substring
				ret.add(start);
				start++;
			}
			else{
				if(start+lpat>=lstr) break;
				else{
					char c = str.charAt(start+lpat);
					if(table.containsKey(c)){
						start+=lpat-table.get(c);
					}
					else{
						start+=lpat;
					}
				}
			}
		}
		return ret;
	}
}
