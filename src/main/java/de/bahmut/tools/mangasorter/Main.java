package de.bahmut.tools.mangasorter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public final class Main {

    private static final Pattern VOLUME_REGEX = Pattern.compile(".*(?:Vol\\.|Volume )([\\d.]+).*");

    public static void main(final String[] args) {
        final Path base = Path.of("");
        try (final Stream<Path> chapters = Files.walk(base, 1)) {
            chapters.filter(not(base::equals))
                    .filter(Files::isDirectory)
                    .filter(Main::isNotVolume)
                    .forEach(Main::handleChapter);
        } catch (final Exception e) {
            System.err.println("Could not handle chapters [" + e.getClass().getSimpleName() + "]: " + e.getMessage());
        }
    }

    private static void handleChapter(final Path chapterDirectory) {
        final Matcher matcher = VOLUME_REGEX.matcher(chapterDirectory.getFileName().toString());
        if (!matcher.matches()) {
            return;
        }
        final String volume = matcher.group(1);

        final Path volumeDirectory = Path.of(chapterDirectory.toAbsolutePath().getParent().getFileName().toString() + " - Volume " + volume);
        if (!volumeDirectory.toFile().exists()) {
            try {
                Files.createDirectories(volumeDirectory);
            } catch (final Exception e) {
                System.err.println("Could not create volume directory " + volumeDirectory + " [" + e.getClass().getSimpleName() + "]: " + e.getMessage());
            }
        }

        final String chapterDirectoryName = chapterDirectory.getFileName().toString();
        final Path volumeChapterDirectory = Path.of(volumeDirectory.toString(), chapterDirectoryName);
        try {
            Files.move(chapterDirectory, volumeChapterDirectory);
        } catch (final Exception e) {
            System.err.println("Could not move chapter " + chapterDirectoryName + " to volume " + volume + " [" + e.getClass().getSimpleName() + "]: " + e.getMessage());
        }
    }

    private static boolean isNotVolume(final Path chapter) {
        return !chapter.getFileName().toString().toLowerCase().startsWith("vol");
    }

}
