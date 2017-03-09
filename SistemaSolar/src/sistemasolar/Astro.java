/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasolar;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.RotationPathInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Point3d;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

/**
 *
 * @author Nacho
 */
public class Astro extends BranchGroup{
    
    /// La rama de donde cuelga la figura que se cambia
    private BranchGroup figure;
    /// El objeto que controla la rotación continua de la figura
    private Alpha value;
    
    TransformGroup rotation;
    
    TransformGroup posicionar;
    
    Astro(float diametro, int velocidadRotacion, int radioSeparacion, 
            String aspecto){
        
        figure = new BranchGroup();
   
    // Se le dan permisos para poder intercambiar las figuras
    figure.setCapability(Group.ALLOW_CHILDREN_EXTEND);
    figure.setCapability(Group.ALLOW_CHILDREN_WRITE);
    
    //Defino un nuevo aspecto
    Appearance appearance = new Appearance ();
    
    //Cargo una textura y se la añado al aspecto creado. 
    Texture texture = new TextureLoader (aspecto, null).getTexture();
    appearance.setTexture (texture);
    
    Primitive sphere = new Sphere (diametro, Primitive.GENERATE_TEXTURE_COORDS 
                | Primitive.GENERATE_NORMALS_INWARD, 64,appearance);
    
    //añadimos la esfera al BG.
    figure.addChild(sphere);
    
    //Se crea la rotación
    rotation = createRotation (velocidadRotacion);
     
    //Se cuelga la figura de la rotación.
    rotation.addChild (figure);
    
    posicionar = movePlanet(radioSeparacion);
    
    posicionar.addChild(rotation);
    
    }
    
    //Tiempo rotación en ms.
    private TransformGroup createRotation (int tiempoRotation) {
    // Se crea el grupo que contendrá la transformación de rotación
    // Todo lo que cuelgue de él rotará
    TransformGroup transform = new TransformGroup ();
    // Se le permite que se cambie en tiempo de ejecución
    transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    // Se crea la matriz de rotación
    Transform3D yAxis = new Transform3D ();
    // Se crea un interpolador, un valor numérico que se ira modificando en tiempo de ejecución
    value = new Alpha (-1, Alpha.INCREASING_ENABLE, 0, 0, 
            tiempoRotation, 0, 0, 0, 0, 0);
    // Se crea el interpolador de rotación, las figuras iran rotando
    RotationInterpolator rotator = new RotationInterpolator (value, transform, yAxis,
        0.0f, (float) Math.PI*2.0f);
    // Se le pone el entorno de activación y se activa
    rotator.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 500.0));
    rotator.setEnable(true);
    // Se cuelga del grupo de transformación y este se devuelve
    transform.addChild(rotator);
    return transform;
  }
    
    private TransformGroup movePlanet (int radio) {
    // Se mueve un planeta hasta el punto donde puede empezar a transladarse
    Transform3D movePlanet = new Transform3D();
    movePlanet.set (new Vector3f (radio, 0.0f, 0.0f));
    TransformGroup transform = new TransformGroup (movePlanet);
    return transform;
    
  }
    
    
    
}
