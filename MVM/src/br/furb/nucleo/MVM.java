package br.furb.nucleo;


import br.furb.enumerator.EnumData;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

/**
 *
 * @author
 */
public class MVM {

    public static int botao = 0;
    private static short mem[] = new short[1024];
    private Map<EnumData, String> datas;
    StringBuilder traceCode;
    int ax, bx, cx, bp, sp, ip, ri;
    
    public MVM(){
        botao = 0;
//        mem = new short[1024];
        datas = new HashMap<>();
        traceCode = new StringBuilder();
        ax = 0;
        bx = 0;
        cx = 0;
        bp = 0;
        sp = 0;
        ip = 0;
    }
    
    public void traduzirCodigoFonte(String codigoFonte, int enderecoDeCarga) throws IOException {        
        int indice = 0;
        
        String [] codigo = codigoFonte.split("\n");
        String linha = "";
        String instFinal = "";
        short valorEstatico = Short.MIN_VALUE;
        for (int i = 0; i < codigo.length; i++) {
            linha = codigo[i];
            if (linha != null && !"".equals(linha)) {              
                if (linha.contains("[bx+")) {
                    instFinal = linha.substring(0, linha.indexOf("[bx+")+4);
                    valorEstatico = Short.parseShort(linha.substring(linha.indexOf("[bx+")+4, linha.indexOf("]")));
                } else if (linha.contains("[bp+")) {
                    instFinal = linha.substring(0, linha.indexOf("[bp+")+4);
                    valorEstatico = Short.parseShort(linha.substring(linha.indexOf("[bp+")+4, linha.indexOf("]")));
                } else if (linha.contains("[bp-")) {
                    instFinal = linha.substring(0, linha.indexOf("[bp-")+4);
                    valorEstatico = Short.parseShort(linha.substring(linha.indexOf("[bp-")+4, linha.indexOf("]")));
                } else if (linha.contains("[")) {
                    instFinal = linha.substring(0, linha.indexOf("[")+1);
                    valorEstatico = Short.parseShort(linha.substring(linha.indexOf("[")+1, linha.indexOf("]")));
                } else if (linha.contains("{")) { 
                    instFinal = linha.substring(0, linha.indexOf("{")+1);
                    valorEstatico = Short.parseShort(linha.substring(linha.indexOf("{")+1, linha.indexOf("}")));
                } else if (linha.contains("jmp")) {
                    instFinal = "jmp ";
                    valorEstatico = Short.parseShort(linha.substring(linha.indexOf("jmp ")+4, linha.indexOf(";")));
                } else if (linha.contains("int")) {
                    instFinal = "int ";
                    valorEstatico = Short.parseShort(linha.substring(linha.indexOf("int ")+4, linha.indexOf(";")));
                } else if (linha.contains("call")) {
                    instFinal = "call ";
                    valorEstatico = Short.parseShort(linha.substring(linha.indexOf("call ")+5, linha.indexOf(";")));
                } else if (linha.contains("test ax0,")) {
                    instFinal = "test ax0,";
                    valorEstatico = Short.parseShort(linha.substring(linha.indexOf(",")+1, linha.indexOf(";")));
                } else if (linha.contains("test axEqbx,")) {
                    instFinal = "test axEqbx,";
                    valorEstatico = Short.parseShort(linha.substring(linha.indexOf(",")+1, linha.indexOf(";")));
                } else {
                    instFinal = linha;
                }   

                switch (instFinal) {
                    case "init ax;":
                        mem[indice + enderecoDeCarga] = 0;
                        break;
                    case "move ax,bx;":
                        mem[indice + enderecoDeCarga] = 1;
                        break;
                    case "move ax,cx;":
                        mem[indice + enderecoDeCarga] = 2;
                        break;
                    case "move bx,ax;":
                        mem[indice + enderecoDeCarga] = 3;
                        break;
                    case "move cx,ax;":
                        mem[indice + enderecoDeCarga] = 4;
                        break;
                    case "move ax,[":
                        mem[indice + enderecoDeCarga] = 5;
                        mem[++indice + enderecoDeCarga] = valorEstatico;
                        break;
                    case "move ax,[bx+":
                        mem[indice + enderecoDeCarga] = 6;
                        mem[++indice + enderecoDeCarga] = valorEstatico;
                        break;
                    case "move ax,[bp-":
                        mem[indice + enderecoDeCarga] = 7;
                        mem[++indice + enderecoDeCarga] = valorEstatico;
                        break;
                    case "move ax,[bp+":
                        mem[indice + enderecoDeCarga] = 8;
                        mem[++indice + enderecoDeCarga] = valorEstatico;
                        break;
                    case "move [":
                        mem[indice + enderecoDeCarga] = 9;
                        mem[++indice + enderecoDeCarga] = valorEstatico;
                        break;
                    case "move [bx+":
                        mem[indice + enderecoDeCarga] = 10;
                        mem[++indice + enderecoDeCarga] = valorEstatico;
                        break;
                    case "move bp,sp;":
                        mem[indice + enderecoDeCarga] = 11;
                        break;
                    case "move sp,bp;":
                        mem[indice + enderecoDeCarga] = 12;
                        break;
                    case "add ax,bx;":
                        mem[indice + enderecoDeCarga] = 13;
                        break;
                    case "add ax,cx;":
                        mem[indice + enderecoDeCarga] = 14;
                        break;
                    case "add bx,cx;":
                        mem[indice + enderecoDeCarga] = 15;
                        break;
                    case "sub ax,bx;":
                        mem[indice + enderecoDeCarga] = 16;
                        break;
                    case "sub ax,cx;":
                        mem[indice + enderecoDeCarga] = 17;
                        break;
                    case "sub bx,cx;":
                        mem[indice + enderecoDeCarga] = 18;
                        break;
                    case "inc ax;":
                        mem[indice + enderecoDeCarga] = 19;
                        break;
                    case "inc bx;":
                        mem[indice + enderecoDeCarga] = 20;
                        break;
                    case "inc cx;":
                        mem[indice + enderecoDeCarga] = 21;
                        break;
                    case "dec ax;":
                        mem[indice + enderecoDeCarga] = 22;
                        break;
                    case "dec bx;":
                        mem[indice + enderecoDeCarga] = 23;
                        break;
                    case "dec cx;":
                        mem[indice + enderecoDeCarga] = 24;
                        break;
                    case "test ax0,":
                        mem[indice + enderecoDeCarga] = 25;
                        mem[++indice + enderecoDeCarga] = valorEstatico;
                        break;
                    case "jmp ":
                        mem[indice + enderecoDeCarga] = 26;
                        mem[++indice + enderecoDeCarga] = valorEstatico;
                        break;
                    case "call ":
                        mem[indice + enderecoDeCarga] = 27;
                        mem[++indice + enderecoDeCarga] = valorEstatico;
                        break;
                    case "ret;":
                        mem[indice + enderecoDeCarga] = 28;
                        break;
                    case "in ax;":
                        mem[indice + enderecoDeCarga] = 29;
                        break;
                    case "out ax;":
                        mem[indice + enderecoDeCarga] = 30;
                        break;
                    case "push ax;":
                        mem[indice + enderecoDeCarga] = 31;
                        break;
                    case "push bx;":
                        mem[indice + enderecoDeCarga] = 32;
                        break;
                    case "push cx;":
                        mem[indice + enderecoDeCarga] = 33;
                        break;
                    case "push bp;":
                        mem[indice + enderecoDeCarga] = 34;
                        break;
                    case "pop bp;":
                        mem[indice + enderecoDeCarga] = 35;
                        break;
                    case "pop cx;":
                        mem[indice + enderecoDeCarga] = 36;
                        break;
                    case "pop bx;":
                        mem[indice + enderecoDeCarga] = 37;
                        break;
                    case "pop ax;":
                        mem[indice + enderecoDeCarga] = 38;
                        break;
                    case "nop;":
                        mem[indice + enderecoDeCarga] = 39;
                        break;
                    case "halt;":
                        mem[indice + enderecoDeCarga] = 40;
                        break;
                    case "dec sp;":
                        mem[indice + enderecoDeCarga] = 41;
                        break;
                    case "move [bp-":
                        mem[indice + enderecoDeCarga] = 42;
                        mem[++indice + enderecoDeCarga] = valorEstatico;
                        break;
                    case "move [bp+":
                        mem[indice + enderecoDeCarga] = 43;
                        mem[++indice + enderecoDeCarga] = valorEstatico;
                        break;
                    case "move ax,{":
                        mem[indice + enderecoDeCarga] = 44;
                        mem[++indice + enderecoDeCarga] = valorEstatico;
                        break;
                    case "test axEqbx,":
                        mem[indice + enderecoDeCarga] = 45;
                        mem[++indice + enderecoDeCarga] = valorEstatico;
                        break;
                    case "inc sp;":
                        mem[indice + enderecoDeCarga] = 46;
                        break;
                    case "move ax,sp;":
                        mem[indice + enderecoDeCarga] = 47;
                        break;
                    case "move sp,ax;":
                        mem[indice + enderecoDeCarga] = 48;
                        break;
                    case "move ax,bp;":
                        mem[indice + enderecoDeCarga] = 49;
                        break;
                    case "move bp,ax;":
                        mem[indice + enderecoDeCarga] = 50;
                        break;
                    case "iret;":
                        mem[indice + enderecoDeCarga] = 51;
                        break;
                    case "int ":
                        mem[indice + enderecoDeCarga] = 52;
                        mem[++indice + enderecoDeCarga] = valorEstatico;
                        break;
                    case "sub bx,ax;":
                        mem[indice + enderecoDeCarga] = 53;
                        break;
                }
                indice++;
            }
        }
    }
    
    public boolean executaInstrucao(){
        boolean repetir = true;
        
        if (botao == 1) {
            //"push ip" 
            mem[sp] = (short) ip;
            sp--;

            //"push bp" 
            mem[sp] = (short) bp;
            sp--;

            //"push ax" 
            mem[sp] = (short) ax;
            sp--;

            //"push bx" 
            mem[sp] = (short) bx;
            sp--;

            //"push cx" 
            mem[sp] = (short) cx;
            sp--;

            ip = mem[0];
            botao = 0;
        }

        ri = mem[ip];
        switch (ri) {
            case 0:// "init ax"
                ax = 0;
                traceCode.append("init ax");
                break;

            case 1:// "move ax,bx"
                ax = bx;
                traceCode.append("move ax,bx");
                break;
            case 2:// "move ax,cx",
                ax = cx;
                traceCode.append("move ax,cx");
                break;

            case 3:// "move bx,ax"
                bx = ax;
                traceCode.append("move bx,ax");
                break;

            case 4:// "move cx,ax"
                cx = ax;
                traceCode.append("move cx,ax");
                break;

            case 5:// "move ax,[",
                ax = mem[mem[ip + 1]];
                traceCode.append("move ax,[").append(mem[ip + 1]).append("]");
                ip++;
                break;

            case 6:// "move ax,[bx+"
                ax = mem[bx+mem[ip+1]];
                traceCode.append("move ax, [bx+").append(mem[ip+1]).append("]");
                ip++;
                break;

            case 7:// "move ax,[bp-"
                ax = mem[bp - mem[ip+1]];
                traceCode.append("move ax, [bx-").append(mem[ip+1]).append("]");
                ip ++;
                break;

            case 8://"move ax,[bp+"
                ax = mem[bp+mem[ip+1]];
                traceCode.append("move ax, [bp+").append(mem[ip+1]).append("]");
                ip++;
                break;

            case 9://"move ["
                mem[mem[ip + 1]] = (short) ax;
                traceCode.append("move [").append(mem[ip+1]).append("],ax");
                ip++;
                break;

            case 10://"move [bx+"
                mem[bx + mem[ip+1]] = (short) ax;
                traceCode.append("move [bx+").append(mem[ip+1]).append("],ax");
                ip ++;
                break;

            case 11://"move bp,sp"
                bp = sp;
                traceCode.append("move bp,sp");
                break;

            case 12://"move sp,bp"
                sp = bp;traceCode.append("move sp,bp");
                        
                break;

            case 13://"add ax,bx"
                ax = ax + bx;
                traceCode.append("add ax,bx");
                break;

            case 14://"add ax,cx"
                ax = ax + cx;
                traceCode.append("add ax,cx");
                break;

            case 15://"add bx,cx"
                bx = bx + cx;
                traceCode.append("add bx,cx");
                break;

            case 16://"sub ax,bx"
                ax = ax - bx;
                traceCode.append("sub ax,bx");
                break;

            case 17://"sub ax,cx"
                ax = ax - cx;
                traceCode.append("sub ax,cx");
                break;

            case 18://"sub bx,cx"
                bx = bx - cx;
                traceCode.append("sub bx,cx");
                break;

            case 19://"inc ax"
                ax++;
                traceCode.append("inc ax");
                break;

            case 20://"inc bx"
                bx++;
                traceCode.append("inc bx");
                break;

            case 21://"inc cx"
                cx++;
                traceCode.append("inc cx");
                break;

            case 22://"dec ax"
                ax--;
                traceCode.append("dec ax");
                break;

            case 23://"dec bx"
                bx--;
                traceCode.append("dec bx");
                break;

            case 24://"dec cx"
                cx--;
                traceCode.append("dec cx");
                break;

            case 25://"test ax0,"
                if (ax == 0) {
                    ip = mem[ip + 1] - 1; //-1 para compensar o ip++ no laco
                } else {
                    ip++;
                }
                traceCode.append("test ax0,").append(mem[ip]);
                break;

            case 26://"jmp "
                ip = mem[ip + 1];
                traceCode.append("jmp ").append(ip);
                ip--;
                break;

            case 27://"call"
                mem[sp] = (short) (ip + 2);
                sp--;
                ip = mem[ip + 1];
                ip--; //para compensar a alteracao de ip
                traceCode.append("call");
                break;

            case 28://"ret"
                sp++;
                ip = mem[sp];
                ip--;
                traceCode.append("ret");
                break;

            case 29://"in ax"
                ax = Integer.parseInt(JOptionPane.showInputDialog("ax:"));
                break;

            case 30://"out ax"
                datas.put(EnumData.OUT, Integer.toString(ax));
                traceCode.append("out ax");
                break;

            case 31://"push ax"
                mem[sp] = (short) ax;
                sp--;
                traceCode.append("push ax");
                break;

            case 32://"push bx"
                mem[sp] = (short) bx;
                sp--;
                traceCode.append("push bx");
                break;

            case 33://"push cx"
                mem[sp] = (short) cx;
                sp--;
                traceCode.append("push cx");
                break;

            case 34://"push bp"
                mem[sp] = (short) bp;
                sp--;
                traceCode.append("push bp");
                break;

            case 35://"pop bp"
                sp++;
                bp = mem[sp];
                traceCode.append("pop bp");
                break;

            case 36://"pop cx"
                sp++;
                cx = mem[sp];
                traceCode.append("pop cx");
                break;

            case 37://"pop bx"
                sp++;
                bx = mem[sp];
                traceCode.append("pop bx");
                break;

            case 38://"pop ax"
                sp++;
                ax = mem[sp];
                traceCode.append("pop ax");
                break;

            case 39://"nop"
                traceCode.append("nop");
                break;

            case 40: //"halt"
                repetir = false;
                traceCode.append("halt");
                break;

            case 41://"dec sp"
                sp--;
                traceCode.append("dec sp");
                break;

            case 42://"move [bp-"
                mem[bp - mem[ip + 1]] = (short) ax;
                traceCode.append("move [bp-").append(mem[ip+1]).append("], ").append(ax);
                ip++;
                break;

            case 43://"move [bp+"
                mem[bp + mem[ip + 1]] = (short) ax;
                traceCode.append("move [bp+").append(mem[ip+1]).append("], ").append(ax);
                ip++;
                break;

            case 44://"move ax,{"
                ax = mem[ip+1];
                traceCode.append("move ax,{").append(mem[ip+1]).append("}");
                ip++;
                break;

            case 45://"test axEqbx,"
                if (ax == bx) {
                    ip = mem[ip + 1]-1;
                    traceCode.append("test axEqbx -> ip ").append(mem[ip+1]);
                } else {
                    ip++;
                    traceCode.append("test axEqbx -> ip ").append(ip);
                }
                break;

            case 46://"inc sp"
                sp++;
                traceCode.append("inc sp");
                break;

            case 47://"move ax,sp"
                ax = sp;
                traceCode.append("move ax,sp");
                break;

            case 48://"move sp,ax"
                sp = ax;
                traceCode.append("move sp,ax");
                break;

            case 49://"move ax,bp"
                ax = bp;
                traceCode.append("move ax,bp");
                break;

            case 50://"move bp,ax"
                bp = ax;
                traceCode.append("move bp,ax");
                break;

            case 51://"iret"
                //"pop cx"
                sp++;
                cx = mem[sp];
                //"pop bx"
                sp++;
                bx = mem[sp];
                //"pop ax"
                sp++;
                ax = mem[sp];
                //"pop bp"
                sp++;
                bp = mem[sp];
                //"ret"
                sp++;
                ip = mem[sp];
                ip--;
                traceCode.append("iret");
                break;

            case 52://"int"
                traceCode.append("int ").append(mem[ip + 1]);
                //"push ip" 
                mem[sp] = (short) (ip + 2);
                sp--;
                //"push bp" 
                mem[sp] = (short) bp;
                sp--;
                //"push ax" 
                mem[sp] = (short) ax;
                sp--;
                //"push bx" 
                mem[sp] = (short) bx;
                sp--;
                //"push cx" 
                mem[sp] = (short) cx;
                sp--;
                ip = mem[mem[ip + 1]];
                ip--;
                break;

            case 53://"sub bx,ax"
                bx = bx - ax;
                traceCode.append("sub bx,ax");
                break;

            default: {
                repetir = false;
                traceCode.append("Saiu");
            }

            if (ip >= mem.length) {
                traceCode.append("ERRO: a memoria nao pode ser lida");
                repetir = false;
            }
        }
        ip++;
        
        traceCode.append("\n");
        datas.put(EnumData.REGISTRADORES, this.valorRegistradores());
        datas.put(EnumData.TRACE_CODE, traceCode.toString());
        datas.put(EnumData.STACK, this.calcularStack());
        
        return repetir;
    }
    
    private String calcularStack(){
        String stack = "";
        
        for (int i = sp-5; i <= sp; i++) {
            if (i >= 0){
                stack = "[" + (i) + "] - " + mem[i] + "\n" + stack;
            }
        }
        return stack;
    }
    
    private String valorRegistradores(){
        StringBuilder registradores = new StringBuilder();
        registradores.append("AX = ").append(ax).append("\n");
        registradores.append("BX = ").append(bx).append("\n");
        registradores.append("CX = ").append(cx).append("\n");
        registradores.append("BP = ").append(bp).append("\n");
        registradores.append("SP = ").append(sp).append("\n");
        registradores.append("IP = ").append(ip).append("\n");
        return registradores.toString();
    }
    
    public void decodificador() {
        boolean repetir = true;
        
        while (repetir) {
            repetir = this.executaInstrucao();
        }
    }

    public Map<EnumData, String> getDatas() {
        return datas;
    }
}