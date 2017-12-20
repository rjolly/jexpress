package tom.graphic.DataView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.PrintStream;
import java.util.EventObject;
import javax.infobus.InfoBus;
import javax.infobus.InfoBusDataConsumer;
import javax.infobus.InfoBusEvent;
import javax.infobus.InfoBusItemAvailableEvent;
import javax.infobus.InfoBusItemRevokedEvent;
import javax.infobus.InfoBusMember;
import javax.infobus.InfoBusMemberSupport;
import tom.graphic.ThreeD.View3D;
import tom.graphic.ThreeD.View3DCommon;
import tom.graphic.ThreeD.World3D;

// Referenced classes of package tom.graphic.DataView:
//			DataView, IBArray

public class IBDataView extends DataView
	implements InfoBusMember, InfoBusDataConsumer {

	private InfoBusMemberSupport m_ibmImpl;

	private void initCommon() {
		m_ibmImpl = new InfoBusMemberSupport(this);
	}

	public IBDataView() {
		initCommon();
	}

	public InfoBus getInfoBus() {
		return m_ibmImpl.getInfoBus();
	}

	public void setInfoBus(InfoBus b) throws PropertyVetoException {
		m_ibmImpl.setInfoBus(b);
	}

	public void addInfoBusVetoableListener(VetoableChangeListener l) {
		m_ibmImpl.addInfoBusVetoableListener(l);
	}

	public void removeInfoBusVetoableListener(VetoableChangeListener l) {
		m_ibmImpl.removeInfoBusVetoableListener(l);
	}

	public void addInfoBusPropertyListener(PropertyChangeListener l) {
		m_ibmImpl.addInfoBusPropertyListener(l);
	}

	public void removeInfoBusPropertyListener(PropertyChangeListener l) {
		m_ibmImpl.removeInfoBusPropertyListener(l);
	}

	public void dataItemRevoked(InfoBusItemRevokedEvent e) {
		String itemName = e.getDataItemName();
		System.out.println("InfoBusItemRevoked: { " + itemName + " } from " + e.getSource().toString() + "\n");
	}

	public void propertyChange(PropertyChangeEvent propertychangeevent) {
	}

	public void dataItemAvailable(InfoBusItemAvailableEvent e) {
		System.out.println("Data item available" + e);
		if (e.getDataItemName().equals("DataView")) {
			System.out.println("DataView...");
			IBArray array = new IBArray();
			array.dataItemAvailable(e.requestDataItem(this, null), null);
			super.view.world().remObj(super.obj);
			super.obj = getCurrentRepr(array);
			super.view.world().addObj(super.obj);
		}
	}
}
