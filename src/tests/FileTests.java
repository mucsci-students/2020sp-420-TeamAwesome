package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.UMLFileIO;

public class FileTests {
	@Test
	public void fileTest() {
		UMLFileIO fileIOTest = new UMLFileIO();
		assertTrue("Blank instance", fileIOTest.getFile() == null);
		assertFalse("Varible created in fileIO is not set", fileIOTest.fileSet());
		
		int result = fileIOTest.setFile(getTempDir() + "test1.text");
		assertEquals("Made sure file exists.", result,0);
		
		assertTrue("File not Null", fileIOTest.getFile() != null);
		assertTrue("Varible created in fileIO is set", fileIOTest.fileSet());
		assertTrue("File exists", fileIOTest.fileExists());
		String testString = "12ik	fafugh348 ";
		
		
		result = fileIOTest.writeToFile(testString);
		assertEquals("Wrote to file successfully.", result,0);
		Object[] readResult = fileIOTest.readFile();
		assertEquals("Read file equals the test", readResult[0], testString);
		assertEquals("Sucessfully executed", readResult[1], 0);
		
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
