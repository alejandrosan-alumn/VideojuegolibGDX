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
public class Terreno extends Objeto {

    public Terreno(World world, TiledMap mapa, Rectangle rectangulo, Pantalla pantalla) {
        super(world, mapa, rectangulo, pantalla);
        fixture.setUserData(this);
    }
    
    
}
