package Fgnb;
import GA.GA;
import Fgnb.Workorder;
import Fgnb.Workpiece;
import Fgnb.Sequence;
import java.util.ArrayList;
import java.util.Scanner;

public class mainn {

    public static void main(String[] args) {

        Workorder test = new Workorder();
        Scanner hei = new Scanner(System.in);
        test.StartTime = hei.next();
        test.Work_number = hei.nextInt();
        test.Mac_number = hei.nextInt();
        for (int i = 0; i < test.Work_number; i++) {
            Workpiece ldp = new Workpiece();
            ldp.Work_piece = hei.nextInt();
            for (int j = 0; j < ldp.Work_piece; j++) {
                Sequence jbl = new Sequence();
                jbl.mac = hei.nextInt();
                jbl.len = hei.nextInt();
                ldp.SeqList.add(jbl);
            }
            test.PieceList.add(ldp);
        }
      int time;
        int Q[][];
        int W[][];
        String www[][];
        String eee[][];
        time = new DFS().dfs(test);
        Q = new DFS().getAns();
        W = new DFS().getRns();
        www = new DFS().getAss();
        eee = new DFS().getRss();

        /**
         * 深搜测试
         */

        System.out.println("深搜结果：");
        for(int i = 0; i < test.Mac_number; i++) {
            System.out.println("机器:" + (i + 1));
            for (int j = 0; j < time; j++) {
                System.out.print(" " + Q[i][j]);
            }
            System.out.print("\n");
        }
        for(int i=0;i<test.Mac_number;i++){
            System.out.println("机器:"+(i+1));
            for(int j=0;j<time;j++){
                System.out.print(" |"+www[i][j]);
            }
            System.out.println();
        }
        for(int i = 1; i <= test.Work_number; i++) {
            System.out.println("工件: "+i);
            for(int j = 0; j < time; j++) {
                System.out.print(" "+W[i][j]);
            }
            System.out.print("\n");
        }
        for(int i=1;i<=test.Work_number;i++){
            System.out.println("工件:"+i);
            for(int j=0;j<time;j++){
                System.out.print(" |"+eee[i][j]);
            }
            System.out.println("\n");
        }



        /**
         * GA测试
         */

      System.out.println("GA--------------------");
       new GA().Ga(test);
       int ANS[][] = new int [200][200];
       int RNS[][] = new int [200][200];
       String ASS[][] = new String[200][200];
       String RSS[][] = new String[200][200];
       int mins = new GA().gettime();
       ANS = new GA().getAns();
       RNS = new GA().getRns();
       ASS = new GA().getAss();
       RSS = new GA().getRss();

        for(int i = 0; i < test.Mac_number; i++) {
            System.out.println("机器:" + (i + 1));
            for (int j = 0; j < mins; j++) {
                System.out.print(" " + ANS[i][j]);
            }
            System.out.print("\n");
        }
        for(int i=0;i<test.Mac_number;i++){
            System.out.println("机器:"+(i+1));
            for(int j=0;j<mins;j++){
                System.out.print(" |"+ASS[i][j]);
            }
            System.out.println();
        }
        for(int i = 1; i <= test.Work_number; i++) {
            System.out.println("工件: "+i);
            for(int j = 0; j < mins; j++) {
                System.out.print(" "+RNS[i][j]);
            }
            System.out.print("\n");
        }
        for(int i=1;i<=test.Work_number;i++){
            System.out.println("工件:"+i);
            for(int j=0;j<mins;j++){
                System.out.print(" |"+RSS[i][j]);
            }
            System.out.println("\n");
        }



    }
}
