/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasolar;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

/**
 *
 * @author Nacho
 */
public class Planeta extends Astro{
    
    public Planeta(float diametro, int velocidadRotacion, int radioSeparacion, String aspecto, int tiempoTraslacion) {
        super(diametro, velocidadRotacion, radioSeparacion, aspecto);
        
       
        TransformGroup luna = addSatelite(tiempoTraslacion);
        posicionar.addChild(luna);
        
        TransformGroup trasladar = traslacion(tiempoTraslacion);
        trasladar.addChild(super.posicionar);
        
       
        this.addChild(trasladar);
    }
    
    private TransformGroup traslacion(int tiempoTraslacion){
        // Se crea el nodo de transformación: Todo lo que cuelgue de él rotará
        TransformGroup grupoTraslacion = new TransformGroup (); 
        // Se le permite que se cambie en tiempo de ejecución
        grupoTraslacion.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); 
        // Se crea la matriz de rotación
        Transform3D traslacion = new Transform3D (); 
        // Valor numérico que se ira modificando en tiempo de ejecución
        Alpha value = new Alpha (-1, Alpha.INCREASING_ENABLE, 0, 0, 
                tiempoTraslacion, 0, 0, 0, 0, 0); 
        // Se crea el interpolador de rotación, las figuras iran rotando
        RotationInterpolator rotatorAround = new RotationInterpolator 
        (value, grupoTraslacion, traslacion, 0.0f, (float) Math.PI*2.0f); 
        // Se le pone el entorno de activación
        rotatorAround.setSchedulingBounds(new BoundingSphere 
        (new Point3d (0.0, 0.0, 0.0 ), 500.0)); 
        // Se activa
        rotatorAround.setEnable(true); 
        // Se cuelga del grupo de transformación
        grupoTraslacion.addChild(rotatorAround); 
        
        return grupoTraslacion;
    }
    
    private TransformGroup addSatelite(int tiempoTraslacion){
         Satelite Luna = new Satelite((float)0.2, 1000, 2, "imgs/luna.jpg");
        
        TransformGroup trasladar = traslacion(tiempoTraslacion);
        trasladar.addChild(Luna);
        return trasladar;
    }
    
}


