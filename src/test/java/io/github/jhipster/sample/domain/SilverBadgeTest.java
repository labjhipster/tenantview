package io.github.jhipster.sample.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.jhipster.sample.web.rest.TestUtil;

public class SilverBadgeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SilverBadge.class);
        SilverBadge silverBadge1 = new SilverBadge();
        silverBadge1.setId(1L);
        SilverBadge silverBadge2 = new SilverBadge();
        silverBadge2.setId(silverBadge1.getId());
        assertThat(silverBadge1).isEqualTo(silverBadge2);
        silverBadge2.setId(2L);
        assertThat(silverBadge1).isNotEqualTo(silverBadge2);
        silverBadge1.setId(null);
        assertThat(silverBadge1).isNotEqualTo(silverBadge2);
    }
}
