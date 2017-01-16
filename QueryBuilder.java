

import java.io.*;
import java.util.*;



public class QueryBuilder implements Runnable {
	
	
	private static LinkedHashMap<String,Table> mapper; // saving tables for each full name
	private static LinkedHashMap<String,String> shortTofull; // mapping shortname to fullname
	private static String firstLine; // first line of the query
	private static String fullname1,fullname2; // full name for table1 and table2 for each query
	private static HashMap<Integer,ArrayList<Integer>> maprow; //this map is for pre-computation.
	

	// printing based query.
	private static void print(Table t1,int r1,Table t2,int r2){
		if(firstLine.contains("*")){
			System.out.print(t1.y[r1][0]);
			for(int c = 1;c<t1.col;c++){
				System.out.print(" "+t1.y[r1][c]);
			}
			for(int c = 0;c<t2.col;c++){
				System.out.print(" "+t2.y[r2][c]);
			}
		}
		else{
			StringTokenizer s = new StringTokenizer(firstLine);
			s.nextToken(); // remove for select keyword..
			String extra = "";
			while(s.hasMoreTokens()){
				String fname = shortTofull.get(s.nextToken());
				if(fname.equals(fullname1)){
					int colID = t1.colname.get(s.nextToken());
					System.out.print(extra+t1.y[r1][colID]);
				}
				else{
					int colID = t2.colname.get(s.nextToken());
					System.out.print(extra+t2.y[r2][colID]);
				}
				extra = " ";
			}
		}
		System.out.println();
	}
	
	
	
	
	//mapping process for short name and full name and save data for each query full name
	private static void mappingProcess(String u,int i){
		StringTokenizer s = new StringTokenizer(u);
		s.nextToken(); // removing JOIN or FROM keyword
		if(s.countTokens() == 2){
			if(i == 1){
				fullname1 = s.nextToken();
				String sn = s.nextToken();
				shortTofull.put(sn,fullname1);
			}
			else{
				fullname2 = s.nextToken();
				String sn = s.nextToken();
				shortTofull.put(sn,fullname2);
			}
		}
		else if(i == 1){
			fullname1 = s.nextToken();
		}
		else{
			fullname2 = s.nextToken();
		}
	}
	
	
	// indicating second and third line of query.
	private static void seeSecondandThird(String s,String t){
		mappingProcess(s,1);
		mappingProcess(t,2);
	}
	
	
	// indicating first line of query.
	private static void seeFirst(String q){
		firstLine = q.replace(","," ");
		firstLine = firstLine.replace("."," ");
	}
	
	
	
	
	// main query process.
	private static void queryprocess(int c1,int c2){
		Table t1 = mapper.get(fullname1);
		Table t2 = mapper.get(fullname2);
		for(int c = 0;c<t1.row;c++){
			int value = t1.y[c][c1];
			
			// use PRE-COMPUTED value...
			if(maprow.containsKey(value)){
				ArrayList<Integer> rows = maprow.get(value);
				for(int cc = 0;cc<rows.size();cc++){
					print(t1,c,t2,rows.get(cc));
				}
			}
			else{
				ArrayList<Integer> list = new ArrayList<Integer>();
				for(int d = 0;d<t2.row;d++){
					if(value == t2.y[d][c2]){
						print(t1,c,t2,d);
						list.add(d);
					}
				}
				maprow.put(value,list);
			}
		}
		System.out.println();
	}
	
	
	
	
	
	
	// printing initial column name
	private static void printColName(){
		if(firstLine.contains("*")){
		    // print all the column...
			LinkedHashMap<String,Integer> map = mapper.get(fullname1).colname;
			boolean f = true;
			for(String colname : map.keySet()){
				if(f){
					System.out.print(colname); 
					f = false;
				}
				else System.out.print(" "+colname);
			}
			map = mapper.get(fullname2).colname;
			for(String colname : map.keySet()){
				System.out.print(" "+colname);
			}
		}
		else{
			StringTokenizer s = new StringTokenizer(firstLine);
			s.nextToken(); // remove the select keyword..
			s.nextToken(); // ignore table names for column printing...
			String colname = s.nextToken();
			System.out.print(colname);
			while(s.hasMoreTokens()){
				s.nextToken();  // ignore table names for column printing...
				colname = s.nextToken();
				System.out.print(" "+colname);
			}
		}
		System.out.println();
	}
	
	
	
	
	
	
	// indicating 4th line of query.
	private static void seeFourth(String q){
		q = q.replace("="," ");
		q = q.replace("."," ");
		StringTokenizer s = new StringTokenizer(q);
		String fname1,colname1,fname2,colname2;
		s.nextToken(); // removing ON keyword.
		if(shortTofull.size() == 2){
			fname1 = shortTofull.get(s.nextToken());
			colname1 = s.nextToken();
			fname2 =  shortTofull.get(s.nextToken());
			colname2 = s.nextToken();
		}
		else{
			fname1 = s.nextToken();
			colname1 = s.nextToken();
			fname2 =  s.nextToken();
			colname2 = s.nextToken();
		}
		
		// To make them correct sequence.
		if(!fname1.equals(fullname1)){
			fname1 = fullname1;
			String swap = colname1;
			colname1 = colname2;
			fname2 = fullname2;
			colname2 = swap;
		}
		
		
		
		int colIndex1 = mapper.get(fname1).colname.get(colname1);
		int colIndex2 = mapper.get(fname2).colname.get(colname2);
		
		printColName();
		queryprocess(colIndex1,colIndex2);
		

	}
	
	
	
	
	
	private static void seeTheQueries(String [] q){
		
		seeFirst(q[0]);
		seeSecondandThird(q[1],q[2]);
		seeFourth(q[3]);
		
	}
	
	
	 @Override	
	 public void run() {
		//FI k = new FI("E:/b.txt");
		FI k = new FI(System.in);
		int T = k.nextInt();
		for(int test = 1;test<=T;test++){
			mapper = new LinkedHashMap<String,Table>();
			int ntables = k.nextInt();
			while(ntables-->0){
				String tableName = k.next();
				int col = k.nextInt();
				int row = k.nextInt();
				Table t = new Table(row,col);
				for(int c = 0;c<col;c++){
					t.colname.put(k.next(),c); 
				}
				for(int c = 0;c<row;c++){
					for(int d = 0;d<col;d++){
						t.y[c][d] = k.nextInt();
					}
				}
				
				mapper.put(tableName,t);
			}
			
			int query = k.nextInt();
			System.out.println("Test: "+test);
			while(query-->0){
				maprow = new HashMap<Integer,ArrayList<Integer>>();
				shortTofull = new LinkedHashMap<String,String>();
				firstLine = fullname1 = fullname2 = "";
				String [] q = new String[4];
				for(int c = 0;c<4;c++){
					q[c] = k.nextLine();
				}
				seeTheQueries(q);
				k.nextLine();
			}
		}

	}
	
	

	private static class Table{
		LinkedHashMap<String,Integer> colname;
		int [][] y;
		int row,col;
		private Table(int row,int col){
			this.row = row;
			this.col = col;
			y = new int[row][col];
			colname = new LinkedHashMap<String,Integer>();
		}
	}
	
	
	private static class FI {
	    BufferedReader br;
	    StringTokenizer st;
	    
	    private FI(String path){
	        try {
				br = new BufferedReader(new FileReader(path));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        st = null;
	    }

	    private FI(InputStream stream){
	        br = new BufferedReader(new InputStreamReader(stream));
	        st = null;
	    }

	    private String next() {
	        return nextToken();
	    }
	    
	    private String nextLine(){
	    	try {
				return br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return null;
	    }
	   

	    private String nextToken() {
	        while (st == null || !st.hasMoreTokens()) {
	            String line = null;
	            try {
	                line = br.readLine();
	            } catch (IOException e) {
	                throw new RuntimeException(e);
	            }
	            if (line == null) {
	                return null;
	            }
	            st = new StringTokenizer(line);
	        }
	        return st.nextToken();
	    }

	    private int nextInt() {
	        return Integer.parseInt(nextToken());
	    }

	}
	
}