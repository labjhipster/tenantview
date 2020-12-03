package io.github.jhipster.sample.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.jhipster.sample.web.rest.TestUtil;

public class TheLabelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TheLabel.class);
        TheLabel theLabel1 = new TheLabel();
        theLabel1.setId(1L);
        TheLabel theLabel2 = new TheLabel();
        theLabel2.setId(theLabel1.getId());
        assertThat(theLabel1).isEqualTo(theLabel2);
        theLabel2.setId(2L);
        assertThat(theLabel1).isNotEqualTo(theLabel2);
        theLabel1.setId(null);
        assertThat(theLabel1).isNotEqualTo(theLabel2);
    }
}
