package com.example.projectboard.api.Common.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("비즈니스 로직 - 페이지네이션")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = PaginationService.class)
class PaginationServiceTest {

    private PaginationService sut;

    public PaginationServiceTest(@Autowired PaginationService paginationService) {
        this.sut = paginationService;
    }

    @DisplayName("현재 페이지 번호와 총 페이지 수를 주면, 페이징 바 리스트를 만들어준다.")
    @MethodSource
    @ParameterizedTest(name = "[{index}] 현재페이지: {0}, 총 페이지: {1} => {2}")
    void givenCurrentPageNumberANdTotalPages_whenCalculating_thenReturnsPaginationBarNumbers(int currentPageNumber, int totalPages, List<Integer> expected) {
        // Given

        // When
        List<Integer> actual = sut.getPaginationBarNumbers(currentPageNumber, totalPages);
        // Then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> givenCurrentPageNumberANdTotalPages_whenCalculating_thenReturnsPaginationBarNumbers() {
        return Stream.of(
                arguments(0, 13, Arrays.asList(0, 1, 2, 3, 4)),
                arguments(1, 13, Arrays.asList(0, 1, 2, 3, 4)),
                arguments(2, 13, Arrays.asList(0, 1, 2, 3, 4)),
                arguments(3, 13, Arrays.asList(1, 2, 3, 4, 5)),
                arguments(4, 13, Arrays.asList(2, 3, 4, 5, 6)),
                arguments(5, 13, Arrays.asList(3, 4, 5, 6, 7)),
                arguments(6, 13, Arrays.asList(4, 5, 6, 7, 8)),
                arguments(10, 13, Arrays.asList(8, 9, 10, 11, 12)),
                arguments(11, 13, Arrays.asList(9, 10, 11, 12)),
                arguments(12, 13, Arrays.asList(10, 11, 12))
        );
    }

    @DisplayName("현재 설정되어 있는 페이지네이션 바의 길이를 알려준다.")
    @Test
    void givenNothing_whenCalling_thenReturnsCurrentBarLength() {
        // Given

        // When
        int barLength = sut.currentBarLength();

        // Then
        assertThat(barLength).isEqualTo(5);
    }
}