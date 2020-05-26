/**
 * Copyright (C) 2019  João M F Rebelo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package rebelo.reports.cli;

import java.io.File;
import net.sf.jasperreports.engine.JRException;
import org.apache.logging.log4j.Level;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import picocli.CommandLine;

/**
 *
 * @author João Rebelo
 */
public class CompilerTest {
    
    public CompilerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInFile method, of class Compiler.
     */
    @Test
    public void testSetGetInFile() {
        System.out.println("SetGetInFile");
        Compiler comp = new Compiler();
        File expResult = new File("file");
        comp.setInFile(expResult);
        File result = comp.getInFile();
        assertEquals(expResult.getPath(), result.getPath());
    }

    /**
     * Test of getOutFile method, of class Compiler.
     */
    @Test
    public void testSetGetOutFile() {
        System.out.println("SetGetOutFile");
        Compiler comp = new Compiler();
        File expResult = new File("file");
        comp.setOutFile(expResult);
        File result = comp.getOutFile();
        assertEquals(expResult.getPath(), result.getPath());
    }

    /**
     * Test of call method, of class Compiler.
     */
    @Test
    public void testCall() throws Exception {
        System.out.println("call");
        String resdir = getClass().getClassLoader().getResource("./reports/sakila/").getPath();
        File jrxmlFile = new File(resdir + "sakila.jrxml");
        File compFile = new File(resdir + "compiled.jasper");
        File revertedFile = new File(resdir + "reverted.jrxml");
        
        if(compFile.exists()){
            compFile.delete();
        }
        
        if(revertedFile.delete()){
            revertedFile.delete();
        }
        
        String[] argsJrxmlToJasper = {
            "compiler"
           , "-in="+ jrxmlFile.getAbsolutePath()  
           , "-out=" +compFile.getAbsolutePath()
        };
        Compiler comp = new Compiler();
        new CommandLine(comp).execute(argsJrxmlToJasper);
        
         if(compFile.exists() == false){
            fail("Failed to compile the file");
        }
        
         String[] argsJasperToJrxml = {
            "compiler"
           , "-in="+compFile.getAbsolutePath() 
           , "-out="+revertedFile.getAbsolutePath()
        };
        Compiler comprev = new Compiler();
        new CommandLine(comprev).execute(argsJasperToJrxml);
         
        if(revertedFile.exists() == false){
            fail("Failed to revert file the file");
        }
    }

    /**
     * Test of CompileJrxmlFile and reverJasperFile method, of class Compiler.
     */
    @Test
    public void testCompileJrxmlFileAndReverJasperFile() throws JRException{
        String resdir = getClass().getClassLoader().getResource("./reports/sakila/").getPath();
        File jrxmlFile = new File(resdir + "sakila.jrxml");
        File compFile = new File(resdir + "compiled.jasper");
        File revertedFile = new File(resdir + "reverted.jrxml");

        if(compFile.exists()){
            compFile.delete();
        }
        
        if(revertedFile.delete()){
            revertedFile.delete();
        }
        
        Compiler comp = new Compiler();
        comp.setInFile(jrxmlFile);
        comp.setOutFile(compFile);
        comp.compileJrxmlFile();
        
        if(compFile.exists() == false){
            fail("Failed to compile the file");
        }
        
        comp.setInFile(compFile);
        comp.setOutFile(revertedFile);
        comp.reverJasperFile();
        
        if(revertedFile.exists() == false){
            fail("Failed to revert file the file");
        }
        
    }
    
}
