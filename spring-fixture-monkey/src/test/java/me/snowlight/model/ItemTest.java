package me.snowlight.model;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {
    private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .plugin(new JakartaValidationPlugin())
            .build();

    @DisplayName("Java Validation")
    @RepeatedTest(20)
    void sample() {
        Item sut = fixtureMonkey.giveMeBuilder(Item.class).sample();
        assertThat(sut.getId()).isNotNull();
        assertThat(sut.getToken()).isNotNull();
        assertThat(sut.getToken().length()).isBetween(20, 30);
        assertThat(sut.getOptionGroups()).hasSizeLessThanOrEqualTo(10);

        for (ItemOptionGroup group: sut.getOptionGroups()) {
            assertThat(group.getGroupName()).hasSizeLessThanOrEqualTo(10);
        }
    }

    @DisplayName("Set Null")
    @RepeatedTest(20)
    void set_null() {
        Item sut = fixtureMonkey.giveMeBuilder(Item.class)
                        .setNull("id")
                        .setNull("token")
                        .sample();
        assertThat(sut.getId()).isNull();
        assertThat(sut.getToken()).isNull();
    }

    @DisplayName("Arbitraries")
    @RepeatedTest(20)
    void set_arbitraries() {
        Item sut = fixtureMonkey.giveMeBuilder(Item.class)
                .set("id", Arbitraries.longs().between(10000L, 20000L))
                .sample();
        assertThat(sut.getId()).isBetween(10000L, 20000L);
    }

    @DisplayName("Post Condition")
    @RepeatedTest(20)
    void set_postcondition() {
        Item sut = fixtureMonkey.giveMeBuilder(Item.class)
                .setPostCondition("id", Long.class, id -> id < 20)
                .sample();
        assertThat(sut.getId()).isLessThan(20);
    }
}