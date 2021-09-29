package com.example.tetris.logica;

public abstract class Pieza {
    protected int [][] coordenadas = new int[4][2];
    protected int forma;
    protected int valor;
    protected int numFila;
    protected int numColumna;

    public int[][] getCoordenadas() {
        return coordenadas;
    }

    public int getValor() {
        return valor;
    }

    public int getNumFila() { return numFila; }

    public int getNumColumna() { return numColumna; }

    public Pieza(){
        this.forma = 1;
    }
    public abstract void rotar();
}