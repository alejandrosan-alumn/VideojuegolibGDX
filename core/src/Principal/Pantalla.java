/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Mapa.CrearMapa;
import Mapa.Enemigo;
import Mapa.EnemigoTierra;
import Mapa.EnemigoVolador;
import Mapa.HUD;
import Mapa.Jefe;
import Mapa.Mega;
import MegaHombre.MegaHombre;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *
 * @author alexP
 */
public class Pantalla implements Screen {
    
    private MegaHombre game;
    public OrthographicCamera cam;
    private Viewport gamePort;
    
    public TmxMapLoader maploader;
    public TiledMap map;
    String mapaCargado;
    public OrthogonalTiledMapRenderer renderer;
    public World world;
    public HUD hud;
    
    public Mega megaHombre;
    public TextureAtlas atlasGeneral;
    
    private CrearMapa mapa;
    
    private Music musica;

    public Pantalla(MegaHombre game, String mapaCargado) {
        
        atlasGeneral = new TextureAtlas("MEGAMAN_ENEMY.atlas");
        this.game = game;
        hud = new HUD(this.game.batch);
        cam = new OrthographicCamera();
        gamePort = new FitViewport(this.game.VENTANA_WIDHT/1, this.game.VENTANA_HEIGHT/1, cam);
        cam.position.set(360, gamePort.getScreenHeight(), 0);
        
        maploader = new TmxMapLoader();
        map = maploader.load(mapaCargado);
        renderer = new OrthogonalTiledMapRenderer(map, 1/1);
        
        cam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/1.5f, 0);
        
        //Gravedad.
        world = new World(new Vector2(0, -30), true);
        
        mapa = new CrearMapa(world, map, this);
        cam.position.y = this.game.VENTANA_HEIGHT/2;

        megaHombre = new Mega(world, this);
        
        world.setContactListener(new WorldListener());
        
        musica = this.game.manager.get("bgmusic.ogg", Music.class);
        musica.setLooping(true);
        musica.setVolume(0.1f);
        musica.play();
    }

    @Override
    public void show() {
    }

    public void Update(float delta){
        EntradaUsuario();
        world.step(1 / 60f, 6, 2);
        megaHombre.Update(delta);
        if (megaHombre.body.getPosition().x > 200 && megaHombre.body.getPosition().x < 1780) {

            cam.position.x = megaHombre.body.getPosition().x;
        }
        
        for(Enemigo enemigo : mapa.getEnemigos()){
            
            enemigo.update(delta, megaHombre);
            if(enemigo.getX() < megaHombre.getX() + 100/1)
                enemigo.cuerpoEnemigo.setActive(true);
        }
        
        /*for(EnemigoVolador enemigo : mapa.getVoladores()){
            
            enemigo.update(delta, megaHombre);
            if(enemigo.getX() < megaHombre.getX() + 200/1)
                enemigo.cuerpoEnemigo.setActive(true);
        }
        
        for(EnemigoTierra enemigo : mapa.getTerrenales()){
            
            enemigo.update(delta, megaHombre);
            if(enemigo.getX() < megaHombre.getX() + 200/1)
                enemigo.cuerpoEnemigo.setActive(true);
        }*/
        
        for(Jefe jefe : mapa.getJefes()){
            
            jefe.update(delta, megaHombre);
            if(jefe.getX() < megaHombre.getX() +100){
                
                jefe.cuerpoEnemigo.setActive(true);
            }
            if(jefe.isDestruido()){
                
                musica.stop();
                game.setScreen(new MainMenuScreen(this.game));
            }
        }

        hud.update(delta);
        //this.megaHombre.Update(delta);
        cam.update();
        renderer.setView(cam);
    }
    
    public void EntradaUsuario(){
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            
            megaHombre.Saltar();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)&&megaHombre.body.getLinearVelocity().x <=2) {

            
            megaHombre.Derecha();
        }


        if (Gdx.input.isKeyPressed(Input.Keys.A)&&megaHombre.body.getLinearVelocity().x >=-2) {

            megaHombre.Izquierda();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

            megaHombre.Disparar();
            //sound.play();
        }
    }
    
    @Override
    public void render(float delta) {
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Update(delta);
        renderer.render();
        this.game.batch.setProjectionMatrix(cam.combined);
        this.game.batch.begin();
        this.megaHombre.draw(game.batch);
        
        for(Enemigo enemigo : mapa.getEnemigos()){
            
            enemigo.draw(game.batch);
        }
        
        /*for(EnemigoVolador enemigo : mapa.getVoladores()){
            
            enemigo.draw(game.batch);
        }
        for(EnemigoTierra enemigo : mapa.getTerrenales()){
            
            enemigo.draw(game.batch);
        }
        
        for(Jefe enemigo : mapa.getJefes()){
            
            enemigo.draw(game.batch);
        }*/
        
        this.game.batch.end();
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        
        gamePort.update(width, height);
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
    
        this.map.dispose();
        renderer.dispose();
        world.dispose();
    }

    public String getMapaCargado() {
        return mapaCargado;
    }

    public void setMapaCargado(String mapaCargado) {
        this.mapaCargado = mapaCargado;
    }
}
