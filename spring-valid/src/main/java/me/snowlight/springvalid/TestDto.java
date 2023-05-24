package me.snowlight.springvalid;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class TestDto {
    @NotBlank(message = "name 은 필수 값입니다.")
    private String name;
}
