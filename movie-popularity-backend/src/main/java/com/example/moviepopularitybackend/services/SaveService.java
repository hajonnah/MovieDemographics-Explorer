
package com.example.moviepopularitybackend.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;
import java.io.FileNotFoundException;

import com.example.moviepopularitybackend.model.UserPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *
 * Service for saving and fetching user preference data into a json file
 */
@Service
public class SaveService {

    // filename for saving and fetching
    private static final String FILE_NAME = "preferences.json";

    /**
     * Saves incoming single userpreferences without duplication into json file,
     * creates file if one does not exist.
     * 
     * @param up The {@link UserPreferences} object to be saved.
     * 
     */
    public void SavetoFile(UserPreferences up) {
        try {

            File file = new File(FILE_NAME);

            Gson gson = new Gson();

            // file already exists, save into it
            if (file.exists()) {

                BufferedReader br = new BufferedReader(new FileReader(file));

                // describing the data format for reading
                TypeToken<Set<UserPreferences>> collectionType = new TypeToken<Set<UserPreferences>>() {
                };
                Set<UserPreferences> currentSet = gson.fromJson(br, collectionType);

                // since data is saved as a set, addition automatically avoids duplication

                // new preference save
                if (currentSet.add(up)) {
                    // rewrite file since new addition found
                    br.close();

                    try ( // clear old file with false bool
                            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
                        String json = gson.toJson(currentSet);
                        bw.append(json);
                    }

                }
                // duplicate preference - skip
                else {
                    br.close();
                }

            }
            // make new file, save into it
            else {
                // preferences saved as a set to avoid duplicates
                Set<UserPreferences> prefset = new HashSet<>();
                prefset.add(up);
                file.createNewFile();
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                    String json = gson.toJson(prefset);
                    bw.append(json);
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * Get current saved userpreferences from json file. Error checks for
     * empty or badly formatted file and non existing file.
     *
     * @return A Set of {@link UserPreferences} containing all saved preferences, or
     *         an empty set
     *         if no preferences exist or if the file is not properly formatted.
     */
    public Set<UserPreferences> GetFileContents() {
        Set<UserPreferences> currentSet = new HashSet<>();
        try {
            File file = new File(FILE_NAME);
            Gson gson = new Gson();

            // Old prefs exist
            if (file.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    TypeToken<Set<UserPreferences>> collectionType = new TypeToken<Set<UserPreferences>>() {
                    };
                    currentSet = gson.fromJson(br, collectionType.getType());
                }

                // issue with fromJson-check
                if (currentSet == null) {
                    System.out.println("Preferences file exists but is empty or not properly formatted.");
                    currentSet = new HashSet<>();
                } else {
                    System.out.println("Loading preferences: Success");
                }
            } else {
                System.out.println("Preferences file does not exist.");
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentSet;
    }

    /**
     * Save a set of preferences instead of one. Overwrites existing data.
     * Error check for non-existing file, creates one if needed.
     * 
     * @param preferences A Set of {@link UserPreferences} to be saved.
     */
    public void SaveAllPreferences(Set<UserPreferences> preferences) {
        try {
            File file = new File(FILE_NAME);
            Gson gson = new Gson();

            // file exists or is created
            if (file.exists() || file.createNewFile()) {

                try ( // clear file with false bool
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
                    String json = gson.toJson(preferences);
                    bw.write(json);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
