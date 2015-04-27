package mcd;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

public class Unpacker {

    public void to(File dest) throws IOException, URISyntaxException {
        if (!dest.isDirectory()) {
            throw new RuntimeException(dest.getPath() + " is not a directory.");
        }

        copyFromJar("/packed", Paths.get(dest.getPath()));
    }

    /**
     * Copies a "source" folder from the resources folder. From StackOverflow:
     * http://stackoverflow.com/questions/1386809/copy-directory-from-a-jar-file
     */
    protected void copyFromJar(String source, final Path target) throws URISyntaxException, IOException {
        URI resource = getClass().getResource("").toURI();
        FileSystem fileSystem = FileSystems.newFileSystem(
                resource,
                Collections.<String, String>emptyMap()
        );


        final Path jarPath = fileSystem.getPath(source);

        Files.walkFileTree(jarPath, new SimpleFileVisitor<Path>() {

            private Path currentTarget;

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                currentTarget = target.resolve(jarPath.relativize(dir).toString());
                Files.createDirectories(currentTarget);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, target.resolve(jarPath.relativize(file).toString()));
                return FileVisitResult.CONTINUE;
            }

        });
    }
}
