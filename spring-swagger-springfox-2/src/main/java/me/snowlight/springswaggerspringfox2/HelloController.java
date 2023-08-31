package me.snowlight.springswaggerspringfox2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.xml.bind.annotation.XmlElement;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {


    @Data
    public static class Reservation {
        @JsonProperty("RSVN_NO")
        private String RSVN_NO;
        @JsonProperty("RSVN_SEQ_NO")
        private String RSVN_SEQ_NO;
        @JsonProperty("GEST_NAME")
        private String GEST_NAME;
        @JsonProperty("FIRST_NAME")
        private String FIRST_NAME;
        @JsonProperty("LAST_NAME")
        private String LAST_NAME;
        @JsonProperty("MOBILE_NO")
        private String MOBILE_NO;
        @JsonProperty("EMAIL")
        private String EMAIL;
        @JsonProperty("KIOSK_ID")
        private String KIOSK_ID;
        @JsonProperty("KIOSK_IP")
        private String KIOSK_IP;
        @JsonProperty("LANG_TYPE")
        private String LANG_TYPE;
        @JsonProperty("KIOSK_SVC_YN")
        private String KIOSK_SVC_YN;

    }

    @PostMapping("/v1/helo")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "map",
                    dataType = "Reservation",
                    paramType = "body"
            )
    })
    public String hello(@RequestBody Map<String, Object> map) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("TEST_TEST", map.get("TEST_TEST"));

        System.out.println(stringObjectHashMap.get("TEST_TEST"));

        return "Success";
    }

}
