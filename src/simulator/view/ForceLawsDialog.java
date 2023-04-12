package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.JTextArea;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.JSONObject;

import simulator.control.Controller;

public class ForceLawsDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controller _ctrl;
	private JSONTableModel _dataTable;
	private int _commit; 
	private int selectedLawIndex;
	
	
	//nested class that stores the values for the table in type JSON
	private class JSONTableModel extends AbstractTableModel{

	
		private static final long serialVersionUID = 1L;
		private String[][] data;   
		private String[] header = {"Key", "Value", "Description"};
		private String _type;
		
		
		public JSONTableModel() {
			update(0);
		}


		
		//updates the values of the table for the value of selected forcelaw
		private void update(int i) {
			
			JSONObject law = _ctrl.getForceLawsInfo().get(i);
			_type = law.getString("type");
			JSONObject dat = law.getJSONObject("data");
			Set<String> keys = dat.keySet();
			data = new String[keys.size()][3];
			int j = 0;
			for (String key: keys) {
				data[j][0] = key;
				data[j][2] = dat.getString(key);
				j++;
			}
			fireTableStructureChanged();
		}
		
		
		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public int getColumnCount() {
			return header.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
				return data[rowIndex][columnIndex];
		}
		
		@Override
		public void setValueAt(Object o, int rowIndex, int columnIndex) {
			data[rowIndex][columnIndex] = o.toString();
		}
		
		@Override
		public String getColumnName(int column) {
			return header[column];
		}


		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			boolean is = false;
			if (columnIndex == 1) {
				is = true;
			}
			return is;
		}


		public String getData() {
				StringBuilder s = new StringBuilder();
				s.append('{');
				for (int i = 0; i < data.length; i++) {
					if (!data[i][0].isEmpty() && !data[i][2].isEmpty()) {
						s.append('"');
						s.append(data[i][0]);
						s.append('"');
						s.append(':');
						s.append(data[i][1]);
						s.append(',');
					}
				}

				if (s.length() > 1)
					s.deleteCharAt(s.length() - 1);
				s.append('}');

				return s.toString();
			}


		
		
		
		
	}
	
	
	
	public ForceLawsDialog(Controller controller, Frame frame) {
		super(frame, true);
		this._ctrl = controller;
		initGUI();
	}
	
	private void initGUI() {
		
		setTitle("Force Laws Selection");
		_commit = 0;
		JPanel mainPanel = new JPanel(new BorderLayout()); //main panel where everything goes
		JPanel topPanel = new JPanel(new BorderLayout()); //panel where description an table go
		JPanel botPanel = new JPanel(new BorderLayout()); //panel where spiner and buttons go
		
		JTextArea fctext  = new JTextArea();
		String[] forceLawsDesc = new String[_ctrl.getForceLawsInfo().size()]; //LIST WITH DESCRIPTIONS OF THE FORCE LAWS
		
		
		//TEXT AREA W/ DESCRIPTION OF THE DIALOG
		fctext.setText(  "Select a force law and provide values for the parameters in the  Value column (default values are used for parameters with no value)." );
		
		
		
		
		
		//SPINNER FOR SELECTING FORCE LAWS
		//(1) add force laws descriptions to the list
		for(int i = 0; i < _ctrl.getForceLawsInfo().size(); i++) {
			forceLawsDesc[i] = _ctrl.getForceLawsInfo().get(i).getString("desc");
		}
		//(2) spinner value selected is stored in lawSelected
		JLabel fclabel = new JLabel("Force law: ");
		JComboBox<String> box = new JComboBox<String>(forceLawsDesc);
		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedLawIndex = box.getSelectedIndex();
				_dataTable.update(selectedLawIndex);
				
			}});

		
		//DATA TABLE
		
		_dataTable = new JSONTableModel();
		JTable dataTable = new JTable(_dataTable) {

			private static final long serialVersionUID = 1L;
			
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				
				return component;
				
			}
			};
			
			JScrollPane tabelScroll = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			tabelScroll.setPreferredSize(new Dimension(100, 100));
		
			
			
			
			//OK / CANCEL BUTTS
			JPanel buttonsPanel = new JPanel();
			buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);

			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					_commit = 0;
					ForceLawsDialog.this.setVisible(false);
				}
			});
			buttonsPanel.add(cancelButton);
			
			
			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JSONObject a = new JSONObject();
					a.put("type", (_ctrl.getForceLawsInfo().get(selectedLawIndex)).getString("type"));
					a.put("data", new JSONObject(_dataTable.getData()));
				
					_commit = 1;
					ForceLawsDialog.this.setVisible(false);
					
					try{
					_ctrl.setForcesLaws(a);
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			buttonsPanel.add(okButton);
			
			
			//ADD cCOMPONENTS INTO PANELS
			topPanel.add(fctext, BorderLayout.PAGE_START);
			topPanel.add(tabelScroll, BorderLayout.PAGE_END);
			botPanel.add(box, BorderLayout.PAGE_START );
			botPanel.add(buttonsPanel, BorderLayout.PAGE_END);
			mainPanel.add(topPanel, BorderLayout.PAGE_START);
			mainPanel.add(botPanel, BorderLayout.PAGE_END);
			
			mainPanel.setPreferredSize(new Dimension(400, 250));

			setContentPane(mainPanel);
			
			pack();
			setResizable(true);// change to 'true' if you want to allow resizing
			setVisible(false); // we will show it only when open is called
			
		
	}

	
	
	public int open() {

		if (getParent() != null)
			setLocation(//
					getParent().getLocation().x + getParent().getWidth() / 2 - getWidth() / 2, //
					getParent().getLocation().y + getParent().getHeight() / 2 - getHeight() / 2);
		pack();
		setVisible(true);
		return _commit;
	}
	
	
	
	

	public JSONObject getJSON() {
		JSONObject a = new JSONObject();
		
		a.put("type",_ctrl.getForceLawsInfo().get(selectedLawIndex).getString("type"));
		a.put("data", new JSONObject(_dataTable.getData()));
	
		
		return a;
		}
	

}
