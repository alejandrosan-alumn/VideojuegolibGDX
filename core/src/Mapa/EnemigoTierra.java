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
public class EnemigoTierra extends Enemigo {

    
    public enum State{
        ADELANTE, ATRAS
    }
    public State currentState;
    public State previousState;
    private float stateTime;
    private Animation animacionAdelante;
    private Animation animacionAtras;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destruido;

    public EnemigoTierra(World world, Pantalla pantalla, float x, float y) {
        super(world, pantalla, x, y);
        
        frames = new Array<>();
        frames.add(new TextureRegion(pantalla.atlasGeneral.findRegion("enemysprite1"), 484, 510,110,120));
        frames.add(new TextureRegion(pantalla.atlasGeneral.findRegion("enemysprite1"), 594, 510,110,120));
        animacionAdelante = new Animation(0.3f, frames);
        animacionAtras = new Animation(0.3f, frames);
        currentState = previousState = State.ADELANTE;
        setBounds(getX(), getY(), 40/MegaHombre.MegaHombre.PPM, 40/MegaHombre.MegaHombre.PPM);
        setToDestroy = false;
        destruido = false;
    }
    
    
    @Override
    protected void DefinirEnemigo() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        this.cuerpoEnemigo = world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/MegaHombre.MegaHombre.PPM);
        fdef.filter.categoryBits = 16;
        fdef.filter.maskBits = 1|256|8|2;
        fdef.shape = shape;
        cuerpoEnemigo.createFixture(fdef).setUserData(this);
        
        PolygonShape manoIzquierda = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(6, -8).scl(1/1);
        vertice[1] = new Vector2(6, 8).scl(1/1);
        vertice[2] = new Vector2(4, -6).scl(1/1);
        vertice[3] = new Vector2(4, 6).scl(1/1);
        manoIzquierda.set(vertice);
        fdef.shape = manoIzquierda;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = 32;
        cuerpoEnemigo.createFixture(fdef).setUserData(this);
        
        PolygonShape manoDerecha = new PolygonShape();
        Vector2[] vertice2 = new Vector2[4];
        vertice2[0] = new Vector2(-6, -8).scl(1/1);
        vertice2[1] = new Vector2(-6, 8).scl(1/1);
        vertice2[2] = new Vector2(-4, -6).scl(1/1);
        vertice2[3] = new Vector2(-4, 6).scl(1/1);
        manoDerecha.set(vertice);
        fdef.shape = manoDerecha;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = 32;
        cuerpoEnemigo.createFixture(fdef).setUserData(this);
        cuerpoEnemigo.setGravityScale(0f);
    }
    
    public TextureRegion getFrame(float delta){
        
        TextureRegion region;
        switch(currentState){
            
            case ADELANTE:
            case ATRAS:
                region = (TextureRegion) animacionAtras.getKeyFrame(stateTime, true);
            break;
            default:
                region = (TextureRegion) animacionAdelante.getKeyFrame(stateTime, true);
            break;
        }
        
        if(velocidad.x< 0 && !region.isFlipX())
            region.flip(true, false);
        if(velocidad.x>0&&region.isFlipX())
            region.flip(true, false);
        stateTime = currentState == previousState ? stateTime + delta:0;
        previousState = currentState;
        return region;
    }

    @Override
    public void update(float delta, Mega megahombre) {

        stateTime = stateTime + delta;
        if(setToDestroy && !destruido){
            
            world.destroyBody(cuerpoEnemigo);
            destruido = true;
            stateTime = 0;
        }
        else if(!destruido){
            if(currentState == State.ADELANTE&&stateTime > 2){
                currentState = State.ATRAS;
                VelocidadInversa(true, false);
            }
            else if(currentState == State.ATRAS&&stateTime > 2){
                
                currentState = State.ADELANTE;
                VelocidadInversa(true, false);
            }
            setRegion(getFrame(delta));
            setPosition(this.cuerpoEnemigo.getPosition().x - getWidth()/2, cuerpoEnemigo.getPosition().y - 8/1);
            cuerpoEnemigo.setLinearVelocity(velocidad);
        }
    }

    @Override
    public void GolpeaMega(Blaster blaster) {

        setToDestroy = true;
        HUD.Puntuar(20);
    }

    @Override
    public void Dispara() {

    }
    
    @Override
    public void draw(Batch batch){
        
        if(!destruido){
            super.draw(batch);
        }
    }
}
