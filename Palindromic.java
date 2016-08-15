package StringHandle;

public class Palindromic {
	public static String palindromicDp(String st){
		if(st==null) return st;
		char[] chars = st.toCharArray();
		int l = chars.length;
		boolean[][] dps = new boolean[l][l];
		dps[0][0]=true;
		for(int i=1;i<l;i++){
			dps[i][i]=true;
			dps[i][i-1]=true;
		}
		int left=0,right=0;
		for(int t=2;t<=l;t++){
			for(int i=0;i+t<=l;i++){
				if(chars[i]==chars[t+i-1] && dps[i+1][t+i-2]){
					dps[i][t+i-1]=true;
					if(t>right-left+1){
						left= i;
						right = t+i-1;
					}
				}
			}
		}
		return st.substring(left,right+1);
	}

	public static String palindromicDpConstMem(String st){
		if(st==null) return st;
		int left=0,right=0,l=st.length();
		for(int i=1;i<l;i++){
			for(int oe=0;oe<2;oe++)
			{
				int low = i-1, high = i+oe;
				while(low>=0 && high<l && st.charAt(low)==st.charAt(high)){
					low--;
					high++;
				}
				if(high-1-(low+1)>right-left){
					right=high-1;
					left =low+1;
				}
			}	
		}
		return st.substring(left,right+1);
	}

}
