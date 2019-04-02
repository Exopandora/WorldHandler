package exopandora.worldhandler.installer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import exopandora.worldhandler.Main;

public class ChangeFolderListener implements ActionListener
{
	private final JTextField textField;
	
	public ChangeFolderListener(JTextField textField)
	{
		this.textField = textField;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		JFileChooser fileChooser = new JFileChooser();
		File inital = new File(this.textField.getText());
		
		if(inital.exists())
		{
			fileChooser.setCurrentDirectory(inital);
		}
		else
		{
			fileChooser.setCurrentDirectory(Main.getInitialDirectory());
		}
		
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.showOpenDialog(null);
		
		File file = fileChooser.getSelectedFile();
		
		if(file != null)
		{
			this.textField.setText(file.getAbsolutePath());
		}
	}
}
