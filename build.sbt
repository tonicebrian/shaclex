// Local dependencies
lazy val srdfVersion           = "0.1.38"
lazy val shexsVersion          = "0.1.43"
lazy val shaclsVersion         = "0.1.45"
lazy val sutilsVersion         = "0.1.48"


// Dependency versions
lazy val antlrVersion          = "4.7.1"
lazy val catsVersion           = "2.0.0"
lazy val commonsTextVersion    = "1.8"
lazy val circeVersion          = "0.12.0-RC3"
lazy val diffsonVersion        = "4.0.0"
// lazy val effVersion            = "4.6.1"
lazy val jenaVersion           = "3.13.1"
lazy val jgraphtVersion        = "1.3.1"
lazy val logbackVersion        = "1.2.3"
lazy val loggingVersion        = "3.9.2"
lazy val rdf4jVersion          = "3.0.0"
lazy val scalacheckVersion     = "1.14.0"
lazy val scalacticVersion      = "3.0.8"
lazy val scalaTestVersion      = "3.0.8"
lazy val scalaGraphVersion     = "1.11.5"
lazy val scalatagsVersion      = "0.6.7"
lazy val scallopVersion        = "3.3.1"
lazy val seleniumVersion       = "2.35.0"
lazy val sextVersion           = "0.2.6"
lazy val typesafeConfigVersion = "1.3.4"
lazy val xercesVersion         = "2.12.0"

// Compiler plugin dependency versions
lazy val simulacrumVersion    = "1.0.0"
// lazy val kindProjectorVersion = "0.9.5"
lazy val scalaMacrosVersion   = "2.1.1"

// Dependency modules
lazy val antlr4            = "org.antlr"                  % "antlr4"               % antlrVersion
lazy val catsCore          = "org.typelevel"              %% "cats-core"           % catsVersion
lazy val catsKernel        = "org.typelevel"              %% "cats-kernel"         % catsVersion
lazy val catsMacros        = "org.typelevel"              %% "cats-macros"         % catsVersion
lazy val circeCore         = "io.circe"                   %% "circe-core"          % circeVersion
lazy val circeGeneric      = "io.circe"                   %% "circe-generic"       % circeVersion
lazy val circeParser       = "io.circe"                   %% "circe-parser"        % circeVersion
lazy val commonsText       = "org.apache.commons"         %  "commons-text"        % commonsTextVersion
lazy val diffsonCirce      = "org.gnieh"                  %% "diffson-circe"       % diffsonVersion
// lazy val eff               = "org.atnos"                  %% "eff"                 % effVersion
lazy val jgraphtCore       = "org.jgrapht"                % "jgrapht-core"         % jgraphtVersion
lazy val logbackClassic    = "ch.qos.logback"             % "logback-classic"      % logbackVersion
lazy val jenaArq           = "org.apache.jena"            % "jena-arq"             % jenaVersion
lazy val jenaFuseki        = "org.apache.jena"            % "jena-fuseki-main"     % jenaVersion
lazy val rdf4j_runtime     = "org.eclipse.rdf4j"          % "rdf4j-runtime"        % rdf4jVersion

// WESO components
lazy val srdf              = "es.weso"                    % "srdf_2.13"            % srdfVersion
lazy val srdfJena          = "es.weso"                    % "srdfjena_2.13"        % srdfVersion
lazy val srdf4j            = "es.weso"                    % "srdf4j_2.13"          % srdfVersion
lazy val utils             = "es.weso"                    % "utils_2.13"           % srdfVersion
lazy val typing            = "es.weso"                    % "typing_2.13"          % sutilsVersion
lazy val validating        = "es.weso"                    % "validating_2.13"      % sutilsVersion
lazy val sutils            = "es.weso"                    % "sutils_2.13"          % sutilsVersion
lazy val utilsTest         = "es.weso"                    % "utilstest_2.13"       % sutilsVersion
lazy val shex              = "es.weso"                    % "shex_s_2.13"          % shexsVersion
lazy val shapeMaps         = "es.weso"                    % "shapemaps_2.13"     % shexsVersion
lazy val shacl             = "es.weso"                    % "shacl_s_2.13"         % shaclsVersion


lazy val scalaLogging      = "com.typesafe.scala-logging" %% "scala-logging"       % loggingVersion
lazy val scallop           = "org.rogach"                 %% "scallop"             % scallopVersion
lazy val scalactic         = "org.scalactic"              %% "scalactic"           % scalacticVersion
lazy val scalacheck        = "org.scalacheck"             %% "scalacheck"          % scalacheckVersion
lazy val scalaTest         = "org.scalatest"              %% "scalatest"           % scalaTestVersion
lazy val scalatags         = "com.lihaoyi"                %% "scalatags"           % scalatagsVersion
lazy val selenium          = "org.seleniumhq.selenium"    % "selenium-java"        % seleniumVersion
// lazy val htmlUnit          = "org.seleniumhq.selenium"    % "htmlunit-driver"      % seleniumVersion
lazy val sext              = "com.github.nikita-volkov"   % "sext"                 % sextVersion
lazy val typesafeConfig    = "com.typesafe"               % "config"               % typesafeConfigVersion
lazy val xercesImpl        = "xerces"                     % "xercesImpl"           % xercesVersion
lazy val simulacrum        = "org.typelevel" %% "simulacrum"     % simulacrumVersion


lazy val shaclex = project
  .in(file("."))
  .enablePlugins(ScalaUnidocPlugin, SbtNativePackager, WindowsPlugin, JavaAppPackaging, LauncherJarPlugin)
  .disablePlugins(RevolverPlugin)
//  .settings(
//    buildInfoKeys := BuildInfoKey.ofN(name, version, scalaVersion, sbtVersion),
//    buildInfoPackage := "es.weso.shaclex.buildinfo" 
//  )
  .settings(commonSettings, packagingSettings, publishSettings, ghPagesSettings, wixSettings)
  .aggregate(schemaInfer, schema, converter, slang, sgraph)
  .dependsOn(schemaInfer, schema, converter, slang, sgraph)
  .settings(
    unidocProjectFilter in (ScalaUnidoc, unidoc) := inAnyProject -- inProjects(noDocProjects: _*),
    libraryDependencies ++= Seq(
      logbackClassic,
      scalaLogging,
      scallop,
      typesafeConfig,
    ),
    cancelable in Global      := true,
    fork                      := true,
    parallelExecution in Test := false,
    ThisBuild / turbo := true
  )

lazy val schemaInfer = project
  .in(file("modules/schemaInfer"))
  .disablePlugins(RevolverPlugin)
  .settings(
    commonSettings,  
    publishSettings,
    libraryDependencies ++= Seq(srdf)
  )
  .dependsOn(
    schema
  )

lazy val schema = project
  .in(file("modules/schema"))
  .disablePlugins(RevolverPlugin)
  .settings(
    commonSettings, 
    publishSettings,
    libraryDependencies ++= Seq(
      srdfJena,
      shex,
      shacl,
      shapeMaps
      )
  )
  .dependsOn(
    slang,
    converter
  )

lazy val slang = project
  .in(file("modules/slang"))
  .disablePlugins(RevolverPlugin)
  .settings(commonSettings, publishSettings)
  .dependsOn(
  )
  .settings(
    libraryDependencies ++= Seq(
      catsCore,
      catsKernel,
      catsMacros,
      shex, 
      shacl,
      sutils,
      srdf,
      srdf4j % Test,
      srdfJena % Test
      )
  )

lazy val sgraph = project
  .in(file("modules/sgraph"))
  .disablePlugins(RevolverPlugin)
  .settings(commonSettings, publishSettings)
  .dependsOn(
  )
  .settings(
    libraryDependencies ++= Seq(
      sutils,
      utilsTest % Test,
      srdf,
      catsCore,
      catsKernel,
      catsMacros,
      srdf4j % Test,
      srdfJena % Test
      )
  )

lazy val converter = project
  .in(file("modules/converter"))
  .disablePlugins(RevolverPlugin)
  .settings(
    commonSettings, 
    publishSettings,
    libraryDependencies ++= Seq(
     srdfJena % Test,
     shex,
     shacl
     )
  )

/* ********************************************************
 ******************** Grouped Settings ********************
 **********************************************************/

lazy val noDocProjects = Seq[ProjectReference]()

lazy val noPublishSettings = Seq(
//  publish := (),
//  publishLocal := (),
  publishArtifact := false
)

lazy val sharedDependencies = Seq(
  libraryDependencies ++= Seq(
    scalactic,
    scalaTest % Test
  )
)

lazy val packagingSettings = Seq(
  mainClass in Compile        := Some("es.weso.shaclex.Main"),
  mainClass in assembly       := Some("es.weso.shaclex.Main"),
  test in assembly            := {},
  assemblyJarName in assembly := "shaclex.jar",
  packageSummary in Linux     := name.value,
  packageSummary in Windows   := name.value,
  packageDescription          := name.value
)

lazy val compilationSettings = Seq(
  scalaVersion := "2.13.1",
  // format: off
  scalacOptions ++= Seq(
    "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
    "-encoding", "utf-8",                // Specify character encoding used by source files.
    "-explaintypes",                     // Explain type errors in more detail.
    "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.  "-encoding", "UTF-8",
    "-language:_",
    "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
    "-Xlint",
    "-Yrangepos",
    "-Ywarn-dead-code",                  // Warn when dead code is identified.
    "-Xfatal-warnings",
    "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
    "-Ymacro-annotations"
  )
  // format: on
)

lazy val wixSettings = Seq(
  wixProductId        := "39b564d5-d381-4282-ada9-87244c76e14b",
  wixProductUpgradeId := "6a710435-9af4-4adb-a597-98d3dd0bade1"
// The same numbers as in the docs?
// wixProductId := "ce07be71-510d-414a-92d4-dff47631848a",
// wixProductUpgradeId := "4552fb0e-e257-4dbd-9ecb-dba9dbacf424"
)

lazy val ghPagesSettings = Seq(
  git.remoteRepo := "git@github.com:labra/shaclex.git"
)

lazy val commonSettings = compilationSettings ++ sharedDependencies ++ Seq(
  organization := "es.weso",
  resolvers ++= Seq(
    Resolver.bintrayRepo("labra", "maven"),
    Resolver.bintrayRepo("weso", "weso-releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)

def antlrSettings(packageName: String) = Seq(
  antlr4GenListener in Antlr4 := true,
  antlr4GenVisitor in Antlr4  := true,
  antlr4Dependency in Antlr4  := antlr4,
  antlr4PackageName in Antlr4 := Some(packageName),
)

lazy val publishSettings = Seq(
  maintainer      := "Jose Emilio Labra Gayo <labra@uniovi.es>",
  homepage        := Some(url("https://github.com/labra/shaclex")),
  licenses        := Seq("MIT" -> url("http://opensource.org/licenses/MIT")),
  scmInfo         := Some(ScmInfo(url("https://github.com/labra/shaclex"), "scm:git:git@github.com:labra/shaclex.git")),
  autoAPIMappings := true,
  apiURL          := Some(url("http://labra.github.io/shaclex/latest/api/")),
  pomExtra        := <developers>
                       <developer>
                         <id>labra</id>
                         <name>Jose Emilio Labra Gayo</name>
                         <url>https://github.com/labra/</url>
                       </developer>
                     </developers>,
  scalacOptions in doc ++= Seq(
    "-diagrams-debug",
    "-doc-source-url",
    scmInfo.value.get.browseUrl + "/tree/master€{FILE_PATH}.scala",
    "-sourcepath",
    baseDirectory.in(LocalRootProject).value.getAbsolutePath,
    "-diagrams",
  ),
  publishMavenStyle              := true,
  bintrayRepository in bintray   := "weso-releases",
  bintrayOrganization in bintray := Some("weso")
)
