# Rebelo Reports Cli

The Rebelo Reports Cli is the module to run the Rebelo Reports from the command prompt, running Rebelo Reports Cli is possible to integrate with any technology where you can execute external commands, example among others:  
PHP exec(command, output, return_var)  
C# System.Diagnostics.Process.Start("PATH to jre", "-jar RRreports.jar Command Line Arguments")  
    
# Rebelo Reports

Rebelo Reports is a middleware to use the Jasper Reports Framework in the cases that you can not use the Jasper Reports embedded in you software,in cases that you software is not java or there are incompatibility of licences.

Project in:  
https://github.com/joaomfrebelo/reports_core  
https://jitpack.io/#joaomfrebelo/reports_cli

## To include in your project

Use the Jitpack

https://jitpack.io/#joaomfrebelo/reports_cli

For maven prjects

Include in the repositories section of your pom file:

	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>

In the dependecies of your pom file

    <dependency>
	    <groupId>com.github.joaomfrebelo</groupId>
	    <artifactId>reports_cli</artifactId>
	    <version>Tag of version</version>
	</dependency>


## Example using from PHP

The best way is to use the Rebelo Reports for PHP:  
https://github.com/joaomfrebelo/reports_4_php
  
Or directly  
```
$error = null;
$out = array();
$exec = 'java -jar /path/to/rebelo_cli.jar -f=/path/properties.xml -v=off';
exec($exec, $out, $error);
```
  
Is so simple as that to run a Jasper Report from PHP or any other non Java language  
An example of properties.xml in under the folder examples

For more usages execute -> java -jar /path/to/rebelo_cli.jar -h

The sakila.jasper and sakila.jrxml file is made and build with the Jaspersoft Studio  
The example are to MySql Sakila database
  
  
## Features  
### Export to:  
- PDF  
- Digital Sign PDF
- Csv
- Docx
- Html
- Json
- Ods  
- Odt  
- Pptx  
- Rtf  
- Text  
- Xls  
- Xlsx  
- Xml  
- To printer

### Others
- Export multiple reports as one with the same exporter.
- Export copies of the repoprt at onces with a parametrs of the copy index  
- Pass parameters to the report well typed, parameters types:  
    - const P_STRING     = "string";
    - const P_BOOL       = "bool";
    - const P_BOOLEAN    = "boolean";
    - const P_DOUBLE     = "double";
    - const P_FLOAT      = "float";
    - const P_INTEGER    = "integer";
    - const P_LONG       = "long";
    - const P_SHORT      = "short";
    - const P_BIGDECIMAL = "bigdecimal";
    - const P_DATE       = "date";
    - const P_TIME       = "time";
    - const P_SQL_TIME   = "sqltime";
    - const P_SQL_DATE   = "sqldate";
    - const P_TIMESTAMP  = "timestamp";


## License

Copyright (C) 2019  João M F Rebelo

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

## License issues

The use of Rebelo Reports with non AGPL software and with Commercial software:

Because  Rebelo Reports uses AGPL license it’s important to keep the AGPL license file around. In my opinion your software that is going to invoke Rebelo Reports doesn’t have to be licensed under AGPL as the Rebelo Reports if is being  used via its modules as an external executable Midleware and not as part of your project and your source code, compile, install and use as two separated software.

How ever this only reflects my opinion about the compatibilities of licences and the use of the software under AGPL and other licences included commercial ones as described above, is your responsibility to verify if the legality of the compatibility and uses.
