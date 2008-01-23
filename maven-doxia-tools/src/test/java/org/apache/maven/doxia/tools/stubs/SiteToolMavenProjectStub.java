package org.apache.maven.doxia.tools.stubs;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.DefaultArtifactRepository;
import org.apache.maven.artifact.repository.layout.DefaultRepositoryLayout;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Reporting;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;

/**
 * @author <a href="mailto:vincent.siveton@gmail.com">Vincent Siveton</a>
 * @version $Id$
 */
public class SiteToolMavenProjectStub
    extends MavenProjectStub
{
    private Build build;

    private Reporting reporting;

    public SiteToolMavenProjectStub()
    {
        MavenXpp3Reader pomReader = new MavenXpp3Reader();
        Model model = null;

        try
        {
            model = pomReader.read( new FileReader( new File( getBasedir(), "pom.xml" ) ) );
            setModel( model );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }

        setGroupId( model.getGroupId() );
        setArtifactId( model.getArtifactId() );
        setVersion( model.getVersion() );
        setName( model.getName() );
        setUrl( model.getUrl() );
        setPackaging( model.getPackaging() );

        Build build = new Build();
        build.setFinalName( model.getArtifactId() );
        build.setDirectory( super.getBasedir() + "/target/test/unit/site-tool-test/target" );
        build.setSourceDirectory( getBasedir() + "/src/main/java" );
        build.setOutputDirectory( super.getBasedir() + "/target/test/unit/site-tool-test/target/classes" );
        build.setTestSourceDirectory( getBasedir() + "/src/test/java" );
        build.setTestOutputDirectory( super.getBasedir() + "/target/test/unit/site-tool-test/target/test-classes" );
        setBuild( build );

        List compileSourceRoots = new ArrayList();
        compileSourceRoots.add( getBasedir() + "/src/main/java" );
        setCompileSourceRoots( compileSourceRoots );

        List testCompileSourceRoots = new ArrayList();
        testCompileSourceRoots.add( getBasedir() + "/src/test/java" );
        setTestCompileSourceRoots( testCompileSourceRoots );

        reporting = new Reporting();
        reporting.setOutputDirectory( super.getBasedir() + "/target/test/unit/site-tool-test/target/site" );
    }

    /** {@inheritDoc} */
    public Build getBuild()
    {
        return build;
    }

    /** {@inheritDoc} */
    public void setBuild( Build build )
    {
        this.build = build;
    }

    /** {@inheritDoc} */
    public File getBasedir()
    {
        return new File( super.getBasedir() + "/src/test/resources/unit/site-tool-test" );
    }

    /** {@inheritDoc} */
    public List getRemoteArtifactRepositories()
    {
        ArtifactRepository repository = new DefaultArtifactRepository( "central", "http://repo1.maven.org/maven2",
                                                                       new DefaultRepositoryLayout() );

        return Collections.singletonList( repository );
    }

    /** {@inheritDoc} */
    public Reporting getReporting()
    {
        return this.reporting;
    }
}
