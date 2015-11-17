package com.kenzan.msl.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.io.Files;
import com.kenzan.msl.data.row.Q01User;
import com.kenzan.msl.data.row.Q02UserData;
import com.kenzan.msl.data.row.Q03AverageRatings;
import com.kenzan.msl.data.row.Q04FeaturedSong;
import com.kenzan.msl.data.row.Q05SongsByFacet;
import com.kenzan.msl.data.row.Q11SongsByUser;
import com.kenzan.msl.data.row.Q15AlbumArtistBySong;
import com.kenzan.msl.data.row.RowUtil;

/**
 * @author peterburt
 */
public class H5ToCSV {

    private static final String SORT_DIRECTORY = "sort";
    private static final int FILE_SIZE_LIMIT = 1250;
    private static final int H5_TO_CSV = 0;
    private static final int NORMALIZE_SONGS = 1;
    private static final int NORMALIZE_ALBUMS = 2;
    private static final int NORMALIZE_ARTISTS = 3;
    private static final int NORMALIZE_SIMILAR_ARTISTS = 4;
    private static final int NORMALIZE_CLEAN = 5;
    private static final String H5_LIST = "01_h5_list.txt";
    private static final String RAW_DATA = "02_raw_data.txt";
    private static final String RAW_DATA_BY_SONG_ID = "03_raw_data_by_song_id.txt";
    private static final String RAW_DATA_NORMALIZED_SONGS = "04_raw_data_normalized_songs.txt";
    private static final String RAW_DATA_BY_ALBUM_ID = "05_raw_data_by_album_id.txt";
    private static final String RAW_DATA_NORMALIZED_ALBUMS = "06_raw_data_normalized_albums.txt";
    private static final String RAW_DATA_BY_ARTIST_ID = "07_raw_data_by_artist_id.txt";
    private static final String RAW_DATA_NORMALIZED_ARTISTS = "08_raw_data_normalized_artists.txt";
    private static final String ARTIST_ID_MAP = "09_artist_id_map.txt";
    private static final String RAW_DATA_NORMALIZED_SIMILAR_ARTISTS = "10_raw_data_normalized_similar_artists.txt";
    private static final String CLEAN_DATA = "11_clean_data.txt";
    private static final String CLEAN_DATA_BY_SONG_ID = "12_clean_data_by_song_id.txt";

    private static final String Q01_USERS = "../data/Q01_users.csv";
    private static final String Q02_USER_DATA = "../data/Q02_user_data.csv";
    private static final String Q03_AVERAGE_RATING = "../data/Q03_average_rating.csv";

    private static final String Q04_FEATURED_SONGS = "../data/Q04_featured_songs.csv";
    private static final String Q05_SONGS_BY_FACET = "../data/Q05_songs_by_facet.csv";
    private static final String Q15_ALBUM_ARTIST_BY_SONG = "../data/Q15_album_artist_by_song.csv";
    private static final String Q11_SONGS_BY_USER = "../data/Q11_songs_by_user.csv";

    private static final String Q06_FEATURED_ALBUMS = "../data/Q06_featured_albums.csv";
    private static final String Q07_ALBUMS_BY_FACET = "../data/Q07_albums_by_facet.csv";
    private static final String Q14_SONGS_ARTIST_BY_ALBUM = "../data/Q14_songs_artist_by_album.csv";
    private static final String Q12_ALBUMS_BY_USER = "../data/Q12_albums_by_user.csv";

    private static final String Q08_FEATURED_ARTISTS = "../data/Q08_featured_artists.csv";
    private static final String Q09_ARTISTS_BY_FACET = "../data/Q09_artists_by_facet.csv";
    private static final String Q10_SONGS_ALBUM_BY_ARTIST = "../data/Q10_songs_album_by_artist.csv";
    private static final String Q13_ARTISTS_BY_USER = "../data/Q13_artists_by_user.csv";

    // 12_clean_data_by_song_id
    // Q4, Q5, Q15, Q11
    // 13_clean_data_by_album_id
    // Q6, Q7, Q14, Q12
    // 14_clean_data_by_artist_id
    // Q8, Q9, Q10, Q13

    // "/Users/peterburt/Desktop/msl-subset/MillionSongSubset/data"

    public H5ToCSV(final String directory) throws IOException {

        this.writeH5List(directory, H5_LIST);
        this.readDataWriteData(H5_LIST, RAW_DATA, H5_TO_CSV);
        this.mergeSortData(RAW_DATA, RAW_DATA_BY_SONG_ID, Field.SONG_ID);
        this.readDataWriteData(RAW_DATA_BY_SONG_ID, RAW_DATA_NORMALIZED_SONGS, NORMALIZE_SONGS);
        this.mergeSortData(RAW_DATA_NORMALIZED_SONGS, RAW_DATA_BY_ALBUM_ID, Field.ALBUM_ID);
        this.readDataWriteData(RAW_DATA_BY_ALBUM_ID, RAW_DATA_NORMALIZED_ALBUMS, NORMALIZE_ALBUMS);
        this.mergeSortData(RAW_DATA_NORMALIZED_ALBUMS, RAW_DATA_BY_ARTIST_ID, Field.ARTIST_ID);
        this.readDataWriteData(RAW_DATA_BY_ARTIST_ID, RAW_DATA_NORMALIZED_ARTISTS, NORMALIZE_ARTISTS);
        this.readDataWriteData(RAW_DATA_NORMALIZED_ARTISTS, RAW_DATA_NORMALIZED_SIMILAR_ARTISTS,
                NORMALIZE_SIMILAR_ARTISTS);
        this.readDataWriteData(RAW_DATA_NORMALIZED_SIMILAR_ARTISTS, CLEAN_DATA, NORMALIZE_CLEAN);

        // create table csv
        final List<Q01User> users = this.generateUsers(Q01_USERS);
        new File(Q02_USER_DATA).delete();
        new File(Q03_AVERAGE_RATING).delete();
        this.mergeSortData(CLEAN_DATA, CLEAN_DATA_BY_SONG_ID, Field.SONG_ID);
        this.createSongTables(CLEAN_DATA_BY_SONG_ID, users);
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        new H5ToCSV(args[0]);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
    }

    private void createSongTables(final String readFilePath, final List<Q01User> users)
            throws FileNotFoundException, IOException {

        final File readFile = new File(readFilePath);
        int count = 0;
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(readFile));
                final PrintWriter q2Writer = new PrintWriter(new FileWriter(Q02_USER_DATA, true));
                final PrintWriter q3Writer = new PrintWriter(new FileWriter(Q03_AVERAGE_RATING, true));
                final PrintWriter q4Writer = new PrintWriter(new FileWriter(Q04_FEATURED_SONGS));
                final PrintWriter q5Writer = new PrintWriter(new FileWriter(Q05_SONGS_BY_FACET));
                final PrintWriter q11Writer = new PrintWriter(new FileWriter(Q11_SONGS_BY_USER));
                final PrintWriter q15Writer = new PrintWriter(new FileWriter(Q15_ALBUM_ARTIST_BY_SONG))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                final NormalizedRow normalizedRow = new NormalizedRow.NormalizedRowBuilder(line).build();
                final Random random = new Random();
                final int[] ratings = new int[3];
                ratings[0] = random.nextInt(6);
                ratings[1] = random.nextInt(6);
                ratings[2] = random.nextInt(6);
                int numRating = 0;
                int sumRating = 0;
                for (int x = 0; x < ratings.length; x++) {
                    if (ratings[x] > 0) {
                        numRating += 1;
                        sumRating += ratings[x];
                    }
                }
                final int averageRating = numRating > 0 ? sumRating / numRating : 0;
                final Q01User[] randomUsers = new Q01User[3];
                randomUsers[0] = users.get(random.nextInt(100));
                randomUsers[1] = users.get(random.nextInt(100));
                randomUsers[2] = users.get(random.nextInt(100));

                for (int x = 0; x < randomUsers.length; x++) {
                    final boolean favorited = random.nextBoolean();
                    final Date timestamp = RowUtil.randomDate();
                    if (favorited) {
                        q2Writer.println(new Q02UserData(randomUsers[x].getId(), ContentType.SONG,
                                normalizedRow.getSong().getId(), timestamp, ratings[x]));
                        q11Writer.println(new Q11SongsByUser(normalizedRow, randomUsers[x].getId(), timestamp));
                    } else {
                        q2Writer.println(new Q02UserData(randomUsers[x].getId(), ContentType.SONG,
                                normalizedRow.getSong().getId(), null, ratings[x]));
                    }
                }

                q3Writer.println(
                        new Q03AverageRatings(normalizedRow.getSong().getId(), ContentType.SONG, numRating, sumRating));

                q4Writer.println(new Q04FeaturedSong(normalizedRow));
                for (int x = averageRating; x >= 1; x--) {
                    q5Writer.println(new Q05SongsByFacet(normalizedRow, Rating.values()[x - 1].toString()));
                }
                for (final Genre genre : normalizedRow.getArtist().getGenres()) {
                    q5Writer.println(new Q05SongsByFacet(normalizedRow, genre.toString()));
                }
                q15Writer.println(new Q15AlbumArtistBySong(normalizedRow));
                count += 1;
                line = bufferedReader.readLine();
            }
        }
    }

    private List<Q01User> generateUsers(final String writeFilePath) throws IOException {

        final List<Q01User> users = new ArrayList<Q01User>();
        final File writeFile = new File(writeFilePath);
        try (final PrintWriter printWriter = new PrintWriter(new FileWriter(writeFile))) {
            for (int i = 0; i < 100; i++) {
                final Q01User user = new Q01User.UserBuilder(UUID.randomUUID(), String.format("username%02d", i),
                        String.format("password%02d", i), RowUtil.randomDate()).build();
                users.add(user);
                printWriter.println(user);
            }
        }
        return users;
    }

    private void writeH5List(final String readDirectoryPath, final String writeFilePath) throws IOException {

        final File writeFile = new File(writeFilePath);
        writeFile.delete();
        final File readDirectory = new File(readDirectoryPath);
        final String[] extensions = new String[] { "h5" };
        final Iterator<File> files = FileUtils.iterateFiles(readDirectory, extensions, true);
        int count = 0;
        try (final PrintWriter printWriter = new PrintWriter(new FileWriter(writeFile))) {
            while (files.hasNext()) {
                final File file = files.next();
                final String absolutePath = file.getAbsolutePath();
                printWriter.println(absolutePath);
                count += 1;
            }
            System.out.println("writeH5List: " + count + " h5 files");
        }
    }

    private void h5ToCsv(final BufferedReader bufferedReader, final PrintWriter printWriter) throws IOException {

        String line = bufferedReader.readLine();
        int count = 0;
        while (line != null) {
            final ProcessBuilder processBuilder = new ProcessBuilder("python", "src/main/python/hdf5_to_csv.py", line);
            final Process process = processBuilder.start();
            try (final BufferedReader pythonOutput = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                final String rawLine = pythonOutput.readLine();
                final RawRow rawRow = new RawRow.RawRowBuilder(rawLine).build();
                printWriter.println(rawRow.toString());
                count += 1;
                System.out.println(count);
                line = bufferedReader.readLine();
            }
        }
        System.out.println("h5ToCsv: " + count + " lines");
    }

    private void normalizeSongs(final BufferedReader bufferedReader, final PrintWriter printWriter) throws IOException {

        String line = bufferedReader.readLine();
        int count = 0;
        List<RawRow> rowsToNormalize = new ArrayList<RawRow>();
        while (line != null) {
            final RawRow rawRow = new RawRow.RawRowBuilder(line).build();
            rowsToNormalize.add(rawRow);
            line = bufferedReader.readLine();
            if (line == null
                    || !rawRow.getSong().getId().equals(new RawRow.RawRowBuilder(line).build().getSong().getId())) {
                final List<Integer> durations = new ArrayList<Integer>();
                final List<Float> hotnesses = new ArrayList<Float>();
                for (final RawRow row : rowsToNormalize) {
                    final String duration = row.getSong().getDuration();
                    if (StringUtils.isNotBlank(duration)) {
                        durations.add(Integer.parseInt(duration));
                    }
                    final String hotness = row.getSong().getHotness();
                    if (StringUtils.isNotBlank(hotness)) {
                        hotnesses.add(Float.parseFloat(hotness));
                    }
                }
                // new id
                final UUID uuid = UUID.randomUUID();
                // new durate (average duration)
                Integer sumDuration = 0;
                for (final Integer duration : durations) {
                    sumDuration += duration;
                }
                // new hotness (average hotness)
                Float sumHotness = 0.0f;
                for (final Float hotness : hotnesses) {
                    sumHotness += hotness;
                }
                // replace
                for (final RawRow row : rowsToNormalize) {
                    final RawSong oldSong = row.getSong();
                    final RawSong newSong = new RawSong.SongBuilder(uuid.toString(), oldSong.getName(),
                            sumDuration.toString(), sumHotness.toString()).build();
                    if (StringUtils.isNotBlank(oldSong.getName())) {
                        printWriter.println(
                                new RawRow.RawRowBuilder(newSong, row.getAlbum(), row.getArtist()).build().toString());
                        count += 1;
                    } else {
                        System.out.println("Eliminating record (no song name) id: " + oldSong.getId());
                    }
                }
                rowsToNormalize = new ArrayList<RawRow>();
            }
        }
        System.out.println("normalizeSongs: " + count + " lines");
    }

    private void normalizeAlbums(final BufferedReader bufferedReader, final PrintWriter printWriter)
            throws IOException {

        String line = bufferedReader.readLine();
        int count = 0;
        List<RawRow> rowsToNormalize = new ArrayList<RawRow>();
        while (line != null) {
            final RawRow rawRow = new RawRow.RawRowBuilder(line).build();
            rowsToNormalize.add(rawRow);
            line = bufferedReader.readLine();
            if (line == null
                    || !rawRow.getAlbum().getId().equals(new RawRow.RawRowBuilder(line).build().getAlbum().getId())) {
                final List<String> names = new ArrayList<String>();
                final List<Integer> years = new ArrayList<Integer>();
                final List<Float> hotnesses = new ArrayList<Float>();
                for (final RawRow row : rowsToNormalize) {
                    final String name = row.getAlbum().getName();
                    if (StringUtils.isNotBlank(name)) {
                        names.add(name);
                    }
                    final String year = row.getAlbum().getYear();
                    if (StringUtils.isNotBlank(year)) {
                        years.add(Integer.parseInt(year));
                    }
                    final String hotness = row.getSong().getHotness();
                    if (StringUtils.isNotBlank(hotness)) {
                        hotnesses.add(Float.parseFloat(hotness));
                    }
                }
                // new id
                final UUID uuid = UUID.randomUUID();
                // new name (shortest recorded name)
                String shortest = "";
                if (!names.isEmpty()) {
                    shortest = names.get(0);
                    for (final String name : names) {
                        if (name.length() < shortest.length()) {
                            shortest = name;
                        }
                    }
                }
                // new year (highest recorded year)
                Integer highest = 0;
                if (!years.isEmpty()) {
                    highest = years.get(0);
                    for (final Integer year : years) {
                        if (year > highest) {
                            highest = year;
                        }
                    }
                }
                // new hotness (average hotness of all songs for this album)
                Float sum = 0.0f;
                for (final Float hotness : hotnesses) {
                    sum += hotness;
                }
                final Float average = sum / rowsToNormalize.size();
                // replace
                for (final RawRow row : rowsToNormalize) {
                    final RawAlbum newAlbum = new RawAlbum.AlbumBuilder(uuid.toString(), shortest, highest.toString(),
                            average.toString()).build();
                    if (StringUtils.isNotBlank(shortest)) {
                        printWriter.println(
                                new RawRow.RawRowBuilder(row.getSong(), newAlbum, row.getArtist()).build().toString());
                        count += 1;
                    } else {
                        System.out.println("Eliminating record (no album name) id: " + row.getSong().getId());
                    }
                }
                rowsToNormalize = new ArrayList<RawRow>();
            }
        }
        System.out.println("normalizeAlbums: " + count + " lines");
    }

    private void normalizeArtists(final BufferedReader bufferedReader, final PrintWriter printWriter)
            throws IOException {

        String line = bufferedReader.readLine();
        int count = 0;
        List<RawRow> rowsToNormalize = new ArrayList<RawRow>();
        try (final PrintWriter mapWriter = new PrintWriter(new FileWriter(ARTIST_ID_MAP))) {
            while (line != null) {
                final RawRow rawRow = new RawRow.RawRowBuilder(line).build();
                rowsToNormalize.add(rawRow);
                line = bufferedReader.readLine();
                if (line == null || !rawRow.getArtist().getId()
                        .equals(new RawRow.RawRowBuilder(line).build().getArtist().getId())) {
                    final List<String> mbids = new ArrayList<String>();
                    final List<String> names = new ArrayList<String>();
                    final List<Float> hotnesses = new ArrayList<Float>();
                    final List<String> genres = new ArrayList<String>();
                    final List<String> similarArtists = new ArrayList<String>();
                    for (final RawRow row : rowsToNormalize) {
                        final String mbid = row.getArtist().getMbid();
                        if (StringUtils.isNotBlank(mbid)) {
                            mbids.add(mbid);
                        }
                        final String name = row.getArtist().getName();
                        if (StringUtils.isNotBlank(name)) {
                            names.add(name);
                        }
                        final String hotness = row.getArtist().getHotness();
                        if (StringUtils.isNotBlank(hotness)) {
                            hotnesses.add(Float.parseFloat(hotness));
                        }
                        final String genreString = row.getArtist().getGenres();
                        if (StringUtils.isNotBlank(genreString)) {
                            genres.addAll(Arrays.asList(genreString.split("\\|")));
                        }
                        final String similarArtistsString = row.getArtist().getSimilarArtists();
                        if (StringUtils.isNotBlank(similarArtistsString)) {
                            similarArtists.addAll(Arrays.asList(similarArtistsString.split("\\|")));
                        }
                    }
                    // new id
                    final UUID uuid = UUID.randomUUID();
                    // new mbid (first mbid)
                    String mbid = "";
                    if (!mbids.isEmpty()) {
                        mbid = mbids.get(0);
                    }
                    // new name (shortest recorded name)
                    String shortest = "";
                    if (!names.isEmpty()) {
                        shortest = names.get(0);
                        for (final String name : names) {
                            if (name.length() < shortest.length()) {
                                shortest = name;
                            }
                        }
                    }
                    // new hotness (average hotness of all songs for this album)
                    Float sum = 0.0f;
                    for (final Float hotness : hotnesses) {
                        sum += hotness;
                    }
                    final Float average = sum / rowsToNormalize.size();
                    // new genres (set of all recorded genres)
                    final Set<String> genresSet = new HashSet<String>(genres);
                    // new similar artists (set of all similar artists)
                    final Set<String> similarArtistsSet = new HashSet<String>(similarArtists);
                    // replace and create map
                    for (final RawRow row : rowsToNormalize) {
                        final RawArtist newArtist = new RawArtist.ArtistBuilder(uuid.toString(), mbid, shortest,
                                average.toString(), String.join("|", genresSet), String.join("|", similarArtistsSet))
                                        .build();
                        if (StringUtils.isNotBlank(mbid) && StringUtils.isNotBlank(shortest)) {
                            printWriter.println(new RawRow.RawRowBuilder(row.getSong(), row.getAlbum(), newArtist)
                                    .build().toString());
                            count += 1;
                        } else {
                            System.out.println(
                                    "Eliminating record (no mbid or no artist name) id: " + row.getSong().getId());
                        }
                    }
                    mapWriter.println(new StringBuilder().append(rowsToNormalize.get(0).getArtist().getId()).append(",")
                            .append(uuid.toString()).append(": ").append(shortest));
                    rowsToNormalize = new ArrayList<RawRow>();
                }
            }
            System.out.println("normalizeArtists: " + count + " lines");
        }
    }

    private void normalizeSimilarArtists(final BufferedReader bufferedReader, final PrintWriter printWriter)
            throws IOException {

        // read map
        final Map<String, String> artistIdMap = new HashMap<String, String>();
        try (final BufferedReader mapReader = new BufferedReader(new FileReader(ARTIST_ID_MAP))) {
            String mapEntry = mapReader.readLine();
            while (mapEntry != null) {
                final String[] parts = mapEntry.split(",");
                artistIdMap.put(parts[0], parts[1]);
                mapEntry = mapReader.readLine();
            }
        }
        String line = bufferedReader.readLine();
        int count = 0;
        List<RawRow> rowsToNormalize = new ArrayList<RawRow>();
        while (line != null) {
            final RawRow rawRow = new RawRow.RawRowBuilder(line).build();
            rowsToNormalize.add(rawRow);
            line = bufferedReader.readLine();
            if (line == null
                    || !rawRow.getArtist().getId().equals(new RawRow.RawRowBuilder(line).build().getArtist().getId())) {
                // replace
                for (final RawRow row : rowsToNormalize) {
                    final RawArtist oldArtist = row.getArtist();
                    final String oldSimilarArtistString = oldArtist.getSimilarArtists();
                    final List<String> newSimilarArtists = new ArrayList<String>();
                    if (StringUtils.isNotBlank(oldSimilarArtistString)) {
                        final String[] oldSimilarArtists = oldSimilarArtistString.split("\\|");
                        for (final String oldSimilarArtist : oldSimilarArtists) {
                            final String newSimilarArtist = artistIdMap.get(oldSimilarArtist);
                            if (StringUtils.isNotBlank(newSimilarArtist)) {
                                newSimilarArtists.add(newSimilarArtist);
                            }
                        }
                    }
                    final RawArtist newArtist = new RawArtist.ArtistBuilder(row.getArtist().getId(),
                            row.getArtist().getMbid(), row.getArtist().getName(), row.getArtist().getHotness(),
                            row.getArtist().getGenres(), String.join("|", newSimilarArtists)).build();
                    if (StringUtils.isNotBlank(String.join("|", newSimilarArtists))) {
                        printWriter.println(
                                new RawRow.RawRowBuilder(row.getSong(), row.getAlbum(), newArtist).build().toString());
                        count += 1;
                    } else {
                        System.out.println("Eliminating record (no similar artists) id: " + row.getSong().getId());
                    }
                }
                rowsToNormalize = new ArrayList<RawRow>();
            }
        }
        System.out.println("normalizeSimilarArtists: " + count + " lines");
    }

    private void cleanData(final BufferedReader bufferedReader, final PrintWriter printWriter) throws IOException {

        String line = bufferedReader.readLine();
        int count = 0;
        while (line != null) {
            final NormalizedRow clean = new NormalizedRow.NormalizedRowBuilder(line).build();
            printWriter.println(clean.toString());
            count += 1;
            line = bufferedReader.readLine();
        }
        System.out.println("cleanData: " + count + " lines");
    }

    private void readDataWriteData(final String readFilePath, final String writeFilePath, final int step)
            throws IOException {

        final File readFile = new File(readFilePath);
        final File writeFile = new File(writeFilePath);
        writeFile.delete();

        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(readFile));
                final PrintWriter printWriter = new PrintWriter(new FileWriter(writeFile))) {
            if (step == H5_TO_CSV) {
                h5ToCsv(bufferedReader, printWriter);
            }
            if (step == NORMALIZE_SONGS) {
                normalizeSongs(bufferedReader, printWriter);
            }
            if (step == NORMALIZE_ALBUMS) {
                normalizeAlbums(bufferedReader, printWriter);
            }
            if (step == NORMALIZE_ARTISTS) {
                normalizeArtists(bufferedReader, printWriter);
            }
            if (step == NORMALIZE_SIMILAR_ARTISTS) {
                normalizeSimilarArtists(bufferedReader, printWriter);
            }
            if (step == NORMALIZE_CLEAN) {
                cleanData(bufferedReader, printWriter);
            }
        }
    }

    private void mergeSortData(final String readFilePath, final String writeFilePath, final Field field)
            throws IOException {

        final File readFile = new File(readFilePath);
        final File writeFile = new File(writeFilePath);
        writeFile.delete();
        final File sortDirectory = new File(SORT_DIRECTORY);
        sortDirectory.delete();
        sortDirectory.mkdir();

        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(readFile))) {
            String line = bufferedReader.readLine();
            int count = 0;
            List<RawRow> rawRows = new ArrayList<RawRow>();
            final List<File> filesToMerge = new LinkedList<File>();
            int fileNum = 0;
            // Distribute
            while (line != null) {
                rawRows.add(new RawRow.RawRowBuilder(line).build());
                count += 1;
                line = bufferedReader.readLine();
                if (rawRows.size() == FILE_SIZE_LIMIT || line == null) {
                    fileNum += 1;
                    final File sortedFile = new File(SORT_DIRECTORY, String.format("%05d.txt", fileNum));
                    try (final PrintWriter sortedWriter = new PrintWriter(new FileWriter(sortedFile))) {
                        if (field.equals(Field.SONG_ID)) {
                            Collections.sort(rawRows, RawRow.songIdComparator);
                        } else if (field.equals(Field.ALBUM_ID)) {
                            Collections.sort(rawRows, RawRow.albumIdComparator);
                        } else if (field.equals(Field.ARTIST_ID)) {
                            Collections.sort(rawRows, RawRow.artistIdComparator);
                        }
                        for (final RawRow rawRow : rawRows) {
                            sortedWriter.println(rawRow.toString());
                        }
                    }
                    filesToMerge.add(sortedFile);
                    rawRows = new ArrayList<RawRow>();
                }
            }
            // Merge
            while (filesToMerge.size() > 1) {
                final File sortedReadFileOne = filesToMerge.remove(0);
                final File sortedReadFileTwo = filesToMerge.remove(0);
                fileNum += 1;
                final File sortedWriteFile = new File(SORT_DIRECTORY, String.format("%05d.txt", fileNum));
                sortedWriteFile.delete();
                filesToMerge.add(sortedWriteFile);
                try (final BufferedReader sortedReaderOne = new BufferedReader(new FileReader(sortedReadFileOne));
                        final BufferedReader sortedReaderTwo = new BufferedReader(new FileReader(sortedReadFileTwo));
                        final PrintWriter sortedWriter = new PrintWriter(new FileWriter(sortedWriteFile))) {
                    String lineFromOne = sortedReaderOne.readLine();
                    String lineFromTwo = sortedReaderTwo.readLine();
                    boolean keepReading = true;
                    while (keepReading) {
                        if (lineFromOne == null && lineFromTwo == null) {
                            keepReading = false;
                        } else if (lineFromOne == null) {
                            sortedWriter.println(lineFromTwo);
                            lineFromTwo = sortedReaderTwo.readLine();
                        } else if (lineFromTwo == null) {
                            sortedWriter.println(lineFromOne);
                            lineFromOne = sortedReaderOne.readLine();
                        } else {
                            final RawRow rawRowOne = new RawRow.RawRowBuilder(lineFromOne).build();
                            final RawRow rawRowTwo = new RawRow.RawRowBuilder(lineFromTwo).build();
                            int order = 0;
                            if (field.equals(Field.SONG_ID)) {
                                order = rawRowOne.getSong().getId().compareTo(rawRowTwo.getSong().getId());
                            } else if (field.equals(Field.ALBUM_ID)) {
                                order = rawRowOne.getAlbum().getId().compareTo(rawRowTwo.getAlbum().getId());
                            } else if (field.equals(Field.ARTIST_ID)) {
                                order = rawRowOne.getArtist().getId().compareTo(rawRowTwo.getArtist().getId());
                            }
                            if (order <= 0) {
                                sortedWriter.println(rawRowOne.toString());
                                lineFromOne = sortedReaderOne.readLine();
                            } else {
                                sortedWriter.println(rawRowTwo.toString());
                                lineFromTwo = sortedReaderTwo.readLine();
                            }
                        }
                    }
                }
                sortedReadFileOne.delete();
                sortedReadFileTwo.delete();
            }
            final File sorted = filesToMerge.get(0);
            Files.move(sorted, writeFile);
            sorted.delete();
            sortDirectory.delete();
            System.out.println("mergeSortData: " + count + " lines");
        }
    }
}
