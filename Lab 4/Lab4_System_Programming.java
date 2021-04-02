//variant 2
import java.util.Scanner;

class F extends Thread {
    private int x;
    private boolean f;

    public F(int x){this.x = x;}

    public void run(){
        switch (x) {
            case 0:
                f = false;
                break;
            case 2:
                f = true;
                break;
            case 3:
                f = true;
                break;
            default:
                while (true) {}
        }
    }

    public boolean getF(){return f;}
}

class G extends Thread {
    private int x;
    private boolean g;

    public G(int x){this.x = x;}
    
    public void run(){
        switch (x) {
            case 0:
                g = false;
                break;
            case 1:
                g = true;
                break;
            case 3:
                g = true;
                break;
            default:
                while (true) {}
        }
    }
    
    public boolean getG(){return g;}
}

public class Lab4_System_Programming {
    public static void main(String[] args) {

        System.out.println("Input number x: ");
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();

        F f = new F(x);
        G g = new G(x);

        f.start();
        g.start();

        int time = 3000;
        
        try {
            f.join(time);

            boolean isFAlive = f.isAlive();

            while (isFAlive) {
                System.out.println("Function f takes a very long time, press:\n 1) to continue\n 2) to stop\n 3) to continue without message");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        f.join(time);
                        break;
                    case 2:
                        isFAlive = false;
                        break;
                    case 3:
                        f.join();
                        isFAlive = false;
                        break;
                    default:
                    System.out.println("Print correct value");
                        break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            g.join(time);

            boolean isFAlive = g.isAlive();

            while (isFAlive) {
                System.out.println("Function g takes a very long time, press:\n 1) to continue\n 2) to stop\n 3) to continue without message");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        g.join(time);
                        break;
                    case 2:
                        isFAlive = false;
                        break;
                    case 3:
                        g.join();
                        isFAlive = false;
                        break;
                    default:
                    System.out.println("Print correct value");
                        break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scanner.close();
        
        boolean fValue = f.getF();
        boolean gValue = g.getG();

        System.out.println("f value is: " + fValue);
        System.out.println("g value is: " + gValue);

        boolean conjValue = fValue && gValue; 

        System.out.println("f&&g value is: " + conjValue);
    }
}