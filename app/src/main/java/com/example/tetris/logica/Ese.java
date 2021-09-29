package com.example.tetris.logica;

public class Ese extends Pieza {
    public Ese(){
        super();
        this.coordenadas[0][0] = 0;
        this.coordenadas[0][1] = 1;
        this.coordenadas[1][0] = 0;
        this.coordenadas[1][1] = 2;
        this.coordenadas[2][0] = 1;
        this.coordenadas[2][1] = 0;
        this.coordenadas[3][0] = 1;
        this.coordenadas[3][1] = 1;
        this.valor = 5;
        this.numFila = 2;
        this.numColumna = 3;
    }
     @Override
    public void rotar(){
        if(this.forma==1){
            this.coordenadas[0][0] = 0;
            this.coordenadas[0][1] = 0;
            this.coordenadas[1][0] = 1;
            this.coordenadas[1][1] = 0;
            this.coordenadas[2][0] = 1;
            this.coordenadas[2][1] = 1;
            this.coordenadas[3][0] = 2;
            this.coordenadas[3][1] = 1;
            this.forma=2;
            this.numFila = 3;
            this.numColumna = 2;
        }else if(this.forma ==2){
            this.coordenadas[0][0] = 0;
            this.coordenadas[0][1] = 1;
            this.coordenadas[1][0] = 0;
            this.coordenadas[1][1] = 2;
            this.coordenadas[2][0] = 1;
            this.coordenadas[2][1] = 0;
            this.coordenadas[3][0] = 1;
            this.coordenadas[3][1] = 1;
            this.forma=1;
            this.numFila = 2;
            this.numColumna = 3;
        }
     }
}
