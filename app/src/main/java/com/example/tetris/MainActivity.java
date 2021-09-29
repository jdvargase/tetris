package com.example.tetris;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tetris.logica.Tetris;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GridLayout tablero,siguiente_pieza;
    private TextView puntaje;
    private Button reiniciar;
    private Button pausar;
    private ImageView [][] matriz = new ImageView[20][10];
    private ImageView [][] matriz_pieza = new ImageView [6][6];
    private int x1,x2;
    private int y1,y2;
    private int aux;
    private Tetris tetris = new Tetris();
    private Timer timer;
    private TimerTask timerTask;
    final Handler handler = new Handler();
    Handler reinicio_automatico = new Handler();
    Handler pausa = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mAuth = FirebaseAuth.getInstance();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int ancho = size.x;
        int alto = size.y;
        tablero = (GridLayout)findViewById(R.id.Tablero);
        tablero.setColumnCount(10);
        tablero.setRowCount(20);
        siguiente_pieza = (GridLayout)findViewById(R.id.Siguiente_Pieza);
        siguiente_pieza.setColumnCount(6);
        siguiente_pieza.setRowCount(6);
        puntaje = (TextView)findViewById(R.id.puntaje);
        reiniciar = (Button)findViewById(R.id.Reiniciar);
        pausar = (Button)findViewById(R.id.btn_pausa);

        for (int i=0;i<20;i++){
            for (int j=0;j<10;j++){
                ImageView imagen = new ImageView(this);
                imagen.setLayoutParams(new LinearLayout.LayoutParams(ancho/17, ancho/17));
                imagen.setBackgroundColor(Color.BLACK);
                matriz[i][j]=imagen;
                tablero.addView(imagen);
            }
        }
        for (int i=0;i<6;i++){
            for (int j=0;j<6;j++){
                ImageView siguiente = new ImageView(this);
                siguiente.setLayoutParams(new LinearLayout.LayoutParams(ancho/24, ancho/24));
                siguiente.setBackgroundColor(Color.BLACK);
                matriz_pieza[i][j]=siguiente;
                siguiente_pieza.addView(siguiente);
            }
        }
        View pantalla = getWindow().getDecorView();
        pantalla.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    x1 = (int)motionEvent.getX();
                    y1 = (int)motionEvent.getY();
                } else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    x2 = (int)motionEvent.getX();
                    y2 = (int)motionEvent.getY();
                    if(aux == 0){
                        movimiento(x1,x2,y1,y2);
                    }
                }
                return true;
            }
        });
        pantalla_completa(pantalla);
        reactivar_pantalla_completa(pantalla);
        pintar();
        comenzarTimer();
        pintar_siguiente();

        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reinicio_automatico.postDelayed(new Runnable() {
                    @Override
                    public void run(){
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                },500);
            }
        });

        pausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aux == 0){
                    detenertimertask();
                    pausar.setText("REANUDAR");
                    pausa();
                    aux = 1;
                }else if(aux == 1){
                    comenzarTimer();
                    pausar.setText("PAUSAR");
                    pintar();
                    aux = 0;
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void reactivar_pantalla_completa(final View vw) {
        vw.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                pantalla_completa(vw);
            }
        });
    }
    private void pantalla_completa(View vw){
        vw.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void movimiento(int x1, int x2, int y1, int y2){
        int deltaX=x1-x2;
        int deltaY=y1-y2;
        if(Math.abs(deltaY)>=Math.abs(deltaX)){
            if(deltaY>0){
                tetris.limpiarPiezaActual();
                tetris.rotar();
                tetris.pintarPieza();
                pintar();
            }else if(deltaY<0){
                while(tetris.validarAbajo()){
                    tetris.abajo();
                    pintar();
                }
            }
        }else{
            if(deltaX>0){
                tetris.izquierda();
                pintar();
            }else if(deltaX<0){
                tetris.derecha();
                pintar();
            }
        }
    }
    private void pintar(){
        for(int i=0;i<20;i++){
            for(int j=0;j<10;j++){
                if(tetris.matriz[i][j] == -1){
                    this.matriz[i][j].setBackgroundColor(Color.GRAY);
                }else if(tetris.matriz[i][j] == 1){
                    this.matriz[i][j].setBackgroundColor(Color.BLUE);
                }else if(tetris.matriz[i][j] == 2){
                    this.matriz[i][j].setBackgroundColor(Color.RED);
                }else if(tetris.matriz[i][j] == 3){
                    this.matriz[i][j].setBackgroundColor(Color.GREEN);
                }else if(tetris.matriz[i][j] == 4){
                    this.matriz[i][j].setBackgroundColor(Color.YELLOW);
                }else if(tetris.matriz[i][j] == 5){
                    this.matriz[i][j].setBackgroundColor(Color.MAGENTA);
                }else if(tetris.matriz[i][j] == 6){
                    this.matriz[i][j].setBackgroundColor(Color.CYAN);
                }else if(tetris.matriz[i][j] == 7){
                    this.matriz[i][j].setBackgroundColor(Color.rgb(148,9,229));
                }else if(tetris.matriz[i][j] == 0){
                    this.matriz[i][j].setBackgroundColor(Color.BLACK);
                }
            }
        }
    }

    private void pintar_siguiente(){
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                if(tetris.matrizsiguiente[i][j+2] == 1){
                    this.matriz_pieza[i+1][j].setBackgroundColor(Color.BLUE);
                }else if(tetris.matrizsiguiente[i][j+2] == 2){
                    this.matriz_pieza[i+1][j].setBackgroundColor(Color.RED);
                }else if(tetris.matrizsiguiente[i][j+2] == 3){
                    this.matriz_pieza[i+1][j].setBackgroundColor(Color.GREEN);
                }else if(tetris.matrizsiguiente[i][j+2] == 4){
                    this.matriz_pieza[i+1][j].setBackgroundColor(Color.YELLOW);
                }else if(tetris.matrizsiguiente[i][j+2] == 5){
                    this.matriz_pieza[i+1][j].setBackgroundColor(Color.MAGENTA);
                }else if(tetris.matrizsiguiente[i][j+2] == 6){
                    this.matriz_pieza[i+1][j].setBackgroundColor(Color.CYAN);
                }else if(tetris.matrizsiguiente[i][j+2] == 7){
                    this.matriz_pieza[i+1][j].setBackgroundColor(Color.rgb(148,9,229));
                }else if(tetris.matrizsiguiente[i][j+2] == 0){
                    this.matriz_pieza[i+1][j].setBackgroundColor(Color.BLACK);
                }
            }
        }
    }
    private void pausa(){
        for(int i=0;i<20;i++){
            for(int j=0;j<10;j++){
                this.matriz[i][j].setBackgroundColor(Color.BLACK);
            }
        }
    }
    public void comenzarTimer(){
            timer = new Timer();
            inicializarTimerTask();
            timer.schedule(timerTask, 1500, 1000);
    }
    public void inicializarTimerTask(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run(){
                        if(tetris.perdio){
                            detenertimertask();
                        }else{
                            tetris.abajo();
                            pintar();
                            pintar_siguiente();
                            puntaje.setText("PUNTAJE: " + tetris.puntaje);
                        }
                    }
                });
            }
        };
    }
    public void detenertimertask(){
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
