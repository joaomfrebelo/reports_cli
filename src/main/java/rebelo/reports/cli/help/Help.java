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
package rebelo.reports.cli.help;

/**
 *
 * @author João Rebelo
 */
public class Help {
    public void printHelp(){
        Version v = new Version();
        
        System.out.println("Rebelo Reports CLI Version " + v.ver());
        System.out.println("");
        System.out.println("Usage: java -jar RReports_cli.jar /path/to_xmlFile");
        System.out.println("or");
        System.out.println("Usage: java -jar RReports_cli.jar options");
        System.out.println("");
        System.out.println("Where option include:");
        System.out.println("-h or --help Print this menu");
        System.out.println("-d or --dir /path/to/xml_file_dir");
        System.out.println("-d or --dir option will do for the same file the export of all reports defined in all xml files in the directories");
        System.out.println("");
        //compiler
        System.out.println("To compile a jrxml file to a jasper file or revert a jasper file to a jrxml");        
        System.out.println("Usage: java -jar RReports_cli.jar compiler -in=/path/src/file -out=/path/dest/file");  
    }
}
