import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import core.UMLFileIO;

public class FileTest {
	@Test
	public void fileTest() {
		UMLFileIO fileIOTest = new UMLFileIO();
		assertTrue("Blank instance", fileIOTest.getFile() == null);
		assertFalse("Varible created in fileIO is not set", fileIOTest.fileSet());
		
		int result = fileIOTest.setFile("test1.text");
		assertEquals("Made sure file exists.", 0, result);
		
		assertTrue("File not Null", fileIOTest.getFile() != null);
		assertTrue("Varible created in fileIO is set", fileIOTest.fileSet());
		assertTrue("File exists", fileIOTest.fileExists());
		String testString = "12ik	fafugh348 ";
		
		
		result = fileIOTest.writeToFile(testString);
		assertEquals("Wrote to file successfully.", 0, result);
		Object[] readResult = fileIOTest.readFile();
		assertEquals("Read file equals the test", testString, readResult[0]);
		assertEquals("Sucessfully executed", 0, readResult[1]);
		
		// Clean up created file
		fileIOTest.getFile().delete();
	}
}
