/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapa;

import MegaHombre.MegaHombre;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *
 * @author alexP
 */
public class HUD {
    
    public Stage stage;
    private Viewport viewport;
    public static int puntos = 0;
    private static Label  puntuacion;
    private Label tiempoJugado;
    private Label contadorTiempo;
    private int tiempo;
    private float contador;

    public HUD(SpriteBatch batch) {
    
        tiempo = 0;
        contador = 0;
        contadorTiempo = new Label(String.format("%03d s", tiempo), new Label.LabelStyle(new BitmapFont(), Color.RED));
        tiempoJugado = new Label("Tiempo jugado", new Label.LabelStyle(new BitmapFont(), Color.RED));
        puntuacion = new Label(String.format("%03d", puntos), new Label.LabelStyle(new BitmapFont(), Color.RED));
        Label labelpuntuacion = new Label("Puntuacion", new Label.LabelStyle(new BitmapFont(), Color.RED));
        viewport = new FitViewport(MegaHombre.VENTANA_WIDHT, MegaHombre.VENTANA_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        
        Table table = new Table();
        table.top();
        table.setFillParent(true);
 
        table.add(tiempoJugado).expandX().padTop(5);
        table.add(labelpuntuacion).expandX();
        table.row();
        table.add(contadorTiempo).expandX();
        table.add(puntuacion).expandX().padTop(5);
        
        stage.addActor(table);
    }
    
    public void update(float delta){
        
        contador = contador + delta;
        if(contador >= 1){
            tiempo++;
            contadorTiempo.setText(String.format("%03d s", tiempo));
            contador = 0; 
        }
    }
    
    public static void Puntuar(int valor){
        
        puntos = puntos + valor;
        puntuacion.setText(String.format("%03d", puntos));
    }
}
