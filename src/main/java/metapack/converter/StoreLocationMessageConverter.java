package metapack.converter;

import metapack.model.StoreLocation;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class StoreLocationMessageConverter extends AbstractHttpMessageConverter<List<StoreLocation>> {

    public static final String TR_TD = "<tr><td>";
    public static final String TD_TR = "</td></tr>";
    public static final String TD_TD = "</td><td>";
    public static final String START_HTML_BODY_TABLE_BORDER_1 = "<html><body><table border='1'>";
    public static final String END_TABLE_BODY_HTML = "</table></body></html>";

    public StoreLocationMessageConverter() {
    }

    public StoreLocationMessageConverter(MediaType supportedMediaType) {
        super(supportedMediaType);
    }

    public StoreLocationMessageConverter(MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return ArrayList.class.equals(clazz);
    }

    @Override
    protected List<StoreLocation> readInternal(Class<? extends List<StoreLocation>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }


    @Override
    protected void writeInternal(List<StoreLocation> storeLocations, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        OutputStreamWriter writer = new OutputStreamWriter(httpOutputMessage.getBody());
        writer.write(START_HTML_BODY_TABLE_BORDER_1);
        for (StoreLocation storeLocation : storeLocations) {
            writer.write(TR_TD + storeLocation.getStoreName() + TD_TD
                    + storeLocation.getAddress().getPostcode() + TD_TD
                    + storeLocation.getAddress().getCity() + TD_TD
                    + storeLocation.getAddress().getStreet() + TD_TD
                    + storeLocation.getAddress().getCoordinates().getLongitude() + TD_TD
                    + storeLocation.getAddress().getCoordinates().getLatitude() + TD_TR);
        }
        writer.write(END_TABLE_BODY_HTML);
        writer.close();
    }
}

