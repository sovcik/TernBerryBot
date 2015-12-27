package webcam;

/**
 * Created by Jozef on 27.12.2015.
 * Original source code published by Robert McMurray on June 12, 2015
 * http://blogs.msdn.com/b/robert_mcmurray/archive/2015/06/12/simple-java-wrapper-class-for-raspistill-on-the-raspberry-pi-2.aspx
 */

// This class is a very simple Java wrapper for the raspistill executable,
// which makes it easier to take photos from a Java application. Note that
// there are considerably more parameters available for raspistill which
// could be added to this class. (e.g. Shutter Speed, ISO, AWB, etc.)

public class RaspiStill
{
    // Define the path to the raspistill executable.
    private String _raspistillPath = "/opt/vc/bin/raspistill";
    // Define the amount of time that the camera will use to take a photo.
    private int _picTimeout = 5000;
    // Define the image quality.
    private int _picQuality = 100;

    // Specify a default image width.
    private int _picWidth = 2592;
    // Specify a default image height.
    private int _picHeight = 1944;
    // Specify a default image name.
    private String _picName = "tern-program.jpg";
    // Specify a default image encoding.
    private String _picType = "jpg";

    private String _outPath = "";

    // Default class constructor.
    public void RaspiStill(String rsPath, String outPath)
    {
        _raspistillPath = rsPath;
        _outPath = outPath + "/";

    }

    // Default method to take a photo using the private values for name/width/height.
    // Note: See the overloaded methods to override the private values.
    public void TakePicture()
    {
        try
        {
            // Determine the image type based on the file extension (or use the default).
            if (_picName.indexOf('.')!=-1)
                _picType = _picName.substring(_picName.lastIndexOf('.')+1);

            // Create a new string builder with the path to raspistill.
            StringBuilder sb = new StringBuilder(_raspistillPath);

            // Add parameters for no preview and burst mode.
            sb.append(" -n -bm");
            // Configure the camera timeout.
            sb.append(" -t " + _picTimeout);
            // Configure the picture width.
            sb.append(" -w " + _picWidth);
            // Configure the picture height.
            sb.append(" -h " + _picHeight);
            // Configure the picture quality.
            sb.append(" -q " + _picQuality);
            // Specify the image type.
            sb.append(" -e " + _picType);
            // Specify the name of the image.
            sb.append(" -o " + _outPath + _picName);

            // Invoke raspistill to take the photo.
            Runtime.getRuntime().exec(sb.toString());
            // Pause to allow the camera time to take the photo.
            Thread.sleep(_picTimeout);
        }
        catch (Exception e)
        {
            // Exit the application with the exception's hash code.
            System.exit(e.hashCode());
        }
    }

    // Overloaded method to take a photo using specific values for the name/width/height.
    public void TakePicture(String name, int width, int height)
    {
        _picName = name;
        _picWidth = width;
        _picHeight = height;
        TakePicture();
    }

    // Overloaded method to take a photo using a specific value for the image name.
    public void TakePicture(String name)
    {
        TakePicture(name, _picWidth, _picHeight);
    }

    // Overloaded method to take a photo using specific values for width/height.
    public void TakePicture(int width, int height)
    {
        TakePicture(_picName, width, height);
    }
}