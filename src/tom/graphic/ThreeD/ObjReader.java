package tom.graphic.ThreeD;

import java.io.IOException;
import java.io.InputStream;

// Referenced classes of package tom.graphic.ThreeD:
//			Object3D

public interface ObjReader {

	public abstract boolean checkIfTypeIsKnow(InputStream inputstream, String s);

	public abstract void constructFromStream(Object3D object3d, InputStream inputstream, String s) throws IOException;
}
