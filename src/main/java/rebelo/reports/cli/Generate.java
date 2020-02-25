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
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.xml.bind.JAXBException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.xml.sax.SAXException;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import rebelo.reports.core.NullNotAllowedException;
import rebelo.reports.core.PrinterNotFoundException;
import rebelo.reports.core.RRException;
import rebelo.reports.core.RRProperties;
import rebelo.reports.core.RRPropertiesException;
import rebelo.reports.core.Report;
import rebelo.reports.core.datasource.DataSourceException;
import rebelo.reports.core.parse.ParseException;
import rebelo.reports.core.parse.ParseXML;
import rebelo.reports.core.sign.SignPdfException;

/**
 *
 * @author João Rebelo
 */
@Command(
        name = "Rebelo Reports CLI",
        description = "Run Rebelo Reports from CLI",
        mixinStandardHelpOptions = true,
        version = ""
)
public final class Generate implements Callable<Integer> {

    /**
     * Logge instance
     */
    protected static final Logger LOG = LogManager.getLogger();

    /**
     * The xml file conteined the properties of the report to be generated
     */
    @Option(
            names = {"-f", "--file"},
            description = "Create the report from one xml file"
    )
    private File file;

    /**
     * The directory conteined all xml files containing the properties of the
     * report to be generated.<br>
     * All reports, one of each xml file will be exported in only one report,
     * one per page
     */
    @Option(
            names = {"-d", "--dir"},
            description = "The directory with all xml files "
            + "containing the properties of the report to be generated. "
            + "All reports, one of each xml file will be exported in only "
            + "one report, one per page"
    )
    private File dir;

    /**
     * If is true the xml file will be deleted after report generator
     */
    @Option(
            names = {"-e", "--erase"},
            description = "Delete the file or all files in the directory "
            + "after the report "
            + "hase been generated "
    )
    private boolean delFile;

    /**
     * If is true the directory will be deleted
     */
    @Option(
            names = {"-E", "--Erase"},
            description = "Delete the directory "
            + "after the report "
            + "hase been generated, only works with -d option "
    )
    private boolean delDir;

    /**
     * Output file base dir. The base dir for output file report if not null it
     * will be preppend
     */
    @Option(
            names = {"-o", "-out"},
            description = "The path to pre append to the output file "
            + "path defined in the xml file"
    )
    private String outputBaseDir = null;

    /**
     * Jaster report file base dir. The base dir for the jasper report file if
     * not null it will be preppend
     */
    @Option(
            names = {"-j", "-jasper"},
            description = "The path to pre append to the jasper file "
            + "path defined in the xml file"
    )
    private String jasperFileBaseDir = null;
    /**
     * The log level
     */
    @Option(
            names = {"-v", "-verbose"},
            description = "The log level, can be: OFF, FATAL, ERROR, WARN, INFO, DEBUG, TRACE, ALL"
    )
    private String verbose;

    /**
     * Generate
     */
    public Generate() {
        if (null != this.verbose) {
            this.setLogLevel(Level.valueOf(verbose));
        }
    }

    /**
     *
     * Get xml properties report file
     *
     * @return
     */
    @Null
    public File getFile() {
        return file;
    }

    /**
     *
     * Set xml properties report file
     *
     * @param file
     */
    public void setFile(@Null File file) {
        this.file = file;
    }

    /**
     * Get the directory that have the xml properties report files
     *
     * @return
     */
    @Null
    public File getDir() {
        return dir;
    }

    /**
     * Set the directory that have the xml properties report files
     *
     * @param dir
     */
    public void setDir(@Null File dir) {
        this.dir = dir;
    }

    /**
     * Define if the file is to be deleted
     *
     * @return
     */
    @NotNull
    public boolean getDelFile() {
        return delFile;
    }

    /**
     * Define if the file is to be deleted
     *
     * @param delFile
     */
    public void setDelFile(@NotNull boolean delFile) {
        this.delFile = delFile;
    }

    /**
     * Define if the directory is to be deleted
     *
     * @return
     */
    @NotNull
    public boolean getDelDir() {
        return delDir;
    }

    /**
     * Define if the directory is to be deleted
     *
     * @param delDir
     */
    public void setDelDir(@NotNull boolean delDir) {
        this.delDir = delDir;
    }

    /**
     * Get the path that is to be pre append to the output file path, if null
     * none will be pre append
     *
     * @return
     */
    @Null
    public String getOutputBaseDir() {
        return outputBaseDir;
    }

    /**
     * Set the path that is to be pre append to the output path, if null none
     * will be pre append
     *
     * @param outputBaseDir
     */
    public void setOutputBaseDir(@Null String outputBaseDir) {
        this.outputBaseDir = outputBaseDir;
    }

    /**
     * Get the path that is to be pre append to the output path, if null none
     * will be pre append
     *
     * @return
     */
    @Null
    public String getJasperFileBaseDir() {
        return jasperFileBaseDir;
    }

    /**
     * Set the path that is to be pre append to the output path, if null none
     * will be pre append
     *
     * @param jasperFileBaseDir
     */
    public void setJasperFileBaseDir(@Null String jasperFileBaseDir) {
        this.jasperFileBaseDir = jasperFileBaseDir;
    }

    /**
     *
     * Get the log level
     *
     * @return
     */
    @NotNull
    public Level getLogLevel() {
        return LOG.getLevel();
    }

    /**
     *
     * Set the log level
     *
     * @param logLevel *
     */
    public void setLogLevel(@NotNull Level logLevel) {
        Configurator.setLevel(
                rebelo.reports.cli.Generate.class.getName(),
                logLevel
        );
    }

    /**
     * get verbose
     *
     * @return
     */
    @Null
    public String getVerbose() {
        return verbose;
    }

    /**
     * Set verbose
     *
     * @param verbose
     */
    public void setVerbose(@Null String verbose) {
        LOG.traceEntry("Verbose: ".concat(verbose == null ? "null" : verbose));
        this.verbose = verbose;
        if (null != this.verbose) {
            this.setLogLevel(Level.valueOf(verbose));
        } else {
            this.setLogLevel(Level.OFF);
        }
    }

    @SuppressWarnings("FinallyDiscardsException")
    @Override
    public Integer call() throws Exception {

        if (null != verbose) {
            this.setLogLevel(Level.valueOf(verbose));
        }

        Report.logLevel = this.getLogLevel();

        String msg;
        Integer exitCode;
        try {
            LOG.traceEntry();
            if (file == null && dir == null) {
                msg = "No dir or file option, please exectute --help for options";
                LOG.error(msg);
                System.out.println(msg);
                LOG.traceExit();
                return ErrorCode.NO_DIR_OR_FILE_OPTION;
            }

            if (file != null && dir != null) {
                msg = "Option dir and file setted, please exectute --help for options";
                LOG.error(msg);
                System.out.println(msg);
                LOG.traceExit();
                return ErrorCode.NO_DIR_OR_FILE_OPTION;
            }

            if (file != null) {
                LOG.debug(() -> "Option file seted, checking file");
                LOG.trace(() -> "Checking if path is a file");
                if (file.isFile() == false) {
                    msg = String.format(
                            "Option file path '%s' is not a file", file.getAbsolutePath()
                    );
                    LOG.error(msg);
                    System.out.println(msg);
                    LOG.traceExit();
                    return ErrorCode.WRONG_PATH;
                }

                LOG.trace(() -> "Checking if file is readable");
                if (file.canRead() == false) {
                    msg = String.format(
                            "File path '%s' is not readable", file.getAbsolutePath()
                    );
                    LOG.error(msg);
                    System.out.println(msg);
                    LOG.traceExit();
                    return ErrorCode.IS_NOT_READABLE;
                }
                this.generateFile();
                LOG.traceExit();
                LOG.traceExit();
                return ErrorCode.NO_ERROR;
            }

            if (dir != null) {
                LOG.debug(() -> "Option dir seted, checking file");
                LOG.trace(() -> "Checking if path is a directory");
                if (dir.isDirectory() == false) {
                    msg = String.format(
                            "Option dir path '%s' is not a directory", dir.getAbsolutePath()
                    );
                    LOG.error(msg);
                    System.out.println(msg);
                    LOG.traceExit();
                    return ErrorCode.WRONG_PATH;
                }

                LOG.trace(() -> "Checking if directory is readable");
                if (dir.canRead() == false) {
                    msg = String.format(
                            "File path '%s' is not readable", dir.getAbsolutePath()
                    );
                    LOG.error(msg);
                    System.out.println(msg);
                    LOG.traceExit();
                    return ErrorCode.IS_NOT_READABLE;
                }

                if (dir.listFiles().length == 0) {
                    msg = String.format(
                            "No files id directory '%s'",
                            dir.getAbsolutePath());
                    LOG.error(msg);
                    System.out.println(msg);
                    LOG.traceExit();
                    return ErrorCode.NO_FILES_IN_DIR;
                }

                for (File propFile : dir.listFiles()) {
                    if (propFile.canRead() == false) {
                        msg = String.format(
                                "File path '%s' is not readable", propFile.getAbsolutePath()
                        );
                        LOG.error(msg);
                        System.out.println(msg);
                        return ErrorCode.IS_NOT_READABLE;
                    }
                }

                ArrayList<File> xmlFiles = new ArrayList<>();
                for (File fileIndir : dir.listFiles()) {
                    if (fileIndir.getAbsolutePath().endsWith(".xml") == false) {
                        LOG.trace(() -> String.format(
                                "File '%s' is not a xml file",
                                fileIndir.getAbsolutePath())
                        );
                        continue;
                    }
                    xmlFiles.add(fileIndir);
                }

                if (xmlFiles.isEmpty()) {
                    msg = String.format(
                            "No XML files id directory '%s'",
                            dir.getAbsolutePath());
                    LOG.error(msg);
                    System.out.println(msg);
                    LOG.traceExit();
                    return ErrorCode.NO_FILES_IN_DIR;
                }

                this.generateDir(xmlFiles);
                LOG.traceExit();
                return ErrorCode.NO_ERROR;
            }

            msg = "Unknow option to generate report";
            LOG.error(msg);
            System.out.println(msg);
            LOG.traceExit();
            return ErrorCode.UNKNOW_OPTION;

        } catch (RRPropertiesException e) {
            LOG.catching(e);
            msg = e.getMessage();
            exitCode = ErrorCode.REPORT_PROPERTIES_ERROR;
        } catch (DataSourceException e) {
            LOG.catching(e);
            msg = e.getMessage();
            exitCode = ErrorCode.REBELO_REPORT_DATASOURCE_ERROR;
        } catch (JRException e) {
            LOG.catching(e);
            msg = e.getMessage();
            exitCode = ErrorCode.JASPER_ENGINE_REPORT_ERROR;
        } catch (RRException e) {
            LOG.catching(e);
            msg = e.getMessage();
            exitCode = ErrorCode.ERROR_GENERATE_REPORT;
        } catch (SQLException e) {
            LOG.catching(e);
            msg = e.getMessage();
            exitCode = ErrorCode.SQL_ERROR;
        } catch (MalformedURLException
                | java.text.ParseException
                | JAXBException
                | SAXException
                | ParseException e) {
            msg = e.getMessage();
            exitCode = ErrorCode.PARSING_ERROR;
        } catch (PrinterNotFoundException e) {
            LOG.catching(e);
            msg = e.getMessage();
            exitCode = ErrorCode.PRINTER_ERROR;
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | NullNotAllowedException e) {
            LOG.catching(e);
            msg = e.getMessage();
            exitCode = ErrorCode.UNDEFINED;
        } catch (CliException e) {
            LOG.catching(e);
            msg = e.getMessage();
            exitCode = ErrorCode.CLI_GENERATE_ERROR;
        } catch (CliDelFileException e) {
            LOG.catching(e);
            msg = e.getMessage();
            exitCode = ErrorCode.CLI_DEL_FILE_ERROR;
        }catch(SignPdfException e){
            LOG.catching(e);
            msg = e.getMessage();
            exitCode = ErrorCode.PDF_SIGNING_ERROR;
        }
        LOG.error(msg);
        System.out.println(msg);
        LOG.traceExit();
        return exitCode;

    }

    /**
     *
     * Generate report from a propertis xml file
     *
     * @throws SAXException
     * @throws NullNotAllowedException
     * @throws JAXBException
     * @throws ParseException
     * @throws RRPropertiesException
     * @throws MalformedURLException
     * @throws DataSourceException
     * @throws java.text.ParseException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws JRException
     * @throws RRException
     * @throws PrinterNotFoundException
     * @throws CliDelFileException
     * @throws rebelo.reports.core.sign.SignPdfException
     */
    protected void generateFile()
            throws SAXException, NullNotAllowedException,
            JAXBException, ParseException, RRPropertiesException,
            MalformedURLException, DataSourceException,
            java.text.ParseException,
            ClassNotFoundException,
            InstantiationException,
            SQLException, IllegalAccessException,
            JRException, RRException,
            PrinterNotFoundException, 
            CliDelFileException, SignPdfException {

        LOG.traceEntry("File: {}", file);

        ParseXML parse = new ParseXML();
        parse.setOutputBaseDir(this.outputBaseDir);
        parse.setJasperFileBaseDir(this.jasperFileBaseDir);
        RRProperties prop = parse.parse(file);
        Report rep = new Report(prop);

        LOG.info(() -> {
            String info = "Report will be exported to file propertie '%s'";
            try {
                return String.format(info, rep.getProperties().getOutputFile());
            } catch (NullNotAllowedException e) {
                return String.format(info, "???");
            }
        });

        rep.exportReport();

        if (delFile) {
            try {
                java.nio.file.Files.delete(
                        java.nio.file.Paths.get(file.getPath())
                );
                LOG.debug(() -> String.format(
                        "File '%s' was deleted", file.getPath()));
            } catch (IOException e) {
                LOG.catching(e);
                String msg = String.format(
                        "File '%s' it was not deleted with error '%s'",
                        file.getPath(), e.getMessage());
                LOG.traceExit();
                throw new CliDelFileException(msg);
            }
        }

        LOG.traceExit();
    }

    /**
     *
     * Generate the report from all properties xml files in a directory
     *
     * @param xmlFiles
     * @throws SAXException
     * @throws NullNotAllowedException
     * @throws JAXBException
     * @throws ParseException
     * @throws RRPropertiesException
     * @throws MalformedURLException
     * @throws DataSourceException
     * @throws java.text.ParseException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws JRException
     * @throws RRException
     * @throws PrinterNotFoundException
     * @throws CliException
     * @throws CliDelFileException
     * @throws rebelo.reports.core.sign.SignPdfException
     */
    @SuppressWarnings("Convert2Lambda")
    protected void generateDir(ArrayList<File> xmlFiles)
            throws SAXException, NullNotAllowedException,
            JAXBException, ParseException, RRPropertiesException,
            MalformedURLException, DataSourceException,
            java.text.ParseException,
            ClassNotFoundException,
            InstantiationException,
            SQLException, IllegalAccessException,
            JRException,
            RRException,
            PrinterNotFoundException, CliException, 
            CliDelFileException, SignPdfException {
        
        LOG.traceEntry("Directory: {}, with '{}' files to parse",
                dir, xmlFiles.size());

        String msg = null;
        ArrayList<JasperPrint> list = new ArrayList<>();
        Report report = null;
        
        int x = 0;
        for (File propFile : xmlFiles) {
            if (propFile.getAbsolutePath().endsWith(".xml") == false) {
                LOG.trace(() -> String.format(
                        "File '%s' is not a xml file",
                        propFile.getAbsolutePath())
                );
                continue;
            }

            LOG.debug(() -> String.format(
                    "Init of parse file '%s'",
                    propFile.getAbsolutePath())
            );
            ParseXML parse = new ParseXML();
            parse.setOutputBaseDir(this.outputBaseDir);
            parse.setJasperFileBaseDir(this.jasperFileBaseDir);
            RRProperties prop = parse.parse(propFile);
            Report rep = new Report(prop);
            list.addAll(rep.getJasperPrint());
            if (x == xmlFiles.size() - 1) {
                LOG.info(() -> {
                    String info = "Report will be exported to file propertie '%s'";
                    try {
                        return String.format(info, prop.getOutputFile());
                    } catch (NullNotAllowedException e) {
                        return String.format(info, "???");
                    }
                });
                report = rep;
                report.setJasperPrint(list);
            }
            x++;
        }

        if (report != null) {
            report.exportReport();
        } else {
            msg = String.format(
                    "No files in diretcory '%s'",
                    dir.getAbsoluteFile());
            LOG.traceExit();
            throw new CliException(msg);
        }
                
        boolean alldel = true;
        if (this.delFile || this.delDir) {
            for (File propFile : xmlFiles) {
                try {
                    java.nio.file.Files.delete(
                            java.nio.file.Paths.get(propFile.getPath())
                    );
                    LOG.debug(() -> String.format(
                            "File '%s' was deleted", propFile.getPath()));
                } catch (IOException e) {
                    LOG.catching(e);
                    LOG.debug(() -> String.format(
                            "File '%s' it was not deleted with error '%s'",
                            propFile.getPath(), e.getMessage()));
                    alldel = false;
                }
            }
        } else {
            LOG.traceExit();
            return;
        }

        if (alldel == false) {
            msg = String.format(
                    "Report was generated but some files wasn't deleted in directory '%s'",
                    dir.getAbsolutePath());
            LOG.traceExit();
            throw new CliDelFileException(msg);
        }

        if (delDir) {
            try {
                java.nio.file.Files.delete(
                        java.nio.file.Paths.get(dir.getPath())
                );
                LOG.debug(() -> String.format(
                        "Directory '%s' was deleted", dir.getPath()));
            } catch (IOException e) {
                LOG.catching(e);
                LOG.debug(() -> String.format(
                        "Report was exported but directory '%s' it was not deleted with error '%s'",
                        dir.getPath(), e.getMessage()));
                LOG.traceExit();
                throw new CliDelFileException(msg);
            }
        }

        LOG.traceExit();
    }

}
