package gui;

import java.util.AbstractMap;
import java.util.HashMap;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Cache for images requested by the app, to avoid repeating loading large
 * images multiple times. Also handles scaling images to the required size.
 *
 * @author Nick Houser
 */
public class ImageCache {

    //a hashmap for previously loaded images
    private final AbstractMap<String, Image> cache;

    /**
     * Constructor which initializes internal data structures.
     */
    public ImageCache() {
        cache = new HashMap<>();
    }

    /**
     * Request method for an ImageView which uses the cached image if available,
     * or the disk image if not. Also saves the disk image to the cache if
     * needed.
     *
     * @param id the id of the requested image
     * @param size the size to which the image should be scaled
     * @return a scaled copy of the requested image
     */
    public ImageView image(String id, double size) {
        if (!cache.containsKey(id)) {
            String fileName = id + ".png";
            String path = getClass().getClassLoader().getResource(fileName).toExternalForm();
            Image image = new Image(path);
            cache.put(id, image);
        }
        ImageView toReturn = new ImageView(cache.get(id));
        toReturn.setFitWidth(size);
        toReturn.setFitHeight(size);
        return toReturn;
    }
}
