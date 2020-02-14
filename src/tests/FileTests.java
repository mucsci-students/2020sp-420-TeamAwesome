package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import resources.UMLFileIO;

public class FileTests {
	@Test
	public void fileTest() {
		UMLFileIO fileIOTest = new UMLFileIO();
		assertTrue("Blank instance", fileIOTest.getFile() == null);
		assertFalse("Varible created in fileIO is not set", fileIOTest.fileSet());
		
		try {
			fileIOTest.setFile(getTempDir() + "test1.text");
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue("File not Null", fileIOTest.getFile() != null);
		assertTrue("Varible created in fileIO is set", fileIOTest.fileSet());
		assertTrue("File exists", fileIOTest.fileExists());
		String testString = "12ik	fafugh348 ";
		try {
			fileIOTest.writeToFile(testString);
			assertEquals("Read file equals the test", fileIOTest.readFile(), testString);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		// Clean up created file
		fileIOTest.getFile().delete();
	}

	/**
	 * Get the system temp directory for temporary file creation
	 * @return - String of absolute path to temp directory
	 */
	public static String getTempDir() {
		String prop = "java.io.tmpdir";
		String tempDir = System.getProperty(prop);
		return tempDir;
	}
}
