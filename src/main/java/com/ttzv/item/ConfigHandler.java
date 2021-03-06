package com.ttzv.item;

import com.ttzv.item.properties.Update4jCfg;
import org.update4j.Configuration;
import org.update4j.FileMetadata;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class for use only with update4j framework - allows creation of Configuration object and configuration .xml file
 */
public class ConfigHandler {

    private static final String ARG_NEW = "--new";
    private static final String ARG_SYNC = "--sync";

    private static final String CONFIG_PATH = "target/deploy/config/config.xml";
    private static final String DEPLOY_FILES = "target/deploy/files/";

    public static void main(String[] args) throws IOException {
        Update4jCfg.getInstance().init(null);

        boolean configPathExists = Files.exists(Paths.get(CONFIG_PATH).getParent());
        String BASE_URI = Update4jCfg.getInstance().retrieveProp(Update4jCfg.UPDATE4J_BASE_URI);
        String BASE_PATH = Update4jCfg.getInstance().retrieveProp(Update4jCfg.UPDATE4J_BASE_PATH);
        System.out.println(Paths.get(DEPLOY_FILES).getParent().resolve("cfg"));
        System.out.println(Paths.get(BASE_PATH).getParent().resolve("cfg"));

        if(args != null && args.length > 0) {
            switch (args[0]) {
                case ARG_NEW: // --new
                    Path configPath = Paths.get(CONFIG_PATH);
                    System.out.println(configPath.toString());
                    Configuration config = Configuration.builder()
                            .baseUri(BASE_URI)
                            .basePath(BASE_PATH)
                            .files(FileMetadata.streamDirectory(DEPLOY_FILES)
                                    .peek(f -> f.modulepath())
                                    .peek(f -> f.uri("files/" + f.getSource().getFileName()))
                                    .peek(f -> f.osFromFilename()))
                            .files(FileMetadata.streamDirectory(Paths.get(DEPLOY_FILES).getParent().resolve("cfg"))
                                    .peek(f -> f.modulepath(false))
                                    .peek(f -> f.uri("cfg/" + f.getSource().getFileName()))
                                    .peek(f -> f.path(Paths.get(BASE_PATH).getParent().resolve("cfg").resolve(f.getSource().getFileName())))
                            )
                            .files(FileMetadata.streamDirectory(Paths.get(DEPLOY_FILES).getParent().resolve("templates"))
                                    .peek(f -> f.modulepath(false))
                                    .peek(f -> f.uri(f.getSource().subpath(2, f.getSource().getNameCount()-1).toString() + "/" + f.getSource().getFileName()))
                                    .peek(f-> f.path(Paths.get(BASE_PATH).getParent().resolve(f.getSource().subpath(2, f.getSource().getNameCount()))))
                            )
                            .property("default.launcher.main.class", "com.ttzv.item.Main")
                            .build();
                    if (!configPathExists) {
                        Files.createDirectories(Paths.get(CONFIG_PATH).getParent());
                    }
                    System.out.println(config.getBasePath());
                    try (Writer out = Files.newBufferedWriter(configPath)) {
                        config.write(out);
                    }
                    break;

                case ARG_SYNC: // --sync
                    if (!configPathExists) {
                        System.err.println("Valid configuration not found in given path\n" +
                                CONFIG_PATH + "\n" +
                                "try using *new* first");
                    } else {
                        configPath = Paths.get(CONFIG_PATH);
                        Configuration configuration = Configuration.read(Files.newBufferedReader(configPath));
                        Configuration newConfig = configuration.sync(configPath);
                        try (Writer out = Files.newBufferedWriter(configPath)) {
                            newConfig.write(out);
                        }
                    }
                    break;

                default:
                    System.err.println("no such argument " + args[0]);
                    break;

            }
        }
    }
}


