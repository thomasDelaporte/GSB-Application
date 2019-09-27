package fr.delaporte.laboratoireGSB.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.HashMap;

import fr.delaporte.laboratoireGSB.data.models.Personnel;
import fr.delaporte.laboratoireGSB.data.models.Personnel_Table;
import fr.delaporte.laboratoireGSB.helper.enums.Role;

public class Session {

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    public static final String IS_LOGIN = "isLogin";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ROLE = "role";
    public static final String KEY_ID = "idPersonnel";

    /**
     * Initialisation dy système de session
     * Doit être initialiser au démérage de l'application.
     *
     * @param applicationContext
     * @param nameFile
     */
    @SuppressLint("CommitPrefEdits")
    public static void init(Context applicationContext, String nameFile) {

        pref = applicationContext.getSharedPreferences(nameFile, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * Initialisation du système de session
     * Doit être initialiser au démérage de l'application.
     *
     * @param applicationContext
     */
    public static void init(Context applicationContext){
        init(applicationContext, "fr.delaporte.account");
    }

    /**
     * Permet la connexion d'un utilisateur
     * Stock toutes les données dans le fichier "SharePref" définis
     * dans l'initialisation.
     *
     * @param tempPersonnel
     */
    public static void loginUser(Personnel tempPersonnel) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, tempPersonnel.getEmail());
        editor.putInt(KEY_ROLE, tempPersonnel.getRole().ordinal());
        editor.putInt(KEY_ID, tempPersonnel.getIdPersonnel());

        editor.commit();
    }

    /**
     * Déconnexion de l'utilisateur
     */
    public static void logoutUser(){

        editor.putBoolean(IS_LOGIN, false);
        editor.commit();
    }


    /**
     * Retourne si l'utilisateur est connecté.
     *
     * @return isLoggedIn
     */
    public static boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Retourne si l'utilisateur c'est déjà enrengistré
     * L'email est enrengistré lors des connexions de ce fait
     * si elle exiszte déjà, la méthode retourne vrai.
     *
     * @return isRembered
     */
    public static boolean isRembered() {
        return pref.getString(KEY_EMAIL, null) != null;
    }

    /**
     * Retourne les informations (email, role, id) de l'utilisateur
     * sous un format HashMap<String, Object>.
     *
     * @return userDetails
     */
    public static HashMap<String, Object> getUserDetails(){

        HashMap<String, Object> user = new HashMap<>();

        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_ROLE, pref.getInt(KEY_ROLE, Role.VISITEURMEDICAL.ordinal()));
        user.put(KEY_ID, pref.getInt(KEY_ID, 0));

        return user;
    }

    /**
     * Retourne l'id de l'utilisateur connecté.
     *
     * @return userID
     */
    public static Integer getUserID() {
        return pref.getInt(KEY_ID, 0);
    }

    /**
     * Retounr le role de l'utilisateur connecté.
     *
     * @return userRole
     */
    public static Role getUserRole() {
        return Role.values()[pref.getInt(KEY_ROLE, 0)];
    }


    /**
     * Retourne l'objet Personnel avec l'id actuellement connecté.
     *
     * @return Personnel
     */
    public static Personnel getPersonnel() {
        return SQLite.select()
                .from(Personnel.class)
                .where(Personnel_Table.idPersonnel.eq(getUserID())).querySingle();
    }
}
