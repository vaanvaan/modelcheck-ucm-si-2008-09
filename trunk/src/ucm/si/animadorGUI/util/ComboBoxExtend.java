/**
 * 
 */
package ucm.si.animadorGUI.util;

import java.util.Set;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import ucm.si.Checker.util.StateAndLabel;

/**
 * @author usuario_local
 *
 */
public class ComboBoxExtend<D> extends JComboBox{
	
	//private JComboBox combo;
	//private Vector<StateAndLabel<S>> items;

	public ComboBoxExtend(Vector<D> items) {
		super(items);
		/*Vector<String> vaux = new Vector<String>();
		for(int i = 0; i< items.size(); i++)
		{
			super.addItem(items.get(i).getLabel());
		}
		this.items = items;*/
                //this.
		//combo = new JComboBox(vaux);
		
	}

	public void pepe()
	{
		//this.combo.getSelectedItem();
		//this.combo.addItem(null);
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
	public D getSelectedItem() {
		/*int i = this.getSelectedIndex();
		S s = this.items.get(i).getState();
		return s;*/
            if(super.getSelectedItem().getClass().equals(String.class))
            {
                return null;
            }
            
            return (D) super.getSelectedItem();
	}
	
	

}
