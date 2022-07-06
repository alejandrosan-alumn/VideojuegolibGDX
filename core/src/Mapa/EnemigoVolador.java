/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapa;

import Principal.Pantalla;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author alexP
 */
public class EnemigoVolador extends Enemigo {
    
    private float stateTime;
    private Animation volar;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destruido;
    private Array<BalaEnemiga> balas;

    public EnemigoVolador(World world, Pantalla pantalla, float x, float y) {
        super(world, pantalla, x, y);
        frames = new Array<>();
        for(int i = 0; i < 3; i++){
            
            frames.add(new TextureRegion(pantalla.atlasGeneral.findRegion("enemysprite1"), i*130, 680, 130, 75));
        }
        volar = new Animation(0.2f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 30/MegaHombre.MegaHombre.PPM, 30/MegaHombre.MegaHombre.PPM);
        setToDestroy = false;
        destruido = false;
        balas = new Array<>(1);
        
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
        cuerpoEnemigo.setGravityScale(0f);
        
    }

    @Override
    public void update(float delta, Mega megahombre) {

        stateTime = stateTime + delta;
        if(setToDestroy == true && !this.destruido){
            world.destroyBody(cuerpoEnemigo);
            destruido = true;
            stateTime = 0;
        }
        else if(!destruido){
            
            cuerpoEnemigo.setLinearVelocity(0,0);
            setPosition(cuerpoEnemigo.getPosition().x - getWidth()/2, cuerpoEnemigo.getPosition().y - getHeight()/2);
            setRegion((TextureRegion) this.volar.getKeyFrame(stateTime, true));
            //Dispara();
        }
        /*for(BalaEnemiga balazo : balas){
            
            balazo.update(delta);
            if(balazo.isDestruido())
                balas.removeValue(balazo, true);
        }*/
    }

    @Override
    public void GolpeaMega(Blaster blaster) {

        setToDestroy = true;
        HUD.Puntuar(20);
    }

    @Override
    public void Dispara() {

        if(balas.isEmpty())
            balas.add(new BalaEnemiga(this.pantalla, world, this.cuerpoEnemigo.getPosition().x, cuerpoEnemigo.getPosition().y));
    }
    
    @Override
    public void draw(Batch batch){
        
        if(!destruido){
            super.draw(batch);
            /*for(BalaEnemiga balazo : balas){
                balazo.draw(batch);
            }*/
        }
    }
}
