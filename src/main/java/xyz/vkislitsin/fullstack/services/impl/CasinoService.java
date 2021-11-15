package xyz.vkislitsin.fullstack.services.impl;

import java.util.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import xyz.vkislitsin.fullstack.enums.SpinComponent;
import xyz.vkislitsin.fullstack.services.ICasinoService;

@Service
@RequiredArgsConstructor
public class CasinoService implements ICasinoService {

    // some basic conf
    private static final Integer COMPONENT_COUNT = 3;
    private static final Integer NO_CHEAT_CREDITS = 39;
    private static final Integer LIGHT_CHEAT_CREDITS = 60;

    // rewards conf
    private static final Integer CHERRY_REWARD = 10;
    private static final Integer LEMON_REWARD = 20;
    private static final Integer ORANGE_REWARD = 30;
    private static final Integer WATERMELON_REWARD = 40;

    @Override
    public List<String> getSpinResults() {
        List<String> components = new ArrayList<>();
        for (int i = 0; i < COMPONENT_COUNT; i++) {
            components.add(SpinComponent.getRandom().name());
        }
        return components;
    }

    @Override
    public List<String> spinWithCredits(Integer credits) {
        double cheatLevel = 0.0;
        if (credits != null && credits > 0) {
            if (isCheatNeeded(credits)) {
                Random random = new Random();
                cheatLevel = 0.3;
                if (credits > LIGHT_CHEAT_CREDITS) {
                    cheatLevel = 0.6;
                }
                if (random.nextDouble() <= cheatLevel) {
                    List<String> dummyResult = this.getSpinResults();

                    // we may log this result here
                    System.out.println("First result was " + dummyResult + ", level is " + cheatLevel + ", re-rolling");
                }
            }
        }
        return this.getSpinResults();
    }

    @Override
    public Integer countCredits(List<String> spinResult, Integer currentCreditsAmount) {
        if (spinResult != null && !spinResult.isEmpty()) {
            if (spinResult.stream().distinct().count() <= 1) {
                switch (spinResult.get(0)) {
                    case "CHERRY": return CHERRY_REWARD;
                    case "LEMON": return LEMON_REWARD;
                    case "ORANGE": return ORANGE_REWARD;
                    case "WATERMELON": return WATERMELON_REWARD;
                    default: return 0;
                }
            }
        }
        return -1;
    }

    private boolean isCheatNeeded(Integer currentAmount) {
        return currentAmount > NO_CHEAT_CREDITS;
    }
}
