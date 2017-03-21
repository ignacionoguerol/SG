/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.sun.j3d.utils.geometry.Primitive;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

/**
 *
 * @author fvelasco
 */
class TheScene extends BranchGroup {
  /// La rama de donde cuelga la figura que se cambia
  private BranchGroup figure;
  /// El objeto que controla la rotación continua de la figura
  private Alpha value;
  
  TheScene () { 
    // Se crea la transformación para la rotación
    TransformGroup rotation = createRotation ();
    
    // Se crea la rama con una figura
    figure = createScene ();
    
    // Y se cuelga de la rotación
    rotation.addChild (figure);
    
    // Se cuelga rotación de la escena
    this.addChild(rotation);
  }
  
  private TransformGroup createRotation () {
    // Se crea el grupo que contendrá la transformación de rotación
    // Todo lo que cuelgue de él rotará
    TransformGroup transform = new TransformGroup ();
    // Se le permite que se cambie en tiempo de ejecución
    transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    // Se crea la matriz de rotación
    Transform3D yAxis = new Transform3D ();
    // Se crea un interpolador, un valor numérico que se ira modificando en tiempo de ejecución
    value = new Alpha (-1, Alpha.INCREASING_ENABLE, 0, 0, 
            4000, 0, 0, 0, 0, 0);
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
  
  private BranchGroup createScene () {
    // Se crea la rama desde la que cuelga todo
    BranchGroup bg = new BranchGroup ();
    
    // Se le dan permisos para poder intercambiar las figuras
    bg.setCapability(Group.ALLOW_CHILDREN_EXTEND);
    bg.setCapability(Group.ALLOW_CHILDREN_WRITE);
    
    // Y le ponemos una figura
    bg.addChild(PrimitiveBranches.Cube.getPrimitiveBranch());
    
    return bg;
  }
  /*
  void setAppearance (Appearances ap) {  
    // Cambia el material de todas las primitivas 
    //   Cuando se cambien en la escena ya tienen el material cambiado
    for (PrimitiveBranches pb : PrimitiveBranches.values()) {
      ((Primitive) pb.getPrimitiveBranch().getChild(0)).setAppearance(ap.getAppearance());
    }
  }
  
  void setPrimitive (PrimitiveBranches pb) {
    // Se quita la figura actual y se pone la indicada
    figure.removeChild(0);
    figure.addChild(pb.getPrimitiveBranch());
  }
  
  void setRotationOnOff (boolean onOff) {
    if (onOff)
      value.resume();
    else
      value.pause();
  }*/
}
