/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasolar;

/**
 *
 * @author Nacho
 */
public class Satelite extends Astro{
    
    public Satelite(float diametro, int velocidadRotacion, float radioSeparacion, String aspecto) {
        super(diametro, velocidadRotacion, radioSeparacion, aspecto);
        
        this.addChild(super.posicionar);
    }
    
}
