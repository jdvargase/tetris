package com.example.tetris.logica;

public class Cuadro extends Pieza {
    public Cuadro(){
        super();
        this.coordenadas[0][0] = 0;
        this.coordenadas[0][1] = 0;
        this.coordenadas[1][0] = 0;
        this.coordenadas[1][1] = 1;
        this.coordenadas[2][0] = 1;
        this.coordenadas[2][1] = 0;
        this.coordenadas[3][0] = 1;
        this.coordenadas[3][1] = 1;
        this.valor = 2;
        this.numFila = 2;
        this.numColumna = 2;
    }
    @Override
    public void rotar() {}
}