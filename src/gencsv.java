package Output; /**
 * Created by erinsong on 6/10/17.
 */

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;


public class gencsv {

    public static void main(String[] args) throws IOException {

        String FileName = "";
    CSVWriter writer = new CSVWriter(new FileWriter("outputfile.csv"), ',');
    // feed in your array (or convert your data to an array)

     writer.writeNext(args);
	 writer.close();

    }
}
