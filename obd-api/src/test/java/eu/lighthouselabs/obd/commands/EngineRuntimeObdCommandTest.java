/*
 * TODO put header 
 */
package eu.lighthouselabs.obd.commands;

import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import eu.lighthouselabs.obd.commands.engine.EngineRuntimeObdCommand;

/**
 * Runtime since engine start in seconds, with a maximum value of 65535.
 */
@PrepareForTest(InputStream.class)
public class EngineRuntimeObdCommandTest {

	private EngineRuntimeObdCommand command;
	private InputStream mockIn;
	
	/**
	 * @throws Exception
	 */
	@BeforeClass
	public void setUp() throws Exception {
		command = new EngineRuntimeObdCommand();
	}
	
	/**
	 * Test for valid InputStream read, 65535 seconds.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testMaximumValueOf65535Seconds() throws IOException {
		// mock InputStream read
		mockIn = createMock(InputStream.class);
		mockIn.read();
		expectLastCall().andReturn(0x41);
		expectLastCall().andReturn(0x31);
		expectLastCall().andReturn(0xFF);
		expectLastCall().andReturn(0xFF);
		expectLastCall().andReturn(0x13);
		expectLastCall().andReturn(0x3E); // '>'
		
		replayAll();
		
		// call the method  to test
		command.readResult(mockIn);
		assertEquals(command.getFormattedResult(), "18:12:15");
		
		verifyAll();
	}
	
	/**
	 * Test for valid InputStream read, 67 seconds
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSomeValue() throws IOException {
		// mock InputStream read
		mockIn = createMock(InputStream.class);
		mockIn.read();
		expectLastCall().andReturn(0x41);
		expectLastCall().andReturn(0x31);
		expectLastCall().andReturn(0x45);
		expectLastCall().andReturn(0x43);
		expectLastCall().andReturn(0x13);
		expectLastCall().andReturn(0x3E); // '>'
		
		replayAll();
		
		// call the method  to test
		command.readResult(mockIn);
		assertEquals(command.getFormattedResult(), "04:55:31");
		
		verifyAll();
	}
	
	/**
	 * Test for valid InputStream read, 0 seconds.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testMinimumValueOf0Seconds() throws IOException {
		// mock InputStream read
		mockIn = createMock(InputStream.class);
		mockIn.read();
		expectLastCall().andReturn(0x41);
		expectLastCall().andReturn(0x31);
		expectLastCall().andReturn(0x00);
		expectLastCall().andReturn(0x00);
		expectLastCall().andReturn(0x13);
		expectLastCall().andReturn(0x3E); // '>'
		
		replayAll();
		
		// call the method  to test
		command.readResult(mockIn);
		assertEquals(command.getFormattedResult(), "00:00:00");
		
		verifyAll();
	}
	
	/**
	 * Clear resources.
	 */
	@AfterClass
	public void tearDown() {
		command = null;
		mockIn = null;
	}
	
}