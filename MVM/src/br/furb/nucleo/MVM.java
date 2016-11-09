package br.furb.nucleo;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    
    public static void decodificador(int programa, int aux) {
        int ax = 0, bx = 0, cx = 0, bp = 0, sp = 0, ip, ri;
        boolean repetir = true;
        ip = 0 + aux;


        while (repetir) {
            System.out.println("Valor de IP: " + ip);
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
                System.out.println("EXECUTOU INTERRUPCAO: INT3");
            }

            ri = mem[ip];
            switch (ri) {
                case 0:// "init ax"
                    ax = 0;
                    break;

                case 1:// "move ax,bx"
                    ax = bx;
                    break;
                case 2:// "move ax,cx",
                    ax = cx;
                    break;

                case 3:// "move bx,ax"
                    bx = ax;
                    break;

                case 4:// "move cx,ax"

                    cx = ax;

                    break;

                case 5:// "move ax,[",
                    ax = mem[mem[ip + 1]];
                    System.out.println("Executou move ax,[" + mem[ip + 1] + "]");
                    ip++;
                    break;

                case 6:// "move ax,[bx+"
                    ax = mem[bx+mem[ip+1]];
                    System.out.println("Executou move ax, [bx+" + mem[ip+1]+ "]");
                    ip++;
                    break;

                case 7:// "move ax,[bp-"
                    ax = mem[bp - mem[ip+1]];
                    System.out.println("Executou move ax, [bx-" + mem[ip+1]+ "]");
                    ip ++;
                    break;

                case 8://"move ax,[bp+"
                    ax = mem[bp+mem[ip+1]];
                    System.out.println("Executou move ax, [bp+" + mem[ip+1]+ "]");
                    ip++;
                    break;

                case 9://"move ["
                    mem[mem[ip + 1]] = (short) ax;
                    System.out.println("Executou move [" + mem[ip+1]+ "],ax");
                    ip++;


                    break;

                case 10://"move [bx+"
                    mem[bx + mem[ip+1]] = (short) ax;
                    System.out.println("Executou move [bx+" + mem[ip+1]+ "],ax");
                    ip ++;


                    break;

                case 11://"move bp,sp"

                    bp = sp;

                    break;

                case 12://"move sp,bp"

                    sp = bp;

                    break;

                case 13://"add ax,bx"

                    ax = ax + bx;

                    break;

                case 14://"add ax,cx"

                    ax = ax + cx;

                    break;

                case 15://"add bx,cx"

                    bx = bx + cx;

                    break;

                case 16://"sub ax,bx"

                    ax = ax - bx;

                    break;

                case 17://"sub ax,cx"

                    ax = ax - cx;

                    break;

                case 18://"sub bx,cx"

                    bx = bx - cx;

                    break;

                case 19://"inc ax"

                    ax++;

                    break;

                case 20://"inc bx"

                    bx++;

                    break;

                case 21://"inc cx"

                    cx++;

                    break;

                case 22://"dec ax"

                    ax--;

                    break;

                case 23://"dec bx"

                    bx--;

                    break;

                case 24://"dec cx"

                    cx--;

                    break;

                case 25://"test ax0,"

                    if (ax == 0) {

                        ip = aux + mem[ip + 1] - 1; //-1 para compensar o ip++ no laco

                    } else {

                        ip++;

                    }

                    break;

                case 26://"jmp "

                    ip = aux + mem[ip + 1];

                    ip--;

                    break;

                case 27://"call"

                    mem[sp] = (short) (ip + 2);

                    sp--;

                    ip = aux + mem[ip + 1];

                    ip--; //para compensar a alteracao de ip

                    break;

                case 28://"ret"

                    sp++;

                    ip = mem[sp];

                    ip--;

                    break;

                case 29://"in ax"

                    ax = Integer.parseInt(JOptionPane.showInputDialog("ax:"));



                    break;

                case 30://"out ax"

                    System.out.println("Saida: AX=" + ax);

                    break;

                case 31://"push ax"

                    mem[sp] = (short) ax;

                    sp--;

                    break;

                case 32://"push bx"

                    mem[sp] = (short) bx;

                    sp--;

                    break;

                case 33://"push cx"

                    mem[sp] = (short) cx;

                    sp--;

                    break;

                case 34://"push bp"

                    mem[sp] = (short) bp;

                    sp--;

                    break;

                case 35://"pop bp"

                    sp++;

                    bp = mem[sp];

                    break;

                case 36://"pop cx"

                    sp++;

                    cx = mem[sp];

                    break;

                case 37://"pop bx"

                    sp++;

                    bx = mem[sp];

                    break;

                case 38://           "pop ax"

                    sp++;

                    ax = mem[sp];

                    break;

                case 39://"nop"

                    break;

                case 40: //"halt"

                    repetir = false;

                    break;

                case 41://"dec sp"

                    sp--;

                    break;

                case 42://"move [bp-"

                    mem[aux + bp - mem[ip + 1]] = (short) ax;

                    ip++;

                    break;

                case 43://"move [bp+"



                    break;

                case 44://"move ax,{"
                    ax = mem[ip+1];
                    ip++;
                    break;

                case 45://"test axEqbx,"

                    if (ax == bx) {
                        ip = mem[ip + 1]-1;
                        System.out.println("Executou THEN test axEqbx -> ip"+ mem[ip+1]);
                    } else {

                        ip++;
                        System.out.println("Executou ELSE test axEqbx -> ip" + ip);

                    }

                    break;

                case 46://"inc sp"

                    sp++;

                    break;

                case 47://"move ax,sp"

                    ax = sp;

                    break;

                case 48://"move sp,ax"

                    sp = ax;

                    break;

                case 49://"move ax,bp"

                    ax = bp;

                    break;

                case 50://"move bp,ax,{"

                    bp = ax;

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

                    break;



                case 52://"int"

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

                    ip = mem[aux + mem[ip + 1]];
                    ip--;

                    break;
                case 53://"sub bx,ax"

                    bx = bx - ax;

                    break;


                default: {

                    repetir = false;

                    System.out.println("Saiu");

                }

                if (ip >= mem.length) {

                    System.out.println("ERRO: a memoria nao pode ser lida");

                    repetir = false;

                }

            }

            ip++;
            //System.out.println("IP - " + ip);
        }
        /**
         System.out.println("Valor de AX: " + ax);
         System.out.println("Valor de BX: " + bx);
         System.out.println("Valor de CX: " + cx);
         System.out.println("Valor de SP: " + sp);
         System.out.println("Valor de mem[3]: " + mem[3]);
         System.out.println("Valor de mem[4]: " + mem[4]);
         System.out.println("Valor de mem[5]: " + mem[5]);
         System.out.println("Valor de mem[14]: " + mem[14]);
         System.out.println("Valor de mem[13]: " + mem[13]);
         System.out.println("Valor de mem[12]: " + mem[12]);
         System.out.println("Valor de mem[11]: " + mem[11]);
         **/
    }
    
    public static void geraCodigoFonte(short mem[], int programa, int fimPrograma, int enderecoDeCarga) throws IOException {        
        Path localArquivo = Paths.get("C:\\Temp\\programa.txt");
        Files.deleteIfExists(localArquivo);
        BufferedWriter arquivo = new BufferedWriter(new FileWriter(localArquivo.toFile()));        
        String newLine = System.getProperty("line.separator");
        arquivo.write("Programa: " + programa + newLine);
        StringBuilder conteudoArquivo = new StringBuilder();
        fimPrograma += enderecoDeCarga;
        for (int ip = enderecoDeCarga; ip < fimPrograma; ip++) {
            switch (mem[ip]) {
                case 00:
                    conteudoArquivo.append("init ax;").append(newLine);
                    break;
                case 1:
                    conteudoArquivo.append("move ax,bx;").append(newLine);
                    break;
                case 2:
                    conteudoArquivo.append("move ax,cx;").append(newLine);
                    break;
                case 3:
                    conteudoArquivo.append("move bx,ax;").append(newLine);
                    break;
                case 4:
                    conteudoArquivo.append("move cx,ax;").append(newLine);
                    break;
                case 5:
                    conteudoArquivo.append("move ax,[").append(mem[++ip]).append("];").append(newLine);
                    break;
                case 6:
                    conteudoArquivo.append("move ax,[bx+").append(mem[++ip]).append("];").append(newLine);
                    break;
                case 7:
                    conteudoArquivo.append("move ax,[bp-").append(mem[++ip]).append("];").append(newLine);
                    break;
                case 8:
                    conteudoArquivo.append("move ax,[bp+").append(mem[++ip]).append("];").append(newLine);
                    break;
                case 9:
                    conteudoArquivo.append("move [").append(mem[++ip]).append("],ax;").append(newLine);
                    break;
                case 10:
                    conteudoArquivo.append("move [bx+").append(mem[++ip]).append("],ax;").append(newLine);
                    break;
                case 11:
                    conteudoArquivo.append("move bp,sp;").append(newLine);
                    break;
                case 12:
                    conteudoArquivo.append("move sp,bp;").append(newLine);
                    break;
                case 13:
                    conteudoArquivo.append("add ax,bx;").append(newLine);
                    break;
                case 14:
                    conteudoArquivo.append("add ax,cx;").append(newLine);
                    break;
                case 15:
                    conteudoArquivo.append("add bx,cx;").append(newLine);
                    break;
                case 16:
                    conteudoArquivo.append("sub ax,bx;").append(newLine);
                    break;
                case 17:
                    conteudoArquivo.append("sub ax,cx;").append(newLine);
                    break;
                case 18:
                    conteudoArquivo.append("sub bx,cx;").append(newLine);
                    break;
                case 19:
                    conteudoArquivo.append("inc ax;").append(newLine);
                    break;
                case 20:
                    conteudoArquivo.append("inc bx;").append(newLine);
                    break;
                case 21:
                    conteudoArquivo.append("inc cx;").append(newLine);
                    break;
                case 22:
                    conteudoArquivo.append("dec ax;").append(newLine);
                    break;
                case 23:
                    conteudoArquivo.append("dec bx;").append(newLine);
                    break;
                case 24:
                    conteudoArquivo.append("dec cx;").append(newLine);
                    break;
                case 25:
                    conteudoArquivo.append("test ax0,").append(mem[++ip]).append(";").append(newLine);
                    break;
                case 26:
                    conteudoArquivo.append("jmp ").append(mem[++ip]).append(";").append(newLine);
                    break;
                case 27:
                    conteudoArquivo.append("call ").append(mem[++ip]).append(";").append(newLine);
                    break;
                case 28:
                    conteudoArquivo.append("ret;").append(newLine);
                    break;
                case 29:
                    conteudoArquivo.append("in ax;").append(newLine);
                    break;
                case 30:
                    conteudoArquivo.append("out ax;").append(newLine);
                    break;
                case 31:
                    conteudoArquivo.append("push ax;").append(newLine);
                    break;
                case 32:
                    conteudoArquivo.append("push bx;").append(newLine);
                    break;
                case 33:
                    conteudoArquivo.append("push cx;").append(newLine);
                    break;
                case 34:
                    conteudoArquivo.append("push bp;").append(newLine);
                    break;
                case 35:
                    conteudoArquivo.append("pop bp;").append(newLine);
                    break;
                case 36:
                    conteudoArquivo.append("pop cx;").append(newLine);
                    break;
                case 37:
                    conteudoArquivo.append("pop bx;").append(newLine);
                    break;
                case 38:
                    conteudoArquivo.append("pop ax;").append(newLine);
                    break;
                case 39:
                    conteudoArquivo.append("nop;").append(newLine);
                    break;
                case 40:
                    conteudoArquivo.append("halt;").append(newLine);
                    break;
                case 41:
                    conteudoArquivo.append("dec sp;").append(newLine);
                    break;
                case 42:
                    conteudoArquivo.append("move [bp-").append(mem[++ip]).append("], ax;").append(newLine);
                    break;
                case 43:
                    conteudoArquivo.append("move [bp+").append(mem[++ip]).append("], ax;").append(newLine);
                    break;
                case 44:
                    conteudoArquivo.append("move ax,{").append(mem[++ip]).append("};").append(newLine);
                    break;
                case 45:
                    conteudoArquivo.append("test axEqbx,").append(mem[++ip]).append(";").append(newLine);
                    break;
                case 46:
                    conteudoArquivo.append("inc sp;").append(newLine);
                    break;
                case 47:
                    conteudoArquivo.append("move ax,sp;").append(newLine);
                    break;
                case 48:
                    conteudoArquivo.append("move sp,ax;").append(newLine);
                    break;
                case 49:
                    conteudoArquivo.append("move ax,bp;").append(newLine);
                    break;
                case 50:
                    conteudoArquivo.append("move bp,ax;").append(newLine);
                    break;
                case 51:
                    conteudoArquivo.append("iret;").append(newLine);
                    break;
                case 52:
                    conteudoArquivo.append("int ").append(mem[++ip]).append(";").append(newLine);
                    break;
                case 53:
                    conteudoArquivo.append("sub bx,ax;").append(newLine);
                    break;
            }
        }
        arquivo.write(conteudoArquivo.toString());
        arquivo.flush();
        arquivo.close();
    }
    
    public static void traduzirCodigoFonte(String codigoFonte, int enderecoDeCarga) throws IOException {        
        int indice = 0;
        
//        Path localArquivo = Paths.get(caminho);
//        BufferedReader arquivo = new BufferedReader(new FileReader(localArquivo.toFile()));
        String [] codigo = codigoFonte.split("\n");
        String linha = codigo[0];
        String instFinal = "";
        short valorEstatico = Short.MIN_VALUE;
        while (linha != null) {              
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
            codigo = codigo[1].split("\n");
            linha = codigo[0];
        }
        decodificador(-1, enderecoDeCarga);
    }
}