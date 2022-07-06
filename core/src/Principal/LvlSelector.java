/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import MegaHombre.MegaHombre;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *
 * @author alexP
 */
public class LvlSelector implements Screen {

    private Viewport viewport;
    private Stage stage;
    private MegaHombre game;
    Pantalla pantalla;

    public LvlSelector(MegaHombre game) {
        this.game = game;
        viewport = new FitViewport(this.game.VENTANA_WIDHT, this.game.VENTANA_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, (this.game.batch));
    }
    
    @Override
    public void show() {

        stage = new Stage();
        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.center();
        
        Label titulo = new Label("MegaHombre\nSELECCIONAR NIVEL", new Label.LabelStyle(new BitmapFont(), Color.ORANGE));
        titulo.setFontScale(2.5f);
        Image botonNivel1 = new Image(new Texture("nivel1.png"));
        botonNivel1.addListener(new ClickListener(){
            
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                dispose();
                pantalla = new Pantalla(game, "map.tmx");
                //pantalla.setMapaCargado("map.tmx");
                game.setScreen(pantalla);
            }
        }
        );
        Image botonNivel2 = new Image(new Texture("nivel2.png"));
        botonNivel2.addListener(new ClickListener(){
            
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                dispose();
                pantalla = new Pantalla(game, "map2.tmx");
                game.setScreen(pantalla);
            }
        }
        );
        
        Image botonNivel3 = new Image(new Texture("nivel3.png"));
        botonNivel3.addListener(new ClickListener(){
            
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                dispose();
                pantalla = new Pantalla(game, "map3.tmx");
                game.setScreen(pantalla);
            }
        }
        );
        
        Image botonNivel4 = new Image(new Texture("nivel4.png"));
        botonNivel4.addListener(new ClickListener(){
            
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                dispose();
                pantalla = new Pantalla(game, "map4.tmx");
                game.setScreen(pantalla);
            }
        }
        );
        
        tabla.row().height(50);
        tabla.add(titulo).center().pad(35f);
        tabla.row().height(50);
        tabla.add(botonNivel1).center().width(150).pad(5f);
        tabla.row().height(50);
        tabla.add(botonNivel2).center().width(150).pad(5f);
        tabla.row().height(50);
        tabla.add(botonNivel3).center().width(150).pad(5f);
        tabla.row().height(50);
        tabla.add(botonNivel4).center().width(150).pad(5f);
        
        stage.addActor(tabla);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float f) {

        ScreenUtils.clear(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    } 
}
