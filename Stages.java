/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.systeminfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Felipe, Geovanna e Mateus
 */

public class Stages {
    //Variaveis globais
    private static List<String[]> col = new ArrayList<>();
    private static int countsWord[] = new int[26];
    private static long lineNumber = 0;
    private static String[] arqName = {"idatracacao.txt","cdtup.txt","berco.txt","portoatracacao.txt","ano.txt","mes.txt","tipooperacao.txt","tiponavegacaoatracacao.txt",
                                       "terminal.txt","nacionalidadearmador.txt","tesperaatracacao.txt","tesperacainicioop.txt","toperacao.txt","tesperadesatracacao.txt","tatracado.txt",
                                       "testadia.txt","idcarga.txt", "origem.txt","destino.txt","cdmercadoria.txt","naturezacarga.txt","qtcarga.txt","pesocargabruta.txt","sentido.txt",
                                       "cdmercadoria_cntr.txt","pesocarga_cntr.txt"};

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        // TODO code application logic here
        readCSV();
    }
    
    public static void readCSV() throws FileNotFoundException, InterruptedException, IOException{
        long startStage1 = System.currentTimeMillis(); // Tempo inicial da etapa 1
        //Entrada do arquivo
        BufferedReader br = new BufferedReader(new FileReader("C://Users//Felipe//Documents//Ciências da Computação//5º Semestre//Sistemas Operacionais//Trabalho Interdisciplinar//dataset_00_sem_virg.csv"));
        // batchSize - tamanho de linhas por batch (partes)
        int batchSize = 14665111/14;
        String line = "";
        String cvsSplitBy = ",";
        // Tempos para a etapa 2
        long startStage2 = 0;
        long elapsedStage2 = 0;
        // Tempos para a etapa 3
        long startStage3 = 0;
        long elapsedStage3 = 0;
        
        try {
            while ((line = br.readLine()) != null) { // Laço para percorrer todo o arquivo CSV
                
                String[] colsCSV = line.split(cvsSplitBy); // Split nas linhas para separar em colunas
                col.add(colsCSV); // Adicionando em uma lista de string (global)
                lineNumber++;
                
                // Processamento das linhas delimitadas pelo batchSize
                if (lineNumber == batchSize){
                    startStage2 = System.currentTimeMillis(); 
                    // Utilizando threads para o processamento
                    ThreadColumns threadColumns = new ThreadColumns();
                    threadColumns.start();
                    threadColumns.join();
                    elapsedStage2 += System.currentTimeMillis() - startStage2; // Tempo final etapa 2;
                    //Começo do tempo da etapa 3
                    startStage3 = System.currentTimeMillis();
                    writeInArchives(); //Metodo para escrever nos arquivos
                    elapsedStage3 += System.currentTimeMillis() - startStage3;  // Tempo final etapa 3;                
                    lineNumber = 0; // Zerando o numero de linhas para processar as proximas
                    col.clear(); // Limpando a lista
                }
            }
            br.close(); //Fechando o arquivo
            br = null; 
        } catch (IOException e) {
            System.out.println("\nException caught: " + e.getMessage());   // Qualquer exceção
        }
        writeInArchivesCountWords();
        long elapsedStage1 = System.currentTimeMillis() - startStage1; // Tempo final etapa 1;
        
        System.out.println("\n------------------- BenchMark -------------------\n");
        
        System.out.println("Tempo de execução - Etapa 1: "+ ((elapsedStage1-elapsedStage2-elapsedStage3))/1000d +" s");
        System.out.println("Tempo de execução - Etapa 2: "+ elapsedStage2/1000d +" s");
        System.out.println("Tempo de execução - Etapa 3: "+ elapsedStage3/1000d +" s");
        
    }

    // ThreadColumns - realiza a contagem das palavras de uma coluna
    public static class ThreadColumns extends Thread {
        @Override
        public void run(){
            for(int i = 0; i < lineNumber; i++){
                for(int j = 0; j < 26; j++){    
                boolean integerOrNot = col.get(i)[j].matches("^\\d+$"); // verificando se apresenta números
                    if (integerOrNot == false){
                        countsWord[j] += 1; // soma das palavras
                    }
                }
            }
        }
    };
    
    public static void writeInArchives() throws IOException {
        //Laço para percorrer os 26 arquivos armazenados em arqName
        //Criando cada um e escrevendo o conteudo
        for (int j = 0; j < 26; j++){
            FileWriter fileWrite = new FileWriter(arqName[j], true);
            BufferedWriter br = new BufferedWriter(fileWrite);
            for(int i = 0; i < lineNumber; i++){   
                br.write(col.get(i)[j]);
                br.newLine();
            }
            br.close();
        }
    }
    
    public static void writeInArchivesCountWords() throws IOException {
        //Laço para percorrer os 26 arquivos armazenados em arqName
        //Escrevendo na ultima linha de cada a quantidade de palavras armazenadas em countsWord
        for (int j = 0; j < 26; j++){
            FileWriter fileWrite = new FileWriter(arqName[j], true);
            BufferedWriter br = new BufferedWriter(fileWrite);
            br.write(Integer.toString(countsWord[j]));
            br.newLine();
            br.close();
        }
    }
}
