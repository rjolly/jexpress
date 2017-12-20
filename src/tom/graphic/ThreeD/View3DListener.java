package tom.graphic.ThreeD;

import java.util.EventListener;

// Referenced classes of package tom.graphic.ThreeD:
//			View3DEvent

public interface View3DListener
	extends EventListener {

	public abstract void elemSelected(View3DEvent view3devent);
}
