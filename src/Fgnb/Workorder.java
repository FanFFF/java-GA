package Fgnb;
import java.util.*;
import java.util.ArrayList;

public class Workorder {

    public int Work_number;

    public int Mac_number;

    public String StartTime;
    public ArrayList<Workpiece> PieceList = new ArrayList();

    public int getn(){ return Work_number; }
    public void setn(int n){ this.Work_number=n; }
    public int getm(){ return Mac_number; }
    public void setm(int m){ this.Mac_number=m; }
}
