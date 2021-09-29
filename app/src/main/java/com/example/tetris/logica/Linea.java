package com.example.tetris.logica;

public class Linea extends Pieza {
    public Linea(){
        super();
        this.coordenadas[0][0] = 0;
        this.coordenadas[0][1] = 0;
        this.coordenadas[1][0] = 1;
        this.coordenadas[1][1] = 0;
        this.coordenadas[2][0] = 2;
        this.coordenadas[2][1] = 0;
        this.coordenadas[3][0] = 3;
        this.coordenadas[3][1] = 0;
        this.valor = 1;
        this.numFila = 4;
        this.numColumna = 1;
    }

    @Override
    public void rotar(){
        if(this.forma==1){
            this.coordenadas[0][0] = 0;
            this.coordenadas[0][1] = 0;
            this.coordenadas[1][0] = 0;
            this.coordenadas[1][1] = 1;
            this.coordenadas[2][0] = 0;
            this.coordenadas[2][1] = 2;
            this.coordenadas[3][0] = 0;
            this.coordenadas[3][1] = 3;
            this.forma=2;
            this.numFila = 1;
            this.numColumna = 4;
        }else if(this.forma==2){
            this.coordenadas[0][0] = 0;
            this.coordenadas[0][1] = 0;
            this.coordenadas[1][0] = 1;
            this.coordenadas[1][1] = 0;
            this.coordenadas[2][0] = 2;
            this.coordenadas[2][1] = 0;
            this.coordenadas[3][0] = 3;
            this.coordenadas[3][1] = 0;
            this.forma=1;
            this.numFila=4;
            this.numColumna = 1;
        }
    }
}
