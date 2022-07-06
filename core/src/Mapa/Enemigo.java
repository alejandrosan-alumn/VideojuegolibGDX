/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapa;

import Principal.Pantalla;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author alexP
 */
public abstract class Enemigo extends Sprite {
    
    protected World world;
    protected Pantalla pantalla;
    public Body cuerpoEnemigo;
    public Vector2 velocidad;

    public Enemigo(World world, Pantalla pantalla, float x, float y) {
        this.world = world;
        this.pantalla = pantalla;
        setPosition(x, y);
        DefinirEnemigo();
        velocidad = new Vector2(1, 0);
        cuerpoEnemigo.setActive(false);
    }

    protected abstract void DefinirEnemigo();
    
    public abstract void update(float delta, Mega megahombre);
    
    public abstract void GolpeaMega(Blaster blaster);
    
    public abstract void Dispara();
    
    public void VelocidadInversa(boolean x, boolean y){
        
        if(x)
            velocidad.x = - velocidad.x;
        if(y)
            velocidad.y = - velocidad.y;
    }
    
}
