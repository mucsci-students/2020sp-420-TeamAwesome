// Package name
package views.components.testable;

// System imports
import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Mock JFileChooser for testing.
 * @author ryan
 *
 */
public class TestableFileChooser extends JFileChooser  {
	private static final long serialVersionUID = 1L;
	
	// File to be return when asked
	private File returnFile;
	
	/**
	 * Initialize instance of testable JFileChooser
	 * @param returnFile - the file the test want's to get
	 */
	public TestableFileChooser(File returnFile) {
		this.returnFile = returnFile;
	}
	
	@Override
	public void setDialogTitle(String title) {}
	
	@Override
	public void setFileFilter(FileFilter f) {}
	
	@Override
	public int showSaveDialog(Component parent) {
		return JFileChooser.APPROVE_OPTION;
	}
	
	@Override
	public int showOpenDialog(Component parent) {
		return JFileChooser.APPROVE_OPTION;
	}
	
	@Override
	public File getSelectedFile() {
		return returnFile;
	}
}
