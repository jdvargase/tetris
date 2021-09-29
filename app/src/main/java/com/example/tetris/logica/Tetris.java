package com.example.tetris.logica;

public class Tetris {

    public int [][]matriz;
    public int [][]matrizsiguiente;
    public long puntaje;
    private int FilaActual;
    private int ColumnaActual;
    private int num=(int)Math.ceil(Math.random()*7);
    private int siguiente;
    private Pieza pActual;
    private Pieza psiguiente;
    public boolean perdio=false;

    public Tetris(){
        this.matriz = new int [20][10];
        this.matrizsiguiente = new int [20][10];
        this.nuevaPieza();
        this.pintarPieza();
    }

    public void nuevaPieza(){
        limpiarsiguiente();
        this.FilaActual = 0;
        this.ColumnaActual = 4;
        if(num == 1){
            this.pActual = new Linea();
        }else if(num == 2){
            this.pActual = new Cuadro();
        }else if(num == 3){
            this.pActual = new Ele1();
        }else if(num == 4){
            this.pActual = new Ele2();
        }else if(num == 5){
            this.pActual = new Ese();
        }else if(num == 6){
            this.pActual = new Te();
        }else if(num == 7){
            this.pActual = new Zeta();
        }
        this.perdio = perder();
        this.siguientepieza();
        this.pintarPiezasiguiente();
    }
    public void siguientepieza(){
        siguiente = (int)Math.ceil(Math.random()*7);
        num = siguiente;
        if(siguiente == 1){
            this.psiguiente = new Linea();
        }else if(siguiente == 2){
            this.psiguiente = new Cuadro();
        }else if(siguiente == 3){
            this.psiguiente = new Ele1();
        }else if(siguiente == 4){
            this.psiguiente = new Ele2();
        }else if(siguiente == 5){
            this.psiguiente = new Ese();
        }else if(siguiente == 6){
            this.psiguiente = new Te();
        }else if(siguiente == 7){
            this.psiguiente = new Zeta();
        }
    }
    public void limpiarsiguiente(){
        for(int i=0;i<20;i++){
            for(int j=0;j<10;j++){
                matrizsiguiente[i][j]=0;
            }
        }
    }
    public void pintarPiezasiguiente(){
        for(int i = 0; i<4;i++ ) {
            this.matrizsiguiente[this.FilaActual + this.psiguiente.getCoordenadas()[i][0]][this.ColumnaActual + this.psiguiente.getCoordenadas()[i][1]] = this.psiguiente.getValor();
        }
    }

    private boolean perder(){
        for(int i=0; i<4;i++){
            if(this.matriz[this.FilaActual + this.pActual.getCoordenadas()[i][0]][this.ColumnaActual+this.pActual.getCoordenadas()[i][1]] == -1){
                return true;
            }else
                return false;
        }
        return false;
    }

    public void rotar(){
        if(pActual.valor != 2){
            if(pActual.valor == 1){
                if(ColumnaActual > 6){
                    limpiarPiezaActual();
                    ColumnaActual = 6;
                }
                if(FilaActual > 16){
                    limpiarPiezaActual();
                    FilaActual = 16;
                }
            }else{
                if(ColumnaActual == 8){
                    limpiarPiezaActual();
                    ColumnaActual = ColumnaActual - 1;
                }
                if(FilaActual == 18){
                    limpiarPiezaActual();
                    FilaActual = FilaActual - 1;
                }
            }
        }
        this.pActual.rotar();
        if(validarRotar()){
            if(pActual.forma == 1){
                pActual.forma = 3;
            }else if(pActual.forma == 2){
                pActual.forma = 4;
            }else{
                pActual.forma = pActual.forma - 2;
            }
            pActual.rotar();
        }
    }

    public boolean validarRotar(){
        for(int i=0;i<4;i++){
            if(matriz[FilaActual + pActual.getCoordenadas()[i][0]][ColumnaActual + pActual.getCoordenadas()[i][1]] == -1){
                return true;
            }
        }
        return false;
    }

    public void izquierda(){
        if(validarIzquierda()){
                this.limpiarPiezaActual();
                this.ColumnaActual--;
                this.pintarPieza();
        }
    }

    public void derecha(){
        if(validarDerecha()) {
            this.limpiarPiezaActual();
            this.ColumnaActual++;
            this.pintarPieza();
        }
    }

    public void abajo(){
        if(validarAbajo()) {
            this.limpiarPiezaActual();
            this.FilaActual++;
            this.pintarPieza();
        }else{
            fijarPieza();
            validarTetris();
            this.nuevaPieza();
        }
    }

    private void validarTetris(){
        int cantidad = 0;
        for(int i=this.FilaActual; i<this.FilaActual+this.pActual.getNumFila();i++){
            if(this.verificarFilaFijada(i)){
                cantidad++;
                this.bajaJuego(i);
            }
        }
        this.aumentarpuntaje(cantidad);
    }

    private void bajaJuego(int fila) {
        for(int i=fila-1;i>=0;i--){
            for(int j=0;j<10;j++){
                this.matriz[i+1][j]=this.matriz[i][j];
            }
        }
    }

    private boolean verificarFilaFijada(int fila) {
        for(int j=0; j<10;j++){
            if(this.matriz[fila][j] != -1){
                return false;
            }
        }
        return true;
    }

    private void fijarPieza() {
        for(int i=0;i<4;i++){
            this.matriz[this.FilaActual + this.pActual.getCoordenadas()[i][0]][this.ColumnaActual + this.pActual.getCoordenadas()[i][1]] = -1;
        }
    }

    public boolean validarAbajo(){
        if((this.FilaActual+this.pActual.getNumFila()) == 20){
            return false;
        }else {
            for (int i = 0; i < 4; i++) {
                if (this.matriz[this.FilaActual + this.pActual.getCoordenadas()[i][0] + 1][this.ColumnaActual + this.pActual.getCoordenadas()[i][1]] == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validarIzquierda() {
        if(this.ColumnaActual == 0){
            return false;
        }else {
            for (int i = 0; i < 4; i++) {
                if (this.matriz[this.FilaActual + this.pActual.getCoordenadas()[i][0]][this.ColumnaActual + this.pActual.getCoordenadas()[i][1] - 1] == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validarDerecha(){
        if((this.ColumnaActual+this.pActual.getNumColumna())==10){
            return false;
        }else{
            for (int i = 0; i < 4; i++) {
                if (this.matriz[this.FilaActual + this.pActual.getCoordenadas()[i][0]][this.ColumnaActual + this.pActual.getCoordenadas()[i][1] + 1] == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void pintarPieza(){
        for(int i = 0; i<4;i++ ) {
            this.matriz[this.FilaActual + this.pActual.getCoordenadas()[i][0]][this.ColumnaActual + this.pActual.getCoordenadas()[i][1]] = this.pActual.getValor();
        }
    }

    public void limpiarPiezaActual(){
        for(int i = 0; i<4;i++ ) {
            this.matriz[this.FilaActual + this.pActual.getCoordenadas()[i][0]][this.ColumnaActual + this.pActual.getCoordenadas()[i][1]] = 0;
        }
    }
    public void aumentarpuntaje (int cantidad){
        if(cantidad == 1){
            this.puntaje = this.puntaje + 1;
        }else if(cantidad == 2){
            this.puntaje = this.puntaje + 3;
        }else if(cantidad == 3){
            this.puntaje = this.puntaje + 6;
        }else if(cantidad == 4){
            this.puntaje = this.puntaje + 10;
        }else if(cantidad >= 5){
            this.puntaje = this.puntaje + ((cantidad+1)*2);
        }
    }
}
