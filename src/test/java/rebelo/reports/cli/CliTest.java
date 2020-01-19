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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

/**
 *
 * @author João Rebelo
 */
public class CliTest {
    
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    
    public CliTest() {
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
     * Test call --version, of class Cli.
     */
    @Test
    public void testVersionMain() {
        exit.expectSystemExit();
        System.out.println("main --version");
        String[] args = {"--version"};
        Cli.main(args);       
    }

    /**
     * Test call --help, of class Cli.
     */
    @Test
    public void testHelpMain() {
        exit.expectSystemExit();
        System.out.println("main --help");
        String[] args = {"--help"};
        Cli.main(args);
    }
    
}
