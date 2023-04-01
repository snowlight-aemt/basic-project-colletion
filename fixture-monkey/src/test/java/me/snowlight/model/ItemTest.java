package me.snowlight.model;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

class ItemTest {

    private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .plugin(new JakartaValidationPlugin())
            .build();

    @RepeatedTest(50)
    void sample() {
        Item sut = fixtureMonkey.giveMeBuilder(Item.class).sample();
        Assertions.assertNotNull(sut.getId());
        Assertions.assertNotNull(sut.getToken());
    }

    @RepeatedTest(50)
    void test() {
        Item sut = fixtureMonkey.giveMeBuilder(Item.class).build()
                        .sample();
        Assertions.assertNotNull(sut.getId());
        Assertions.assertNotNull(sut.getToken());
    }
}