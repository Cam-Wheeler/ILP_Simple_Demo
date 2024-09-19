package ilp.tutorials.simpledemo.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Position {
    @JsonProperty("lng")
    private double lng;
    @JsonProperty("lat")
    private double lat;
}
