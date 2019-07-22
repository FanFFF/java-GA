package GA;

import Fgnb.Sequence;
import Fgnb.Workorder;
import Fgnb.Workpiece;

import java.text.SimpleDateFormat;
import java.util.*;

public class GA {
    static int[] machineWorkTime = new int[200];
    static int[] processIds = new int[200];
    static int[][] endTime = new int[200][200];
    static int[][] startTime = new int[200][200];
    static int[][] Machine = new int[205][205];
    static int[][] Time = new int[205][205];
    static int[][] Process = new int[205][205];
    static int machine;
    static int job;
    static int process;
    static int chromosome_size;
    static int population_numben = 20;//种群规模
    static int times = 5;//迭代次数
    static int N = 200;
    static double cross = 0.95; //交叉概率
    static double mutation = 0.05;//变异概率
    static ArrayList<Gene> populations = new ArrayList();//种群


    /**
     * 这几个数据是要返回到主函数的，然而我不会把他们变成privat，一变前面就接不到数。不到为啥
     * 用到这几个数据的在307行往后
     */
    static int mins;
    static int Ans[][] = new int [200][200];
    static int Rns[][] = new int [200][200];
    static String Ass[][] = new String [200][200];
    static String Rss[][] = new String [200][200];


    /**
     * 找时间
     * @return  time 最短时间
     */
    public int gettime(){ return mins; }
    public void settime(int time){this.mins=time;}
    /**
     * 得到机器-时间甘特图基本数据，返回Ans数组
     * @return Ans (机器-时间)数组
     */
    public int[][] getAns(){ return Ans; }
    public void setAns(int s[][]){ this.Ans=s; }
    /**
     * 得到工件-时间甘特图基本数据，返回Rns数组
     * @return Rns (工件-时间)数组
     */
    public int[][] getRns(){ return Rns; }
    public void setRns(int s[][]){ this.Rns=s; }
    /**
     * 得到机器-时间甘特图颜色结束时间，返回Ass字符数组
     * @return Ass (机器-时间)字符数组
     */
    public String[][] getAss(){ return Ass; }
    public void setAss(String s[][]){ this.Ass=s; }
    /**
     * 得到工件-时间甘特图颜色结束时间，返回Rss字符数组
     * @return Rss (工件-时间)字符数组
     */
    public String[][] getRss(){ return Rss; }
    public void setRss(String s[][]){ this.Rss=s; }



    public int randint1(int end) {
        Random r = new Random();
        int ran1 = r.nextInt(end);
        return ran1;
    }

    public int getint(String w) {

        int e = Integer.parseInt(w);
        return e;
    }

    public String getstring(int i) {
        String q = String.valueOf(i);
        return q;
    }
    public void generateVector(ArrayList<Integer> v, int length) {
        for (int i = 0; i < length; i++) {
            if (i == -1) continue;
            v.add(i);
        }
    }

    /**
     * 计算适应度
     *
     * @param gene
     * @return gene 单个染色体对象
     */
    public Gene calculateFitness(Gene gene) {
        int fulfillTime = 0;

        for (int i = 0; i <= 150; i++) {
            processIds[i] = 0;
            machineWorkTime[i] = 0;
            for (int j = 0; j <= 150; j++) {
                startTime[i][j] = 0;
                endTime[i][j] = 0;
            }
        }
        //System.out.println(" >>"+gene.chromosome);
        //System.out.println("pro= "+processIds[0]+" "+processIds[1]+" "+processIds[2]);
        for (int i = 0; i < gene.getchromosome().size(); i++) {
            int jobId = getint((String) gene.getchromosome().get(i)) - 1;
            //System.out.println(">> " + gene.chromosome);
            int processId = processIds[jobId];
            int machineId = Machine[jobId][processId];
            int time = Time[jobId][processId];
            //System.out.println(">> "+jobId+" "+processId+" "+machineId+" "+time);
            processIds[jobId]++;
            startTime[jobId][processId] = processId == 0 ? machineWorkTime[machineId] : Math.max(endTime[jobId][processId - 1], machineWorkTime[machineId]);
            machineWorkTime[machineId] = startTime[jobId][processId] + time;
            endTime[jobId][processId] = machineWorkTime[machineId];
            if (machineWorkTime[machineId] > fulfillTime) {
                fulfillTime = machineWorkTime[machineId];
            }
        }
        Gene qq = new Gene();
        gene.setfitness(fulfillTime);
        qq = gene;
        return qq;
    }

    /**
     * 初始化种群
     *
     * @param genes
     * @param population
     */
    public void initializePopolation(ArrayList<Gene> genes, int population) {
        ArrayList<Gene> gs = new ArrayList<>();
        for (int i = 0; i < population; i++) {
            ArrayList<Integer> index_list = new ArrayList<>();
            new GA().generateVector(index_list, job * machine);
            Gene gene = new Gene();
            for (int q = 0; q < job * machine; q++) {
                gene.getchromosome().add("0");
            }
            for (int j = 0; j < job; j++) {
                for (int k = 0; k < machine; k++) {

                    int index = new GA().randint1(index_list.size());
                    int value = index_list.get(index);
                    //System.out.print("index="+index+" value="+value+" ");
                    index_list.remove(index);
                    if (Process[j][k] != -1) {
                        //System.out.println("str="+gene.chromosome+" ");
                        String str = new GA().getstring(j + 1);
                        gene.getchromosome().remove(value);
                        gene.getchromosome().add(value, str);
                    }
                }
            }
            // System.out.println("shit1 = "+gene.chromosome);
            // for(int l=0;l<gene.chromosome.)
            for (int l = 0; l < gene.getchromosome().size(); l++) {
                String str = "0";
                if (gene.getchromosome().get(l).equals(str)) {
                    gene.getchromosome().remove(l);
                    l--;
                }
            }
            //System.out.println("shit2 = "+gene.chromosome);
            Gene ss = new GA().calculateFitness(gene);
           // System.out.println(" " + ss.getfitness());
            populations.add(ss);
        }
    }

    /**
     * 基因突变
     *
     * @param gene
     */
    public Gene geneticMutation(Gene gene) {
        ArrayList<Integer> index_list = new ArrayList<>();
        new GA().generateVector(index_list, chromosome_size);
        for (int i = 0; i < 2; i++) {
            int first = randint1(index_list.size() - 1);
            index_list.remove(first);
            int second = randint1(index_list.size() - 1);

        }
        gene = new GA().calculateFitness(gene);
        return gene;
    }

    /**
     * 选择个体
     *
     * @return
     */
    public Gene selectIndividual() {
        ArrayList<Integer> index_list = new ArrayList<>();
        new GA().generateVector(index_list, 20);
        ArrayList<Gene> simple = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int index = new GA().randint1(index_list.size() - 1);
            index_list.remove(index);
            simple.add(populations.get(index));
        }
        Gene best = new Gene();
        for (int i = 1; i < 3; i++) {
            if (simple.get(i).getfitness() < best.getfitness()) {
                best = simple.get(i);
            }
        }
        return best;
    }

    /**
     * 算法主体
     * @param test  order对象
     */
    public void Ga(Workorder test) {
        for (int i = 0; i <= 200; i++) {
            for (int j = 0; j <= 200; j++) {
                Machine[i][j] = -1;
                Process[i][j] = -1;
            }
        }

        job = test.Work_number;
        machine = test.Mac_number;
        for (int i = 0; i < job; i++) {
            Workpiece q = test.PieceList.get(i);
            int k = q.Work_piece;
            chromosome_size += k;

            if (process < k)
                process = k;
            for (int j = 0; j < k; j++) {
                Sequence qq = q.SeqList.get(j);
                Machine[i][j] = qq.mac;
                Time[i][j] = qq.len;
            }
        }
       // System.out.println("size = " + chromosome_size);
        for (int i = 0; i < job; i++) {
            for (int j = 0; j < process; j++) {
                if (Machine[i][j] != -1) {
                    Process[i][Machine[i][j]] = j;
                }
            }
        }
        //-------------------------------------------------------------------------------------------
        initializePopolation(populations, population_numben);
        int n = times;
        while (n-- > 0) {
            //System.out.println("n=" + n);
            double P = randint1(populations.size() - 1);
            if (P < mutation) {
                int index = randint1(populations.size() - 1);
                Gene sb = new Gene();
                sb = geneticMutation(populations.get(index));
                populations.remove(index);
                populations.add(index, sb);
            } else {
                int size = populations.size();
                int m = size / 4;
                Comparator<Gene> comparator = new Comparator<Gene>() {
                    @Override
                    public int compare(Gene o1, Gene o2) {
                        if (o1.getfitness() < o2.getfitness())
                            return 1;
                        else if (o1.getfitness() == o2.getfitness())
                            return 0;
                        else
                            return -1;

                    }
                };
                Collections.sort(populations, comparator);
            }
        }
        Gene best_gene = new Gene();
        best_gene.setfitness(10000);
        for (int i = 0; i < populations.size(); i++) {
           // System.out.println("chromosome = " + populations.get(i).getchromosome() + " " + populations.get(i).getfitness());
            if (best_gene.getfitness() > populations.get(i).getfitness()) {
                best_gene.setchromosome(populations.get(i).getchromosome());
                best_gene.setfitness(populations.get(i).getfitness());
            }
        }
        System.out.println("result=" + best_gene.getchromosome() + " time=" + best_gene.getfitness());
        best_gene = new GA().calculateFitness(best_gene);
        for (int i = 0; i < machine; i++) {
            System.out.println("machine=" + i + " work time=" + machineWorkTime[i]);
        }
        for (int j = 0; j < job; j++) {
            for (int k = 0; k < process; k++) {
                if (Machine[j][k] != -1) {
                    System.out.println("job" + j + " process" + k + " machine" + Machine[j][k] + " starttime=" + startTime[j][k] + " endtime=" + endTime[j][k]);

                    for(int q=startTime[j][k];q<endTime[j][k];q++){//*******整理Ans
                        Ans[Machine[j][k]][q]=j+1;
                    }
                }
            }
        }
        //-------------------------------------------------------------------------------------------------
        new GA().settime(best_gene.getfitness());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {//*******整理Ans对应结束时间Ass数组
            for (int i = 0; i < machine; i++) {
                for (int j = 0; j < mins; j++) {
                    if (Ans[i][j] == 0) {
                        continue;
                    }
                    String str = test.StartTime;
                    Date dt = sdf.parse(str);
                    Calendar js = Calendar.getInstance();
                    js.setTime(dt);
                    js.add(Calendar.DAY_OF_YEAR, j + 1);
                    Date dt1 = js.getTime();
                    String restr = sdf.format(dt1);
                    Ass[i][j] = restr;
                }
            }
        }
        catch (Exception e){
        }

        for(int i = 0; i < job; i++) {// *******整理Rns
            for(int j = 0; j < mins; j++) {
                if(Ans[i][j] == 0)
                    continue;
                else {
                    Rns[Ans[i][j]][j] = i+1;
                }
            }
        }
        SimpleDateFormat rdf = new SimpleDateFormat("yyyy-MM-dd");
        try {//**********整理Rns对应结束时间Rss数组
            for(int i=1;i<=job;i++){
                for(int j=0 ;j<mins ;j++){
                    if(Rns[i][j]==0){
                        continue;
                    }
                    else {
                        String str = test.StartTime;
                        Date dtt = rdf.parse(str);
                        Calendar jss = Calendar.getInstance();
                        jss.setTime(dtt);
                        jss.add(Calendar.DAY_OF_YEAR, j + 1);
                        Date dt11 = jss.getTime();
                        String restr = rdf.format(dt11);
                        Rss[i][j] = restr;
                    }
                }
            }
        }
        catch (Exception e){
        }

    }



}

