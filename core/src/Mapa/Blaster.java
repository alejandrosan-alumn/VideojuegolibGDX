/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapa;

import Principal.Pantalla;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author alexP
 */
public class Blaster extends Sprite {
    
    Pantalla p;
    World world;
    Array<TextureRegion> frames;
    Animation<TextureRegion> animacionBlast;
    float stateTime;
    boolean disparoDerecha;
    boolean destruido;
    boolean setDestruido;
    
    Body disparoBody;

    public Blaster(Pantalla p, float x, float y,World world ,boolean disparoDerecha) {
        this.p = p;
        this.disparoDerecha = disparoDerecha;
        this.world = world;
        frames = new Array<TextureRegion>();
        for(int i = 1; i <=3; i++){
            
            if(i==1){
                frames.add(new TextureRegion(p.atlasGeneral.findRegion("megasprite_remake"), 10, 400, 140, 110));
            }
            if(i==2){
                frames.add(new TextureRegion(p.atlasGeneral.findRegion("megasprite_remake"), 170, 400, 140, 110));
            }
            if(i==3){
                frames.add(new TextureRegion(p.atlasGeneral.findRegion("megasprite_remake"), 350, 400, 140, 110));
            }
        }
        animacionBlast = new Animation<TextureRegion>(0.4f, frames);
        setRegion(animacionBlast.getKeyFrame(0));
        setBounds(x, y, 50/MegaHombre.MegaHombre.PPM, 50/MegaHombre.MegaHombre.PPM);
        DefineBlaster();
    }
    
    public void DefineBlaster(){
        
        BodyDef bdef = new BodyDef();
        //El primero es disparo a la derecha, el segundo a la izquierda.
        bdef.position.set(this.disparoDerecha ? getX() + 12/MegaHombre.MegaHombre.PPM:getX() - 30/MegaHombre.MegaHombre.PPM, getY() - 0.15f);
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!this.world.isLocked())
            this.disparoBody = world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(this.disparoDerecha ? 0.15f:0.1f, 0.1f));
        shape.setRadius(9/MegaHombre.MegaHombre.PPM);
        fdef.filter.categoryBits = 256;
        fdef.filter.maskBits = 1|32|2|128|8;
        fdef.shape = shape;
        this.disparoBody.createFixture(fdef).setUserData(this);
        disparoBody.setGravityScale(0f);
        fdef.isSensor = true;
        
        if(this.disparoDerecha){
            disparoBody.setLinearVelocity(new Vector2(50f, 0));
        }else{
            animacionBlast.getKeyFrame(0).flip(true, false);
            animacionBlast.getKeyFrame(0.50f).flip(true, false);
            animacionBlast.getKeyFrame(1f).flip(true, false);
            disparoBody.setLinearVelocity(new Vector2(-50f, 0));
        }
    }
    
    public void update(float delta){
        
        stateTime = delta;
        setRegion(animacionBlast.getKeyFrame(stateTime, false));
        //Establecer posición del disparo basado en el personaje principal.
        setPosition(disparoBody.getPosition().x, disparoBody.getPosition().y);
        if(this.setDestruido && !this.destruido){
            
            world.destroyBody(disparoBody);
            destruido = true;
        }
        disparoBody.setLinearVelocity(disparoBody.getLinearVelocity().x, 0);
        if((this.disparoDerecha&&disparoBody.getLinearVelocity().x <= 0)||((!this.disparoDerecha&&disparoBody.getLinearVelocity().x>=0)))
            setDestruccion();
        if(stateTime > 1)
            setDestruccion();
    }
    
    public void setDestruccion(){
        this.setDestruido = true;
    }

    public boolean isDestruido() {
        return destruido;
    }
    
}
