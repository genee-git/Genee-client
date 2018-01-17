
package resources;

import java.io.InputStream;

import com.genee.Constants;

public final class ResourceLoader {

  public static InputStream getStream(String path) throws NullPointerException {
    if (path != null && !path.contains(Constants.SYMBOL_SLASH)) {
      path = Constants.SYMBOL_SLASH + path;
    }
    return ResourceLoader.class.getResourceAsStream(path);
  }

}
