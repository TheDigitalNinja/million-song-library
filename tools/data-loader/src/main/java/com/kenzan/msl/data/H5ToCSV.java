package com.kenzan.msl.data;

import java.io.BufferedReader;
import java.io.File;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.google.common.io.Files;
import com.kenzan.msl.data.row.Q01User;
import com.kenzan.msl.data.row.Q02UserRatings;
import com.kenzan.msl.data.row.Q03AverageRatings;
import com.kenzan.msl.data.row.Q04FeaturedSong;
import com.kenzan.msl.data.row.Q05SongsByFacet;
import com.kenzan.msl.data.row.Q06FeaturedAlbum;
import com.kenzan.msl.data.row.Q07AlbumsByFacet;
import com.kenzan.msl.data.row.Q08FeaturedArtist;
import com.kenzan.msl.data.row.Q09ArtistsByFacet;
import com.kenzan.msl.data.row.Q10SongsAlbumsByArtist;
import com.kenzan.msl.data.row.Q11SongsByUser;
import com.kenzan.msl.data.row.Q12AlbumsByUser;
import com.kenzan.msl.data.row.Q13ArtistsByUser;
import com.kenzan.msl.data.row.Q14SongsArtistByAlbum;
import com.kenzan.msl.data.row.Q15AlbumArtistBySong;

/**
 * {@link H5ToCSV} takes the path of the million song data directory as an
 * argument. Then sorts, normalizes, and distributes the data across multiple
 * CSV files ready for copying into Cassandra.
 *
 * @author peterburt
 */
public class H5ToCSV {

    private static final String SORT_DIRECTORY = "sort";
    private static final int FILE_SIZE_LIMIT = 1250;
    private static final int NORMALIZE_SONGS = 1;
    private static final int NORMALIZE_ALBUMS = 2;
    private static final int NORMALIZE_ARTISTS = 3;
    private static final int NORMALIZE_SIMILAR_ARTISTS = 4;
    private static final int NORMALIZE_CLEAN = 5;

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
    private static final String CLEAN_DATA_BY_ALBUM_ID = "13_clean_data_by_album_id.txt";
    private static final String CLEAN_DATA_BY_ARTIST_ID = "14_clean_data_by_album_id.txt";

    private static final String Q01_USERS = "../data/Q01_users.csv";
    private static final String Q02_USER_DATA = "../data/Q02_user_data.csv";
    private static final String Q03_AVERAGE_RATINGS = "../data/Q03_average_ratings.csv";

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
    private static final String Q10_SONGS_ALBUMS_BY_ARTIST = "../data/Q10_songs_albums_by_artist.csv";
    private static final String Q13_ARTISTS_BY_USER = "../data/Q13_artists_by_user.csv";

    public H5ToCSV() throws IOException, InterruptedException {

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
        new File(Q03_AVERAGE_RATINGS).delete();
        this.mergeSortData(CLEAN_DATA, CLEAN_DATA_BY_SONG_ID, Field.SONG_ID);
        this.createSongTables(CLEAN_DATA_BY_SONG_ID, users);
        this.mergeSortData(CLEAN_DATA, CLEAN_DATA_BY_ALBUM_ID, Field.ALBUM_ID);
        this.createAlbumTables(CLEAN_DATA_BY_ALBUM_ID, users);
        this.mergeSortData(CLEAN_DATA, CLEAN_DATA_BY_ARTIST_ID, Field.ARTIST_ID);
        this.createArtistTables(CLEAN_DATA_BY_ARTIST_ID, users);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final long startTime = System.currentTimeMillis();
        new H5ToCSV();
        final long stopTime = System.currentTimeMillis();
        final long elapsedTime = stopTime - startTime;
        final String minutesSeconds = String.format("%02d:%02d", elapsedTime / 1000 / 60, elapsedTime / 1000 % 60);
        System.out.println(minutesSeconds);
    }

    private void createArtistTables(final String readFilePath, final List<Q01User> users) throws IOException {

        final File readFile = new File(readFilePath);
        int count = 0;
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(readFile));
                final PrintWriter q2Writer = new PrintWriter(new FileWriter(Q02_USER_DATA, true));
                final PrintWriter q3Writer = new PrintWriter(new FileWriter(Q03_AVERAGE_RATINGS, true));
                final PrintWriter q8Writer = new PrintWriter(new FileWriter(Q08_FEATURED_ARTISTS));
                final PrintWriter q9Writer = new PrintWriter(new FileWriter(Q09_ARTISTS_BY_FACET));
                final PrintWriter q13Writer = new PrintWriter(new FileWriter(Q13_ARTISTS_BY_USER));
                final PrintWriter q10Writer = new PrintWriter(new FileWriter(Q10_SONGS_ALBUMS_BY_ARTIST))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                final NormalizedRow normalizedRow = new NormalizedRow.NormalizedRowBuilder(line).build();
                normalizedRow.lookUpAlbumImage();
                count += 1;
                System.out.println("Creating artist table .... Processing line #" + count);
                line = bufferedReader.readLine();
                if (line == null || !normalizedRow.getArtist().getId()
                        .equals(new NormalizedRow.NormalizedRowBuilder(line).build().getArtist().getId())) {
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
                        final Date timestamp = randomDate();
                        if (favorited) {
                            q13Writer.println(new Q13ArtistsByUser(normalizedRow, randomUsers[x].getId(), timestamp));
                            q13Writer.flush();
                        } 
                        if (ratings[x] > 0) {
                            q2Writer.println(new Q02UserRatings(randomUsers[x].getId(), ContentType.ARTIST,
                                    normalizedRow.getSong().getId(), ratings[x]));
                            q2Writer.flush();
                        }
                    }
                    if (averageRating > 0) {
                        q3Writer.println(new Q03AverageRatings(normalizedRow.getArtist().getId(), ContentType.ARTIST,
                                numRating, sumRating));
                        q3Writer.flush();
                    }

                    Q08FeaturedArtist featuredArtist = new Q08FeaturedArtist(normalizedRow);
                    q8Writer.println(featuredArtist);
                    q8Writer.flush();

                    for (int x = averageRating; x >= 1; x--) {
                        q9Writer.println(new Q09ArtistsByFacet(normalizedRow, Rating.values()[x - 1].toString()));
                        q9Writer.flush();
                    }
                    for (final Genre genre : normalizedRow.getArtist().getGenres()) {
                        q9Writer.println(new Q09ArtistsByFacet(normalizedRow, genre.toString()));
                        q9Writer.flush();
                    }
                }
                Q10SongsAlbumsByArtist songsAlbumsByArtist = new Q10SongsAlbumsByArtist(normalizedRow);
                q10Writer.println(songsAlbumsByArtist);
                q10Writer.flush();
            }
        }
        System.out.println("createArtistTables: " + count + " lines");
    }

    private void createAlbumTables(final String readFilePath, final List<Q01User> users) throws IOException {

        final File readFile = new File(readFilePath);
        int count = 0;
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(readFile));
                final PrintWriter q2Writer = new PrintWriter(new FileWriter(Q02_USER_DATA, true));
                final PrintWriter q3Writer = new PrintWriter(new FileWriter(Q03_AVERAGE_RATINGS, true));
                final PrintWriter q6Writer = new PrintWriter(new FileWriter(Q06_FEATURED_ALBUMS));
                final PrintWriter q7Writer = new PrintWriter(new FileWriter(Q07_ALBUMS_BY_FACET));
                final PrintWriter q12Writer = new PrintWriter(new FileWriter(Q12_ALBUMS_BY_USER));
                final PrintWriter q14Writer = new PrintWriter(new FileWriter(Q14_SONGS_ARTIST_BY_ALBUM))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                final NormalizedRow normalizedRow = new NormalizedRow.NormalizedRowBuilder(line).build();
                normalizedRow.lookUpAlbumImage();
                count += 1;
                System.out.println("Creating albums table .... Processing line #" + count);
                line = bufferedReader.readLine();
                if (line == null || !normalizedRow.getAlbum().getId()
                        .equals(new NormalizedRow.NormalizedRowBuilder(line).build().getAlbum().getId())) {
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
                        final Date timestamp = randomDate();
                        if (favorited) {
                            q12Writer.println(new Q12AlbumsByUser(normalizedRow, randomUsers[x].getId(), timestamp));
                            q12Writer.flush();
                        } 
                        if (ratings[x] > 0) {
                            q2Writer.println(new Q02UserRatings(randomUsers[x].getId(), ContentType.ALBUM,
                                    normalizedRow.getSong().getId(), ratings[x]));
                            q2Writer.flush();
                        }
                    }
                    if (averageRating > 0) {
                        q3Writer.println(new Q03AverageRatings(normalizedRow.getAlbum().getId(), ContentType.ALBUM,
                                numRating, sumRating));
                        q3Writer.flush();
                    }

                    Q06FeaturedAlbum featureAlbum = new Q06FeaturedAlbum(normalizedRow);
                    q6Writer.println(featureAlbum);
                    q6Writer.flush();

                    for (int x = averageRating; x >= 1; x--) {
                        q7Writer.println(new Q07AlbumsByFacet(normalizedRow, Rating.values()[x - 1].toString()));
                        q7Writer.flush();
                    }
                    for (final Genre genre : normalizedRow.getArtist().getGenres()) {
                        q7Writer.println(new Q07AlbumsByFacet(normalizedRow, genre.toString()));
                        q7Writer.flush();
                    }
                }
                q14Writer.println(new Q14SongsArtistByAlbum(normalizedRow));
                q14Writer.flush();
            }
        }
        System.out.println("createAlbumTables: " + count + " lines");
    }

    private void createSongTables(final String readFilePath, final List<Q01User> users) throws IOException {

        final File readFile = new File(readFilePath);
        int count = 0;
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(readFile));
                final PrintWriter q2Writer = new PrintWriter(new FileWriter(Q02_USER_DATA, true));
                final PrintWriter q3Writer = new PrintWriter(new FileWriter(Q03_AVERAGE_RATINGS, true));
                final PrintWriter q4Writer = new PrintWriter(new FileWriter(Q04_FEATURED_SONGS));
                final PrintWriter q5Writer = new PrintWriter(new FileWriter(Q05_SONGS_BY_FACET));
                final PrintWriter q11Writer = new PrintWriter(new FileWriter(Q11_SONGS_BY_USER));
                final PrintWriter q15Writer = new PrintWriter(new FileWriter(Q15_ALBUM_ARTIST_BY_SONG))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                final NormalizedRow normalizedRow = new NormalizedRow.NormalizedRowBuilder(line).build();
                normalizedRow.lookUpAlbumImage();
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
                    final Date timestamp = randomDate();
                    if (favorited) {
                        q11Writer.println(new Q11SongsByUser(normalizedRow, randomUsers[x].getId(), timestamp));
                        q11Writer.flush();
                    }
                    if (ratings[x] > 0) {
                        q2Writer.println(new Q02UserRatings(randomUsers[x].getId(), ContentType.SONG,
                                normalizedRow.getSong().getId(), ratings[x]));
                        q2Writer.flush();
                    }
                }

                if (averageRating > 0) {
                    q3Writer.println(new Q03AverageRatings(normalizedRow.getSong().getId(), ContentType.SONG, numRating,
                            sumRating));
                    q3Writer.flush();
                }

                Q04FeaturedSong featureSong = new Q04FeaturedSong(normalizedRow);
                q4Writer.println(featureSong);
                q4Writer.flush();

                for (int x = averageRating; x >= 1; x--) {
                    q5Writer.println(new Q05SongsByFacet(normalizedRow, Rating.values()[x - 1].toString()));
                    q5Writer.flush();
                }
                for (final Genre genre : normalizedRow.getArtist().getGenres()) {
                    q5Writer.println(new Q05SongsByFacet(normalizedRow, genre.toString()));
                    q5Writer.flush();
                }
                q15Writer.println(new Q15AlbumArtistBySong(normalizedRow));
                q5Writer.flush();
                System.out.println("Creating songs table .... Processed line #" + count);
                count += 1;
                line = bufferedReader.readLine();
            }
        }
        System.out.println("createSongTables: " + count + " lines");
    }

    private List<Q01User> generateUsers(final String writeFilePath) throws IOException {

        final List<Q01User> users = new ArrayList<Q01User>();
        final File writeFile = new File(writeFilePath);
        try (final PrintWriter printWriter = new PrintWriter(new FileWriter(writeFile))) {
            for (int i = 0; i < 100; i++) {
                final Q01User user = new Q01User(UUID.randomUUID(),
                        String.format("username%02d@kenzan.com", i), String.format("password%02d", i),
                        randomDate());
                users.add(user);
                printWriter.println(user);
            }
        }
        return users;
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
                final List<String> names = new ArrayList<String>();
                final List<Integer> durations = new ArrayList<Integer>();
                final List<Float> hotnesses = new ArrayList<Float>();
                for (final RawRow row : rowsToNormalize) {
                    final String name = row.getSong().getName();
                    if (StringUtils.isNotBlank(name)) {
                        names.add(name);
                    }
                    final String duration = row.getSong().getDuration();
                    if (StringUtils.isNotBlank(duration)) {
                        durations.add(Integer.parseInt(duration));
                    }
                    final String hotness = row.getSong().getHotness();
                    if (StringUtils.isNotBlank(hotness)) {
                        hotnesses.add(Float.parseFloat(hotness));
                    }
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
                // new duration (average duration)
                Integer sumDuration = 0;
                for (final Integer duration : durations) {
                    sumDuration += duration;
                }
                final Integer averageDuration = sumDuration / rowsToNormalize.size();
                // new hotness (average hotness)
                Float sumHotness = 0.0f;
                for (final Float hotness : hotnesses) {
                    sumHotness += hotness;
                }
                final Float averageHotness = sumHotness / rowsToNormalize.size();
                // replace
                for (final RawRow row : rowsToNormalize) {
                    final RawSong oldSong = row.getSong();
                    // songs are treated as tracks, the same song may have many id's
                    final UUID uuid = UUID.randomUUID();
                    final RawSong newSong = new RawSong.SongBuilder(uuid.toString(), shortest,
                            averageDuration.toString(), averageHotness.toString()).build();
                    if (StringUtils.isNotBlank(shortest)) {
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

    private static Date randomDate() {
        return new Date(0L + (long) (Math.random() * (new Date().getTime() - 0L)));
    }
}
