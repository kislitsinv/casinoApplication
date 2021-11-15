package xyz.vkislitsin.fullstack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import xyz.vkislitsin.fullstack.services.ICasinoService;

@SpringBootTest
public class CasinoServiceTests {

    private static final List<String> COMMON_RESULTS = Arrays.asList("CHERRY", "LEMON", "WATERMELON");
    private static final List<String> BINGO_RESULTS = Arrays.asList("WATERMELON", "WATERMELON", "WATERMELON");
    private static final Integer CREDITS_AMOUNT = 5;

    @Mock
    private ICasinoService casinoService;

    @Test
    public void testCreditsCountWithCommon() {
        when(casinoService.countCredits(COMMON_RESULTS, CREDITS_AMOUNT)).thenReturn(4);
        Integer credits = casinoService.countCredits(COMMON_RESULTS, CREDITS_AMOUNT);
        System.out.println(credits);
        Assertions.assertEquals(4, credits);
    }

    @Test
    public void testCreditsCountWithBingo() {
        when(casinoService.countCredits(BINGO_RESULTS, CREDITS_AMOUNT)).thenReturn(45);
        Integer credits = casinoService.countCredits(BINGO_RESULTS, CREDITS_AMOUNT);
        Assertions.assertEquals(45, credits);
    }
}
