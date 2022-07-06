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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author alexP
 */
public class BalaEnemiga extends Sprite {
    
    Pantalla pantalla;
    World world;
    Array<TextureRegion> frames;
    Animation<TextureRegion> disparando;
    float stateTime;
    boolean destruido;
    boolean setToDestroy;
    
    Body balaBody;

    public BalaEnemiga(Pantalla pantalla, World world, float x, float y) {
        this.pantalla = pantalla;
        this.world = world;
        frames = new Array<>();
        
        for (int i = 1; i <= 3; i++) {
            if (i == 1)
                frames.add(new TextureRegion(pantalla.atlasGeneral.findRegion("megasprite_remake"), 56, 992, 24, 24));
            if (i == 2)
                frames.add(new TextureRegion(pantalla.atlasGeneral.findRegion("megasprite_remake"),342 , 992, 24, 24));
            if (i == 3)
                frames.add(new TextureRegion(pantalla.atlasGeneral.findRegion("megasprite_remake"), 620, 992, 24, 24));
        }
        
        disparando = new Animation<>(0.4f, frames);
        setRegion(disparando.getKeyFrame(0));
        setBounds(x, y, 10/MegaHombre.MegaHombre.PPM, 10/MegaHombre.MegaHombre.PPM);
        DefinirBala();
    }

    private void DefinirBala() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!this.world.isLocked())
            balaBody = world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(3, 3).scl(1 / MegaHombre.MegaHombre.PPM);
        vertice[1] = new Vector2(3, -3).scl(1 / MegaHombre.MegaHombre.PPM);
        vertice[2] = new Vector2(-3, -3).scl(1 / MegaHombre.MegaHombre.PPM);
        vertice[3] = new Vector2(-3, 3).scl(1 / MegaHombre.MegaHombre.PPM);
        shape.set(vertice);
        
        fdef.filter.categoryBits = 64;
        fdef.filter.maskBits = 1|2|8;
        fdef.shape = shape;
        balaBody.createFixture(fdef).setUserData(this);
    }
    
    public void update(float delta){
        
        stateTime = stateTime + delta;
        setRegion(this.disparando.getKeyFrame(stateTime, true));
        setPosition(balaBody.getPosition().x - getWidth()/2, balaBody.getPosition().y-getHeight()/2);
        
        if(stateTime > 5 || setToDestroy){
            
            world.destroyBody(balaBody);
            destruido = true;
        }
        balaBody.setLinearVelocity(0, -1);
    }
    
    public void SetToDestroy(){
        
        setToDestroy = true;
    }

    public boolean isDestruido() {
        return destruido;
    }
}
