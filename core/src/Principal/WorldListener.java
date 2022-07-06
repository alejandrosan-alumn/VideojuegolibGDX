/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Mapa.BalaEnemiga;
import Mapa.Blaster;
import Mapa.Enemigo;
import Mapa.EnemigoVolador;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 *
 * @author alexP
 */
public class WorldListener implements ContactListener {

    @Override
    public void beginContact(Contact contacto) {

        Fixture fixA = contacto.getFixtureA();
        Fixture fixB = contacto.getFixtureB();
        
        int cBits = fixA.getFilterData().categoryBits|fixB.getFilterData().categoryBits;
        
        switch(cBits){
            
            case 32|256:
                if(fixA.getFilterData().categoryBits == 32)
                    ((Enemigo)fixA.getUserData()).GolpeaMega((Blaster)fixB.getUserData());
                else
                    ((Enemigo)fixB.getUserData()).GolpeaMega((Blaster)fixA.getUserData());
            break;
            case  256| 8:
                    if(fixA.getFilterData().categoryBits == 256)
                        ((Blaster)fixA.getUserData()).setDestruccion();
                    else
                        ((Blaster)fixB.getUserData()).setDestruccion();
            break;
            case 64|1:
    
                if(fixA.getFilterData().categoryBits == 64)
                    ((BalaEnemiga) fixA.getUserData()).SetToDestroy();
                else
                    ((BalaEnemiga) fixB.getUserData()).SetToDestroy();
            break;
            case 2|16:
            case 2|32:
                
            break;
            case 2|64:
                
            break;
            case 4|2:
                
            break;
        }
    }

    @Override
    public void endContact(Contact cntct) {

    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {

    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {

    }
}
