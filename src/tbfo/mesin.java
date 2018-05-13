/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tbfo;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * state dimulai dari A
 * A bertemu dengan 1...9 -> B
 * B bertemu + - * / -> C  
 * C bertemu 1...9 -> B
 * B bertemu 0...9 -> B
 * B bertemu = -> D
 * D merupakan final state
 * @author mtauf
 */
public class mesin {
    char state='A'; //state mulai
    String string;
    ArrayList<Angka> listAngka = new ArrayList<>();
    ArrayList<Operator> listOp = new ArrayList<>();
    public mesin(String string){
        this.string=string;
        controlState();
    }
    void controlState(){
        //Check apakah string diterima
        
        if(isStringDiterima()){
            pisahString();
            kalkulasi();
        }else{
            JOptionPane.showMessageDialog(null, "String tidak diterima oleh mesin!");
        }
    }
    private float sum=0;
    void kalkulasi(){
        sum=listAngka.get(0).getAngka();
        sum=perkalian();
        sum=pembagian();
        sum=pertambahan();
        sum=pengurangan();
        JOptionPane.showMessageDialog(null, sum);
        
    }
    private float pembagian(){
        for(int i=0;i<listOp.size();i++){
            System.out.println(i);
            if(listOp.get(i).getOp()=='/'){
                sum=bagi(listAngka.get(i).getAngka(),listAngka.get(i+1).getAngka());
                removeListOp(i);
                changeListAngka(i, new Angka(sum));
                removeListAngka(i+1);
                i=-1;
            }
        }
//        System.out.println(sum);
        return sum;
    }
    private float perkalian(){
        for(int i=0;i<listOp.size();i++){
            System.out.println(i);
            if(listOp.get(i).getOp()=='*'){
                sum=kali(listAngka.get(i).getAngka(),listAngka.get(i+1).getAngka());
                removeListOp(i);
                changeListAngka(i, new Angka(sum));
                removeListAngka(i+1);
                i=-1;
            }
        }
//        System.out.println(sum);
        return sum;
    }
    private float pertambahan(){
        for(int i=0;i<listOp.size();i++){
            System.out.println(i);
            if(listOp.get(i).getOp()=='+'){
                sum=tambah(listAngka.get(i).getAngka(),listAngka.get(i+1).getAngka());
                removeListOp(i);
                changeListAngka(i, new Angka(sum));
                removeListAngka(i+1);
                i=-1;
            }
        }
//        System.out.println(sum);
        return sum;
    }
    private float pengurangan(){
        for(int i=0;i<listOp.size();i++){
            System.out.println(i);
            if(listOp.get(i).getOp()=='-'){
                sum=kurang(listAngka.get(i).getAngka(),listAngka.get(i+1).getAngka());
                removeListOp(i);
                changeListAngka(i, new Angka(sum));
                removeListAngka(i+1);
                i=-1;
            }
        }
        return sum; 
    }
  
    void printAngkaOp(){
        for(int i=0;i<listAngka.size();i++){
            System.out.print(i+". "+listAngka.get(i).angka+" | ");
        }
        System.out.println();
        for(int i=0;i<listOp.size();i++){
            System.out.print(i+". "+listOp.get(i).op+" | ");
        }
        System.out.println();
    }
    float tambah(float a1, float a2){
        return a1+a2;
    }
    float kurang(float a1, float a2){
        return a1-a2;
    }
    float kali(float a1, float a2){
        return a1*a2;
    }
    float bagi(float a1, float a2){
        return a1/a2;
    }
    void pisahString(){
        int batasAwal=0,batasAkhir=0;
        String subString;
        int indexAngka;
        for(int i=0;i<string.length();i++){
            if(string.charAt(i)=='+'||string.charAt(i)=='='||string.charAt(i)=='-'||string.charAt(i)=='*'||string.charAt(i)=='/'){
                batasAkhir=i;
                subString=string.substring(batasAwal, batasAkhir);
                addListAngka(new Angka(Float.parseFloat(subString)));
                addListOp(new Operator(string.charAt(i)));
                batasAwal=i+1;
                System.out.println("temp= "+ subString);
            }
            
        }
        
    }
    
    
    private void changeListAngka(int i, Angka a){
        listAngka.set(i, a);
    }
    private void addListAngka(Angka a){
        listAngka.add(a);
    }
    private void addListOp(Operator op){
        listOp.add(op);
    }
    private void removeListAngka(int a){
        listAngka.remove(a);
    }
    private void removeListOp(int a){
        listOp.remove(a);
    }
    
    
    boolean isStringDiterima(){
        char[] charString = string.toCharArray();
        for(int i=0; i<charString.length; i++){
            System.out.println(charString[i]);
            if(!isCharDiterima(charString[i])){
                return false;
            }else{
                moveState(charString[i]);
            }
        }
        System.out.println(state);
        if(state=='D'){
            return true; //apabila posisi state terakhir maka string diterima
        }else{
            return false;
        }
    }
    
    void moveState(char s){
        if(state=='A'){
            if(s>='1' && s<='9'){
                state= 'B';
            }
        }else if(state=='B'){
            if(s>='0' && s<='9'){
                state= 'B';
            }else if(s=='*' || s=='/' || s=='+' || s=='-'){
                state='C';
            }else if(s=='='){
                state='D';
            }
        }else if(state=='C'){
            if(s>='1' && s<='9'){
                state='B';
            }
        }
    }
    
    boolean isCharDiterima(char s){
        switch (state) {
            case 'A':
                if(s>='1' && s<'9'){
                    return true;
                }else{
                    System.out.println(s);
                    return false;
                }
            case 'B':
                if(s>='0' && s<='9'){
                    return true;
                }else if(s=='*' || s=='/' || s=='+' || s=='-' || s=='='){
                    return true;
                }else{
                    return false;
                }
            case 'C':
                if(s>='1' && s<='9'){
                    return true;
                }else{
                    return false;
                }
            default:
                return false;
        }
    }
}
