package Fgnb;
import java.util.*;
public class Mission {
    public int mac;
    public int len;
    public int ed;
    public int Sta;
    public int End;
    public int macc;
    public int no;
    public int getSta(){ return Sta; }
    public void setSta(int Sta){ this.Sta=Sta; }
    public int getEnd(){ return End; }
    public void setEnd(int End){ this.End=End; }
    public int getmacc(){
        return macc;
    }
    public void setmacc(int macc){
        this.macc=macc;
    }
    public int getno(){
        return no;
    }
    public void setno(int no){ this.no=no; }
    public int getmac(){
        return mac;
    }
    public void setmac(int mac){
        this.mac=mac;
    }
    public int getlen(){
        return len;
    }
    public void setlen(int len){
        this.len=len;
    }
    public int geted(){
        return ed;
    }
    public void seted(int ed){
        this.ed=ed;
    }
}
