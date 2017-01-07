package Project5;

public class DisjointSet 
{

	int[] set;

	public DisjointSet(int numElements){
		set = new int[numElements];
		for(int i = 0; i<set.length;i++)
			set[i] = -1;
	}

	public void union(int root1, int root2){ 
		if(set[root2]<set[root1]){
			set[root2]+=set[root1];
			set[root1]=root2;
			
		}
		else{
			set[root1]+=set[root2];
			set[root2]=root1;
		}
	}

	public int find(int x){
		if(set[x]<0)
			return x;
		else
			return set[x]=find(set[x]);
	}

	public String toString(){
		String ans = "";
 		for(int i = 0; i < set.length; i++)
			ans += "\t"+set[i];
 		return ans;

	}
}
