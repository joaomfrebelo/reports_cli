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

/**
 *
 * Class with the error codes
 * 
 * @author João Rebelo
 */
public class ErrorCode {
       
    /**
     * unedefined error
     */
    public static final Integer NO_ERROR = 0;
    
    /**
     * unedefined error
     */
    public static final Integer UNDEFINED = 1;
    
    /**
     * When no dir or file option selected
     */
    public static final Integer NO_DIR_OR_FILE_OPTION = 2;
    
    /**
     * When dir and file option are selected
     */
    public static final Integer DIR_AND_FILE_OPTION = 3;
    
    /**
     * When wrong dir or file path
     */
    public static final Integer WRONG_PATH = 4;
    
    /**
     * File or dir is not readable
     */
    public static final Integer IS_NOT_READABLE = 5;
    
    /**
     * Unknow option
     */
    public static final Integer UNKNOW_OPTION = 6;
    
    /**
     * Error generating RRreport
     */
    public static final Integer ERROR_GENERATE_REPORT = 7;
    
    /**
     * Error generating RRreport
     */
    public static final Integer REPORT_PROPERTIES_ERROR = 8;
    
    /**
     * RRebelo report datasource defenitions error
     */
    public static final Integer REBELO_REPORT_DATASOURCE_ERROR = 9; 
    
    /**
     * Jasper report engine error
     */
    public static final Integer JASPER_ENGINE_REPORT_ERROR = 10; 
    
    /**
     * SQL error
     */
    public static final Integer SQL_ERROR = 11; 
    
    /**
     * Parsing error
     */
    public static final Integer PARSING_ERROR = 12;
    
    /**
     * Printer error
     */
    public static final Integer PRINTER_ERROR = 13;
    
    /**
     * No files in directory
     */
    public static final Integer NO_FILES_IN_DIR = 14;
    
    /**
     * Error in Cli Generate
     */
    public static final Integer CLI_GENERATE_ERROR = 15;
    
    /**
     * Error deleting file or dir
     */
    public static final Integer CLI_DEL_FILE_ERROR = 16;   
    
    /**
     * Pasing a argument in main method that not exist
     */
    public static final Integer UNMATCHED_ARGUMENT = 17;
    
    /**
     * Error signing the pdf
     */
    public static final Integer PDF_SIGNING_ERROR = 18;
}
