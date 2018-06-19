package de.ur.aue.discuss.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ur.aue.discuss.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class DiscussionItemElement {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DiscussionItem> ITEMS = new ArrayList<DiscussionItem>();

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

    private static void addItem(DiscussionItem item) {
        ITEMS.add(item);
    }

    private static DiscussionItem createDummyItem(int id, String title, int region, ArrayList<Integer> categories) {
        return new DiscussionItem(id, title, region, categories);
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
    public static class DiscussionItem implements Parcelable {
        public int id;
        public final String title;
        public final int region;
        public  ArrayList<Integer> categories;

        public DiscussionItem(int id, String title, int region, ArrayList<Integer> categories) {
            this.id = id;
            this.title = title;
            this.region = region;
            this.categories = categories;
        }

        protected DiscussionItem(Parcel in) {
            id = in.readInt();
            title = in.readString();
            region = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(title);
            dest.writeInt(region);
            dest.writeList(categories);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<DiscussionItem> CREATOR = new Creator<DiscussionItem>() {
            @Override
            public DiscussionItem createFromParcel(Parcel in) {
                return new DiscussionItem(in);
            }

            @Override
            public DiscussionItem[] newArray(int size) {
                return new DiscussionItem[size];
            }
        };

        @Override
        public String toString() {
            return title;
        }
    }
}
