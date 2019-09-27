package fr.delaporte.laboratoireGSB.data;

import android.util.Log;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.IProperty;
import com.raizlabs.android.dbflow.sql.migration.BaseMigration;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import fr.delaporte.laboratoireGSB.data.models.Drug;
import fr.delaporte.laboratoireGSB.data.models.Personnel;
import fr.delaporte.laboratoireGSB.data.models.Practitioner;
import fr.delaporte.laboratoireGSB.data.models.Visit;
import fr.delaporte.laboratoireGSB.helper.enums.Role;

@Database(name= LaboratoireDatabase.NAME, version= LaboratoireDatabase.VERSION)
public abstract class LaboratoireDatabase {

    public static final String NAME = "LaboratoireGSB";
    public static final int VERSION = 2;

    @Migration(version= 0, database=LaboratoireDatabase.class)
    public static class LaboratoireMigration extends BaseMigration {

        @Override
        public void migrate(DatabaseWrapper database) {

            database.execSQL("CREATE TRIGGER drugleft_insert\n" +
                    "BEFORE INSERT ON drugsleft\n" +
                    "BEGIN\n" +
                    "    \n" +
                    "\tSELECT CASE WHEN NEW.quantity <= 0 THEN\n" +
                    "\t\tRAISE(ABORT, 'Quantité invalide <= 0')\n" +
                    "\tEND;\n" +
                    "\t\n" +
                    "\tUPDATE drugs SET quantityAvailable = quantityAvailable - NEW.quantity WHERE idDrug = NEW.drug_idDrug;\n" +
                    "END");
            database.execSQL("CREATE TRIGGER drugleft_update_delete\n" +
                    "BEFORE UPDATE ON drugsleft\n" +
                    "WHEN NEW.quantity = 0\n" +
                    "BEGIN\n" +
                    "\tUPDATE drugs SET quantityAvailable = OLD.quantity WHERE idDrug = NEW.drug_idDrug;\n" +
                    "    DELETE FROM drugsleft WHERE idDrugLeft = NEW.idDrugLeft;\n" +
                    "END");
            database.execSQL("CREATE TRIGGER drugleft_update\n" +
                    "BEFORE UPDATE ON drugsleft\n" +
                    "WHEN NEW.quantity <> 0\n" +
                    "BEGIN\n" +
                    "\tUPDATE drugs SET quantityAvailable = quantityAvailable + (OLD.quantity - NEW.quantity) WHERE idDrug = NEW.drug_idDrug;\n" +
                    "END");

            Log.d("DB", "Initialiser les données");

            ArrayList<Personnel> personnels = new ArrayList<>();

            Personnel delegue = new Personnel("Miron", "Gilber", "delegue@cci.fr", "delegue");
            delegue.setRole(Role.DELEGUE);

            Personnel fabienneMedical = new Personnel("Fabienne", "Cailot", "fabienne@cci.fr", "123");
            fabienneMedical.setRole(Role.VISITEURMEDICAL);

            Personnel florenceMedical = new Personnel("Florence", "Rodrigue", "florence@cci.fr", "456");
            florenceMedical.setRole(Role.VISITEURMEDICAL);

            Personnel joseMedical = new Personnel("Jose", "Montagne", "jose@cci.fr", "789");
            joseMedical.setRole(Role.VISITEURMEDICAL);

            personnels.add(delegue);
            personnels.add(fabienneMedical);
            personnels.add(florenceMedical);
            personnels.add(joseMedical);
            FlowManager.getModelAdapter(Personnel.class).saveAll(personnels, database);


            /* JEUX D ESSAIS POUR LES PRACTICINS (5 Practiciens) */
            ArrayList<Practitioner> practitioners = new ArrayList<>();

            Practitioner gamachePrac = new Practitioner("Gamache", "Alain", "35, rue de Lille", 91800, "Brunoy");
            practitioners.add(gamachePrac);
            practitioners.add(new Practitioner("Busque", "Octave", "58, rue des Nations Unies", 47000, "Agen"));
            practitioners.add(new Practitioner("Deschenes", "Denis", "52, rue Sadi Carnot", 59130, "Lambersart"));
            practitioners.add(new Practitioner("Bourque", "Ferrau", "61, Avenue Millies Lacroix", 44700, "Orvault"));
            practitioners.add(new Practitioner("Nool", "Guillaume", "96, rue Léon Dierx", 93190, "Livry-Gargan"));
            FlowManager.getModelAdapter(Practitioner.class).saveAll(practitioners, database);


            /* JEUX D ESSAIS MEDICAMENTS(10 Médicaments */
            ArrayList<Drug> drugs = new ArrayList<>();

            drugs.add(new Drug("Doliprane", 5));
            drugs.add(new Drug("Efferalgan", 73));
            drugs.add(new Drug("Dafalgan", 32));
            drugs.add(new Drug("Levothyrox", 51));
            drugs.add(new Drug("Imodium", 1));
            drugs.add(new Drug("Kardegic", 26));
            drugs.add(new Drug("Spasfon", 8));
            drugs.add(new Drug("Isimig", 68));
            drugs.add(new Drug("Tahor", 190));
            drugs.add(new Drug("Spedifen", 325));
            FlowManager.getModelAdapter(Drug.class).saveAll(drugs, database);

            int[] hoursAvailable = {8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};
            int[] minutesAvailable = {0, 30, 45};

            ArrayList<Visit> visits = new ArrayList<>();
            Random random = new Random();

            for(int i = 0; i <= random.nextInt(100) + 60; i++){

                Personnel visiteurMedical = personnels.get(random.nextInt(personnels.size() - 1) + 1);
                Practitioner practitioner = practitioners.get(random.nextInt(practitioners.size()));

                Calendar date = Calendar.getInstance();
                date.add(Calendar.DAY_OF_YEAR, random.nextInt(20 + 1 + 20) - 20);
                date.set(Calendar.HOUR_OF_DAY, hoursAvailable[random.nextInt(hoursAvailable.length)]);
                date.set(Calendar.MINUTE, minutesAvailable[random.nextInt(minutesAvailable.length)]);

                Visit visit = new Visit(visiteurMedical, practitioner, date);
                visits.add(visit);
            }

            FlowManager.getModelAdapter(Visit.class).saveAll(visits, database);
        }
    }
}