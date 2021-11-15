package xyz.vkislitsin.fullstack.services;

import java.util.List;

public interface ICasinoService {
    List<String> getSpinResults();

    List<String> spinWithCredits(Integer credits);

    Integer countCredits(List<String> spinResult, Integer currentCreditsAmount);
}
