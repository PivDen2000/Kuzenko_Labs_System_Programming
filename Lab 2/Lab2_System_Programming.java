//variant 2
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Lab2_System_Programming {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        scanner.close();

        File file = new File(filename);
        scanner = new Scanner(file);

        int A,S,s0,F;
        A = Integer.parseInt(scanner.nextLine());
        S = Integer.parseInt(scanner.nextLine());
        s0 = Integer.parseInt(scanner.nextLine());
        
        String[] F_strings = scanner.nextLine().split(" ");
        F = Integer.parseInt(F_strings[0]);

        int[] F_ints = new int[F_strings.length];
        for(int i = 0; i < F_strings.length; ++i){
            F_ints[i] = Integer.parseInt(F_strings[i]);
        }

        ArrayList<String> f = new ArrayList<>(); 
        while(scanner.hasNextLine()){
            f.add(scanner.nextLine());
        }
        scanner.close();

        boolean[] used = new boolean[S];
        boolean[] passe = new boolean[S];

        dfs(s0,f,used);
        for (String fi : f) {
            if(!fi.split(" ")[0].equals(fi.split(" ")[2])){
            passe[Integer.parseInt(fi.split(" ")[0])] = true;
            }
        }

        ArrayList<Integer> reachless = new ArrayList<>();
        ArrayList<Integer> impasse = new ArrayList<>();
        for (int i = 0; i < used.length; i++) {
            if(!used[i]){reachless.add(i+1);}
            if(!passe[i]){impasse.add(i+1);}
        }
        
        System.out.println(reachless);
        System.out.println(impasse);

    }

    public static void dfs(int pos, ArrayList<String> f, boolean[] used) {
        used[pos] = true;
        int next = pos;
        for (String fi : f) {
            if (Integer.parseInt(fi.split(" ")[0]) == pos) {
                next = Integer.parseInt(fi.split(" ")[2]);
                if(!used[next]){
                    dfs(next, f, used);
                }
            }
        }
    }
}