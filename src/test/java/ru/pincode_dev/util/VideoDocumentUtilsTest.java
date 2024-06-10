package ru.pincode_dev.util;

import org.junit.jupiter.api.Test;
import ru.pincode_dev.model.VideoDocument;

import static org.junit.jupiter.api.Assertions.*;

public class VideoDocumentUtilsTest {
    @Test
    public void testCreateWithoutId() {
        String textSimple = "МФЦ";
        String videoURL = "https://storage.yandexcloud.net/akhidov-ivr/22.mp4";
        String[] children = {"6659c55ebbc8ff905fd7e61a", "6659d57e67313ea32ccbef4a"};
        String[] infoChildren = null;
        boolean isSearchable = true;
        String iconURL = "https://storage.yandexcloud.net/akhidov-ivr/icon22.svg";

        VideoDocument originalDocument = new VideoDocument(
                "6659d57e67313ea32ccbef44",
                textSimple,
                videoURL,
                children,
                infoChildren,
                isSearchable,
                iconURL
        );

        VideoDocument newDocument = VideoDocumentUtils.
                createWithoutId(originalDocument);

        assertEquals(textSimple, newDocument.getTextSimple());
        assertEquals(videoURL, newDocument.getVideoURL());
        assertArrayEquals(children, newDocument.getChildren());
        assertArrayEquals(infoChildren, newDocument.getInfoChildren());
        assertEquals(isSearchable, newDocument.getIsSearchable());
        assertEquals(iconURL, newDocument.getIconURL());

        assertNull(newDocument.getId());
    }
}
