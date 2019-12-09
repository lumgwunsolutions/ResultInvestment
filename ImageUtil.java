package group.lsg.resultinvestmentapp.Class;

import java.util.Date;

public class ImageUtil {
    public static String generateImageTitle(UploadImagePrefix prefix,
                                            String parentId) {
        if (parentId != null) {
            return prefix.toString() + parentId;
        }

        return prefix.toString() + new Date().getTime();
    }
}
