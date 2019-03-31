import java.io.IOException;
import java.util.*;

class Main 
{ 
    public static String findRepPattern(String str) {
        int len = str.length();
        if (len % 2 != 0) {
            return null;
        }
        for(int i = 1; i <= len/2; ++i) {
            String prefix = str.substring(0, i);
            int start = i;
            boolean found = true;

            while (start < len && found) {
                String lastPrefix = str.substring(start, start + i);
                // System.out.println("i : " + i + " start : " + start + " len : " + len + " found : " + found + " prefix : " + prefix +" lastPrefix : " + lastPrefix);
                if (lastPrefix.equals(prefix)) {
                    start += i;
                } else {
                    found = false;
                }
                // System.out.println("i : " + i + " start : " + start + " len : " + len + " found : " + found + " prefix : " + prefix +" lastPrefix : " + lastPrefix);
            }

            if (start == len) {
                System.out.println("pattern: " + prefix + " N = " + (len / prefix.length()));
                return prefix;
            }
        }
        return null;
    } 
    
    public static int printAllSubsets(int [] arr, int k) {
        int count = 0;
        int n = arr.length;
        for (int i = 0; i < (1 << n); i++) {
            // System.out.print("subsets : ");
            int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
            int minIdx = -1, maxIdx = -1;
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    // System.out.print("\t" + arr[j]);
                    if (min > arr[j]) {
                        min = arr[j];
                        minIdx = j;
                    }
                    if(max < arr[j]) {
                        max = arr[j];
                        maxIdx = j;
                    }
                }
            }
            if (max != Integer.MIN_VALUE && min != Integer.MAX_VALUE && min + max <= k) {
                count++;
                System.out.println("min : " + min + "-" + minIdx + " max : " + max + "-" + minIdx);
            }
        }
        return count;
    }
    
    public static int maxTeams(Country[] countries) {
        
        PriorityQueue<Country> countryList = new PriorityQueue<>(countries.length, Collections.reverseOrder(new CountryComparator()));
        for (int i = 0; i < countries.length; i++) {
            countryList.add(countries[i]);
        }
        Iterator<Country> itr = countryList.iterator();
        while(itr.hasNext()) {
            System.out.println(itr.next().getNumOfPlayers());
        }
        return 1;
    }
    
    public static int longestCycle(List<List<Integer>> adj, int node) {

      Queue<Integer> q = new LinkedList<Integer>();
      
      boolean[] visited = new boolean[adj.size()];
      int[] levels = new int[adj.size()];
      levels[node] = 1;
      
      q.add(node);
      visited[node] = true;
      
      while (!q.isEmpty()) {
        int top = q.poll();
        
        for (Integer x: adj.get(top)) {
          if (visited[x]) {
             if (x == node) {
              return levels[top];
             }
          } else {
          
            levels[x] = levels[top] + 1;
            q.add(x);
          }
        }
      
      }
      return -1;
    }

    static void KMPSearch(String pat, String txt) 
    { 
        int M = pat.length(); 
        int N = txt.length(); 
        int lps[] = new int[M]; 
        int j = 0;

        computeLPSArray(pat, M, lps); 
  
        int i = 0;
        while (i < N) { 
            if (pat.charAt(j) == txt.charAt(i)) { 
                j++; 
                i++; 
            }
            
            if (j == M) { 
                System.out.println("Found pattern at index " + (i - j)); 
                j = lps[j - 1]; 
            } else if (i < N && pat.charAt(j) != txt.charAt(i)) { 
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i = i + 1;
                }
            } 
        } 
    } 
  
    static void computeLPSArray(String pat, int M, int lps[]) 
    { 
        int start = 0; 
        int i = 1; 
        lps[0] = 0;
  
        while (i < M) { 
            if (pat.charAt(i) == pat.charAt(start)) { 
                start++; 
                lps[i] = start;
                i++; 
            } 
            else
            { 
                if (start != 0) { 
                    start = lps[start - 1];
                } else { 
                    lps[i] = start; 
                    i++; 
                } 
            } 
        } 
    }
    
    public static void main(String[] args) 
    { 
        String str = "ababab";
        findRepPattern(str); 
        String str2 = "aabbaaabba";
        findRepPattern(str2);
        String str3 = "ababa";
        findRepPattern(str3);
        
        int arr[] = new int[] {2, 4, 5, 7};
        System.out.println(printAllSubsets(arr, 8));
        
        maxTeams(new Country[]{new Country(1),new Country(16),new Country(13),new Country(7),new Country(35),new Country(24)});
        
        // graph
        List<List<Integer>> adj = new ArrayList<List<Integer>>(9);
    
        for (int i = 0; i < 9; i++) {
          adj.add(new ArrayList<Integer>());
        }
        adj.get(4).add(5);
        adj.get(5).add(6);
        adj.get(5).add(7);
        adj.get(7).add(4);
        adj.get(7).add(8);
        System.out.println(longestCycle(adj,4));
        
        KMPSearch("AAA", "AAAABAAA");
    } 
}

class Country {
    public int numOfPlayers;
    
    public Country() {
        super();
    }
    
    public Country(int value) {
        this.numOfPlayers = value;
    }
    
    public int getNumOfPlayers() {
        return numOfPlayers;
    }
    
    public void setNumOfPlayers(int value) {
        this.numOfPlayers = value;
    }
}

class CountryComparator implements Comparator<Country> {
    public int compare(Country c1, Country c2) {
        if(c1.getNumOfPlayers() > c2.getNumOfPlayers()) {
            return 1;
        } else if (c1.getNumOfPlayers() < c2.getNumOfPlayers()) {
            return -1;
        } else {
            return 0;
        }
    }
}