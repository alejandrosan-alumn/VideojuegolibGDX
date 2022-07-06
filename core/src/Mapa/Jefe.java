/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapa;

import Principal.Pantalla;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author alexP
 */
public class Jefe extends Enemigo {

    public enum State{
        PARADO,
        MOVIENDOSE,
        ATACANDO,
        SALTANDO;
    }
    
    public State estadoActual;
    public State estadoAnterior;
    private TextureRegion parado;
    
    private Animation<TextureRegion> moviendose;
    private Animation<TextureRegion> atacando;
    private Animation<TextureRegion> saltando;
    private Animation<TextureRegion> rodando;
    
    private static int vidas = 3;
    private float tiempoEstado;
    private boolean setToDestroy;
    private boolean destruido;

    public Jefe(World world, Pantalla pantalla, float x, float y) {
        super(world, pantalla, x, y);
        estadoActual = State.PARADO;
        estadoAnterior = State.PARADO;
        tiempoEstado = 0;
        Array<TextureRegion> frames = new Array<>();
        //Moviéndose
        for(int i = 0; i < 7; i++){
            if(i < 4)
                frames.add(new TextureRegion(pantalla.atlasGeneral.findRegion("finalboss"),i*120,155,125,93));
            else
                frames.add(new TextureRegion(pantalla.atlasGeneral.findRegion("finalboss"),(i-4)*139 + 485,155,125,93));
        }
        moviendose = new Animation<TextureRegion>(1f, frames);
        frames.clear();
        //Atacando
        for(int i = 0; i < 5; i++){
            frames.add(new TextureRegion(pantalla.atlasGeneral.findRegion("finalboss"),i*132,280,132,86));
        }
        atacando = new Animation<TextureRegion>(1f, frames);
        //Saltando
        for(int i = 0; i < 8; i++){
            if( i == 0 || i == 7)
                frames.add(new TextureRegion(pantalla.atlasGeneral.findRegion("finalboss"),0,0,120,110));
            if(i == 1)
                frames.add(new TextureRegion(pantalla.atlasGeneral.findRegion("finalboss"),120,0,137,110));
            else
                frames.add(new TextureRegion(pantalla.atlasGeneral.findRegion("finalboss"),(i-1) * 115 + 257,0,115,130));
        }
        saltando = new Animation<TextureRegion>(1f, frames);
        frames.clear();
        //Parado
        parado = new TextureRegion(pantalla.atlasGeneral.findRegion("finalboss"),0,160,112,82);
        
        setBounds(getX(), getY(), 60/MegaHombre.MegaHombre.PPM, 60/MegaHombre.MegaHombre.PPM);
        setToDestroy = false;
        destruido = false;
    }
    
    
    @Override
    protected void DefinirEnemigo() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        this.cuerpoEnemigo = world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10/MegaHombre.MegaHombre.PPM);
        fdef.filter.categoryBits = 16;
        fdef.filter.maskBits = 1|8|256|2;
        fdef.shape = shape;
        cuerpoEnemigo.createFixture(fdef).setUserData(this);
        
        //Creando la mano izquierda
        PolygonShape izquierda = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(10, 12).scl(1 / MegaHombre.MegaHombre.PPM);
        vertice[1] = new Vector2(10, -6).scl(1 / MegaHombre.MegaHombre.PPM);
        vertice[2] = new Vector2(8, -6).scl(1 / MegaHombre.MegaHombre.PPM);
        vertice[3] = new Vector2(8, 6).scl(1 / MegaHombre.MegaHombre.PPM);
        izquierda.set(vertice);
        fdef.shape = izquierda;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = 32;
        cuerpoEnemigo.createFixture(fdef).setUserData(this);
        
        //Mano derecha
        PolygonShape derecha = new PolygonShape();
        Vector2[] vertice2 = new Vector2[4];
        vertice2[0] = new Vector2(-10, 12).scl(1 / MegaHombre.MegaHombre.PPM);
        vertice2[1] = new Vector2(-10, -6).scl(1 / MegaHombre.MegaHombre.PPM);
        vertice2[2] = new Vector2(-8, -6).scl(1 / MegaHombre.MegaHombre.PPM);
        vertice2[3] = new Vector2(-8, 6).scl(1 / MegaHombre.MegaHombre.PPM);
        derecha.set(vertice2);
        fdef.shape = derecha;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = 32;
        cuerpoEnemigo.createFixture(fdef).setUserData(this);
    }
    
    public TextureRegion getFrame(float delta){
        
        TextureRegion region;
        switch(estadoActual){
            
            case ATACANDO:
                region = atacando.getKeyFrame(tiempoEstado, true);
            break;
            case MOVIENDOSE:
                region = moviendose.getKeyFrame(tiempoEstado, true);
            break;
            case SALTANDO:
                region = saltando.getKeyFrame(tiempoEstado, true);
            break;
            case PARADO:
            default:
                region = parado;
            break;
        }
        if(velocidad.x > 0 && !region.isFlipX())
            region.flip(true, false);
        if(velocidad.x < 0 && region.isFlipX())
            region.flip(true, false);
        tiempoEstado = estadoActual == this.estadoAnterior ? tiempoEstado + delta : 0;
        estadoAnterior = estadoActual;
        return region;
    }

    @Override
    public void update(float delta, Mega megahombre) {
    
        tiempoEstado = tiempoEstado + delta;
        if(setToDestroy && !destruido){
            
            world.destroyBody(cuerpoEnemigo);
            destruido = true;
            tiempoEstado = 0;
        }
        else if(!destruido){
            
            setRegion(getFrame(delta));
            /*if(estadoActual == State.ATACANDO && tiempoEstado > 3){
                estadoActual = State.PARADO;
                velocidad.x = 0;
                velocidad.y = 0;
            }*/
            if(estadoActual == State.PARADO /*&& tiempoEstado > 2*/)
                estadoActual = State.MOVIENDOSE;
            else if(estadoActual == State.MOVIENDOSE){
                velocidad = seguirPersonaje(megahombre);
            }
            setPosition(cuerpoEnemigo.getPosition().x - getWidth()/2, cuerpoEnemigo.getPosition().y - getHeight()/3);
            cuerpoEnemigo.applyLinearImpulse(velocidad, cuerpoEnemigo.getWorldCenter(), true);
            //cuerpoEnemigo.setLinearVelocity(velocidad);
            //System.out.println("Cuál es su estado: " + estadoActual);
        }
    }

    @Override
    public void GolpeaMega(Blaster blaster) {
    
        vidas--;
        if(vidas > 0)
            estadoActual = State.PARADO;
        else
            setToDestroy = true;
    }

    @Override
    public void Dispara() {
    }
    
    private Vector2 seguirPersonaje(Mega megahombre) {

        Vector2 vector;
        
        if(megahombre.body.getPosition().x < cuerpoEnemigo.getPosition().x)
            vector = new Vector2(-1, 0);
        else
            vector = new Vector2(1, 0);
        return vector;
    }

    @Override
    public void draw(Batch batch) {
        
        if(!destruido || tiempoEstado < 0.5)
            super.draw(batch);
    }

    public boolean isSetToDestroy() {
        return setToDestroy;
    }

    public boolean isDestruido() {
        return destruido;
    }
}