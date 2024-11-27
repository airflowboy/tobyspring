package tobyspring.hellospring;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SortTest {
    Sort sort;

    @BeforeEach
    void setUp() {
        sort = new Sort();
    }

    @Test
    void sort3Items() {
        List<String> list = sort.sortByLength(Arrays.asList("aa", "bbb", "c"));
        Assertions.assertThat(list).isEqualTo(Arrays.asList("c", "aa", "bbb"));
    }

    @Test
    void sort() {
        List<String> list = sort.sortByLength(Arrays.asList("aa", "b"));
        Assertions.assertThat(list).isEqualTo(Arrays.asList("b", "aa"));
    }

    @Test
    void sortAlreadySorted() {
        List<String> list = sort.sortByLength(Arrays.asList("a", "bb", "ccc"));
        Assertions.assertThat(list).isEqualTo(Arrays.asList("a", "bb", "ccc"));
    }
}