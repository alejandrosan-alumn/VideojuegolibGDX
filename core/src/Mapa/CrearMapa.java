/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapa;

import Principal.Pantalla;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author alexP
 */
public class CrearMapa {
    
    private Array<EnemigoVolador> voladores;
    private Array<EnemigoTierra> terrenales;
    private Array<Jefe> jefes;
    
    public CrearMapa(World world, TiledMap mapa, Pantalla p) {
        
        //Creando el terreno.
        for(RectangleMapObject objeto : mapa.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            
            Rectangle rectangulo = objeto.getRectangle();
            new Terreno(world, mapa, rectangulo, p);
        }
        //Creando enemigos voladores.
        voladores = new Array<>();
        for(RectangleMapObject objeto : mapa.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            
            Rectangle rectangulo = objeto.getRectangle();
            voladores.add(new EnemigoVolador(world, p, rectangulo.getX()/MegaHombre.MegaHombre.PPM, rectangulo.getY()/MegaHombre.MegaHombre.PPM));
        }
        //Creando enemigos por tierra.
        terrenales = new Array<>();
        for(RectangleMapObject objeto : mapa.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            
            Rectangle rectangulo = objeto.getRectangle();
            terrenales.add(new EnemigoTierra(world, p, rectangulo.getX()/MegaHombre.MegaHombre.PPM, rectangulo.getY()/MegaHombre.MegaHombre.PPM));
        }
        //Creando al boss
        jefes = new Array<>();
        for(RectangleMapObject objeto : mapa.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            
            Rectangle rectangulo = objeto.getRectangle();
            jefes.add(new Jefe(world, p, rectangulo.getX()/MegaHombre.MegaHombre.PPM, rectangulo.getY()/MegaHombre.MegaHombre.PPM));
        }
    }

    public Array<Jefe> getJefes() {
        return jefes;
    }

    public Array<EnemigoVolador> getVoladores() {
        return voladores;
    }

    public Array<EnemigoTierra> getTerrenales() {
        return terrenales;
    }
    
    public Array<Enemigo> getEnemigos(){
        
        Array<Enemigo> enemigos = new Array<>();
        enemigos.addAll(voladores);
        enemigos.addAll(terrenales);
        enemigos.addAll(jefes);
        
        return enemigos;
    }
}
