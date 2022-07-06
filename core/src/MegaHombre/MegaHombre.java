/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MegaHombre;

import Principal.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author alexP
 */
public class MegaHombre extends Game {

    public SpriteBatch batch;
    public static final int VENTANA_WIDHT = 720;
    public static final int VENTANA_HEIGHT = 480;
    public static final int PPM = 1;
    public AssetManager manager;
    
    @Override
    public void create() {

        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("bgmusic.ogg", Music.class);
        manager.finishLoading();
        setScreen(new MainMenuScreen(this));
    }
    
    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {

    }

    public AssetManager getManager() {
        return manager;
    }
}
