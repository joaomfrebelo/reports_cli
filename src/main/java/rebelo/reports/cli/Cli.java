/**
 * Copyright (C) 2019 João M F Rebelo
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package rebelo.reports.cli;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;
import picocli.CommandLine.UnmatchedArgumentException;

/**
 *
 * @author João Rebelo
 */
public class Cli {

    /**
     * The class LOG
     */
    protected static final Logger LOG = LogManager.getLogger();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            Generate generate = new Generate();
            CommandLine cmd = new CommandLine(generate);
            cmd.parseArgs(args);
            
            if (cmd.isVersionHelpRequested()) {
                Version.print();
                System.exit(0);
            }

            Integer exit = cmd.execute(args);

            if (exit == -1) {
                throw new Exception("Cli.Generate instance no exitCode returned");
            }

            System.exit(exit);
        }catch(UnmatchedArgumentException e){
            LOG.error(e.getMessage());
            System.exit(ErrorCode.UNMATCHED_ARGUMENT);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            System.out.println(e.getMessage());
            System.exit(ErrorCode.UNDEFINED);
        }
    }

}
