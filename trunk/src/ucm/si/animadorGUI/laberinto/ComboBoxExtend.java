/**
 * 
 */
package ucm.si.animadorGUI.laberinto;

import java.util.Set;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import ucm.si.Checker.util.StateAndLabel;

/**
 * @author usuario_local
 *
 */
public class ComboBoxExtend<S> extends JComboBox{
	
	private JComboBox combo;
	private Vector<StateAndLabel<S>> items;

	public ComboBoxExtend(Vector<StateAndLabel<S>> items) {
		
		Vector<String> vaux = new Vector<String>();
		for(int i = 0; i< items.size(); i++)
		{
			vaux.add(items.get(i).getLabel());
		}
		this.items = items;
		this.combo = new JComboBox(vaux);
		
	}

	public void pepe()
	{
		this.combo.getSelectedItem();
		this.combo.addItem(null);
	}

	/*@Override
	public void addItem(Object anObject) {
		
		this.combo.addItem(anObject);
	}

	@Override
	public int getSelectedIndex() {
		// TODO Auto-generated method stub
		return super.getSelectedIndex();
	}*/

	@Override
	public S getSelectedItem() {
		int i = this.combo.getSelectedIndex();
		
		return this.items.get(i).getState();
	}


	@Override
	public void addItem(Object anObject) {
		items.add((StateAndLabel<S>) anObject);
	}

	@Override
	public void removeAllItems() {
		items=new Vector<StateAndLabel<S>>(1);
	}
	
	

}
