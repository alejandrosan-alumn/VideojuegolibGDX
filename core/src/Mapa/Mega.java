/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapa;

import static Mapa.Mega.State.DEAD;
import Principal.Pantalla;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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
public class Mega extends Sprite {

    public enum State {
    
        CAER,
        SALTAR,
        PARARSE,
        CORRIENDO,
        GOLPEANDO,
        DISPARANDOYCORRIENDO,
        DEAD;
    }


    private State currentState;
    private State previousState;
    
    public World world;
    public Body body;
    private TextureRegion parado;
    private TextureRegion muerto;
    private Animation<TextureRegion> corriendo;
    private Animation<TextureRegion> saltando;
    private Animation<TextureRegion> disparando;
    private Animation<TextureRegion> disparandoYCorriendo;
    
    private float stateTimer;
    public boolean derecha;
    private boolean megaHombreMuerto;
    
    private Pantalla pantalla;

    private Array<Blaster> blasters;
    
    public Mega(World world, Pantalla p) {
        
        pantalla = p;
        this.world = world;
        
        currentState = State.PARARSE;
        previousState = State.PARARSE;
        stateTimer = 0;
        derecha = true;
        
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(p.atlasGeneral.findRegion("megasprite_remake"), i*105, 0, 90, 110));
        }
        this.corriendo = new Animation<TextureRegion>(0.1F, frames);
        frames.clear();


        for (int i = 1; i < 4; i++) {

            frames.add(new TextureRegion(p.atlasGeneral.findRegion("megasprite_remake"), i*110, 120, 90, 110));
        }
        this.saltando = new Animation<TextureRegion>(0.2F, frames);
        frames.clear();
        
        for(int i = 1; i < 2; i++){
            
            frames.add(new TextureRegion(p.atlasGeneral.findRegion("megasprite_remake"), 150, 585, 90, 110));
        }
        this.disparando = new Animation<TextureRegion>(0.2F, frames);
        frames.clear();
        
        for(int i = 0; i < 5; i++){
            
            if(i>=2){
                frames.add(new TextureRegion(p.atlasGeneral.findRegion("megaman_shooting_running"), (i-2)*153, 110, 90, 110));
            }
            else{
                frames.add(new TextureRegion(p.atlasGeneral.findRegion("megaman_shooting_running"), i*157, 0, 90, 110));
            }
        }
        this.disparandoYCorriendo = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();
        
        muerto = new TextureRegion(p.atlasGeneral.findRegion("megasprite_remake"), 0, 276, 90, 110);
        parado = new TextureRegion(p.atlasGeneral.findRegion("megasprite_remake"), 0, 0, 90, 110);
        
        DefinirProta();
        
        setBounds(0,0,50/MegaHombre.MegaHombre.PPM, 50/MegaHombre.MegaHombre.PPM);
        setRegion(this.parado);
        
        blasters = new Array<Blaster>();
    }
    
    public State getState() {
        
        State devolver = null;
        if(this.megaHombreMuerto)
            devolver = State.DEAD;
        else if((body.getLinearVelocity().y > 0&&currentState == State.SALTAR)||(body.getLinearVelocity().y < 0&&currentState == State.SALTAR)){
            devolver = State.SALTAR;
        }
        else if(body.getLinearVelocity().y<0)
            devolver = State.CAER;
        else if(body.getLinearVelocity().x!=0)
            devolver = State.CORRIENDO;
        else if(currentState==State.GOLPEANDO)
            devolver = State.GOLPEANDO;
        else if(currentState==State.DISPARANDOYCORRIENDO)
            devolver = State.DISPARANDOYCORRIENDO;
        else
            devolver = State.PARARSE;
        
        return devolver;
    }
    
    public TextureRegion getFrame(float delta) {

        currentState = getState();

        if(body.getLinearVelocity().x != 0 && Gdx.input.isKeyPressed(Input.Keys.SPACE))
            currentState = State.DISPARANDOYCORRIENDO;

        TextureRegion region;
        switch (currentState) {
            case DEAD:
                region = this.muerto;
                break;
            case CORRIENDO:
                region = this.corriendo.getKeyFrame(stateTimer, true);
                break;
            case SALTAR:
                region = this.saltando.getKeyFrame(stateTimer, true);
                break;
            case GOLPEANDO:
                region = this.disparando.getKeyFrame(stateTimer, true);
                break;
            case DISPARANDOYCORRIENDO:
                region = this.disparandoYCorriendo.getKeyFrame(stateTimer, true);
                break;
            case PARARSE:
            default:
                region = this.parado;
                break;
            
        }

        if ((body.getLinearVelocity().x < 0 || !derecha) && !region.isFlipX()) {
            region.flip(true, false);
            derecha = false;
        } else if ((body.getLinearVelocity().x > 0 || derecha) && region.isFlipX()) {
            region.flip(true, false);
            derecha = true;
        }
        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }
    
    public void Update(float delta){
        
        /*if(currentState == State.GOLPEANDO && stateTimer > 0.5)
            currentState = State.PARARSE;*/
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        //setPosition(body.getPosition().x, body.getPosition().y);
        setRegion(getFrame(delta));
        
        for(Blaster blaster : blasters){
            blaster.update(delta);
            if(blaster.isDestruido())
                blasters.removeValue(blaster, true);
        }
    }
    
    public void Saltar(){
        
        if(currentState != State.SALTAR){
            
            //body.applyLinearImpulse(new Vector2(0, 4f), body.getWorldCenter(), true);
            body.applyLinearImpulse(0, 55, body.getWorldCenter().x, body.getWorldCenter().y, true);
            currentState = State.SALTAR;
        }
    }
    
    public void Disparar(){
        
        if(this.body.getLinearVelocity().x != 0)
            currentState = State.DISPARANDOYCORRIENDO;
        else
            currentState = State.GOLPEANDO;
        blasters.add(new Blaster(this.pantalla, body.getPosition().x, body.getPosition().y,this.world ,this.derecha));
    }
    
    private void DefinirProta(){
        
        BodyDef bodydef = new BodyDef();
        bodydef.position.set(150 / MegaHombre.MegaHombre.PPM, 100 / MegaHombre.MegaHombre.PPM);
        bodydef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodydef);

        FixtureDef fixdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / MegaHombre.MegaHombre.PPM);
        fixdef.filter.categoryBits = 2;
        fixdef.filter.maskBits =  1|16|8|32|64|4;
        fixdef.shape = shape;
        //fixdef.isSensor = true;
        body.createFixture(fixdef).setUserData(this);
        //body.createFixture(fixdef);
    }
    
    public void Derecha(){
        
        //body.applyLinearImpulse(new Vector2(60f,0), body.getWorldCenter(), true);
       // body.setLinearVelocity(new Vector2(60f, 0));
        //body.applyLinearImpulse(60,0,body.getWorldCenter().x, body.getWorldCenter().y, true);
        body.applyLinearImpulse(new Vector2(60f, 0), body.getWorldCenter(), true);
    }
    
    public void Izquierda(){
        
        //body.applyLinearImpulse(new Vector2(-0.1f,0), body.getWorldCenter(), true);
        //body.applyLinearImpulse(-60,0,body.getWorldCenter().x, body.getWorldCenter().y, true);
        body.applyLinearImpulse(new Vector2(-60f, 0), body.getWorldCenter(), true);
    }
    
    @Override
    public void draw(Batch batch){
        
        super.draw(batch);
        for(Blaster blaster:blasters){
            blaster.draw(batch);
        }
    }
}
