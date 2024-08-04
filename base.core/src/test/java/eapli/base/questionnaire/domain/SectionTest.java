package eapli.base.questionnaire.domain;

import eapli.base.questionnaire.builder.SectionBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class SectionTest {

    @Test(expected = IllegalArgumentException.class)
    public void withoutDescriptionFails() {
        SectionBuilder sectionBuilder = new SectionBuilder("1","title", "1");
        sectionBuilder.build();
    }

    @Test
    public void withDescriptionPassesTest() {
        SectionBuilder sectionBuilder = new SectionBuilder("1","title", Obligatoriness.OPTIONAL.toString());
        sectionBuilder.withSectionDescription("description").build();
    }

    @Test
    public void withDescriptionandRepeatabilityPassesTest() {
        SectionBuilder sectionBuilder = new SectionBuilder("1","title", Obligatoriness.OPTIONAL.toString());
        sectionBuilder.withSectionDescription("description").withRepeability("1").build();
    }


}