
package Model;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import javax.media.j3d.BranchGroup;

/**
 *
 * @author fvelasco
 */

/// Un enumerado para las distintas figuras de la aplicación
public enum PrimitiveBranches { 
  Cube (Internal.Cube), 
  Ball (Internal.Ball);
  
  private enum Internal { Cube, Ball };

  private final BranchGroup bg;
  
  private PrimitiveBranches (Internal i) {
    // Se crea la rama
    bg = new BranchGroup ();
    // Le permitimos que se quite de su padre
    //     De esta forma se sustituirá por otra figura
    bg.setCapability(BranchGroup.ALLOW_DETACH);
    switch (i) {
      case Cube :
        // Se crea el cubo
        Box aBox = new Box (3.0f, 3.0f, 3.0f,
            Primitive.GENERATE_NORMALS | 
            Primitive.GENERATE_TEXTURE_COORDS |
            Primitive.ENABLE_APPEARANCE_MODIFY, 
            Appearances.Color.getAppearance());

        // Se añade a la rama de la figura
        bg.addChild(aBox);
        break;
        
      case Ball :
        // Lo mismo para una esfera
        bg.addChild(new Sphere (4.0f, 
        Primitive.GENERATE_NORMALS | 
        Primitive.GENERATE_TEXTURE_COORDS |
        Primitive.ENABLE_APPEARANCE_MODIFY, 64, 
        Appearances.Color.getAppearance()));
        break;
    }
  }
  
  public BranchGroup getPrimitiveBranch () {
    return bg;
  }
}
