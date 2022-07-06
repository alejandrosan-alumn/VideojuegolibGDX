/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapa;

import Principal.Pantalla;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author alexP
 */
public class Objeto {
    
    protected World world;
    protected TiledMap mapa;
    protected Body body;
    protected Fixture fixture;
    public Pantalla pantalla;
    protected Rectangle rectangulo;

    public Objeto(World world, TiledMap mapa, Rectangle rectangulo, Pantalla pantalla) {
        this.world = world;
        this.mapa = mapa;
        this.pantalla = pantalla;
        this.rectangulo = rectangulo;
        
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((this.rectangulo.getX()+this.rectangulo.getWidth()/2)/MegaHombre.MegaHombre.PPM, (this.rectangulo.getY()+this.rectangulo.getHeight()/2)/MegaHombre.MegaHombre.PPM);
        
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rectangulo.getWidth()/2/MegaHombre.MegaHombre.PPM, rectangulo.getHeight()/2/MegaHombre.MegaHombre.PPM);
        fdef.shape = shape;
        this.body = this.world.createBody(bdef);
        this.fixture = this.body.createFixture(fdef);
        
    }
    
    public void Collision(){
        
        
    }
}
