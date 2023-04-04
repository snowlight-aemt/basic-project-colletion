package me.snowlight.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Item {
    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 20, max = 30)
    private String token;

    @Size(max = 10)
    private List<ItemOptionGroup> optionGroups;
}
