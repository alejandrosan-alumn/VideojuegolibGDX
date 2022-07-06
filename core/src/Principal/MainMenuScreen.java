/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import MegaHombre.MegaHombre;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *
 * @author alexP
 */
public class MainMenuScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private MegaHombre game;
    Music music;
    Texture background;

    public MainMenuScreen(MegaHombre game) {
        this.game = game;
        viewport = new FitViewport(this.game.VENTANA_WIDHT, this.game.VENTANA_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, (this.game.batch));
        //background = new Texture(Gdx.files.internal("PlaceHolder.png"));
    }
    
    @Override
    public void show() {

        stage = new Stage();
        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.center();
        
        Label titulo = new Label("MegaHombre\nMENU", new Label.LabelStyle(new BitmapFont(), Color.ORANGE));
        titulo.setFontScale(2.5f);
        Image botonJugarImage = new Image(new Texture("jugar_active.png"));
        botonJugarImage.addListener(new ClickListener(){
            
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                dispose();
                game.setScreen(new LvlSelector(game));
            }
        }
        );
        Image botonSalir = new Image(new Texture("salir.png"));
        botonSalir.addListener(new ClickListener(){
            
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                dispose();
                System.exit(0);
            }
        }
        );
        
        tabla.row().height(50);
        tabla.add(titulo).center().pad(35f);
        tabla.row().height(50);
        tabla.add(botonJugarImage).center().width(150).pad(5f);
        tabla.row().height(50);
        tabla.add(botonSalir).center().width(150).pad(5f);
        
        stage.addActor(tabla);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        
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
