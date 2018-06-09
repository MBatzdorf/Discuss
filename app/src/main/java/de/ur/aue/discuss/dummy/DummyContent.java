package de.ur.aue.discuss.dummy;

import android.media.Image;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ur.aue.discuss.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static int id_regionEU =  R.drawable.eu;
    private static int id_regionLocal =  R.drawable.landkreis;
    private static int id_regionGermany =  R.drawable.deutschland;
    private static int id_regionWorld =  R.drawable.welt;

    private static int id_catHealth =  R.drawable.gesundheit;
    private static int id_catFincance =  R.drawable.wirtschaft;
    private static int id_catCrime =  R.drawable.verbrechen;
    private static int id_catEnvironment =  R.drawable.umwelt;
    private static int id_catSocial =  R.drawable.gesellschaft;
    private static int id_catInfrastructure =  R.drawable.infrastruktur;
    private static int id_catWork =  R.drawable.arbeit;

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        ArrayList<Integer> tempCategories = new ArrayList<Integer>();
        tempCategories.add(id_catHealth);
        tempCategories.add(id_catWork);
        tempCategories.add(id_catSocial);
        addItem(createDummyItem(0, "Mehr Geld für Pflege!!!", id_regionGermany, tempCategories));

        ArrayList<Integer> tempCategories1 = new ArrayList<Integer>();
        tempCategories1.add(id_catFincance);
        tempCategories1.add(id_catInfrastructure);
        addItem(createDummyItem(0, "Die Minister der EU kennen sich aus...", id_regionEU, tempCategories1));

        ArrayList<Integer> tempCategories2 = new ArrayList<Integer>();
        tempCategories2.add(id_catCrime);
        tempCategories2.add(id_catSocial);
        tempCategories2.add(id_catEnvironment);
        tempCategories2.add(id_catWork);
        addItem(createDummyItem(0, "EXTREM kontoverses Thema!!!", id_regionWorld, tempCategories2));

        ArrayList<Integer> tempCategories3 = new ArrayList<Integer>();
        tempCategories3.add(id_catInfrastructure);
        tempCategories3.add(id_catWork);
        addItem(createDummyItem(0, "Feuerwehr Obertraubling braucht mehr Löschfahrzeuge", id_regionLocal, tempCategories3));

        ArrayList<Integer> tempCategories4 = new ArrayList<Integer>();
        tempCategories4.add(id_catInfrastructure);
        addItem(createDummyItem(0, "Trump hat das Internet gelöscht", id_regionWorld, tempCategories4));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position, String title, int region, ArrayList<Integer> categories) {
        return new DummyItem(String.valueOf(position), title, region, categories);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String title;
        public final int region;
        public  ArrayList<Integer> categories;

        public DummyItem(String id, String title, int region, ArrayList<Integer> categories) {
            this.id = id;
            this.title = title;
            this.region = region;
            this.categories = categories;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
