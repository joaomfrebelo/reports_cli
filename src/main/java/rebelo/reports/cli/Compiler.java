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
import java.util.concurrent.Callable;
import org.apache.logging.log4j.Level;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import javax.validation.constraints.NotNull;
import net.sf.jasperreports.engine.JRException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
/**
 *
 * @author João Rebelo
 */
@Command(
        name = "Rebelo Reports CLI - Revert jasper file to jrxml",
        description = "Revert jasper file to jrxml",
        mixinStandardHelpOptions = true,
        version = ""
)
public class Compiler  implements Callable<Boolean>{

     /**
     * Logger instance
     */
    protected static final Logger LOG = LogManager.getLogger();

    /**
     * Only for argument porpouse
     */
     @Option(
            names = {"compiler"},
            description = "us ethe compiler class"
    )
    private boolean compiler;
    
    /**
     * The jasper file compiled to revert
     */
    @Option(
            names = {"-in"},
            description = "The jasper compilte file path to revert"
    )
    private File inFile;

    /**
     * The output jrxml file
     */
    @Option(
            names = {"-out"},
            description = "The jrxml output file"
    )
    private File outFile;

    /**
     * The log level
     */
    @Option(
            names = {"-v", "-verbose"},
            description = "The log level, can be: OFF, FATAL, ERROR, WARN, INFO, DEBUG, TRACE, ALL"
    )
    private String verbose;

    /**
     * The compiler class
     */
    private final rebelo.reports.core.compiler.Compiler jrcompile = new rebelo.reports.core.compiler.Compiler();
    
    /**
     * Revert
     */
    public Compiler() {
        if (null != this.verbose) {
            Compiler.setLogLevel(Level.valueOf(verbose));
        }
    }

    /**
     *
     * Set the log level
     *
     * @param logLevel
     */
    public static void setLogLevel(@NotNull Level logLevel) {
        Configurator.setLevel(
                rebelo.reports.cli.Generate.class.getName(),
                logLevel
        );
    }

    /**
     * Get the jasper compiled file
     * @return 
     */
    public File getInFile() {
        return inFile;
    }

    /**
     * Set the jasper compiled file
     * @param inFile 
     */
    public void setInFile(File inFile) {
        this.inFile = inFile;
    }

    /**
     * Get the output jrxml file
     * @return 
     */
    public File getOutFile() {
        return outFile;
    }

    /**
     * Set the output jrxml file 
     * @param outFile 
     */
    public void setOutFile(File outFile) {
        this.outFile = outFile;
    }
       
    @Override
    public Boolean call() throws Exception {
        String in = getInFile().getName();
        String out = getOutFile().getName();
        boolean a = getInFile().getName().endsWith(".jrxml");
        boolean b = getOutFile().getName().endsWith(".jasper");
        
        if(getInFile().getName().endsWith(".jasper")){
            if(getOutFile().getName().endsWith(".jrxml")){
                this.reverJasperFile();
            }else{
                throw new Exception("For a input file '.jasper' the output file must be '.jrxml'");
            }
            return true;
        }
        
        if(getInFile().getName().endsWith(".jrxml")){
            if(getOutFile().getName().endsWith(".jasper")){
                this.compileJrxmlFile();
            }else{
                throw new Exception("For a input file '.jrxml' the output file must be '.jasper'");
            }
            return true;
        }
        
        throw new Exception("Input file shoud be '.jrxml' and output file '.jasper' or "
                + "Input file shoud be '.jasper' and output file '.jrxml'");        
    }

    /**
     * Revert a jasper compiled file to a jrxml file
     * @throws JRException 
     */
    public void reverJasperFile() throws JRException{
        jrcompile.RevertJasperToJrxml(getInFile(), getOutFile());
    }
        
    /**
     * Compile a jrxml file to jasper file
     * @throws JRException 
     */
    public void compileJrxmlFile() throws JRException{
        jrcompile.CompileJrxmlToJasper(getInFile(), getOutFile());
    }
    
}
