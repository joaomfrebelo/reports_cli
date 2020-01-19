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

import com.jcabi.manifests.Manifests;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;
import javax.validation.constraints.Null;

/**
 *
 * @author João Rebelo
 */
public class Version {
    
    /**
     * Artifact version
     */
    @Null
    public final static String VERSION = Version.readManifestKey("Version", "No key 'Version' in manifest file");
    
    /**
     * Artifact ArtifactId
     */
    @Null
    public final static String ARTIFACT_ID = Version.readManifestKey("ArtifactId", "No key 'ArtifactId' in manifest file");
    
    /**
     * Artifact GroupId
     */
    @Null
    public final static String GROUP_ID = Version.readManifestKey("GroupId", "No key 'GroupId' in manifest file");
    
    /**
     * Build jdk
     */
    @Null
    public static String BUILD_JDK = Version.readManifestKey("Build-Jdk-Spec", "No key 'Build-Jdk-Spec' in manifest file");

    public Version() {
    }
    
    /**
     * Print the verison to the standart output
     */
    public static void print(){
        System.out.println(ARTIFACT_ID);
        System.out.println("Version: " + VERSION);
    } 
    
    /**
     * Read and retaurn the key's value in manifests file if not exist return 
     * the default value
     * @param name
     * @param defaultValue
     * @return 
     */
    @Null
    public static String readManifestKey(final String name, @Null String defaultValue) {
        if(Manifests.exists(name)){
            return Manifests.read(name);
        }
        return defaultValue;
    }
    
}
