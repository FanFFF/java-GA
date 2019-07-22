package Fgnb;
import java.text.SimpleDateFormat;
import java.util.*;
public class DFS {
    static int nn = 1000;
    static int vis[] = new int [nn];
    static int a[] = new int [nn];
    static int Mac[] = new int [nn];
    static int Ans[][] = new int [nn][nn];
    static int Rns[][] = new int [nn][nn];
    static int Bns[][] = new int [nn][nn];
    static int n,m,k;
    static int tot;
    static int mins;
    static Mission[][] work = new Mission[nn][nn];
    static Mission[] ans = new Mission[nn];
    static Mission[] tmp = new Mission[nn];
    static String Ass[][] = new String [nn][nn];
    static String Rss[][] = new String [nn][nn];
    public static void init(){//清空空间
        tot = 0;
        mins = 2000;
        for(int i=0 ; i<nn ; i++){
            for(int j=0 ; j<nn; j++){
                work[i][j] = new Mission();
            }
        }
        for(int i=0 ;i<nn ; i++){
            ans[i] = new Mission();
        }
        for(int i=0 ;i<nn ; i++){
            tmp[i] = new Mission();
        }
    }
    public static void dfss(int step, int time){//深搜迭代
        if(time>mins) return;
        if(step==tot){
            if(time<=mins){
                mins=time;
                for(int i=0;i<tot;i++){
                    ans[i].Sta = tmp[i].Sta;
                    ans[i].End = tmp[i].End;
                    ans[i].macc  = tmp[i].macc;
                    ans[i].no = tmp[i].no;
                }
            }
            return;
        }
        for(int i=0;i<n;i++){
            int j = vis[i];
            if(j == a[i]) continue;
            if(j-1<0){
                int Begin = Math.max(Mac[work[i][j].mac], 0);
                int tmpp = Mac[work[i][j].mac];
                Mac[work[i][j].mac] = Begin+work[i][j].len;
                int ntime = Math.max(time, Mac[work[i][j].mac]);
                work[i][j].ed = ntime;
                tmp[step].Sta = Begin;
                tmp[step].End = Mac[work[i][j].mac];
                tmp[step].macc = work[i][j].mac;
                tmp[step].no = i;
                vis[i]++;
                dfss(step+1, ntime);
                vis[i]--;
                Mac[work[i][j].mac] = tmpp;
            }
            else{
                int Begin = Math.max(Mac[work[i][j].mac], work[i][j-1].ed);
                int tmpp = Mac[work[i][j].mac];
                Mac[work[i][j].mac] = Begin+work[i][j].len;
                int ntime = Math.max(time, Mac[work[i][j].mac]);
                work[i][j].ed = ntime;
                tmp[step].Sta = Begin;
                tmp[step].End = Mac[work[i][j].mac];
                tmp[step].macc = work[i][j].mac;
                tmp[step].no = i;
                vis[i]++;
                dfss(step+1, ntime);
                vis[i]--;
                Mac[work[i][j].mac] = tmpp;
            }
        }
    }

    /**
     * DFS.dfs(funmins)深搜计算工单，返回最小时间和4个数组代表的甘特图数据
     * @param FunMins 传入工单对象
     * @return mins 方法返回最小时间mins
     */
    public int dfs(Workorder FunMins){
        new DFS().init();
        n = FunMins.Work_number;
        m = FunMins.Mac_number;
        for(int i=0 ;i<n ; i++){
            Workpiece q = FunMins.PieceList.get(i);
            k = q.Work_piece;
            a[i] = k;
            tot += k;
            for(int j=0; j<k; j++){
                Sequence qq = q.SeqList.get(j);
                work[i][j].mac = qq.mac;
                work[i][j].len = qq.len;
                work[i][j].ed = -1;
            }
        }
        new DFS().dfss(0,0);
        for(int i = 0; i < tot; i++) {//整理Ans
            for(int j = ans[i].Sta; j < ans[i].End; j++) {
                Ans[ans[i].macc][j] = ans[i].no+1;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {//整理Ans对应结束时间Ass数组
            for(int i=0;i<FunMins.Mac_number;i++){
                for(int j=0 ;j<mins ;j++){
                    if(Ans[i][j]==0){
                        continue;
                    }
                    String str = FunMins.StartTime;
                    Date dt = sdf.parse(str);
                    Calendar js = Calendar.getInstance();
                    js.setTime(dt);
                    js.add(Calendar.DAY_OF_YEAR,j+1);
                    Date dt1 = js.getTime();
                    String restr = sdf.format(dt1);
                    Ass[i][j] = restr;
                }
            }
        }
        catch (Exception e){
        }
        //---------------------------------------------------------------------------
        for(int i = 0; i < m; i++) {// 整理Rns
            for(int j = 0; j < mins; j++) {
                if(Ans[i][j] == 0)
                    continue;
                else {
                    Rns[Ans[i][j]][j] = i+1;
                }
            }
        }
        SimpleDateFormat rdf = new SimpleDateFormat("yyyy-MM-dd");
        try {//整理Rns对应结束时间Rss数组
            for(int i=1;i<=FunMins.Work_number;i++){
                for(int j=0 ;j<mins ;j++){
                    if(Rns[i][j]==0){
                        continue;
                    }
                    String str = FunMins.StartTime;
                    Date dtt = rdf.parse(str);
                    Calendar jss = Calendar.getInstance();
                    jss.setTime(dtt);
                    jss.add(Calendar.DAY_OF_YEAR,j+1);
                    Date dt11 = jss.getTime();
                    String restr = rdf.format(dt11);
                    Rss[i][j] = restr;
                }
            }
        }
        catch (Exception e){
        }
        return mins;
    }

    /**
     * 得到机器-时间甘特图基本数据，返回Ans数组
     * @return Ans (机器-时间)数组
     */
    public int[][] getAns(){
        return Ans;
    }

    /**
     * 得到工件-时间甘特图基本数据，返回Rns数组
     * @return Rns (工件-时间)数组
     */
    public int[][] getRns(){
        return Rns;
    }

    /**
     * 得到机器-时间甘特图颜色结束时间，返回Ass字符数组
     * @return Ass (机器-时间)字符数组
     */
    public String[][] getAss(){return Ass;};

    /**
     * 得到工件-时间甘特图颜色结束时间，返回Rss字符数组
     * @return Rss (工件-时间)字符数组
     */
    public String[][] getRss(){return Rss;};


}
