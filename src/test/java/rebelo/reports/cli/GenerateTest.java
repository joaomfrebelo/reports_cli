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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import picocli.CommandLine;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author João Rebelo
 */
public class GenerateTest {

    /**
     * The xml file test
     */
    public static final String XML_TEST_FILE_NAME = "cli_test.xml";

    /**
     * The xml dir where to put the xml file
     */
    public static final String XML_DIR_TEST_NAME = "xmldir";

    /**
     * The same that you use in the propertie &lt;outputfile&gt; in xlmfile
     */
    public static final String OUTPUT_FILE_PATH = "/sakila_xml.pdf";

    /**
     * The name of the directory for the genetared test reports
     */
    public static final String OUTPUT_DIR = "generated_reports";

    public GenerateTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        Configurator.setLevel(rebelo.reports.cli.Generate.class.getName(), Level.ALL);
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
     * Get the resourse File
     *
     * @return
     */
    public File getResourseFile() {
        return new File(
                getClass().getClassLoader().getResource("./").getFile()
        );
    }

    /**
     * Test of run method, of class Generate.
     */
    @Test
    public void testRun() {
        String[] args = {"--version"};
        Generate tar = new Generate();
        new CommandLine(tar).execute(args);
    }

    public Generate communGeneraFileTest() throws IOException {
        File xmldir = new File(
                getResourseFile().getAbsolutePath()
                        .concat("/").concat(XML_DIR_TEST_NAME)
        );

        if (xmldir.isDirectory() == false) {
            if (xmldir.mkdir() == false) {
                fail(String.format(
                        "It was not possible to create the directory '%s' to execute the tests",
                        xmldir.getAbsolutePath())
                );
            }
        } else {
            for (String name : xmldir.list()) {
                (new File(xmldir.getAbsolutePath().concat("/").concat(name))).delete();
            }
        }

        File orixml = new File(
                getResourseFile().getAbsolutePath().concat("/").concat(XML_TEST_FILE_NAME)
        );

        File testxml = new File(
                xmldir.getAbsolutePath().concat("/").concat(XML_TEST_FILE_NAME)
        );

        Path source = Paths.get(orixml.toURI());
        Path target = Paths.get(testxml.toURI());

        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

        //Create output directory
        File out = new File(
                getResourseFile().getAbsolutePath().concat("/").concat(OUTPUT_DIR)
        );
        if (out.exists() == false) {
            if (out.mkdir() == false) {
                fail(
                        String.format("The test directory '%s' was not created because "
                                + "doesn't have write permitions, please "
                                + "java.lang.SecurityManager#checkWrite",
                                out.getAbsolutePath())
                );
            }
        } else if (out.isDirectory()) {
            for (String name : out.list()) {
                (new File(out.getAbsolutePath().concat("/").concat(name))).delete();
            }
        } else {
            fail(
                    String.format("The test directory '%s' was not created because "
                            + "exist a file with that name ",
                            out.getAbsolutePath())
            );
        }

        Generate gen = new Generate();
        gen.setFile(testxml);
        gen.setDir(null);
        gen.setDelDir(false);
        gen.setDelFile(false);
        gen.setOutputBaseDir(out.getAbsolutePath());
        gen.setJasperFileBaseDir(
                getResourseFile().getAbsolutePath().concat("/").concat("reports/sakila")
        );

        return gen;
    }

    /**
     *
     * @throws java.io.IOException
     */
    @Test
    public void testGenerateFileNodelete() throws IOException {
        try {

            Generate gen = this.communGeneraFileTest();
            gen.setDelDir(false);
            gen.setDelFile(false);
            gen.generateFile();

            File outfile = new File(gen.getOutputBaseDir().concat("/sakila_xml.pdf"));

            if (outfile.exists()) {
                assertTrue(true);
            } else {
                fail(String.format("Report file '%s' not generated", outfile.getAbsolutePath()));
            }

            if (gen.getFile().exists() == false) {
                fail(String.format("XML properties file '%s' deleted when set to not be deleted", gen.getFile().getAbsolutePath()));
            }

        } catch (@SuppressWarnings("UseSpecificCatch") Exception ex) {
            Logger.getLogger(GenerateTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @throws java.io.IOException
     */
    @Test
    public void testGenerateFileDeleteFile() throws IOException {
        try {

            Generate gen = this.communGeneraFileTest();

            gen.setDelDir(false);
            gen.setDelFile(true);

            gen.generateFile();

            File outfile = new File(gen.getOutputBaseDir().concat("/sakila_xml.pdf"));

            if (outfile.exists()) {
                assertTrue(true);
            } else {
                fail(String.format("Report file '%s' not generated", outfile.getAbsolutePath()));
            }

            if (gen.getFile().exists()) {
                fail(String.format("XML properties file '%s' not deleted when set to be deleted", gen.getFile().getAbsolutePath()));
            }

        } catch (@SuppressWarnings("UseSpecificCatch") Exception ex) {
            Logger.getLogger(GenerateTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return @throws IOException
     */
    public Generate communGeneraDirTest() throws IOException {
        File xmldir = new File(
                getResourseFile().getAbsolutePath()
                        .concat("/").concat(XML_DIR_TEST_NAME)
        );
        if (xmldir.isDirectory() == false) {
            if (xmldir.mkdir() == false) {
                fail(String.format(
                        "It was not possible to create the directory '%s' to execute the tests",
                        xmldir.getAbsolutePath())
                );
            }
        } else {
            for (String name : xmldir.list()) {
                (new File(xmldir.getAbsolutePath().concat("/").concat(name))).delete();
            }
        }

        File orixml = new File(
                getResourseFile().getAbsolutePath().concat("/").concat(XML_TEST_FILE_NAME)
        );

        File testxml_1 = new File(
                xmldir.getAbsolutePath().concat("/")
                        .concat("1_").concat(XML_TEST_FILE_NAME)
        );

        File testxml_2 = new File(
                xmldir.getAbsolutePath().concat("/")
                        .concat("2_").concat(XML_TEST_FILE_NAME)
        );

        Path source = Paths.get(orixml.toURI());
        Path target_1 = Paths.get(testxml_1.toURI());
        Path target_2 = Paths.get(testxml_2.toURI());

        Files.copy(source, target_1, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(source, target_2, StandardCopyOption.REPLACE_EXISTING);

        //Create output directory
        File out = new File(
                getResourseFile().getAbsolutePath().concat("/").concat(OUTPUT_DIR)
        );

        if (out.exists() == false) {
            if (out.mkdir() == false) {
                fail(
                        String.format("The test directory '%s' was not created because "
                                + "doesn't have write permitions, please "
                                + "java.lang.SecurityManager#checkWrite",
                                out.getAbsolutePath())
                );
            }
        } else if (out.isDirectory()) {
            for (String name : out.list()) {
                (new File(out.getAbsolutePath().concat("/").concat(name))).delete();
            }
        } else {
            fail(
                    String.format("The test directory '%s' was not created because "
                            + "exist a file with that name ",
                            out.getAbsolutePath())
            );
        }

        Generate gen = new Generate();
        gen.setFile(null);
        gen.setDir(xmldir);
        gen.setDelDir(false);
        gen.setDelFile(false);
        gen.setOutputBaseDir(out.getAbsolutePath());
        gen.setJasperFileBaseDir(
                getResourseFile().getAbsolutePath().concat("/").concat("reports/sakila")
        );

        return gen;
    }

    /**
     *
     * @throws java.io.IOException
     */
    @Test
    public void testGenerateDirNodelete() throws IOException {
        try {

            Generate gen = this.communGeneraDirTest();
            gen.setDelDir(false);
            gen.setDelFile(false);
            ArrayList<File> dirFiles = new ArrayList<>();
            dirFiles.addAll(Arrays.asList(gen.getDir().listFiles()));
            gen.generateDir(dirFiles);

            File outfile = new File(gen.getOutputBaseDir().concat("/sakila_xml.pdf"));

            if (outfile.exists()) {
                assertTrue(true);
            } else {
                fail(String.format("Report file '%s' not generated", outfile.getAbsolutePath()));
            }

            if (gen.getDir().list().length == 0) {
                fail(String.format("The XML properties files in '%s' was deleted when set to not be deleted", gen.getDir().getAbsolutePath()));
            }

        } catch (@SuppressWarnings("UseSpecificCatch") Exception ex) {
            Logger.getLogger(GenerateTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @throws java.io.IOException
     */
    @Test
    public void testGenerateDirDeleteFile() throws IOException {
        try {

            Generate gen = this.communGeneraDirTest();
            gen.setDelDir(false);
            gen.setDelFile(true);
            ArrayList<File> dirFiles = new ArrayList<>();
            dirFiles.addAll(Arrays.asList(gen.getDir().listFiles()));
            gen.generateDir(dirFiles);

            File outfile = new File(gen.getOutputBaseDir().concat("/sakila_xml.pdf"));

            if (outfile.exists()) {
                assertTrue(true);
            } else {
                fail(String.format("Report file '%s' not generated", outfile.getAbsolutePath()));
            }

            if (gen.getDir().list().length > 0) {
                fail(String.format("The XML properties files in '%s' was deleted when set to be deleted", gen.getDir().getAbsolutePath()));
            }

        } catch (@SuppressWarnings("UseSpecificCatch") Exception ex) {
            Logger.getLogger(GenerateTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @throws java.io.IOException
     */
    @Test
    public void testGenerateDirDeleteDir() throws IOException {
        try {

            Generate gen = this.communGeneraDirTest();
            gen.setDelDir(true);
            gen.setDelFile(false);
            ArrayList<File> dirFiles = new ArrayList<>();
            dirFiles.addAll(Arrays.asList(gen.getDir().listFiles()));
            gen.generateDir(dirFiles);

            File outfile = new File(gen.getOutputBaseDir().concat("/sakila_xml.pdf"));

            if (outfile.exists()) {
                assertTrue(true);
            } else {
                fail(String.format("Report file '%s' not generated", outfile.getAbsolutePath()));
            }

            if (gen.getDir().exists()) {
                fail(String.format("The direcytory with the XML properties files in '%s' was not deleted when set to be deleted", gen.getDir().getAbsolutePath()));
            }

        } catch (@SuppressWarnings("UseSpecificCatch") Exception ex) {
            Logger.getLogger(GenerateTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void testCallFile() throws IOException {

        File xmldir = new File(
                getResourseFile().getAbsolutePath()
                        .concat("/").concat(XML_DIR_TEST_NAME)
        );

        if (xmldir.isDirectory() == false) {
            if (xmldir.mkdir() == false) {
                fail(String.format(
                        "It was not possible to create the directory '%s' to execute the tests",
                        xmldir.getAbsolutePath())
                );
            }
        } else {
            for (String name : xmldir.list()) {
                (new File(xmldir.getAbsolutePath().concat("/").concat(name))).delete();
            }
        }

        File orixml = new File(
                getResourseFile().getAbsolutePath().concat("/").concat(XML_TEST_FILE_NAME)
        );

        File testxml = new File(
                xmldir.getAbsolutePath().concat("/").concat(XML_TEST_FILE_NAME)
        );

        Path source = Paths.get(orixml.toURI());
        Path target = Paths.get(testxml.toURI());

        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

        //Create output directory
        File out = new File(
                getResourseFile().getAbsolutePath().concat("/").concat(OUTPUT_DIR)
        );
        if (out.exists() == false) {
            if (out.mkdir() == false) {
                fail(
                        String.format("The test directory '%s' was not created because "
                                + "doesn't have write permitions, please "
                                + "java.lang.SecurityManager#checkWrite",
                                out.getAbsolutePath())
                );
            }
        } else if (out.isDirectory()) {
            for (String name : out.list()) {
                (new File(out.getAbsolutePath().concat("/").concat(name))).delete();
            }
        } else {
            fail(
                    String.format("The test directory '%s' was not created because "
                            + "exist a file with that name ",
                            out.getAbsolutePath())
            );
        }

        String[] args = {
            "-f=" + testxml.getAbsolutePath(),
            "-v=ALL",
            "-o=" + out.getAbsolutePath(),
            "-j=" + getResourseFile().getAbsolutePath().concat("/").concat("reports/sakila")
        };
        Generate tar = new Generate();
        new CommandLine(tar).execute(args);

        File outfile = new File(out.getAbsolutePath().concat("/sakila_xml.pdf"));

        if (outfile.exists()) {
            assertTrue(true);
        } else {
            fail(String.format("Report file '%s' not generated", outfile.getAbsolutePath()));
        }

        if (outfile.exists() == false) {
            fail(String.format("XML properties file '%s' deleted when set to not be deleted", outfile.getAbsolutePath()));
        }

    }

    /**
     *
     */
    @Test
    public void testVerbose() {

        Generate gen = new Generate();

        gen.setVerbose(null);
        assertTrue(gen.getLogLevel().equals(Level.OFF));

        gen.setVerbose("ALL");
        assertTrue(gen.getLogLevel().equals(Level.ALL));

        gen.setVerbose("DEBUG");
        assertTrue(gen.getLogLevel().equals(Level.DEBUG));

    }
    
}
