/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasolar;

import javax.media.j3d.BranchGroup;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.media.j3d.Texture;
import javax.vecmath.Vector3f;

/**
 *
 * @author carlos
 */
public class Astro extends BranchGroup{
  
  /// La rama de donde cuelga la figura que se cambia
  private BranchGroup figure;
  /// El objeto que controla la rotación continua de la figura
  private Alpha value;
  
  Astro (int tiempo_rot, int diametro) { 
    // Se crea la transformación para la rotación
    TransformGroup rotation = createRotation (tiempo_rot);
    
    // Se crea la rama desde la que cuelga todo
    BranchGroup bg = new BranchGroup ();
    
    // Se le dan permisos para poder intercambiar las figuras
    bg.setCapability(Group.ALLOW_CHILDREN_EXTEND);
    bg.setCapability(Group.ALLOW_CHILDREN_WRITE);
    
    Appearance app = new Appearance ();
    Texture texture = new TextureLoader ("imgs/tierra.jpg", null).getTexture();
    app.setTexture (texture);
    
    bg.addChild(new Sphere (4.0f, 
        Primitive.GENERATE_NORMALS | 
        Primitive.GENERATE_TEXTURE_COORDS |
        Primitive.ENABLE_APPEARANCE_MODIFY, 64, app));
    
    // Y se cuelga de la rotación
    rotation.addChild (bg);
    
    // Se crea la transformación para mover el planeta
    TransformGroup mover = movePlanet (diametro);
    
    //Se cuelga la rotación de este movimiento
    mover.addChild(rotation);
    
    // Se cuelga rotación de la escena
    this.addChild(mover);
  }
  
  private TransformGroup createRotation (int tiempo_rot) {
    // Se crea el grupo que contendrá la transformación de rotación
    // Todo lo que cuelgue de él rotará
    TransformGroup transform = new TransformGroup ();
    // Se le permite que se cambie en tiempo de ejecución
    transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    // Se crea la matriz de rotación
    Transform3D yAxis = new Transform3D ();
    // Se crea un interpolador, un valor numérico que se ira modificando en tiempo de ejecución
    value = new Alpha (-1, Alpha.INCREASING_ENABLE, 0, 0, 
            tiempo_rot, 0, 0, 0, 0, 0);
    // Se crea el interpolador de rotación, las figuras iran rotando
    RotationInterpolator rotator = new RotationInterpolator (value, transform, yAxis,
        0.0f, (float) Math.PI*2.0f);
    // Se le pone el entorno de activación y se activa
    rotator.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 100.0));
    rotator.setEnable(true);
    // Se cuelga del grupo de transformación y este se devuelve
    transform.addChild(rotator);
    return transform;
  }
  
  /*private TransformGroup createTranslation (int tiempo_rot) {
    // Se crea el grupo que contendrá la transformación de translacion
    
  }*/
  
  private TransformGroup movePlanet (int diametro) {
    // Se mueve un planeta hasta el punto donde puede empezar a transladarse
    
    Transform3D movePlanet = new Transform3D();
    movePlanet.set (new Vector3f (diametro, 0.0f, 0.0f));
    TransformGroup transform = new TransformGroup (movePlanet);
    return transform;
    
  }
  
    
}
