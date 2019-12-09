package group.lsg.resultinvestmentapp.Class;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AboutUsContent {
    public static List<AboutUsItem> ITEMS = new ArrayList<AboutUsItem>();


    public static Map<String, AboutUsItem> ITEM_MAP =
            new HashMap<String, AboutUsItem>();

    private static final int COUNT = 4;


    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createAboutUsItem(i));
            addItem(new AboutUsItem("1", "About Result Investment",
                    "http://www.resultinvestmentnigeria.com/"));
            addItem(new AboutUsItem("2", "About the Developer",
                    "http://www.maven.ng"));
            addItem(new AboutUsItem("3", "About How to use the RINA ",
                    "http://www.resultinvestmentnigeria.com/"));
        }
        private static void addItem(AboutUsItem item) {
            ITEMS.add(item);
            ITEM_MAP.put(item.id, item);
        }

        private static Announcements createReportItem(int position) {
            return new Announcements(String.valueOf(position), "Item " +
                    position, makeDetails(position));
        }


}

    public static class AboutUsItem {
        public String id;
        public String website_name;
        public String website_url;

    public AboutUsItem(String id, String website_name, String website_url) {
        this.id = id;
        this.website_name = website_name;
        this.website_url = website_url;
    }



    @Override
    public String toString() {
        return website_name;
    }

}
    }
