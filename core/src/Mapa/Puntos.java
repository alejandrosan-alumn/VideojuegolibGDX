/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapa;

import Principal.Pantalla;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author alexP
 */
public class Puntos extends Objeto {

    public boolean taked = false;
    
    public Puntos(World world, TiledMap mapa, Rectangle bounds, Pantalla p) {
        super(world, mapa, bounds, p);
        fixture.setUserData(this);
        fixture.setSensor(true);
    }
    
    @Override
    public void Collision() {
        if (!taked) {

            getCell().setTile(null);
            taked = true;

        }
    }
}
