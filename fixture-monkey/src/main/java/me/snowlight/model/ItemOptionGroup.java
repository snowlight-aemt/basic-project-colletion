package me.snowlight.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemOptionGroup {
    @NotBlank
    @Size(max = 10)
    private String groupName;
}
