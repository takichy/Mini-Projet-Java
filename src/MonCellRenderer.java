import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
 
public class MonCellRenderer extends DefaultTableCellRenderer 
{
 
	public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column)
		{
 
			Component cell = super.getTableCellRendererComponent(table, value,isSelected, hasFocus, row, column);
 			
			if ( !isSelected && value =="Présent")
				cell.setBackground(new Color(0, 204, 0));
			else if ( !isSelected && value == "absent")
				cell.setBackground(new Color(255, 77, 77));
			else if ( !isSelected && value == "redoublent")
				cell.setBackground(new Color(255, 77, 77));
			else if ( !isSelected && value == "Validé")
				cell.setBackground(new Color(0, 204, 0));
			else cell.setBackground(Color.WHITE);
			return cell;
		}
}