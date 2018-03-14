package assetmngt.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PagingTest {

    public static final Integer ONE = 1;
    public static final Integer TWO = 2;
    public static final Integer THREE = 2;
    public static final Integer FOUR = 2;

    @Test
    public void should_handle_empty_case() {
        Paging paging = new Paging(0, 10);

        assertThat(paging.apply(new ArrayList<Integer>())).isEmpty();
    }

    @Test
    public void should_support_basic_case() {
        List<Integer> xs = Arrays.asList(ONE, TWO, THREE, FOUR);

        assertThat(new Paging(0, 2).apply(xs)).containsExactly(ONE, TWO);
        assertThat(new Paging(1, 2).apply(xs)).containsExactly(TWO, THREE);
    }

    @Test
    public void should_support_count_greater_than_list_size() {
        List<Integer> xs = Arrays.asList(ONE, TWO, THREE, FOUR);

        assertThat(new Paging(0, 20).apply(xs)).containsExactly(ONE, TWO, THREE, FOUR);
    }

    @Test
    public void should_support_paging_outside_list() {
        List<Integer> xs = Arrays.asList(ONE, TWO, THREE, FOUR);
        Paging paging = new Paging(5, 10);

        assertThat(paging.apply(xs)).isEmpty();
    }
}