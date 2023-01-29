package guru.qa;

import guru.qa.model.ModelJSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import static org.assertj.core.api.Assertions.assertThat;

public class ParseJSONJackson {

    ClassLoader cl = ParseJSONJackson.class.getClassLoader();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void parseJsonJackson() throws Exception {

        try (
                InputStream resource = cl.getResourceAsStream("exampleJson.json");
                InputStreamReader reader = new InputStreamReader(resource);
        ) {
            ModelJSON jsonObject = objectMapper.readValue(reader, ModelJSON.class);
            assertThat(jsonObject.category).isEqualTo("Supermarket");
            assertThat(jsonObject.street).isEqualTo("Krasnyj prospect");
            assertThat(jsonObject.house_number).isEqualTo(331);
            assertThat(jsonObject.phone).isEqualTo("88001234567");
            assertThat(jsonObject.open).isTrue();
            assertThat(jsonObject.food).contains("Apple", "Banana", "Orange");
            assertThat(jsonObject.not_food).contains("Shampoo", "Soap");
        }
    }
}
